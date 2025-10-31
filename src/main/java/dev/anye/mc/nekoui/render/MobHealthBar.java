package dev.anye.mc.nekoui.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.anye.core.format._FormatToString;
import dev.anye.core.math._Math;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.health$bar.HealthBarConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import org.joml.Quaternionf;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

public class MobHealthBar {
    private static final ResourceLocation HealthBar = ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/mob/health_bar.png");
    public static final RenderType HBRT = RenderType.text(HealthBar);






    public static void render(LivingEntity livingEntity, LivingEntityRenderState renderState, SubmitNodeCollector submitNodeCollector, PoseStack poseStack){
        if (HealthBarConfig.I.getDatas().enable) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null && (HealthBarConfig.I.getDatas().renderOnlyView && checkView(minecraft, livingEntity))) {
                if (livingEntity.distanceTo(minecraft.player) > HealthBarConfig.I.getDatas().renderDistance) return;
                Quaternionf camera = minecraft.getEntityRenderDispatcher().camera.rotation();//.cameraOrientation();
                if (HealthBarConfig.I.getDatas().renderHealthBar) {
                    float y = livingEntity.getBbHeight() + HealthBarConfig.I.getDatas().renderTop;
                    int h = Math.max((int) (livingEntity.getHealth() / livingEntity.getMaxHealth() * 121), 0);
                    poseStack.pushPose();
                    poseStack.translate(0, y, 0);
                    poseStack.mulPose(camera);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.scale(-0.0125F, -0.005F, 0.0125F);
                    submitNodeCollector.submitCustomGeometry(poseStack, HBRT, (pose, vertexConsumer) -> {
                        RenderSupport.image(vertexConsumer, pose.pose(), -64, 0,
                                0, 0,
                                128, 32,
                                128, 128,
                                0, renderState.lightCoords);
                        RenderSupport.image(vertexConsumer, pose.pose(), -64, 0,
                                0, 32,
                                Math.min(h, 121), 32,
                                128, 128,
                                0, renderState.lightCoords);
                    });
                    if (HealthBarConfig.I.getDatas().renderHealthBarText) {
                        String txt = _FormatToString.numberToString(livingEntity.getHealth()) + "/" + _FormatToString.numberToString(livingEntity.getMaxHealth());
                        minecraft.font.drawInBatch(txt, -(float) minecraft.font.width(txt) / 2, 16 - (float) minecraft.font.lineHeight / 2, 0x0000000, false, poseStack.last().pose(), minecraft.renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, renderState.lightCoords);
                    }
                    poseStack.popPose();
                }
                if (HealthBarConfig.I.getDatas().renderEffect) {
                    float lY = livingEntity.getBbHeight() / 2 + 0.16f;
                    poseStack.pushPose();
                    poseStack.translate(0.0, lY, 0.0);
                    camera.x = 0;
                    camera.z = 0;
                    poseStack.mulPose(camera);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.scale(-0.025F, -0.025F, 0.025F);
                    draw(livingEntity, submitNodeCollector, poseStack, minecraft, renderState.lightCoords);
                    poseStack.popPose();
                }
            }
        }

    }
    public static boolean checkView(Minecraft minecraft, LivingEntity entity){
        if (minecraft.player != null) {
            return ForgeHooksClient.isNameplateInRenderDistance(entity,minecraft.getEntityRenderDispatcher().distanceToSqr(entity)) && minecraft.player.hasLineOfSight(entity) && entity == minecraft.getEntityRenderDispatcher().crosshairPickEntity;
        }
        return false;
    }
    public static boolean checkView(LocalPlayer localPlayer, LivingEntity entity){
        Vec3 vec3 = localPlayer.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(entity.getX() - localPlayer.getX(), entity.getEyeY() - localPlayer.getEyeY(), entity.getZ() - localPlayer.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > 1.0 - 0.025 / d0 && localPlayer.hasLineOfSight(entity);
    }


    private static double rotationAngle = 0;
    public static double getAngle(){
        if (HealthBarConfig.I.getDatas().effectImageRotationAngle == 0){
            return 0;
        }
        rotationAngle += Math.PI / HealthBarConfig.I.getDatas().effectImageRotationAngle ;
        if (rotationAngle >= _Math.TWICE_PI) {
            rotationAngle = 0.0;
        }
        return rotationAngle;
    }


    public static void draw(LivingEntity entity,SubmitNodeCollector multiBufferSource, PoseStack poseStack,Minecraft minecraft,int packedLight){
        Collection<MobEffectInstance> effects = entity.getActiveEffects();
        if (effects.isEmpty()){
            return;
        }
        /*
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();

         */
        GlStateManager._enableBlend();
        GlStateManager._enableDepthTest();

        int[] i = new int[]{0};
        ArrayList<Point2D.Double> pointPoss = UniformCircleDistribution.distributePoints(entity.getBbWidth()* 40, effects.size(),getAngle());
        effects.forEach(mobEffectInstance -> {
            if (HealthBarConfig.I.getDatas().effectRenderImage) {
                Point2D.Double point = pointPoss.get(i[0]);
                poseStack.pushPose();
                poseStack.translate(point.x - 16, 0, point.y);
                ResourceLocation icon = getIcon(mobEffectInstance);
                //RenderSystem.setShaderTexture(0, icon);
                /*
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                 */
                //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                multiBufferSource.submitCustomGeometry(poseStack,RenderType.text(icon),(pose,vertexConsumer)-> RenderSupport.image(vertexConsumer,poseStack.last().pose(), 0, 0,
                        0, 0,
                        16, 16,
                        16, 16,
                        0,packedLight));


                //GuiGraphics guiGraphics = new GuiGraphics(minecraft,new GuiRenderState());
                //guiGraphics.pose().mul(poseStack.last().pose());
            }else {
                MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
                minecraft.font.drawInBatch(getName(mobEffectInstance), 0, 0, -0xff000000, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, packedLight);
            }
            poseStack.popPose();
            i[0]++;
        });
        GlStateManager._disableBlend();
        GlStateManager._disableDepthTest();
        //RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
    }
    public static void draw(LivingEntity entity,MultiBufferSource multiBufferSource, PoseStack poseStack,Minecraft minecraft,int packedLight){
        Collection<MobEffectInstance> effects = entity.getActiveEffects();
        if (effects.isEmpty()){
            return;
        }
        /*
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();

         */
        GlStateManager._enableBlend();
        GlStateManager._enableDepthTest();

        int[] i = new int[]{0};
        ArrayList<Point2D.Double> pointPoss = UniformCircleDistribution.distributePoints(entity.getBbWidth()* 40, effects.size(),getAngle());
        effects.forEach(mobEffectInstance -> {
            if (HealthBarConfig.I.getDatas().effectRenderImage) {
                Point2D.Double point = pointPoss.get(i[0]);
                poseStack.pushPose();
                poseStack.translate(point.x - 16, 0, point.y);
                ResourceLocation icon = getIcon(mobEffectInstance);
                //RenderSystem.setShaderTexture(0, icon);
                /*
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                 */
                //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.text(icon));

                //GuiGraphics guiGraphics = new GuiGraphics(minecraft,new GuiRenderState());
                //guiGraphics.pose().mul(poseStack.last().pose());
                RenderSupport.image(vertexConsumer,poseStack.last().pose(), 0, 0,
                        0, 0,
                        16, 16,
                        16, 16,
                        0,packedLight);
            }else {
                MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
                minecraft.font.drawInBatch(getName(mobEffectInstance), 0, 0, -0xff000000, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, packedLight);
            }
            poseStack.popPose();
            i[0]++;
        });
        GlStateManager._disableBlend();
        GlStateManager._disableDepthTest();
        //RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
    }
    public static ResourceLocation getReg(MobEffectInstance m){return BuiltInRegistries.MOB_EFFECT.getKey( m.getEffect().value());}
    public static ResourceLocation getIcon(MobEffectInstance m){
        return getIcon(getReg(m));
    }
    public static ResourceLocation getIcon(ResourceLocation res){
        return ResourceLocation.tryBuild(res.getNamespace(),"textures/mob_effect/"+res.getPath()+".png");
    }
    public static Component getName(ResourceLocation res){
        return Component.translatable("effect."+res.getNamespace()+"."+res.getPath());
    }
    public static Component getName(MobEffectInstance effectInstance){
        return getName(getReg(effectInstance));
    }
}
