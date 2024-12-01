package nws.mc.nekoui.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ClientHooks;
import nws.dev.core.format._FormatToString;
import nws.dev.core.math._Math;
import nws.mc.cores.render.DrawImage;
import nws.mc.nekoui.NekoUI;
import nws.mc.nekoui.config.health$bar.HealthBarConfig;
import org.joml.Quaternionf;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

@OnlyIn(Dist.CLIENT)
public class MobHealthBar {
    private static final ResourceLocation HealthBar = ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/hud/mob/health_bar.png");
    public static void render(Entity entity, PoseStack poseStack,int packedLight){
        if (HealthBarConfig.I.getDatas().enable && entity instanceof LivingEntity livingEntity) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null
                    && (HealthBarConfig.I.getDatas().renderOnlyView
                    && checkView(minecraft,livingEntity))
            ) {
                if (livingEntity.distanceTo(minecraft.player) > HealthBarConfig.I.getDatas().renderDistance)return;
                Quaternionf camera = minecraft.getEntityRenderDispatcher().cameraOrientation();
                if (HealthBarConfig.I.getDatas().renderHealthBar) {
                    float y = livingEntity.getBbHeight() + HealthBarConfig.I.getDatas().renderTop;
                    int h = Math.max((int) (livingEntity.getHealth() / livingEntity.getMaxHealth() * 121), 0);
                    poseStack.pushPose();
                    poseStack.translate(0, y, 0);
                    poseStack.mulPose(camera);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.scale(-0.0125F, -0.005F, 0.0125F);
                    DrawImage.blit(poseStack, HealthBar, -64, 0, 0, 0, 0, 128, 32, 128, 128);
                    DrawImage.blit(poseStack, HealthBar, -64, 0, 0, 0, 32, Math.min(h, 121), 32, 128, 128);
                    if (HealthBarConfig.I.getDatas().renderHealthBarText) {
                        String txt = _FormatToString.numberToString(livingEntity.getHealth()) + "/" + _FormatToString.numberToString(livingEntity.getMaxHealth());
                        minecraft.font.drawInBatch(txt, -(float) minecraft.font.width(txt) / 2, 16 - (float) minecraft.font.lineHeight / 2, 0x0000000, false, poseStack.last().pose(), minecraft.renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, packedLight);
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
                    draw(livingEntity, poseStack, minecraft, packedLight);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    poseStack.popPose();
                }
            }
        }

    }
    public static boolean checkView(Minecraft minecraft, LivingEntity entity){
        if (minecraft.player != null) {
            return ClientHooks.isNameplateInRenderDistance(entity,minecraft.getEntityRenderDispatcher().distanceToSqr(entity)) && minecraft.player.hasLineOfSight(entity) && entity == minecraft.getEntityRenderDispatcher().crosshairPickEntity;
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
    private static void draw(LivingEntity entity, PoseStack poseStack,Minecraft minecraft,int packedLight){
        Collection<MobEffectInstance> effects = entity.getActiveEffects();
        if (effects.isEmpty()){
            return;
        }
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        int[] i = new int[]{0};
        ArrayList<Point2D.Double> pointPoss = UniformCircleDistribution.distributePoints(entity.getBbWidth()* 40, effects.size(),getAngle());
        effects.forEach(mobEffectInstance -> {
            if (HealthBarConfig.I.getDatas().effectRenderImage) {
                Point2D.Double point = pointPoss.get(i[0]);
                poseStack.pushPose();
                poseStack.translate(point.x - 16, 0, point.y);
                ResourceLocation icon = getIcon(mobEffectInstance);
                RenderSystem.setShaderTexture(0, icon);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                DrawImage.blit(poseStack, icon, 0, 0, 0, 0, 0, 16, 16, 16, 16);
            }else {
                MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
                minecraft.font.drawInBatch(getName(mobEffectInstance), 0, 0, -1, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, packedLight);
            }
            poseStack.popPose();
            i[0]++;
        });
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
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
