package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.amlib.screen.widget.DropDownListBox;
import anmao.mc.amlib.screen.widget.SquareImageButton;
import anmao.mc.nekoui.config.menu.MenuConfig;
import anmao.mc.nekoui.config.menu.MenuData;
import anmao.mc.nekoui.screen.widget.Label;
import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ProjectsSettingScreen extends ScreenCore {
    protected final Logger LOGGER = LogUtils.getLogger();
    protected Map<String,MenuData> md = null;
    public boolean KeyListen;
    public EditBox idEditBox,nameEditBox,valueEditBox;
    public DropDownListBox runType;
    public ProjectsSettingScreen() {
        super("screen.nekoui.projects_setting");
    }

    @Override
    protected void init() {
        super.init();
        int px = width/2 - 75;
        int py = 32;
        int lbg = 0x55646464;
        int lsc = 0x83838383;
        int lt = 0xffffffff;
        int ts = Color.RED.getRGB();
        addRenderableWidget(new Label(px,py,50,12,getComponent("label.select"),lbg,lt));
        addRenderableWidget(new DropDownListBox(new DT_XYWH(px + 50,py,100,12),getComponent("select_id"),getConfigData()));

        py += 20;
        addRenderableWidget(new Label(px,py,50,12,getComponent("label.id"), lbg,lt));
        idEditBox = new EditBox(font,px+ 50,py,100,12,getComponent("id_input"));
        addRenderableWidget(idEditBox);

        py += 20;
        addRenderableWidget(new Label(px,py,50,12,getComponent("label.name"),lbg,lt));
        nameEditBox = new EditBox(font,px+ 50,py,100,12,getComponent("name_input"));
        addRenderableWidget(nameEditBox);

        py += 20;
        addRenderableWidget(new Label(px,py,50,12,getComponent("label.type"),lbg,lt));
        runType = new DropDownListBox(new DT_XYWH(px + 50,py,50,12),getComponent("type"),getTypes());
        runType.setLine(3);
        addRenderableWidget(runType);

        py += 20;
        addRenderableWidget(new Label(px,py,50,12,getComponent("label.value"),lbg,lt));
        valueEditBox = new EditBox(font,px+ 50,py,100,12,getComponent("value_input"));
        addRenderableWidget(valueEditBox);
        SquareImageButton b = new SquareImageButton(new DT_XYWH(px + 50,py+13,16,16),getComponent("key"),this::setKeyListen);
        b.setBgUsualColor(lbg);
        b.setBgSelectColor(lsc);
        b.setTextSelectColor(ts);
        b.setTextUsualColor(lt);
        addRenderableWidget(b);


        py += 40;
        SquareImageButton save = new SquareImageButton(new DT_XYWH(px,py,32,16),getComponent("save"),this::saveConfig);
        save.setBgUsualColor(lbg);
        save.setBgSelectColor(lsc);
        save.setTextSelectColor(ts);
        save.setTextUsualColor(lt);
        addRenderableWidget(save);
        SquareImageButton delete =new SquareImageButton(new DT_XYWH(px + 64,py,32,16),getComponent("delete"),this::delete);

        delete.setBgUsualColor(lbg);
        delete.setBgSelectColor(lsc);
        delete.setTextSelectColor(ts);
        delete.setTextUsualColor(lt);
        addRenderableWidget(delete);
    }
    public void delete(){
        String id = idEditBox.getValue();
        if (!id.isEmpty()){
            if (md.get(id) != null){
                md.remove(id);
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
        if (index > 2){
            index = 2;
        }else if (index < 0 ){
            index = 0;
        }
        menuData.setType(index);
        menuData.setValue(valueEditBox.getValue());
        md.put(id,menuData);
        Minecraft.getInstance().setScreen(new ProjectsSettingScreen());
    }

    @Override
    public void onClose() {
        saveChange();
        super.onClose();
    }
    public void saveChange(){
        Gson gson = new Gson();
        /*
        Map<Integer, MenuData> resultMap = new HashMap<>();
        int index = 1;
        for (Map.Entry<String, MenuData> entry : md.entrySet()) {
            resultMap.put(index, entry.getValue());
            index++;
        }

         */
        String jsonString = gson.toJson(md);
        try {
            FileWriter writer = new FileWriter(MenuConfig.file);

            try {
                writer.write(jsonString);
                MenuConfig.INSTANCE.setData(md);
            } catch (Throwable var5) {
                try {
                    writer.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            writer.close();
        } catch (IOException var6) {
            LOGGER.error(var6.getMessage());
        }
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
        if (md == null){
            md = MenuConfig.INSTANCE.getDatas();
        }
        md.forEach((s, menuData) -> data.add(new DT_ListBoxData(Component.literal(s),s,this::setData)));
        return data;
    }
    public List<DT_ListBoxData> getTypes(){
        List<DT_ListBoxData> data = new ArrayList<>();
        for (int i = 0; i< 3; i++){
            data.add(new DT_ListBoxData(getComponent("types."+getTypeName(i)),i));
        }
        return data;
    }
    public String getTypeName(int type ){
        return switch (type){
            case 0 -> "message";
            case 1 -> "command";
            case 2 -> "button";
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
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
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        //renderDirtBackground(pGuiGraphics);
        //renderBackground(pGuiGraphics,  pMouseX,  pMouseY,  pPartialTick);
    }
}
