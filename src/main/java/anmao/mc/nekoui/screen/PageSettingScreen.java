package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.amlib.screen.widget.DropDownListBox;
import anmao.mc.amlib.screen.widget.SquareImageButton;
import anmao.mc.nekoui.config.page.PageConfig;
import anmao.mc.nekoui.config.page.PageData;
import anmao.mc.nekoui.screen.widget.Label;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PageSettingScreen extends ScreenCore {
    protected final int lineHeight = 28;
    protected final Logger LOGGER = LogUtils.getLogger();
    public EditBox idEditBox,nameEditBox,projectNumberEditBox, innerRadiusEditBox, outerRadiusEditBox, keyEditBox,textNormalColorEditBox,textHighlightColorEditBox,backgroundNormalColorEditBox,backgroundHighlightColorEditBox;
    public DropDownListBox projectId;
    public PageSettingScreen() {
        super("screen.nekoui.page_setting");
    }

    @Override
    protected void init() {
        super.init();
        int lx = 16;
        int rx = width/2 + 16;
        int py = 16;
        int lbg = 0x55646464;
        int lsc = 0x83838383;
        int lt = 0xffffffff;
        int ts = Color.RED.getRGB();

        Label tmpLabel = addRenderableWidget(new Label(lx,py,-2,12,getComponent("label.select"),lbg,lt));
        addRenderableWidget(new DropDownListBox(new DT_XYWH(lx,py + tmpLabel.getHeight(),100,12),getComponent("select_page"),getConfigData()));

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(lx,py,-2,12,getComponent("label.id"), lbg,lt));
        idEditBox = new EditBox(font,lx,py + tmpLabel.getHeight(),90,12,getComponent("id_input"));
        addRenderableWidget(idEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(lx,py,-2,12,getComponent("label.name"),lbg,lt));
        nameEditBox = new EditBox(font,lx,py + tmpLabel.getHeight(),90,12,getComponent("name_input"));
        addRenderableWidget(nameEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(lx,py,-2,12,getComponent("label.number"),lbg,lt));
        projectNumberEditBox = new EditBox(font,lx,py+tmpLabel.getHeight(),24,12,getComponent("number_input"));
        addRenderableWidget(projectNumberEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(lx,py,-2,12,getComponent("label.inner_radius"),lbg,lt));
        innerRadiusEditBox = new EditBox(font,lx,py+tmpLabel.getHeight(),24,12,getComponent("inner_radius_input"));
        addRenderableWidget(innerRadiusEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(lx,py,-2,12,getComponent("label.outer_radius"),lbg,lt));
        outerRadiusEditBox = new EditBox(font,lx,py + tmpLabel.getHeight(),24,12,getComponent("outer_radius_input"));
        addRenderableWidget(outerRadiusEditBox);


        py = 16;
        tmpLabel = addRenderableWidget(new Label(rx,py,-2,12,getComponent("label.project"),lbg,lt));
        projectId = new DropDownListBox(new DT_XYWH(rx,py + tmpLabel.getHeight(),64,12),getComponent("project"), getProjects());
        projectId.setLine(7);
        addRenderableWidget(projectId);

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(rx,py,-2,12,getComponent("label.key"),lbg,lt));
        keyEditBox = new EditBox(font,rx,py + tmpLabel.getHeight(),100,12,getComponent("key_input"));
        addRenderableWidget(keyEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(rx,py,-2,12,getComponent("label.text_normal_color"),lbg,lt));
        textNormalColorEditBox = new EditBox(font,rx,py + tmpLabel.getHeight(),100,12,getComponent("text_normal_color_input"));
        addRenderableWidget(textNormalColorEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(rx,py,-2,12,getComponent("label.text_highlight_color"),lbg,lt));
        textHighlightColorEditBox = new EditBox(font,rx,py+ tmpLabel.getHeight(),100,12,getComponent("text_highlight_color_input"));
        addRenderableWidget(textHighlightColorEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(rx,py,-2,12,getComponent("label.background_normal_color"),lbg,lt));
        backgroundNormalColorEditBox = new EditBox(font,rx,py+ tmpLabel.getHeight(),100,12,getComponent("background_normal_color_input"));
        addRenderableWidget(backgroundNormalColorEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(new Label(rx,py,-2,12,getComponent("label.background_highlight_color"),lbg,lt));
        backgroundHighlightColorEditBox = new EditBox(font,rx,py+ tmpLabel.getHeight(),100,12,getComponent("background_highlight_color_input"));
        addRenderableWidget(backgroundHighlightColorEditBox);


        py += 30;
        SquareImageButton save = new SquareImageButton(new DT_XYWH(rx,py,32,16),getComponent("save"),this::saveConfig);
        save.setBgUsualColor(lbg);
        save.setBgSelectColor(lsc);
        save.setTextSelectColor(ts);
        save.setTextUsualColor(lt);
        addRenderableWidget(save);

        SquareImageButton delete =new SquareImageButton(new DT_XYWH(rx +save.getWidth() + 12,py,32,16),getComponent("delete_page"),this::delete);

        delete.setBgUsualColor(lbg);
        delete.setBgSelectColor(lsc);
        delete.setTextSelectColor(ts);
        delete.setTextUsualColor(lt);
        addRenderableWidget(delete);
    }
    public List<DT_ListBoxData> getConfigData(){
        List<DT_ListBoxData> data = new ArrayList<>();
        PageConfig.INSTANCE.getDatas().forEach((s, menuData) -> data.add(new DT_ListBoxData(Component.literal(s),s,this::setData)));
        return data;
    }
    public List<DT_ListBoxData> getProjects(){
        List<DT_ListBoxData> data = new ArrayList<>();
        if (!projectNumberEditBox.getValue().isEmpty()) {
            int pn = Integer.parseInt(projectNumberEditBox.getValue());
            if (pn > 0) {
                for (int i = 0; i < pn; i++) {
                    data.add(new DT_ListBoxData(getComponent("project_list").copy().append(":"+i), String.valueOf(i), this::setProjectData));
                }
            }
        }
        return data;
    }
    public void setData(Object v){
        if (v instanceof String id){
            PageData pageData = PageConfig.INSTANCE.getPageData(id);
            if (pageData != null){
                idEditBox.setValue(id);
                nameEditBox.setValue(pageData.getTitle());
                projectNumberEditBox.setValue(String.valueOf(pageData.projectNumber));
                innerRadiusEditBox.setValue(String.valueOf(pageData.innerRadius));
                outerRadiusEditBox.setValue(String.valueOf(pageData.outerRadius));
                projectId.setDataList(getProjects());
                projectId.setLine(7);
            }
        }
    }
    public void setProjectData(Object v){
        if (v instanceof String id){
            PageData pageData = PageConfig.INSTANCE.getPageData(idEditBox.getValue());
            if (pageData != null) {
                PageData.ProjectData pd = pageData.getProject(id);
                if (pd != null) {
                    keyEditBox.setValue(checkValue(pd.key, "project key"));
                    textNormalColorEditBox.setValue(checkValue(pd.textNormalColor, "0xff00ffff"));
                    textHighlightColorEditBox.setValue(checkValue(pd.textHighlightColor, "0xff00ff00"));
                    backgroundNormalColorEditBox.setValue(checkValue(pd.backgroundNormalColor, "0x70000000"));
                    backgroundHighlightColorEditBox.setValue(checkValue(pd.backgroundHighlightColor, "0x50ffffff"));
                }else {
                    keyEditBox.setValue( "project key");
                    textNormalColorEditBox.setValue("");
                    textHighlightColorEditBox.setValue("");
                    backgroundNormalColorEditBox.setValue("");
                    backgroundHighlightColorEditBox.setValue( "");
                }
            }
        }
    }
    public void saveConfig() {
        String id = idEditBox.getValue();
        if (id.isEmpty()) {
            return;
        }
        PageData newPageData = PageConfig.INSTANCE.getPageData(id) ;
        if (newPageData == null) newPageData = new PageData();
        newPageData.setTitle(checkValue(nameEditBox.getValue(), "page title"));
        int pn = Integer.parseInt(checkValue(projectNumberEditBox.getValue(), "7"));
        if (pn > 0) {
            newPageData.setProjectNumber(pn);
            newPageData.setInnerRadius(Integer.parseInt(checkValue(innerRadiusEditBox.getValue(), "20")));
            newPageData.setOuterRadius(Integer.parseInt(checkValue(outerRadiusEditBox.getValue(), "80")));
            if (newPageData.getProjects() == null) newPageData.setProjects(new HashMap<>());
            

            if (projectId.getNowSelectIndex() >= 0 && projectId.getNowSelectIndex() < projectId.getDataList().size()) {
                String pid = (String) projectId.getSelectValue();
                PageData.ProjectData projectData = new PageData.ProjectData();
                projectData.key = checkValue(keyEditBox.getValue(), "project key");
                projectData.textNormalColor = checkValue(textNormalColorEditBox.getValue(), "0xff00ffff");
                projectData.textHighlightColor = checkValue(textHighlightColorEditBox.getValue(), "0xff00ff00");
                projectData.backgroundNormalColor = checkValue(backgroundNormalColorEditBox.getValue(), "0x70000000");
                projectData.backgroundHighlightColor = checkValue(backgroundHighlightColorEditBox.getValue(), "0x50ffffff");
                newPageData.projects.put(pid, projectData);
            }

            PageConfig.INSTANCE.getDatas().put(id, newPageData);
            PageConfig.INSTANCE.save();
            Minecraft.getInstance().setScreen(new PageSettingScreen());

        }

    }
    public void delete(){
        String id = idEditBox.getValue();
        if (!id.isEmpty()){
            if (PageConfig.INSTANCE.getDatas().get(id) != null){
                PageConfig.INSTANCE.getDatas().remove(id);
            }
        }
        Minecraft.getInstance().setScreen(new PageSettingScreen());
    }

    public String checkValue(String v,String defaultValue){
        if (v == null || v.isEmpty()){
            return defaultValue;
        }
        return v;
    }
/*
    @Override
    public void onClose() {
        saveChange();
        super.onClose();
    }
    public void saveChange(){
        Gson gson = new Gson();
        String jsonString = gson.toJson(pageDataMap);
        try {
            FileWriter writer = new FileWriter(PageConfig.file);

            try {
                writer.write(jsonString);
                PageConfig.INSTANCE.setData(pageDataMap);
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

 */
}
