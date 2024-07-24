package anmao.mc.nekoui.dat$type;

public enum MenuRunType {
    message(0), command(1), button(2);
    private final int id;
    MenuRunType(int id){
        this.id = id;
    }
}
