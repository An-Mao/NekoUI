package dev.anye.mc.nekoui.other;

import dev.anye.core.javascript._GraalJS;
import dev.anye.core.javascript._JavaScript;
import dev.anye.core.javascript._NashornJS;
import net.minecraftforge.fml.ModList;

public class Js {
	public static final boolean GRAALJS = GraalJsIsInstall();

	private Js(){}
	public static boolean GraalJsIsInstall() {
		return ModList.isLoaded("graaljs");
	}

	public static _JavaScript<?, ?> getJsEngine(boolean cache) {
		return GRAALJS ? new _GraalJS(cache) : new _NashornJS(cache);
	}

}
