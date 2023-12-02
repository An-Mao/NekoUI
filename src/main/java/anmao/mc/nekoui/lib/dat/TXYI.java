package anmao.mc.nekoui.lib.dat;

public class TXYI {
    private String s;
    private int x;

    private int y;
    private int i;
    public void setDat(String str ){
        String[] a = str.split("#");
        if (a.length == 4) {
            setX(Integer.parseInt(a[0]));
            setY(Integer.parseInt(a[1]));
            setI(Integer.parseInt(a[2]));
            setS(a[3]);
        }
    }
    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
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
        this.i += i + this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
