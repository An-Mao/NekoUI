# **NekoUI**

[中文](https://github.com/An-Mao/NekoUI/blob/main/README_ZH.MD)

This mod comes with [Cores](https://github.com/An-Mao/MC-Cores) built-in.

A Minecraft utility mod available for download via [CurseForge](https://www.curseforge.com/minecraft/mc-mods/nekoui) or [Modrinth](https://modrinth.com/mod/neko-ui).

- Add customizable elements to your screen.
- Execute quick actions via a radial menu (轮盘).
- Entity health display.
- Entity direction indicator.

## How to Add Custom UI Elements

<font size=3 color=#f08000>Regular Players</font> can refer to the wiki to add elements using configuration files. *Elements added via config files can be dynamically added or removed using commands.*

<font size=3 color=#ff0000>Mod Developers</font> can add elements via code. *Note: This method does not support dynamic addition or removal.*

First, add this mod to your project as a dependency:

``` gradle
    compileOnly "dev.anye.mc:nekoui:${nekouiVersion}" // Replace with the actual version
```

Then, you can register elements similarly to how you register standard Minecraft objects:

``` java
    // neoforge
    import dev.anye.mc.nekoui.register.screen_element.ScreenElement;
    import dev.anye.mc.cores.register.Register;
    
    // Get the DeferredRegister
    public static final DeferredRegister<ScreenElement> SCREEN_ELEMENTS = DeferredRegister.create(Registers.SCREEN_ELEMENT.getResourceKey(), YourMod.MOD_ID);
    
    // Register the element
    public static final DeferredHolder<ScreenElement, YourScreenElement> YOUR_SCREEN_ELEMENT = SCREEN_ELEMENTS.register("your_screen_element_name", YourScreenElement::new);
```
