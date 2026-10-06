package dev.anye.mc.nekoui.event;

import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import dev.anye.core.format._FormatToString;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.health_bar.HealthBarConfig;
import dev.anye.mc.nekoui.render.MobHealthBar;
import dev.anye.mc.nekoui.render.RenderSupport;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.client.FramePassManager;
import org.joml.Quaternionf;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class HealthBarFramePassRender implements FramePassManager.PassDefinition {
    private static final Logger LOGGER = LogUtils.getLogger();

	public static final Identifier HealthBarBorder = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/mob/hb_b.png");
	public static final Identifier HealthBarFill = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/mob/hb_f.png");
	public static final RenderType HealthBarBorderRenderType = RenderTypes.text(HealthBarBorder);
	public static final RenderType HealthBarFillRenderType = RenderTypes.text(HealthBarFill);

	private final Minecraft minecraft;
	private LevelRenderState deltaTracker;

	SubmitNodeStorage submitNodeStorage = new SubmitNodeStorage();
	private List<LivingEntity> entities = new ArrayList<>();
	final FeatureRenderDispatcher featureRenderDispatcher;

    public HealthBarFramePassRender(){
        minecraft = Minecraft.getInstance();
		this.featureRenderDispatcher = new FeatureRenderDispatcher(new RenderBuffers(32), minecraft.getModelManager(), minecraft.getAtlasManager(), minecraft.font, minecraft.gameRenderer.gameRenderState());
    }

    @Override
    public void extracts(LevelTargetBundle bundle, FramePass pass, LevelRenderState deltaTracker) {
		bundle.main = pass.readsAndWrites(bundle.main);
		this.deltaTracker = deltaTracker;

		if (!HealthBarConfig.I.data().enable()) return;
		if (minecraft.level == null || minecraft.player == null) return;
		entities = minecraft.level.getEntitiesOfClass(LivingEntity.class, minecraft.player.getBoundingBox().inflate(HealthBarConfig.I.data().renderDistance()), Entity::isAlive);

	}
    @Override
    public void executes(LevelRenderState state) {
		PoseStack poseStack = new PoseStack();
		poseStack.pushPose();
		poseStack.translate(minecraft.gameRenderer.mainCamera().position().multiply(-1,-1,-1));

		entities.forEach(livingEntity -> healthBar(livingEntity,poseStack,submitNodeStorage));
		poseStack.popPose();

		try (
				FeatureRenderDispatcher.PreparedFrame frame = this.featureRenderDispatcher.prepareFrame(submitNodeStorage);
				RenderPass renderPass = RenderSystem.getDevice()
						.createCommandEncoder()
						.createRenderPass(
								() -> "health bar",
								minecraft.gameRenderer.mainRenderTarget().getColorTextureView(),
								Optional.empty(),
								minecraft.gameRenderer.mainRenderTarget().getDepthTextureView(),
								OptionalDouble.empty()
						)
		) {
			RenderSystem.bindDefaultUniforms(renderPass);
			FeatureRenderDispatcher.renderAllFeatures(renderPass, frame);
		}
		featureRenderDispatcher.close();
	}


	private void healthBar(LivingEntity livingEntity,PoseStack poseStack,SubmitNodeStorage submitNodeStorage) {

		if (HealthBarConfig.I.data().renderOnlyView() && !MobHealthBar.checkView(minecraft, livingEntity)) return;

		Quaternionf camera = minecraft.getEntityRenderDispatcher().camera.rotation();//.cameraOrientation();
		if (HealthBarConfig.I.data().renderHealthBar()) {
			float y = livingEntity.getBbHeight() + HealthBarConfig.I.data().renderTop();
			int h = Math.max((int) (livingEntity.getHealth() / livingEntity.getMaxHealth() * 128), 0);
			poseStack.pushPose();
			poseStack.translate(livingEntity.position());
			poseStack.translate(0, y, 0);
			poseStack.rotate(camera);
			poseStack.rotate(Axis.YP.rotationDegrees(180));
			poseStack.scale(-0.0125F, -0.005F, 0.0125F);
			int pl = getPackedLightCoords(livingEntity, 0);
			submitNodeStorage.submitCustomGeometry(poseStack, MobHealthBar.HealthBarBorderRenderType, (pose, buffer) ->
					RenderSupport.image(buffer, pose.pose(), -64, 0,
							0, 0,
							128, 16,
							128, 16,
							0, pl));
			submitNodeStorage.submitCustomGeometry(poseStack, MobHealthBar.HealthBarFillRenderType, (pose, buffer) ->
					RenderSupport.image(buffer, pose.pose(), -64, 0,
							0, 0,
							Math.min(h, 128), 16,
							128, 16,
							0, pl));
			if (HealthBarConfig.I.data().renderHealthBarText()) {
				String txt = _FormatToString.numberToString(livingEntity.getHealth()) + "/" + _FormatToString.numberToString(livingEntity.getMaxHealth());
				minecraft.font.prepareText(txt, -(float) minecraft.font.width(txt) / 2, 9 - (float) minecraft.font.lineHeight / 2, HealthBarConfig.I.data().tempColor(), false, 0);
			}
			poseStack.popPose();
		}
	}


    public final int getPackedLightCoords(Entity entity, float pPartialTicks) {
        BlockPos blockpos = BlockPos.containing(entity.getLightProbePosition(pPartialTicks));
        return LightCoordsUtil.pack(this.getBlockLightLevel(entity, blockpos), this.getSkyLightLevel(entity, blockpos));
    }

    protected int getBlockLightLevel(Entity pEntity, BlockPos pPos) {
        return pEntity.isOnFire() ? 15 : pEntity.level().getBrightness(LightLayer.BLOCK, pPos);
    }
    protected int getSkyLightLevel(Entity pEntity, BlockPos pPos) {
        return pEntity.level().getBrightness(LightLayer.SKY, pPos);
    }
}
