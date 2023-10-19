package anmao.mc.nekoui.am;

public class _Sys {
    private static long TIME = 0;
    public static boolean isOutTime(){
        return (System.currentTimeMillis() - TIME) > 1000;
    }
    public static void setNowTime(){
        TIME = System.currentTimeMillis();
    }
}
