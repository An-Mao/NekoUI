package dev.anye.mc.nekoui.mixin;

import net.minecraft.world.entity.Entity;

public class BaseRenderState {

    private Entity entity;

    public BaseRenderState(){}
    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
