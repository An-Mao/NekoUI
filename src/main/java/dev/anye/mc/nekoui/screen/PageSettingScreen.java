package dev.anye.mc.nekoui.screen;

import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.cores.screen.widget.simple.SimpleDropDownSelectBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleEditBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleLabel;
import dev.anye.mc.nekoui.config.page.PageConfig;
import dev.anye.mc.nekoui.config.page.PageData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@OnlyIn(Dist.CLIENT)
public class PageSettingScreen extends ScreenCore {
    protected final int lineHeight = 30;
    protected final Logger LOGGER = LogUtils.getLogger();
    public SimpleEditBox idEditBox,nameEditBox,projectNumberEditBox, innerRadiusEditBox, outerRadiusEditBox, keyEditBox,textNormalColorEditBox,textHighlightColorEditBox,backgroundNormalColorEditBox,backgroundHighlightColorEditBox;
    public SimpleDropDownSelectBox projectId;
    public PageSettingScreen() {
        super("screen.nekoui.page_setting");
    }

    @Override
    protected void init() {
        super.init();
        int lx = 16;
        int rx = width/2 + 16;
        int py = 16;
        SimpleLabel tmpLabel = addRenderableWidget(createNewLabel(lx,py,-2,12,getComponent("label.select")));
        tmpLabel.setAutoWidth(true);
        addRenderableWidget(createNewSelectBox(lx,py + tmpLabel.getHeight(),100,16,getComponent("select_page"),getConfigData()));

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(lx,py,-2,12,getComponent("label.id")));
        tmpLabel.setAutoWidth(true);
        idEditBox = createNewEditBox(lx,py + tmpLabel.getHeight(),90,16,idEditBox,getComponent("id_input"));
        addRenderableWidget(idEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(lx,py,-2,12,getComponent("label.name")));
        tmpLabel.setAutoWidth(true);
        nameEditBox = createNewEditBox(lx,py + tmpLabel.getHeight(),90,16,nameEditBox,getComponent("name_input"));
        addRenderableWidget(nameEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(lx,py,-2,12,getComponent("label.number")));
        tmpLabel.setAutoWidth(true);
        projectNumberEditBox = createNewEditBox(lx,py+tmpLabel.getHeight(),24,16,projectNumberEditBox,getComponent("number_input"));
        addRenderableWidget(projectNumberEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(lx,py,-2,12,getComponent("label.inner_radius")));
        tmpLabel.setAutoWidth(true);
        innerRadiusEditBox = createNewEditBox(lx,py+tmpLabel.getHeight(),24,16,innerRadiusEditBox,getComponent("inner_radius_input"));
        addRenderableWidget(innerRadiusEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(lx,py,-2,12,getComponent("label.outer_radius")));
        tmpLabel.setAutoWidth(true);
        outerRadiusEditBox = createNewEditBox(lx,py + tmpLabel.getHeight(),24,16,outerRadiusEditBox,getComponent("outer_radius_input"));
        addRenderableWidget(outerRadiusEditBox);


        py = 16;
        tmpLabel = addRenderableWidget(createNewLabel(rx,py,-2,12,getComponent("label.project")));
        tmpLabel.setAutoWidth(true);
        projectId = createNewSelectBox(rx,py + tmpLabel.getHeight(),64,16,getComponent("project"), getProjects());
        projectId.setLine(7);
        addRenderableWidget(projectId);

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(rx,py,-2,12,getComponent("label.key")));
        tmpLabel.setAutoWidth(true);
        keyEditBox = createNewEditBox(rx,py + tmpLabel.getHeight(),100,16,keyEditBox,getComponent("key_input"));
        addRenderableWidget(keyEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(rx,py,-2,12,getComponent("label.text_normal_color")));
        tmpLabel.setAutoWidth(true);
        textNormalColorEditBox = createNewEditBox(rx,py + tmpLabel.getHeight(),100,16,textNormalColorEditBox,getComponent("text_normal_color_input"));
        addRenderableWidget(textNormalColorEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(rx,py,-2,12,getComponent("label.text_highlight_color")));
        tmpLabel.setAutoWidth(true);
        textHighlightColorEditBox = createNewEditBox(rx,py+ tmpLabel.getHeight(),100,16,textHighlightColorEditBox,getComponent("text_highlight_color_input"));
        addRenderableWidget(textHighlightColorEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(rx,py,-2,12,getComponent("label.background_normal_color")));
        tmpLabel.setAutoWidth(true);
        backgroundNormalColorEditBox = createNewEditBox(rx,py+ tmpLabel.getHeight(),100,16,backgroundNormalColorEditBox,getComponent("background_normal_color_input"));
        addRenderableWidget(backgroundNormalColorEditBox);

        py += lineHeight;
        tmpLabel = addRenderableWidget(createNewLabel(rx,py,-2,12,getComponent("label.background_highlight_color")));
        tmpLabel.setAutoWidth(true);
        backgroundHighlightColorEditBox = createNewEditBox(rx,py+ tmpLabel.getHeight(),100,16,backgroundHighlightColorEditBox,getComponent("background_highlight_color_input"));
        addRenderableWidget(backgroundHighlightColorEditBox);


        py += 30;
        SimpleButton save = createNewButton(rx,py,32,16,getComponent("save"),this::saveConfig);
        addRenderableWidget(save);

        SimpleButton delete =createNewButton(rx +save.getWidth() + 12,py,32,16,getComponent("delete_page"),this::delete);
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
                    keyEditBox.setValue(checkValue(pd.key(), "project key"));
                    textNormalColorEditBox.setValue(checkValue(pd.textNormalColor(), "auto"));
                    textHighlightColorEditBox.setValue(checkValue(pd.textHighlightColor(), "auto"));
                    backgroundNormalColorEditBox.setValue(checkValue(pd.backgroundNormalColor(), "auto"));
                    backgroundHighlightColorEditBox.setValue(checkValue(pd.backgroundHighlightColor(), "auto"));
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
                PageData.ProjectData projectData = new PageData.ProjectData(
                        checkValue(keyEditBox.getValue(), "project key"),
                        checkValue(textNormalColorEditBox.getValue(), "auto"),
                        checkValue(textHighlightColorEditBox.getValue(), "auto"),
                        checkValue(backgroundNormalColorEditBox.getValue(), "auto"),
                        checkValue(backgroundHighlightColorEditBox.getValue(), "auto")
                );
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
