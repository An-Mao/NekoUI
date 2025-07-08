package dev.anye.mc.nekoui.screen;

import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.cores.screen.widget.simple.SimpleDropDownSelectBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleEditBox;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.menu.MenuConfig;
import dev.anye.mc.nekoui.config.menu.MenuData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ProjectsSettingScreen extends ScreenCore {
    public static final String ID = "screen."+ NekoUI.MOD_ID +".projects_setting";
    public boolean KeyListen;
    public SimpleEditBox idEditBox,nameEditBox,valueEditBox;
    public SimpleDropDownSelectBox runType;
    public ProjectsSettingScreen() {
        super(ID);
    }

    @Override
    protected void init() {
        super.init();
        int px = width/2 - 75;
        int py = 32;
        addRenderableWidget(createNewLabel(px,py,50,16,getComponent("label.select")));
        addRenderableWidget(createNewSelectBox(px + 50,py,100,16,getComponent("select_id"),getConfigData()));

        py += 23;
        addRenderableWidget(createNewLabel(px,py,50,16,getComponent("label.id")));
        idEditBox = createNewEditBox(px+ 50,py,100,16,idEditBox,getComponent("id_input"));
        addRenderableWidget(idEditBox);

        py += 23;
        addRenderableWidget(createNewLabel(px,py,50,16,getComponent("label.name")));
        nameEditBox = createNewEditBox(px+ 50,py,100,16,nameEditBox,getComponent("name_input"));
        addRenderableWidget(nameEditBox);

        py += 23;
        addRenderableWidget(createNewLabel(px,py,50,16,getComponent("label.type")));
        runType = createNewSelectBox(px + 50,py,50,16,getComponent("type"),getTypes());
        runType.setLine(4);
        addRenderableWidget(runType);

        py += 23;
        addRenderableWidget(createNewLabel(px,py,50,16,getComponent("label.value")));
        valueEditBox = createNewEditBox(px+ 50,py,100,16,valueEditBox,getComponent("value_input"));
        addRenderableWidget(valueEditBox);
        SimpleButton b = createNewButton(px + 50,py+16,16,16,getComponent("key"),this::setKeyListen);
        addRenderableWidget(b);


        py += 52;
        SimpleButton save = createNewButton(px,py,32,16,getComponent("save"),this::saveConfig);
        addRenderableWidget(save);
        SimpleButton delete =createNewButton(px + 64,py,32,16,getComponent("delete"),this::delete);

        addRenderableWidget(delete);
    }
    public void delete(){
        String id = idEditBox.getValue();
        if (!id.isEmpty()){
            if (MenuConfig.INSTANCE.getDatas().get(id) != null){
                MenuConfig.INSTANCE.getDatas().remove(id);
                MenuConfig.INSTANCE.save();
            }
        }
        Minecraft.getInstance().setScreen(new ProjectsSettingScreen());
    }
    public void saveConfig(){
        String id = idEditBox.getValue();
        if (id.isEmpty()){
            return;
        }
        MenuData menuData = new MenuData();
        menuData.setName(nameEditBox.getValue());
        int index = runType.getNowSelectIndex();
        menuData.setType(index);
        menuData.setValue(valueEditBox.getValue());
        MenuConfig.INSTANCE.getDatas().put(id,menuData);
        MenuConfig.INSTANCE.save();
        //md.put(id,menuData);
        Minecraft.getInstance().setScreen(new ProjectsSettingScreen());
    }
    public void setKeyListen(){
        this.KeyListen = !this.KeyListen;
    }
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (KeyListen){
            this.KeyListen = false;
            String old = this.valueEditBox.getValue();
            if (!old.isEmpty()){
                old += " ";
            }
            this.valueEditBox.setValue(old+pKeyCode);
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    public List<DT_ListBoxData> getConfigData(){
        List<DT_ListBoxData> data = new ArrayList<>();
        MenuConfig.INSTANCE.getDatas().forEach((s, menuData) -> data.add(new DT_ListBoxData(Component.literal(s),s,this::setData)));
        return data;
    }
    public List<DT_ListBoxData> getTypes(){
        List<DT_ListBoxData> data = new ArrayList<>();
        for (RunType type : RunType.values()){
            data.add(new DT_ListBoxData(getComponent("types."+type.name()),type.v));
        }
        return data;
    }
    public void setData(Object v){
        if (v instanceof String id){
            MenuData menuData = MenuConfig.INSTANCE.getDatas().get(id);
            if (menuData != null){
                idEditBox.setValue(id);
                nameEditBox.setValue(menuData.getName());
                runType.setSelect(menuData.getType());
                valueEditBox.setValue(menuData.getValue());
            }
        }
    }
    public enum RunType{
        message(0),
        command(1),
        button(2),
        js(3);
        private final int v;
        RunType(int v){
            this.v = v;
        }
        public static RunType fromInt(int type) {
            for (RunType t : RunType.values()) {
                if (t.v== type) {
                    return t;
                }
            }
            throw new IllegalArgumentException("No enum constant with value " + type);
        }
    }
}
