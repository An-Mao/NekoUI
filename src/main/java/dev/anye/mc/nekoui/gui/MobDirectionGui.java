package dev.anye.mc.nekoui.gui;

import com.mojang.blaze3d.textures.GpuTextureView;
import dev.anye.core.color._ColorSupport;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.render.GuiGraphicsX;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.mob$direction.MobDirectionConfig;
import dev.anye.mc.nekoui.render.element.ImageRenderState;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3x2fStack;

import java.util.List;

public class MobDirectionGui extends MobDirectionConfig{
    public static final String ID = "mob_direction";
    public static final ResourceLocation RESOURCE_LOCATION =  ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,ID);
    private static final ResourceLocation MOB_POI = ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/ui/info/poi.png");
    public Vec3 v(Vec3 pos,Vec3 focus, double r){
        Vec3 v = pos.subtract(focus);
        return new Vec3(v.x,0,v.z).normalize().scale(r).add(pos);

    }


    public static void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui)return;
        Level clientLevel = minecraft.level;
        LocalPlayer localPlayer = minecraft.player;
        if (clientLevel == null || localPlayer == null || !clientLevel.isClientSide()) return;
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int startX = screenWidth / 2;
        int startY = screenHeight / 2;
        List<Entity> mobs = clientLevel.getEntities(null, localPlayer.getBoundingBox().inflate(I.getDatas().getPoiRadius()));
        Vec3 playerForward = localPlayer.getForward();
        Vec3 playerPosition = localPlayer.position();
        for (Entity mob : mobs) {
            if (mob == null || (mob instanceof LocalPlayer player && player.equals(localPlayer))) continue;
            if (
                    (
                            I.getDatas().isOnlyLivingEntity()
                                    && mob instanceof LivingEntity
                                    && I.getDatas().isNotInListMode()
                    )
                            ||
                            I.isShowPoi(mob)
            ) {

                Vec3 mobPosition = mob.position();

                double cx = mobPosition.x - playerPosition.x;
                double cz = mobPosition.z - playerPosition.z;
                double g = Math.atan2(cz, cx) - Math.atan2(playerForward.z, playerForward.x);
                g =_Math.pullBackWithPI(g);
                int ox = (int) (I.getDatas().getPoiShowRadius() * Math.cos(g));
                int oz = (int) (I.getDatas().getPoiShowRadius() * Math.sin(g));

                g += _Math.ARC_N135;
                int imageSize;
                if (I.getDatas().isDynamicDisplay()) {
                    double distance = playerPosition.distanceTo(mobPosition);
                    distance *= I.getDatas().getRatio();
                    imageSize = (int) (I.getDatas().getPoiSize() + distance);
                    imageSize = Mth.clamp(imageSize, I.getDatas().getPoiMinSize(), I.getDatas().getPoiMaxSize());
                } else {
                    imageSize = I.getDatas().getPoiSize();
                }
                Matrix3x2fStack pose = guiGraphics.pose();
                pose.pushMatrix();
                pose.translate(startX + oz, startY - ox);
                pose.translate(oz, - ox);
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
                        imageSize,getRenderColor(mob, guiGraphics));

                //drawPoint(getRenderColor(mob, guiGraphics),guiGraphics, MOB_POI, imageSize);
                pose.popMatrix();
                //guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }

        }
    }
    private static void setRenderColor(Entity entity,GuiGraphics guiGraphics){
        int[] color = _ColorSupport.extractRGBA(MobDirectionConfig.I.getEntityColor(entity)) ;
        //RenderSystem.setShaderColor(color[0],color[1],color[2],color[3]);
        //guiGraphics.setColor(color[0],color[1],color[2],color[3]);

    }
    private static int getRenderColor(Entity entity,GuiGraphics guiGraphics){
        return MobDirectionConfig.I.getEntityColor(entity) ;
    }
    private static void drawPoint(
            int color, GuiGraphics guiGraphics,ResourceLocation res,int imageSize){
        blit(color, guiGraphics, res,0,0,0,0,imageSize,imageSize,imageSize,imageSize);
    }
    private static void drawPoint(GuiGraphics guiGraphics,ResourceLocation res,int x,int y,int imageSize){
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,res,x,y,0,0,imageSize,imageSize,imageSize,imageSize);
    }
    public static void blit(
            int color,
            GuiGraphics guiGraphics,
            ResourceLocation pAtlasLocation,
            int pX,
            int pY,
            int pWidth,
            int pHeight,
            float pUOffset,
            float pVOffset,
            int pUWidth,
            int pVHeight,
            int pTextureWidth,
            int pTextureHeight
    ) {
        blit(color, guiGraphics,
                pAtlasLocation, pX, pX + pWidth, pY, pY + pHeight, 0, pUWidth, pVHeight, pUOffset, pVOffset, pTextureWidth, pTextureHeight
        );
    }

    public static void blit(
            int color,
            GuiGraphics guiGraphics,
            ResourceLocation pAtlasLocation, int pX, int pY, float pUOffset, float pVOffset, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight
    ) {
        blit(color, guiGraphics, pAtlasLocation, pX, pY, pWidth, pHeight, pUOffset, pVOffset, pWidth, pHeight, pTextureWidth, pTextureHeight);
    }

    static void blit(
            int color,
            GuiGraphics guiGraphics,
            ResourceLocation pAtlasLocation,
            int pX1,
            int pX2,
            int pY1,
            int pY2,
            int pBlitOffset,
            int pUWidth,
            int pVHeight,
            float pUOffset,
            float pVOffset,
            int pTextureWidth,
            int pTextureHeight
    ) {
        GpuTextureView gputextureview = Minecraft.getInstance().getTextureManager().getTexture(pAtlasLocation).getTextureView();
        guiGraphics.getRenderState().submitGuiElement(new ImageRenderState(RenderPipelines.GUI_TEXTURED,
                TextureSetup.singleTexture(gputextureview),
                guiGraphics.pose(),
                pAtlasLocation,
                pX1,pX2,pY1,pY2,
                pBlitOffset,
                (pUOffset + 0.0F) / (float)pTextureWidth,
                (pUOffset + (float)pUWidth) / (float)pTextureWidth,
                (pVOffset + 0.0F) / (float)pTextureHeight,
                (pVOffset + (float)pVHeight) / (float)pTextureHeight,
                color,
                GuiGraphicsX.getBounds(pX1,pY1,pTextureWidth,pTextureWidth),
                GuiGraphicsX.getBounds(pX1,pY1,pTextureWidth,pTextureWidth)
                ));
    }

}
