package dev.anye.mc.nekoui.config.hotbar;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HotBarData {
    private boolean enable;
    private boolean dynamicDisplay;
    private String startX;
    private String startY;
    private int x;
    private int y;
    private int space;
    private String direction;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setDynamicDisplay(boolean dynamicDisplay) {
        this.dynamicDisplay = dynamicDisplay;
    }

    public boolean isDynamicDisplay() {
        return dynamicDisplay;
    }

    public void setStartX(String startX) {
        this.startX = startX;
    }

    public String getStartX() {
        return startX;
    }

    public void setStartY(String startY) {
        this.startY = startY;
    }

    public String getStartY() {
        return startY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getSpace() {
        return space;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
