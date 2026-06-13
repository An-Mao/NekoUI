# **NekoUI**

[English](https://github.com/An-Mao/NekoUI/blob/main/README.MD)

此Mod已内置[Cores](https://github.com/An-Mao/MC-Cores)

一款我的世界辅助类mod，可以通过【[CurseForge](https://www.curseforge.com/minecraft/mc-mods/nekoui)】或【[Modrinth](https://modrinth.com/mod/neko-ui)】下载  

- 可以自定义添加想要显示在界面上的元素
- 可以通过轮盘来快速执行一些操作
- 生物血量显示
- 生物方位显示

## 如何自定义添加界面元素

<font size=3 color=#f08000>普通玩家</font>可以参考wiki界面用配置文件进行添加。*配置文件方式添加的元素可以利用指令动态添加与删除*

<font size=3 color=#ff0000>Mod开发者</font>可以通过代码添加。*此方式不支持动态添加与删除*

首先引用本mod至项目：

``` gradle
    compileOnly "dev.anye.mc:nekoui:${nekouiVersion}" //将版本替换为实际引用版本
```

之后可以像添加我的世界元素那样

``` java
    //neoforge
    import dev.anye.mc.nekoui.register.screen_element.ScreenElement;
    import dev.anye.mc.cores.register.Register;
    //获取DeferredRegister
    public static final DeferredRegister<ScreenElement> SCREEN_ELEMENTS = DeferredRegister.create(Registers.SCREEN_ELEMENT.getResourceKey(), YourMod.MOD_ID);
    //注册
    public static final DeferredHolder<ScreenElement,YourScreenElement> YOUR_SCREEN_ELEMENT = ITEMS.register("you_screen_element_name", YourScreenElement::new);
```
