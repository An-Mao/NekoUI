package dev.anye.mc.register.nekoui;

import dev.anye.mc.cores.cr.CoresRegs;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.render.MobHealthBar;

public class NekoUIRegister {
    public static void reg(){
        CoresRegs.ENTITY_RENDER_REG.put(NekoUI.MOD_ID+":mob_health_bar", MobHealthBar::render);
    }
}
