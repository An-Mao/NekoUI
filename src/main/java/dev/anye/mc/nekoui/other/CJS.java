package dev.anye.mc.nekoui.other;

import dev.anye.mc.cores.js.Js;
import dev.anye.mc.cores.js._GraalJS;
import dev.anye.mc.cores.js._JavaScript;
import dev.anye.mc.cores.js._NashornJS;

public class CJS {
    public static final _JavaScript<?> E;
    static {
        if (Js.GraalJs) E = _GraalJS.NotSafe(false);
        else E = new _NashornJS(false);
    }
}
