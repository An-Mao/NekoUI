package anmao.mc.nekoui.screen.widget;

import anmao.dev.core.debug.DeBug;
import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SimpleDropDownSelectBox extends SimpleWidgetCore<SimpleDropDownSelectBox> {
    private List<DT_ListBoxData> dataList;
    private int nowSelectIndex = -1;
    private boolean showList = false;
    private int line,lineHeight,linePosY;
    private int pages,nowPage;
    private int usualHeight;
    private int backgroundSelectColor,textSelectColor;

    public SimpleDropDownSelectBox(int x, int y, int w, int h, Component pMessage, DT_ListBoxData... data) {
        this(x, y, w, h, pMessage,Arrays.asList(data));
    }

    public SimpleDropDownSelectBox(int x, int y, int w, int h,  Component pMessage,List<DT_ListBoxData> data) {
        super(x, y, w, h, pMessage);
        backgroundSelectColor = Color.LIGHT_GRAY.getRGB();
        textSelectColor = Color.DARK_GRAY.getRGB();
        setUsualHeight(h);
        this.dataList = data;
        setLine(7);
    }

    public int getUsualHeight() {
        return usualHeight;
    }

    public void setUsualHeight(int usualHeight) {
        this.usualHeight = usualHeight;
    }

    public void setDataList(DT_ListBoxData... data) {
        setDataList(Arrays.asList(data));
    }

    public void setDataList(List<DT_ListBoxData> dataList) {
        nowPage = 1;
        nowSelectIndex = -1;
        this.dataList = dataList;
    }



    public void setLine(int line){
        this.line = line;
        lineHeight = getContentH() * line;
        int a = getContentH()  - font.lineHeight;
        if (a > 0) {
            this.linePosY = a / 2;
        }else {
            this.linePosY = 0;
        }
        this.pages = getPages(this.dataList.size(),line);
        this.nowPage = 1;
    }
    public int getPages(int number , int line){
        int n = number / line;
        if (number % line != 0){
            n ++;
        }
        return n;
    }
    public List<DT_ListBoxData> getDataList() {
        return dataList;
    }
    public Component getSelectComponent(){
        DT_ListBoxData dropDownListBoxData = getSelectData();
        if (dropDownListBoxData != null){
            return dropDownListBoxData.getComponent();
        }
        return Component.literal("-------");
    }
    public Component getComponent(int index){
        DT_ListBoxData dropDownListBoxData = getData(index);
        if (dropDownListBoxData != null){
            return dropDownListBoxData.getComponent();
        }
        return Component.literal("-------");
    }
    public List<Component> getDataTooltip(int index){
        DT_ListBoxData dropDownListBoxData = getData(index);
        if (dropDownListBoxData != null){
            return dropDownListBoxData.getTooltip();
        }
        return List.of(Component.literal("-------")) ;
    }
    public DT_ListBoxData getSelectData(){
        if (nowSelectIndex >= 0 && nowSelectIndex < dataList.size()){
            return dataList.get(nowSelectIndex);
        }
        return null;
    }
    public DT_ListBoxData getData(int index){
        int i = (nowPage - 1) * line + index;
        if (i >= 0 && i < dataList.size()){
            return dataList.get(i);
        }
        return null;
    }
    public Object getSelectValue(){
        if (nowSelectIndex > dataList.size() || nowSelectIndex < 0){
            DeBug.ThrowError("Error Select");
            return null;
        }else {
            return dataList.get(nowSelectIndex).getValue();
        }
    }
    public boolean setSelect(int index){
        if (index > dataList.size() || index < 0){
            return false;
        }
        this.nowSelectIndex = index;
        return true;
    }
    public int getNowSelectIndex(){
        return nowSelectIndex;
    }
    public String FixStrWidth(String s){
        return font.plainSubstrByWidth(s,width);
    }
    public String FixStrWidth(Component s){
        return FixStrWidth(s.getString());
    }
    @Override
    public void onClick(double pMouseX, double pMouseY) {
        super.onClick(pMouseX, pMouseY);
        showList = !showList;
        if (showList){
            setHeight(getUsualHeight() + lineHeight);
        }else {
            setHeight(getUsualHeight());
        }
        updateIndex(pMouseY);
    }
    public void updateIndex(double mouseY){
        int i = (int) ((mouseY - getContentY()) / getContentH());
        if (i > 0){
            nowSelectIndex = i - 1;
            nowSelectIndex += (nowPage - 1) * line;
            DT_ListBoxData dropDownListBoxData = getSelectData();
            if (dropDownListBoxData != null) {
                dropDownListBoxData.OnPress(getSelectValue());
            }
        }
    }
    public boolean isInWidget(double pMouseX,double pMouseY){
        return pMouseX > getContentX() && pMouseX < getContentX() + getContentW() && pMouseY > getContentY() && pMouseY < getContentY() + getContentH();
    }
    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double sx,double sy) {
        if (showList) {
            if (isInWidget(pMouseX,pMouseY)) {
                if (sy < 0 && nowPage < pages){
                    nowPage ++;
                }else if (sy > 0 && nowPage > 1){
                    nowPage --;
                }
                return true;
            }
        }
        return super.mouseScrolled(pMouseX, pMouseY, sx,sy);

    }
    @Override
    protected void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        if (showList) {
            poseStack.translate(0, 0, layerZ+1);
        }else {
            poseStack.translate(0, 0, layerZ);
        }
        //guiGraphics.fill(dt_xywh.x(), dt_xywh.y(), dt_xywh.x()+width, dt_xywh.y() + dt_xywh.height() ,bgSelectColor);

        Component c = getMessage();
        if (nowSelectIndex > -1 || c.equals(Component.empty())){
            c = getSelectComponent();
        }
        guiGraphics.drawString(getFont(),Component.literal(FixStrWidth(c)),getContentX(),getContentY()+linePosY,getTextHoverColor(),false);
        //drawStr(guiGraphics,Component.literal(FixStrWidth(c)),dt_xywh.x(),dt_xywh.y()+linePosY,textSelectColor);
        //guiGraphics.drawString(font,c,dt_xywh.getX(),dt_xywh.getY()+linePosY,textSelectColor,false);
        if (showList){
            for (int i = 0; i < line;i++){
                int lineY = getContentY() + (i+1) * usualHeight;//??????????????????
                Component select = getComponent(i);
                //int bgc = bgUsualColor;
                int hc = textUsualColor;
                if (mouseX > getContentX() && mouseX < getContentX() + width && mouseY > lineY && mouseY < lineY + usualHeight){
                    int bgc = 0xffff0000;
                    hc = textHoverColor;
                    guiGraphics.renderTooltip(font,getDataTooltip(i), Optional.empty(),mouseX,mouseY);
                    guiGraphics.fill(getContentX(),  lineY,getContentX()+getContentW(), lineY+ usualHeight, bgc);
                }
                guiGraphics.drawString(getFont(),Component.literal(FixStrWidth(select)),getContentX(),lineY+ linePosY,hc,false);
                //drawStr(guiGraphics,Component.literal(FixStrWidth(select)),this.getX(),lineY+ linePosY,hc);
            }
        }
        poseStack.popPose();
    }
}
