package dev.anye.mc.nekoui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.anye.core.color._ColorSupport;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.render.Draw;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat$type.MenuPageData;
import dev.anye.mc.nekoui.dat$type.MenuProjectData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
@OnlyIn(Dist.CLIENT)
public class CircularNewMenu extends RenderWidgetCore<CircularNewMenu> {
    protected final List<RenderPageData> renderPageData;
    protected FlipMode flipMode;
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
            List<RenderPageData> renderPageData
    ) {
        super(x, y, w, h, message);
        this.renderPageData = renderPageData;
        maxPage = renderPageData.size();
        setNowPage(0);



        setFlipMode(FlipMode.tire);
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
        setSectors(getNowPageData().projectNumber());
        this.innerRadius = getNowPageData().innerRadius();
        this.outerRadius = getNowPageData().outerRadius();
    }


    public MenuPageData getNowPageData() {
        return renderPageData.get(getNowPage()).pageData();
    }
    public List<DT_ListBoxData> getNowListBoxData() {
        return renderPageData.get(getNowPage()).projects();
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

    public void setFlipMode(FlipMode flipMode) {
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
            return getNowListBoxData().get(index);
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
        if (this.active && this.visible && this.flipMode == FlipMode.button) {
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
        if (flipMode == FlipMode.tire){
            DT_ListBoxData dtListBoxData = getData();
            if (dtListBoxData != null){
                dtListBoxData.OnPress(dtListBoxData.getValue());
            }
        }
        super.onClick(pMouseX, pMouseY);
    }
    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        if (this.flipMode == FlipMode.tire){
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
            guiGraphics.drawString(this.font,getNowPageData().title(), centerX - this.font.width(getNowPageData().title())/2, 6, getTextUsualColor(), false);
            for (int i = 0; i < sectors; i++) {
                double startAngle = i * fanArc;
                double endAngle = (i + 1) * fanArc;
                MenuPageData.ProjectInfo projectInfo = null;
                if (i < getNowPageData().projects().size()) projectInfo = getNowPageData().projects().get(i);
                if (projectInfo == null) {
                    if (angle >= startAngle  && angle < endAngle) {
                        this.index = -1;
                    }
                    continue;
                }else if (projectInfo.key().isEmpty() || projectInfo.key().equals("-empty-")) continue;
                String tColor = projectInfo.backgroundNormalColor().isEmpty()?"auto":projectInfo.backgroundNormalColor();
                int bgc = tColor.equals("auto") ? getBackgroundUsualColor() : _ColorSupport.HexToColor(tColor);

                tColor = projectInfo.textNormalColor().isEmpty()?"auto":projectInfo.textNormalColor();
                int tc = tColor.equals("auto") ? getTextUsualColor() : _ColorSupport.HexToColor(tColor);
                float size = 1;
                if (angle >= startAngle  && angle < endAngle) {

                    tColor = projectInfo.backgroundHighlightColor().isEmpty()?"auto":projectInfo.backgroundHighlightColor();
                    bgc = tColor.equals("auto") ? getBackgroundHoverColor() : _ColorSupport.HexToColor(tColor);

                    tColor = projectInfo.textHighlightColor().isEmpty()?"auto":projectInfo.textHighlightColor();
                    tc = tColor.equals("auto") ? getTextHoverColor() : _ColorSupport.HexToColor(tColor);
                    //bgc = projectData.getBackgroundHighlightColor();
                    //tc = projectData.getTextHighlightColor();
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
                    Item item = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(parts[1]));
                    guiGraphics.renderItem(new ItemStack(item), -8, -8);
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


    public record RenderPageData(MenuPageData pageData, List<DT_ListBoxData> projects){
        public static RenderPageData creat(MenuPageData pageData, DT_ListBoxData.OnPress func){
            List<DT_ListBoxData> data = new ArrayList<>();
            pageData.projects().forEach(( projectData) -> {
                MenuProjectData menuData = Configs.MenuProjects.get(projectData.key());
                String name = "";
                if (menuData != null) {
                    name = menuData.name();
                }
                data.add(new DT_ListBoxData(Component.literal(name), projectData.key(), func));
            });
            return new RenderPageData(pageData,data);
        }
    }
}
