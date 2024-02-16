package anmao.mc.nekoui.config.mob$direction;

public class MobDirectionData {
    private boolean enable;
    private boolean dynamicDisplay;
    private int poiShowRadius;
    private int poiSize;
    private int poiRadius;
    private int poiDynamicSizeMid;
    private int poiDynamicRadiusMid;
    private int poiDynamicSizeClose;
    private int poiDynamicRadiusClose;

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

    public void setPoiDynamicSizeMid(int poiDynamicSizeMid) {
        this.poiDynamicSizeMid = poiDynamicSizeMid;
    }

    public int getPoiDynamicSizeMid() {
        return poiDynamicSizeMid;
    }

    public void setPoiDynamicRadiusMid(int poiDynamicRadiusMid) {
        this.poiDynamicRadiusMid *= poiDynamicRadiusMid;
    }

    public int getPoiDynamicRadiusMid() {
        return poiDynamicRadiusMid;
    }

    public void setPoiDynamicSizeClose(int poiDynamicSizeClose) {
        this.poiDynamicSizeClose = poiDynamicSizeClose;
    }

    public int getPoiDynamicSizeClose() {
        return poiDynamicSizeClose;
    }

    public void setPoiDynamicRadiusClose(int poiDynamicRadiusClose) {
        this.poiDynamicRadiusClose *= poiDynamicRadiusClose;
    }

    public int getPoiDynamicRadiusClose() {
        return poiDynamicRadiusClose;
    }

    public int chickRadius(double cx, double cz) {
        int or = poiSize;
        if (dynamicDisplay){
            double v = cx * cx + cz * cz;
            if (v <= poiDynamicRadiusClose){
                or =  poiDynamicSizeClose;
            }else if (v <= poiDynamicRadiusMid){
                or = poiDynamicSizeMid;
            }
        }
        return or;
    }
}
