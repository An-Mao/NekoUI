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
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import org.joml.Quaternionf;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

public class MobHealthBar {
    private static final Identifier HealthBar_B = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/mob/hb_b.png");
    private static final Identifier HealthBar_F = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/mob/hb_f.png");

    public static final RenderType HealthBarBorderRenderType = RenderTypes.text(HealthBar_B);
    public static final RenderType HealthBarFillRenderType = RenderTypes.text(HealthBar_F);








    public static void render(LivingEntity livingEntity, LivingEntityRenderState renderState, SubmitNodeCollector submitNodeCollector, PoseStack poseStack){
        if (HealthBarConfig.I.getData().enable()) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null && (HealthBarConfig.I.getData().renderOnlyView() && checkView(minecraft, livingEntity))) {
                if (livingEntity.distanceTo(minecraft.player) > HealthBarConfig.I.getData().renderDistance()) return;
                Quaternionf camera = minecraft.getEntityRenderDispatcher().camera.rotation();//.cameraOrientation();
                if (HealthBarConfig.I.getData().renderHealthBar()) {
                    float y = livingEntity.getBbHeight() + HealthBarConfig.I.getData().renderTop();
                    int h = Math.max((int) (livingEntity.getHealth() / livingEntity.getMaxHealth() * 121), 0);
                    poseStack.pushPose();
                    poseStack.translate(0, y, 0);
                    poseStack.mulPose(camera);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.scale(-0.0125F, -0.005F, 0.0125F);
                    submitNodeCollector.submitCustomGeometry(poseStack, HealthBarBorderRenderType, (pose, vertexConsumer) -> RenderSupport.image(vertexConsumer, pose.pose(), -64, 0,
                            0, 0,
                            128, 32,
                            128, 128,
                            0, renderState.lightCoords));
                    submitNodeCollector.submitCustomGeometry(poseStack, HealthBarFillRenderType, (pose, vertexConsumer) -> RenderSupport.image(vertexConsumer, pose.pose(), -64, 0,
                            0, 32,
                            Math.min(h, 121), 32,
                            128, 128,
                            0, renderState.lightCoords));
                    if (HealthBarConfig.I.getData().renderHealthBarText()) {
                        String txt = _FormatToString.numberToString(livingEntity.getHealth()) + "/" + _FormatToString.numberToString(livingEntity.getMaxHealth());

						submitNodeCollector.submitText(poseStack, -(float) minecraft.font.width(txt) / 2, 16 - (float) minecraft.font.lineHeight / 2, FormattedCharSequence.forward(txt, Style.EMPTY), false, Font.DisplayMode.NORMAL, renderState.lightCoords, HealthBarConfig.I.getData().tempColor(), 0, 0);
                    }
                    poseStack.popPose();
                }
                if (HealthBarConfig.I.getData().renderEffect()) {
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
            return ForgeHooksClient.isNameplateInRenderDistance(entity,minecraft.getEntityRenderDispatcher().distanceToSqr(entity),entity.getAttribute(Attributes.NAME_TAG_DISTANCE).getValue()) && minecraft.player.hasLineOfSight(entity) && entity == minecraft.getEntityRenderDispatcher().crosshairPickEntity;
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
        if (HealthBarConfig.I.getData().effectImageRotationAngle() == 0){
            return 0;
        }
        rotationAngle += Math.PI / HealthBarConfig.I.getData().effectImageRotationAngle() ;
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
        //GlStateManager._enableBlend();
        GlStateManager._enableDepthTest();

        int[] i = new int[]{0};
        ArrayList<Point2D.Double> pointPoss = UniformCircleDistribution.distributePoints(entity.getBbWidth()* 40, effects.size(),getAngle());
        effects.forEach(mobEffectInstance -> {
            if (HealthBarConfig.I.getData().effectRenderImage()) {
                Point2D.Double point = pointPoss.get(i[0]);
                poseStack.pushPose();
                poseStack.translate(point.x - 16, 0, point.y);
                Identifier icon = getIcon(mobEffectInstance);
                //RenderSystem.setShaderTexture(0, icon);
                /*
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                 */
                //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                multiBufferSource.submitCustomGeometry(poseStack,RenderTypes.text(icon),(pose,vertexConsumer)-> RenderSupport.image(vertexConsumer,poseStack.last().pose(), 0, 0,
                        0, 0,
                        16, 16,
                        16, 16,
                        0,packedLight));


                //GuiGraphics guiGraphics = new GuiGraphics(minecraft,new GuiRenderState());
                //guiGraphics.pose().mul(poseStack.last().pose());
            }else {
				Component name = getName(mobEffectInstance);
				multiBufferSource.submitText(poseStack, -(float) minecraft.font.width(name) / 2, 16 - (float) minecraft.font.lineHeight / 2, FormattedCharSequence.forward(name.getString(), Style.EMPTY), false, Font.DisplayMode.NORMAL, packedLight, HealthBarConfig.I.getData().tempColor(), 0, 0);

            }
            poseStack.popPose();
            i[0]++;
        });
        //GlStateManager._disableBlend();
        GlStateManager._disableDepthTest();
        //RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
    }
    public static Identifier getReg(MobEffectInstance m){return BuiltInRegistries.MOB_EFFECT.getKey( m.getEffect().value());}
    public static Identifier getIcon(MobEffectInstance m){
        return getIcon(getReg(m));
    }
    public static Identifier getIcon(Identifier res){
        return Identifier.tryBuild(res.getNamespace(),"textures/mob_effect/"+res.getPath()+".png");
    }
    public static Component getName(Identifier res){
        return Component.translatable("effect."+res.getNamespace()+"."+res.getPath());
    }
    public static Component getName(MobEffectInstance effectInstance){
        return getName(getReg(effectInstance));
    }
}
