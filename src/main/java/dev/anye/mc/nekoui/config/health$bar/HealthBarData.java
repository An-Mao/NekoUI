package dev.anye.mc.nekoui.config.health$bar;

import dev.anye.core.color._ColorSupport;

public record HealthBarData(
        boolean enable, int renderDistance, float renderTop,
        boolean renderHealthBar, boolean renderHealthBarText,
        boolean renderEffect, boolean effectRenderImage, int effectImageRotationAngle,
        boolean renderOnlyView, String healthBarTextColor,int tempColor) {
    public static final HealthBarData DEFAULT = new HealthBarData(true,30,0.5f,true,true,true,true,10000,true,"0xFFFFD700",0);
    public HealthBarData(
            boolean enable, int renderDistance, float renderTop,
            boolean renderHealthBar, boolean renderHealthBarText,
            boolean renderEffect, boolean effectRenderImage, int effectImageRotationAngle,
            boolean renderOnlyView, String healthBarTextColor,int tempColor){
        this.enable = enable;
        this.renderDistance = renderDistance;
        this.renderTop = renderTop;
        this.renderHealthBar = renderHealthBar;
        this.renderHealthBarText = renderHealthBarText;
        this.renderEffect = renderEffect;
        this.effectRenderImage = effectRenderImage;
        this.effectImageRotationAngle = effectImageRotationAngle;
        this.renderOnlyView = renderOnlyView;
        this.healthBarTextColor = healthBarTextColor;
        this.tempColor = _ColorSupport.HexToColor(healthBarTextColor);
    }

}
