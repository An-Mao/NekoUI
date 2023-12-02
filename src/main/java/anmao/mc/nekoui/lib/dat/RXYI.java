package anmao.mc.nekoui.lib.dat;

import anmao.mc.nekoui.constant._MC;
import net.minecraft.resources.ResourceLocation;

public class RXYI {
    private ResourceLocation r;
    private int x;

    private int y;
    private int i;

    public void setDat(String str) {
        setR(str);
    }

    public ResourceLocation getR() {
        return r;
    }

    public void setR(String s) {
        String[] sa = s.split(":");
        if (sa.length == 2){
            this.r = new ResourceLocation(sa[0], sa[1]);
        }
    }
    public int getI() {
        return i;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setI(int i) {
        this.i = i + this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
