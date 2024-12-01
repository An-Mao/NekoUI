package nws.mc.nekoui.gui.hot$bar;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HotBarSys {
    private static long TIME = 0;
    public static boolean isOutTime(){
        return (System.currentTimeMillis() - TIME) > 1000;
    }
    public static void setNowTime(){
        TIME = System.currentTimeMillis();
    }
}
