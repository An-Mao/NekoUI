package dev.anye.mc.nekoui.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.anye.core.format._FormatToString;
import dev.anye.core.math._Math;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.health$bar.HealthBarConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.ClientHooks;
import org.joml.Quaternionf;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

public class MobHealthBar {
    private static final Identifier HealthBar = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/mob/hb_b.png");
    public static final RenderType HBRT = RenderTypes.text(HealthBar);
    private static final Identifier HealthBar_FILL = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/mob/hb_f.png");
    public static final RenderType HBRT_FILL = RenderTypes.text(HealthBar_FILL);
    public static final ContextKey<Entity> EntityKey = new ContextKey<>(Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"entity"));
    public static final ContextKey<Float> HealthKey = new ContextKey<>(Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"entity_health_now"));
    public static final ContextKey<Float> MaxHealthKey = new ContextKey<>(Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"entity_health_max"));


    public static void render(LivingEntity livingEntity,LivingEntityRenderState renderState, LivingEntityRenderer<LivingEntity,?,?> renderer, SubmitNodeCollector submitNodeCollector,PoseStack poseStack) {
        if (HealthBarConfig.I.getDatas().enable()) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null && (HealthBarConfig.I.getDatas().renderOnlyView() && checkView(minecraft, livingEntity))) {
                if (livingEntity.distanceTo(minecraft.player) > HealthBarConfig.I.getDatas().renderDistance()) return;
                Quaternionf camera = minecraft.getEntityRenderDispatcher().camera.rotation();//.cameraOrientation();
                if (HealthBarConfig.I.getDatas().renderHealthBar()) {
                    float y = livingEntity.getBbHeight() + HealthBarConfig.I.getDatas().renderTop();
                    int h = Math.max((int) (livingEntity.getHealth() / livingEntity.getMaxHealth() * 128), 0);
                    poseStack.pushPose();
                    poseStack.translate(0, y, 0);
                    poseStack.mulPose(camera);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.scale(-0.0125F, -0.005F, 0.0125F);
                    submitNodeCollector.submitCustomGeometry(poseStack, HBRT, (pose, vertexConsumer) -> {
                        RenderSupport.image(vertexConsumer, pose.pose(), -64, 0,
                                0, 0,
                                128, 16,
                                128, 16,
                                0, renderState.lightCoords);
                    });
                    submitNodeCollector.submitCustomGeometry(poseStack, HBRT_FILL, (pose, vertexConsumer) -> {
                        RenderSupport.image(vertexConsumer, pose.pose(), -64, 0,
                                0, 0,
                                Math.min(h, 128), 16,
                                128, 16,
                                0, renderState.lightCoords);
                    });
                    if (HealthBarConfig.I.getDatas().renderHealthBarText()) {
                        String txt = _FormatToString.numberToString(livingEntity.getHealth()) + "/" + _FormatToString.numberToString(livingEntity.getMaxHealth());
                        poseStack.translate(0,1,-10);
                        submitNodeCollector.submitText(poseStack,-(float) minecraft.font.width(txt) / 2, 16 - (float) minecraft.font.lineHeight / 2, FormattedCharSequence.forward(txt, Style.EMPTY),false, Font.DisplayMode.NORMAL, renderState.lightCoords, HealthBarConfig.I.getDatas().tempColor(),0,0);
                        /*
                        minecraft.font.drawInBatch(txt, -(float) minecraft.font.width(txt) / 2, 16 - (float) minecraft.font.lineHeight / 2, 0x00000000, false, poseStack.last().pose(), minecraft.renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, renderState.lightCoords);

                         */
                    }
                    poseStack.popPose();
                }
                if (HealthBarConfig.I.getDatas().renderEffect()) {
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


    public static void render(Entity entity, EntityRenderState entityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState){
        if (HealthBarConfig.I.getDatas().enable() && entity instanceof LivingEntity livingEntity) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null && (HealthBarConfig.I.getDatas().renderOnlyView() && checkView(minecraft,livingEntity))) {
                if (livingEntity.distanceTo(minecraft.player) > HealthBarConfig.I.getDatas().renderDistance())return;
                Quaternionf camera = minecraft.getEntityRenderDispatcher().camera.rotation();//.cameraOrientation();
                if (HealthBarConfig.I.getDatas().renderHealthBar()) {
                    float y = livingEntity.getBbHeight() + HealthBarConfig.I.getDatas().renderTop();
                    int h = Math.max((int) (livingEntity.getHealth() / livingEntity.getMaxHealth() * 121), 0);
                    poseStack.pushPose();
                    poseStack.translate(0, y, 0);
                    poseStack.mulPose(camera);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.scale(-0.0125F, -0.005F, 0.0125F);
                    submitNodeCollector.submitCustomGeometry(poseStack,HBRT,(pose,vertexConsumer)->{
                        RenderSupport.image(vertexConsumer,pose.pose(), -64, 0,
                                0, 0,
                                128, 32,
                                128, 128,
                                0,entityRenderState.lightCoords);
                        RenderSupport.image(vertexConsumer,pose.pose(), -64, 0,
                                0, 32,
                                Math.min(h, 121), 32,
                                128, 128,
                                0,entityRenderState.lightCoords);
                    });
                    if (HealthBarConfig.I.getDatas().renderHealthBarText()) {
                        String txt = _FormatToString.numberToString(livingEntity.getHealth()) + "/" + _FormatToString.numberToString(livingEntity.getMaxHealth());
                        minecraft.font.drawInBatch(txt, -(float) minecraft.font.width(txt) / 2, 16 - (float) minecraft.font.lineHeight / 2, 0x0000000, false, poseStack.last().pose(), minecraft.renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, entityRenderState.lightCoords);
                    }
                    poseStack.popPose();
                }
                if (HealthBarConfig.I.getDatas().renderEffect()) {
                    float lY = livingEntity.getBbHeight() / 2 + 0.16f;
                    poseStack.pushPose();
                    poseStack.translate(0.0, lY, 0.0);
                    camera.x = 0;
                    camera.z = 0;
                    poseStack.mulPose(camera);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.scale(-0.025F, -0.025F, 0.025F);
                    draw(livingEntity,submitNodeCollector, poseStack, minecraft, entityRenderState.lightCoords);
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
        if (HealthBarConfig.I.getDatas().effectImageRotationAngle() == 0){
            return 0;
        }
        rotationAngle += Math.PI / HealthBarConfig.I.getDatas().effectImageRotationAngle() ;
        if (rotationAngle >= _Math.TWICE_PI) {
            rotationAngle = 0.0;
        }
        return rotationAngle;
    }
    private static void draw(LivingEntity entity,SubmitNodeCollector multiBufferSource, PoseStack poseStack,Minecraft minecraft,int packedLight){
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
            if (HealthBarConfig.I.getDatas().effectRenderImage()) {
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
