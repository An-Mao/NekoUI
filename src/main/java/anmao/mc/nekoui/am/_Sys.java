package anmao.mc.nekoui.am;

import java.util.Objects;

public class _Sys {
    private static long TIME = 0;
    public static boolean isOutTime(){
        return (System.currentTimeMillis() - TIME) > 1000;
    }
    public static void setNowTime(){
        TIME = System.currentTimeMillis();
    }
    public static boolean getBoolean(String s){
        return Objects.equals(s, "enable") || Objects.equals(s, "1") || Objects.equals(s, "open");
    }
    public static int getInt(String s){
        return Integer.parseInt(s);
    }
}
