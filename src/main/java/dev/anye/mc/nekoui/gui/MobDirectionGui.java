package dev.anye.mc.nekoui.gui;

import dev.anye.core.color._ColorSupport;
import dev.anye.core.math._Math;
import dev.anye.core.math._MathCDT;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.mob_direction.MobDirectionConfig;
import dev.anye.mc.nekoui.config.mob_direction.MobDirectionData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3x2fStack;

import java.util.List;

public class MobDirectionGui extends MobDirectionConfig {
	public static final String ID = "mob_direction";
	public static final Identifier RESOURCE_LOCATION = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID, ID);
	private static final Identifier MOB_POI = Identifier.tryBuild(NekoUI.MOD_ID, "textures/ui/info/poi.png");

	public Vec3 v(Vec3 pos, Vec3 focus, double r) {
		Vec3 v = pos.subtract(focus);
		return new Vec3(v.x, 0, v.z).normalize().scale(r).add(pos);

	}


	public static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker partialTick) {
		I.ifPresent(mobDirectionData -> {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.gui.hud.isHidden()) return;
			Level clientLevel = minecraft.level;
			LocalPlayer localPlayer = minecraft.player;
			if (clientLevel == null || localPlayer == null || !clientLevel.isClientSide()) return;
			int screenWidth = minecraft.getWindow().getGuiScaledWidth();
			int screenHeight = minecraft.getWindow().getGuiScaledHeight();
			int startX = screenWidth / 2;
			int startY = screenHeight / 2;
			List<Entity> mobs = clientLevel.getEntities(null, localPlayer.getBoundingBox().inflate(mobDirectionData.poiRadius()));
			for (Entity mob : mobs) {
				render(mob,localPlayer,startX,startY,guiGraphics,mobDirectionData);
			}
		});

	}
	public static void render(Entity mob, LocalPlayer localPlayer,int startX, int startY, GuiGraphicsExtractor guiGraphics, MobDirectionData mobDirectionData){
		if (mob == null || (mob instanceof LocalPlayer player && player.equals(localPlayer))) return;
		if ((mobDirectionData.onlyLivingEntity() && mob instanceof LivingEntity && mobDirectionData.notInListMode())
				|| I.isShowPoi(mob)) {
			Vec3 mobPosition = mob.position();
			Vec3 playerForward = localPlayer.getForward();
			Vec3 playerPosition = localPlayer.position();

			double cx = mobPosition.x - playerPosition.x;
			double cz = mobPosition.z - playerPosition.z;
			double g = Math.atan2(cz, cx) - Math.atan2(playerForward.z, playerForward.x);
			g = _Math.pullBackWithPI(g);
			int ox = (int) (mobDirectionData.poiShowRadius() * Math.cos(g));
			int oz = (int) (mobDirectionData.poiShowRadius() * Math.sin(g));

			g += _MathCDT.ARC_N135;
			int imageSize;
			if (mobDirectionData.dynamicDisplay()) {
				double distance = playerPosition.distanceTo(mobPosition);
				distance *= mobDirectionData.ratio();
				imageSize = (int) (mobDirectionData.poiSize() + distance);
				imageSize = Mth.clamp(imageSize, mobDirectionData.poiMinSize(), mobDirectionData.poiMaxSize());
			} else {
				imageSize = mobDirectionData.poiSize();
			}
			Matrix3x2fStack pose = guiGraphics.pose();
			pose.pushMatrix();
			pose.translate(startX + oz, startY - ox);
			pose.translate(oz, -ox);
			//pose.mulPose(new Quaternionf(new AxisAngle4d(g, 0, 0, 1)));
			pose.rotate((float) g);
			guiGraphics.blit(
					RenderPipelines.GUI_TEXTURED,
					MOB_POI,
					0,
					0,
					0,
					0,//0,
					imageSize,
					imageSize,
					imageSize,
					imageSize, getRenderColor(mob, guiGraphics));

			//drawPoint(getRenderColor(mob, guiGraphics),guiGraphics, MOB_POI, imageSize);
			pose.popMatrix();
			//guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	private static void setRenderColor(Entity entity, GuiGraphicsExtractor guiGraphics) {
		int[] color = _ColorSupport.extractRGBA(MobDirectionConfig.I.getEntityColor(entity));
		//RenderSystem.setShaderColor(color[0],color[1],color[2],color[3]);
		//guiGraphics.setColor(color[0],color[1],color[2],color[3]);

	}

	private static int getRenderColor(Entity entity, GuiGraphicsExtractor guiGraphics) {
		return MobDirectionConfig.I.getEntityColor(entity);
	}

	private static void drawPoint(GuiGraphicsExtractor guiGraphics, Identifier res, int x, int y, int imageSize) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, res, x, y, 0, 0, imageSize, imageSize, imageSize, imageSize);
	}

}
