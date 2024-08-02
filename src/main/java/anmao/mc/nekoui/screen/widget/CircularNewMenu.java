package anmao.mc.nekoui.screen.widget;

import anmao.dev.core.array.Array3D;
import anmao.dev.core.math._Math;
import anmao.mc.amlib.render.Draw;
import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.amlib.screen.widget.RenderWidgetCore;
import anmao.mc.nekoui.config.page.PageData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class CircularNewMenu extends RenderWidgetCore<CircularNewMenu> {
    protected final Array3D<String, PageData,Map<String,DT_ListBoxData>> pageDatas;
    protected CircularNewMenu.FlipMode flipMode;
    protected int sectors;
    protected double fanAngle , fanArc , halfFanArc;
    protected int index = -1;
    protected int nowPage = 0;
    protected int maxPage;
    protected int fanTextInnerSpace;
    protected int innerRadius, outerRadius;
    public CircularNewMenu(
            int x,
            int y,
            int w,
            int h,
            Component message,
            Array3D<String, PageData,Map<String,DT_ListBoxData>> pageDatas
    ) {
        super(x, y, w, h, message);
        this.pageDatas = pageDatas;
        maxPage = pageDatas.getSize();
        setNowPage(0);



        setFlipMode(CircularNewMenu.FlipMode.tire);
        setFanTextInnerSpace(10);
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int newPage) {
        if (newPage < 0){
            this.nowPage = 0;
        }else if (newPage >= maxPage){
            this.nowPage = maxPage - 1;
        }else {
            this.nowPage = newPage;
        }
        setSectors(getNowPageData().getProjectNumber());
        this.innerRadius = getNowPageData().getInnerRadius();
        this.outerRadius = getNowPageData().getOuterRadius();
    }
    public PageData getNowPageData() {
        return pageDatas.getA(getNowPage());
    }
    public Map<String, DT_ListBoxData> getNowListBoxData() {
        return pageDatas.getB(getNowPage());
    }

    public void changePage(boolean add){
        if (add){
            if (nowPage < maxPage - 1){
                setNowPage(nowPage + 1);
            }else if (nowPage == maxPage - 1){
                setNowPage(0);
            }
        }else {
            if (nowPage > 0){
                setNowPage(nowPage - 1);
            }else if (nowPage == 0){
                setNowPage(maxPage - 1);
            }

        }
    }

    public void setFanTextInnerSpace(int fanTextInnerSpace) {
        this.fanTextInnerSpace = fanTextInnerSpace;
    }

    public void setFlipMode(CircularNewMenu.FlipMode flipMode) {
        this.flipMode = flipMode;
    }


    public void setFanArc(double fanArc) {
        this.fanArc = fanArc;
        setHalfFanArc(fanArc / 2d);
    }

    public void setHalfFanArc(double halfFanArc) {
        this.halfFanArc = halfFanArc;
    }


    public int getFanTextInnerSpace() {
        return Math.max(fanTextInnerSpace,innerRadius);
    }

    public void setFanAngle(double fanAngle) {
        this.fanAngle = fanAngle;
    }


    public void setSectors(int sectors) {
        this.sectors = sectors;
        setFanAngle(360d / sectors);
        setFanArc(_Math.TWICE_PI / sectors);
    }

    public DT_ListBoxData getData(){
        return getData(index);
    }
    public DT_ListBoxData getData(int index){
        if (isValidIndex(index)){
            return getNowListBoxData().get(String.valueOf(index));
        }
        return null;
    }
    public boolean isValidIndex(){
        return isValidIndex(index);
    }
    public boolean isValidIndex(int index){
        return index >= 0 && index < getNowListBoxData().size();
    }


    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.active && this.visible && this.flipMode == CircularNewMenu.FlipMode.button) {
            if (pButton == 0){
                changePage(false);
                return true ;
            }else if (pButton == 1){
                changePage(true);
                return true ;
            }
            return false;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
    @Override
    public void onClick(double pMouseX, double pMouseY) {
        if (flipMode == CircularNewMenu.FlipMode.tire){
            DT_ListBoxData dtListBoxData = getData();
            if (dtListBoxData != null){
                dtListBoxData.OnPress(dtListBoxData.getValue());
            }
        }
        super.onClick(pMouseX, pMouseY);
    }
    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        if (this.flipMode == CircularNewMenu.FlipMode.tire){
            changePage(!(pScrollY > 0));
            return true ;
        }
        return super.mouseScrolled(pMouseX, pMouseY, pScrollX,pScrollY);
    }




    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (visible){
            int centerX = getX() , centerY = getY();
            double angle = Math.atan2(mouseY - centerY, mouseX - centerX) + halfFanArc;
            if (angle < 0) {
                angle += _Math.TWICE_PI;
            }

            if (getNowPageData() == null) return;
            guiGraphics.drawString(this.font,getNowPageData().getTitle(), centerX - this.font.width(getNowPageData().getTitle())/2, 6, 0xFFFFFFFF, false);
            for (int i = 0; i < sectors; i++) {
                double startAngle = i * fanArc;
                double endAngle = (i + 1) * fanArc;
                PageData.ProjectData projectData = getNowPageData().getProject(String.valueOf(i));
                if (projectData == null) {
                    if (angle >= startAngle  && angle < endAngle) {
                        this.index = -1;
                    }
                    continue;
                }
                int bgc = projectData.getBackgroundNormalColor() , tc = projectData.getTextNormalColor();
                float size = 1;
                if (angle >= startAngle  && angle < endAngle) {
                    bgc = projectData.getBackgroundHighlightColor();
                    tc = projectData.getTextHighlightColor();
                    size = 1.3f;
                    this.index = i;
                }
                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();
                poseStack.translate(centerX,centerY,0);
                poseStack.mulPose(Axis.ZP.rotation((float) (startAngle)));
                Draw.drawSector(poseStack.last().pose(),innerRadius,outerRadius,-halfFanArc,halfFanArc,bgc);
                if (isValidIndex(i)) {
                    DT_ListBoxData boxData = getData(i);
                    String name = "";
                    if (boxData != null) {
                        name = boxData.getComponent().getString();
                    }
                    drawName(guiGraphics,startAngle,name,tc,size);
                }
                poseStack.popPose();
            }
        }
    }
    public void drawTextName(GuiGraphics guiGraphics,String name, int color){
        name = font.plainSubstrByWidth(name, outerRadius - getFanTextInnerSpace());
        guiGraphics.drawString(font, name, getFanTextInnerSpace(), -font.lineHeight / 2, color, false);
    }

    protected void drawName(GuiGraphics guiGraphics, double rad, String name, int color, float size) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        String[] parts = name.split("\\?");
        if (parts.length == 2){
            pose.mulPose(Axis.ZP.rotation((float) -rad));
            double r = getFanTextInnerSpace() + (outerRadius - innerRadius) / 2d;
            double x =  Math.cos(rad) * r;
            double y =  Math.sin(rad) * r;
            pose.translate(x,y,0);
            pose.scale(size,size,size);

            switch (parts[0]){
                case "slot" -> {
                    LocalPlayer player = Minecraft.getInstance().player;
                    if (player != null){
                        ItemStack item = player.getInventory().getItem(Integer.parseInt(parts[1]));
                        guiGraphics.renderItem(item,-8,-8);
                        guiGraphics.renderItemDecorations(font,item,-8,-8);
                    }
                }
                case "item" -> {
                    Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(parts[1]));
                    if (item != null){
                        guiGraphics.renderItem(new ItemStack(item),-8,-8);
                    }
                }
                case "image" -> {
                    ResourceLocation res = ResourceLocation.tryParse(parts[1]);
                    if (res != null){
                        guiGraphics.blit(res,-8,-8,0,0,0,16,16,16,16);
                    }
                }
            }
        }else {
            pose.scale(size,size,size);
            drawTextName(guiGraphics,name,color);
        }
        pose.popPose();
    }
    public enum FlipMode{
        tire,
        button
    }
}
