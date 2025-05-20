package dev.anye.mc.nekoui.dat$type;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record MenuProjectData(String name, Type type, String value) {
    public MenuProjectData(String name, String type, String value) {
        this(name,Type.getTypeEnum(type),value);
    }
    public MenuProjectData(String name, int type, String value) {
        this(name,Type.fromInt(type),value);
    }

    public enum Type {
        message(0),
        command(1),
        button(2),
        js(3);
        private final int v;
        Type(int v){
            this.v = v;
        }
        public static Type fromInt(int type) {
            for (Type t : Type.values()) {
                if (t.v== type) {
                    return t;
                }
            }
            throw new IllegalArgumentException("No enum constant with value " + type);
        }
        public static Type getTypeEnum(String type) {
            try {
                return Type.fromInt(Integer.parseInt(type));
            } catch (NumberFormatException e) {
                return Type.valueOf(type);
            }
        }
        public int v(){return v;}
    }
}
