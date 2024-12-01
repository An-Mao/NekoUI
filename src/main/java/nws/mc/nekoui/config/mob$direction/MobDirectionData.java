package nws.mc.nekoui.config.mob$direction;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.dev.core.color._ColorSupport;

import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class MobDirectionData {
    private boolean enable;
    private boolean dynamicDisplay;
    private int poiShowRadius;
    private int poiSize;
    private int poiRadius;
    private int poiMaxSize;
    private int poiMinSize;
    private double ratio;
    private boolean onlyLivingEntity;
    private boolean notInListMode;
    private Map<String,Boolean> entityList;
    private String defaultColor;
    private boolean useEggColor;
    private int eggLayerIndex;
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

    public String getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void setUseEggColor(boolean useEggColor) {
        this.useEggColor = useEggColor;
    }

    public boolean isUseEggColor() {
        return useEggColor;
    }

    public void setEggLayerIndex(int eggLayerIndex) {
        this.eggLayerIndex = eggLayerIndex;
    }

    public int getEggLayerIndex() {
        return  Math.clamp(eggLayerIndex,0,1);
    }

    public Map<String, String> getEntityColors() {
        return entityColors;
    }

    public void setEntityColors(Map<String, String> entityColors) {
        this.entityColors = entityColors;
    }

    public int getColor(String name){
        return _ColorSupport.HexToColor(entityColors.getOrDefault(name,getDefaultColor()));
    }

    public void setEntityList(Map<String, Boolean> entityList) {
        this.entityList = entityList;
    }

    public void setOnlyLivingEntity(boolean onlyLivingEntity) {
        this.onlyLivingEntity = onlyLivingEntity;
    }

    public void setNotInListMode(boolean notInListMode) {
        this.notInListMode = notInListMode;
    }

    public boolean isNotInListMode() {
        return notInListMode;
    }

    public boolean isOnlyLivingEntity() {
        return onlyLivingEntity;
    }
    public Map<String, Boolean> getEntityList() {
        return entityList;
    }
    public boolean isShow(String name){
        return entityList.getOrDefault(name,false);
    }
}
