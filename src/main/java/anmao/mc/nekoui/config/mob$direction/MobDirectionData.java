package anmao.mc.nekoui.config.mob$direction;

import anmao.dev.core.color.ColorHelper;

import java.util.Map;

public class MobDirectionData {
    private boolean enable;
    private boolean dynamicDisplay;
    private int poiShowRadius;
    private int poiSize;
    private int poiRadius;
    private int poiMaxSize;
    private int poiMinSize;
    private double ratio;
    private Map<String,String> entityColors;

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

    public void setPoiShowRadius(int poiShowRadius) {
        this.poiShowRadius = poiShowRadius;
    }

    public int getPoiShowRadius() {
        return poiShowRadius;
    }

    public void setPoiSize(int poiSize) {
        this.poiSize = poiSize;
    }

    public int getPoiSize() {
        return poiSize;
    }

    public void setPoiRadius(int poiRadius) {
        this.poiRadius = poiRadius;
    }

    public int getPoiRadius() {
        return poiRadius;
    }

    public void setPoiMaxSize(int poiMaxSize) {
        this.poiMaxSize = poiMaxSize;
    }

    public int getPoiMaxSize() {
        return poiMaxSize;
    }

    public void setPoiMinSize(int poiMinSize) {
        this.poiMinSize *= poiMinSize;
    }

    public int getPoiMinSize() {
        return poiMinSize;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public double getRatio() {
        return ratio;
    }

    public Map<String, String> getEntityColors() {
        return entityColors;
    }

    public void setEntityColors(Map<String, String> entityColors) {
        this.entityColors = entityColors;
    }

    public int getColor(String name){
        return ColorHelper.HexToColor(entityColors.get(name));
    }



}
