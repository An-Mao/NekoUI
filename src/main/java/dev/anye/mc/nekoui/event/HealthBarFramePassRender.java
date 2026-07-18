package dev.anye.mc.nekoui.event;

import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import dev.anye.core.format._FormatToString;
import dev.anye.mc.nekoui.config.health$bar.HealthBarConfig;
import dev.anye.mc.nekoui.render.MobHealthBar;
import dev.anye.mc.nekoui.render.RenderSupport;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.client.FramePassManager;
import org.joml.Quaternionf;
import org.slf4j.Logger;

public class HealthBarFramePassRender implements FramePassManager.PassDefinition {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Minecraft minecraft;
	private DeltaTracker deltaTracker;

	private final SubmitNodeStorage submitNodeStorage = new SubmitNodeStorage();

    public HealthBarFramePassRender(){
        minecraft = Minecraft.getInstance();
    }
    @Override
    public void extracts(LevelTargetBundle bundle, FramePass pass, DeltaTracker deltaTracker) {
        bundle.main = pass.readsAndWrites(bundle.main);
		this.deltaTracker = deltaTracker;
    }
    @Override
    public void executes(LevelRenderState state) {
        if (HealthBarConfig.I.getData().enable()) {
			if (minecraft.level == null || minecraft.player == null) return;
			LevelRenderer levelRenderer = minecraft.levelRenderer;
			EntityRenderDispatcher entityRenderDispatcher = levelRenderer.entityRenderDispatcher();
			PoseStack pose = new PoseStack();
			pose.pushPose();
			//pose.translate(minecraft.gameRenderer.mainCamera().position().multiply(-1,-1,-1));
			minecraft.level.getEntities(null, minecraft.player.getBoundingBox().inflate(HealthBarConfig.I.getData().renderDistance())).forEach(entity -> {
				if (entity instanceof LivingEntity livingEntity) {
					int pl = getPackedLightCoords(livingEntity, 0);
					healthBar(livingEntity, pose);
					entityRenderDispatcher.submit(entityRenderDispatcher.extractEntity(entity, pl), state.cameraRenderState, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), pose, submitNodeStorage);
				}
			});
			pose.popPose();
			//levelRenderer.featureRenderDispatcher().prepareFrame(submitNodeStorage);
			/*
			StagedVertexBuffer stagedVertexBuffer = levelRenderer.renderBuffers().stagedVertexBuffer();
			//VertexConsumer bufSource = stagedVertexBuffer.getVertexBuilder(stagedVertexBuffer.appendDraw(DefaultVertexFormat.POSITION_COLOR, PrimitiveTopology.QUADS));
			ProfilerFiller profiler = Profiler.get();
			profiler.push("prepare");
			prepare();
			profiler.popPush("upload");
			stagedVertexBuffer.upload();
            //MultiBufferSource.BufferSource bufSource = levelRenderer.bufferSource();
			profiler.popPush("draw");
            PoseStack pose = new PoseStack();
            pose.pushPose();
            //pose.translate(minecraft.gameRenderer.getMainCamera().position().multiply(-1,-1,-1));
            minecraft.level.getEntities(null, minecraft.player.getBoundingBox().inflate(HealthBarConfig.I.getData().renderDistance())).forEach(entity -> healthBar(entity,pose,stagedVertexBuffer));
            pose.popPose();
			profiler.popPush("endFrame");
			stagedVertexBuffer.endDraw();
			stagedVertexBuffer.endFrame();

			 */
        }
    }

	private void prepare(){

	}
	private void healthBar(LivingEntity livingEntity,PoseStack poseStack) {
		if (HealthBarConfig.I.getData().renderOnlyView() && MobHealthBar.checkView(minecraft, livingEntity)) {
			Quaternionf cameraq = minecraft.getEntityRenderDispatcher().camera.rotation();//.cameraOrientation();
			if (HealthBarConfig.I.getData().renderHealthBar()) {
				float y = livingEntity.getBbHeight() + HealthBarConfig.I.getData().renderTop();
				int h = Math.max((int) (livingEntity.getHealth() / livingEntity.getMaxHealth() * 128), 0);
				poseStack.pushPose();
				poseStack.translate(livingEntity.position());
				poseStack.translate(0, y, 0);
				poseStack.mulPose(cameraq);
				poseStack.mulPose(Axis.YP.rotationDegrees(180));
				poseStack.scale(-0.0125F, -0.005F, 0.0125F);
				int pl = getPackedLightCoords(livingEntity, 0);


				submitNodeStorage.submitCustomGeometry(poseStack, MobHealthBar.HealthBarBorderRenderType, (pose, buffer) -> {
					RenderSupport.image(buffer, pose.pose(), -64, 0,
							0, 0,
							128, 16,
							128, 16,
							0, pl);
				});

				//VertexConsumer consumer = stagedVertexBuffer.getVertexBuilder(stagedVertexBuffer.appendDraw(MobHealthBar.HealthBarBorderRenderType.format(),PrimitiveTopology.QUADS));

				submitNodeStorage.submitCustomGeometry(poseStack, MobHealthBar.HealthBarFillRenderType, (pose, buffer) -> {
					RenderSupport.image(buffer, pose.pose(), -64, 0,
							0, 0,
							Math.min(h, 128), 16,
							128, 16,
							0, pl);//renderState.lightCoords
				});
					/*
					consumer = stagedVertexBuffer.getVertexBuilder(stagedVertexBuffer.appendDraw(MobHealthBar.HealthBarFillRenderType.format(),PrimitiveTopology.QUADS));
					RenderSupport.image(consumer, pose.last().pose(), -64, 0,
							0, 0,
							Math.min(h, 128), 16,
							128, 16,
							0, pl);//renderState.lightCoords

					 */
				if (HealthBarConfig.I.getData().renderHealthBarText()) {
					String txt = _FormatToString.numberToString(livingEntity.getHealth()) + "/" + _FormatToString.numberToString(livingEntity.getMaxHealth());

					minecraft.font.prepareText(txt, -(float) minecraft.font.width(txt) / 2, 9 - (float) minecraft.font.lineHeight / 2, HealthBarConfig.I.getData().tempColor(), false, 0);
					// pose.last().pose(), Font.DisplayMode.NORMAL, 0, pl
					//renderState.lightCoords
				}

				//profiler.popPush("endFrame");
				//stagedVertexBuffer.endDraw();
				//stagedVertexBuffer.endFrame();
				//stagedVertexBuffer.endBatch();
				poseStack.popPose();
			}
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
