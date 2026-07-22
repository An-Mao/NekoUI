package dev.anye.mc.nekoui.dat_type;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.IOException;

public record MenuProjectData(String name, Type type, String value) {
	private static final Logger LOGGER = LogUtils.getLogger();

	public static final MenuProjectData EMPTY = new MenuProjectData("",Type.MESSAGE,"");



	public MenuProjectData(String name, String type, String value) {
		this(name, Type.getTypeEnum(type), value);
	}

	public MenuProjectData(String name, int type, String value) {
		this(name, Type.fromInt(type), value);
	}


	@JsonAdapter(Type.Adapter.class)
	public enum Type {
		ERROR(-1),
		MESSAGE(0),
		COMMAND(1),
		BUTTON(2),
		JS(3);
		private final int v;

		Type(int v) {
			this.v = v;
		}

		public static Type fromInt(int type) {
			for (Type t : Type.values()) {
				if (t.v == type) {
					return t;
				}
			}
			throw new IllegalArgumentException("No enum constant with value " + type);
		}

		public static Type getTypeEnum(String type) {
			try {
				return Type.fromInt(Integer.parseInt(type));
			} catch (NumberFormatException _) {
				return of(type);
			}
		}

		public int v() {
			return v;
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
