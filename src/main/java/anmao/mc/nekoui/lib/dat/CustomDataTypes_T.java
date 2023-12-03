package anmao.mc.nekoui.lib.dat;

public class CustomDataTypes_T {
    private boolean isNumber = false;
    private String str = "";
    private Number number = 0;

    public void setaBoolean(boolean aBoolean) {
        this.isNumber = aBoolean;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public void setStr(String string) {
        this.str = string;
    }

    public Number getNumber() {
        return number;
    }

    public String getStr() {
        return str;
    }

    public boolean isaBoolean() {
        return isNumber;
    }

    @Override
    public String toString() {
        return "CustomDataTypes_T{" +
                "isNumber=" + isNumber +
                ", str='" + str + '\'' +
                ", number=" + number +
                '}';
    }
}
