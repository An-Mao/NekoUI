package dev.anye.mc.nekoui.dat_type;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;

public record ScreenRender(String x, String y, Vector3i pos, List<Element> elements) {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final ScreenRender EMPTY = new ScreenRender("","",new Vector3i(),List.of());


	@Override
	public @NotNull String toString() {
		return "{\"x\":\"" + x + "\",\"y\":\"" + y + "\",\"pos\":" + pos + ",\"elements\":" + elements + "}";
	}

	public record Element(Vector3i pos, Type type, String key, String color, int width, int height) {
		public Element(Vector3i pos, int type, String key, String color) {
			this(pos, type, key, color, 10, 10);
		}

		public Element(Vector3i pos, int type, String key, String color, int width, int height) {
			this(pos, Type.fromInt(type), key, color, width, height);
		}

		public Element(Vector3i pos, String type, String key, String color) {
			this(pos, type, key, color, 10, 10);
		}

		public Element(Vector3i pos, String type, String key, String color, int width, int height) {
			this(pos, Type.getTypeEnum(type), key, color, width, height);
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

	@JsonAdapter(Type.Adapter.class)
	public enum Type {
		ERROR(-1),
		IMAGE(0),
		SELF(1),
		CUSTOM(2),
		PLAYER_DATA(3),
		JS(4),
		SLOT(5);

		private final int value;

		Type(int value) {
			this.value = value;
		}

		public static Type fromInt(int type) {
			for (Type t : Type.values()) {
				if (t.value == type) {
					return t;
				}
			}
			throw new IllegalArgumentException("No enum constant with value " + type);
		}

		public static Type getTypeEnum(String type) {
			try {
				return Type.fromInt(Integer.parseInt(type));
			} catch (NumberFormatException e) {
				return of(type);
			}
		}

		public static Type of(String type){
			for (Type t : Type.values()){
				if (t.name().equalsIgnoreCase(type)) return t;
				if (t.name().replace("_","").equalsIgnoreCase(type)) return t;
			}
			LOGGER.error("unknow type => {} ,please check it.",type);
			return ERROR;
		}
		public static class Adapter extends TypeAdapter<Type> {
			@Override
			public void write(JsonWriter out, Type value) throws IOException {
				out.value(value.name());
			}
			@Override
			public Type read(JsonReader in) throws IOException {
				return Type.getTypeEnum(in.nextString());
			}
		}
	}

}