package dev.anye.mc.nekoui.other;

import dev.anye.core.javascript._JavaScript;
import dev.anye.core.javascript._GraalJS;
import dev.anye.core.javascript._NashornJS;
import dev.anye.mc.cores.js.Js;

public class CJS {
    public static final _JavaScript<?,?> E = getNewEngine(true);


    public static _JavaScript<?,?> getNewEngine(boolean cache) {
        if (Js.GraalJs) return _GraalJS.NotSafe(cache);
        else return new _NashornJS(cache);
    }
}
