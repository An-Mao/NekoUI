package dev.anye.mc.nekoui.config.screen$element;

import com.google.gson.JsonObject;

public class ScreenElementDataElement {
    private int[] pos;
    private String type;
    private JsonObject parameter;

    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type; // Type.fromInt(type);
    }
    /*
    public void setType(String type) {
        try {
            setType(Integer.parseInt(type));
        } catch (NumberFormatException e) {
            this.type = Type.valueOf(type);
        }
    }

     */
    public Type getTypeEnum() {
        try {
            return Type.fromInt(Integer.parseInt(type));
        } catch (NumberFormatException e) {
            return Type.valueOf(type);
        }
    }
    public JsonObject getParameter() {
        return parameter;
    }

    public void setParameter(JsonObject parameter) {
        this.parameter = parameter;
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
    }
}
