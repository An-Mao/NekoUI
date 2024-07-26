package anmao.mc.nekoui.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;

public class GG extends GuiGraphics {
    public GG() {
        super(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
    }
    public GG(Minecraft minecraft){
        super(minecraft, minecraft.renderBuffers().bufferSource());
    }
    public GG(MultiBufferSource.BufferSource pBufferSource) {
        super(Minecraft.getInstance(), pBufferSource);
    }
    public GG(PoseStack poseStack){
        this();
        mulPose(poseStack);
    }
    public GG(Minecraft minecraft,PoseStack poseStack){
        this(minecraft);
        mulPose(poseStack);
    }
    public GG(MultiBufferSource.BufferSource pBufferSource,PoseStack poseStack){
        this(pBufferSource);
        mulPose(poseStack);
    }
    public void mulPose(PoseStack pose){
        this.pose().mulPose(pose.last().pose());
    }
    public void pushPose(){
        this.pose().pushPose();
    }
    public void popPose(){
        this.pose().popPose();
    }













    public static GG create(){
        return new GG();
    }
    public static GG createWithPose(PoseStack pose){
        return new GG(pose);
    }
}
