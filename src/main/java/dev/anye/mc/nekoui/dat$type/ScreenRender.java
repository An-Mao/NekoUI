package dev.anye.mc.nekoui.dat$type;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public record ScreenRender(String x, String y, Vector3i pos, List<Element> elements){
    @Override
    public @NotNull String toString() {
        return "{\"x\":\"" + x + "\",\"y\":\"" + y + "\",\"pos\":" + pos + ",\"elements\":" + elements + "}";
    }

    public record Element(Vector3i pos, Type type, String key, String color, int width, int height){
        public Element(Vector3i pos, int type,String key,String color){
            this(pos,type,key,color,10,10);
        }
        public Element(Vector3i pos, int type,String key,String color, int width,int height){
            this(pos,Type.fromInt(type),key,color,width,height);
        }
        public Element(Vector3i pos, String type,String key,String color){
            this(pos,type,key,color,10,10);
        }
        public Element(Vector3i pos, String type,String key,String color, int width,int height){
            this(pos,Type.getTypeEnum(type),key,color,width,height);
        }



        @Override
        public @NotNull String toString() {
            return "Element{" +
                    "pos=" + pos +
                    ", type=" + type +
                    ", key='" + key + '\'' +
                    ", color='" + color + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

    public enum Type {
        Image(0),
        Self(1),
        Custom(2),
        PlayerData(3),
        Js(4),
        Slot(5);

        private final int value;

        Type(int value) {
            this.value = value;
        }
        public static Type fromInt(int type) {
            for (Type t : Type.values()) {
                if (t.value== type) {
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
    }
}