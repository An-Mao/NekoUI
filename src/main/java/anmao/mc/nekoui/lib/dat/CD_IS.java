package anmao.mc.nekoui.lib.dat;

import java.util.Objects;

public class CD_IS {
    private int type;
    private String id;
    private String a;
    private String[] sa;

    private int x;

    private int y;
    private int i;
    public void setDat(String s){
        String[] a = s.split("\\|");
        if (a.length == 2) {
            String[] b = a[0].split("#");
            if (b.length == 5) {
                setType(b[0]);
                setId(b[1]);
                setX(Integer.parseInt(b[2]));
                setY(Integer.parseInt(b[3]));
                setI(Integer.parseInt(b[4]));
                setA(a[1]);
                setSa(this.a);
            }
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setI(int i) {
        this.i = i + this.y;
    }

    public int getI() {
        return i;
    }

    public void setSa(String s) {
        this.sa = s.split("#");
    }

    public String[] getSa() {
        return sa;
    }

    public void setType(String type) {
        if (Objects.equals(type, "t")){
            this.type = 0;
        }
        if (Objects.equals(type, "i")) {
            this.type = 1;
        }
    }

    public void setA(String a) {
        this.a = a;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public String getA() {
        return a;
    }

    public String getId() {
        return id;
    }
}
