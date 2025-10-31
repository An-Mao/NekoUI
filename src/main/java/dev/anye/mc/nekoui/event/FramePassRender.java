package dev.anye.mc.nekoui.event;

import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import dev.anye.core.format._FormatToString;
import dev.anye.mc.nekoui.config.health$bar.HealthBarConfig;
import dev.anye.mc.nekoui.render.MobHealthBar;
import dev.anye.mc.nekoui.render.RenderSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.FramePassManager;
import org.joml.Quaternionf;
import org.slf4j.Logger;

public class FramePassRender implements FramePassManager.PassDefinition {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Minecraft minecraft;
    public FramePassRender(){
        minecraft = Minecraft.getInstance();
    }
    @Override
    public void targets(LevelTargetBundle bundle, FramePass pass) {
        bundle.main = pass.readsAndWrites(bundle.main);
    }
    @Override
    public void executes() {
        if (HealthBarConfig.I.getDatas().enable) {
            RenderBuffers buffers = minecraft.renderBuffers();
            MultiBufferSource.BufferSource bufSource = buffers.bufferSource();
            PoseStack pose = new PoseStack();
            pose.pushPose();
            if (minecraft.level == null || minecraft.player == null) return;
            minecraft.level.getEntities(null, minecraft.player.getBoundingBox().inflate(HealthBarConfig.I.getDatas().renderDistance)).forEach(entity -> {
                if (entity instanceof LivingEntity livingEntity) {
                    if (HealthBarConfig.I.getDatas().renderOnlyView && MobHealthBar.checkView(minecraft, livingEntity)) {
                        Quaternionf cameraq = minecraft.getEntityRenderDispatcher().camera.rotation();//.cameraOrientation();
                        if (HealthBarConfig.I.getDatas().renderHealthBar) {
                            float y = livingEntity.getBbHeight() + HealthBarConfig.I.getDatas().renderTop;
                            int h = Math.max((int) (livingEntity.getHealth() / livingEntity.getMaxHealth() * 121), 0);
                            pose.pushPose();
                            pose.translate(livingEntity.position());
                            pose.translate(0, y, 0);
                            pose.mulPose(cameraq);
                            pose.mulPose(Axis.YP.rotationDegrees(180));
                            pose.scale(-0.0125F, -0.005F, 0.0125F);
                            VertexConsumer consumer = bufSource.getBuffer(MobHealthBar.HBRT);
                            RenderSupport.image(consumer, pose.last().pose(), -64, 0,
                                    0, 0,
                                    128, 32,
                                    128, 128,
                                    0, 15);
                            RenderSupport.image(consumer, pose.last().pose(), -64, 0,
                                    0, 32,
                                    Math.min(h, 121), 32,
                                    128, 128,
                                    0, 15);//renderState.lightCoords
                            bufSource.endBatch();
                            if (HealthBarConfig.I.getDatas().renderHealthBarText) {
                                String txt = _FormatToString.numberToString(livingEntity.getHealth()) + "/" + _FormatToString.numberToString(livingEntity.getMaxHealth());
                                minecraft.font.drawInBatch(txt, -(float) minecraft.font.width(txt) / 2, 16 - (float) minecraft.font.lineHeight / 2, 0x0000000, false, pose.last().pose(), minecraft.renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 15);//renderState.lightCoords
                            }
                            pose.popPose();
                        }
                    }
                }
            });
            pose.popPose();
        }
    }
}
