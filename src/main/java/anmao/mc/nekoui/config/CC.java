package anmao.mc.nekoui.config;

import anmao.mc.nekoui.lib.Debug;
import anmao.mc.nekoui.lib.am.XmlCore;
import anmao.mc.nekoui.lib.am._Sys;
import anmao.mc.nekoui.lib.dat.CustomDataTypes_InfoConfig_Icon;
import anmao.mc.nekoui.lib.dat.CustomDataTypes_InfoConfig_Key;
import anmao.mc.nekoui.lib.dat.CustomDataTypes_InfoConfig_Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CC {
    private static File FILE_CONFIG_CONFIG;
    private static File FILE_CONFIG_HUD;
    private static String DIR_RUN;
    private static String DIR_CONFIG;

    private static String PATH_CONFIG_HUD;
    private static String PATH_CONFIG_CONFIG;
    private static String hudId = "default";
    private static Map<String,Element> kv = new HashMap<>();


    //
    public static boolean showMinecraftHud;
    public static boolean hudMobMode;
    public static boolean hudMobDynamicDisplay;
    public static int hudMobPoiShowRadius;
    public static int hudMobPoiSize;
    public static int hudMobPoiRadius;
    public static int hudMobPoiDynamicSizeMid;
    public static int hudMobPoiDynamicRadiusMid;
    public static int hudMobPoiDynamicSizeClose;
    public static int hudMobPoiDynamicRadiusClose;

    public static boolean hudInfoMode;
    public static int hudInfoX;
    public static int hudInfoY;
    public static String hudInfoSpace;


    public static boolean hudItemMode;
    public static boolean hudItemDynamicDisplay;
    public static int hudItemX;
    public static int hudItemY;
    public static int hudItemSpace;

    public static CustomDataTypes_InfoConfig_Key[] infoKeys;
    public static HashMap<String, CustomDataTypes_InfoConfig_Icon> infoIcons = new HashMap<>();
    public static HashMap<String, CustomDataTypes_InfoConfig_Text> infoTexts = new HashMap<>();
    public static void _start(){
        DIR_RUN = System.getProperty("user.dir");
        DIR_CONFIG = DIR_RUN+"\\NekoConfig";
        File folder = new File(DIR_CONFIG);
        if (!folder.exists()){
            if (!folder.mkdir()){
                Debug.error("Create Dir failed");
            }
        }
        //load
        PATH_CONFIG_CONFIG = DIR_CONFIG +"\\NekoUI-Config.xml";
        FILE_CONFIG_CONFIG = new File(PATH_CONFIG_CONFIG);
        if (!FILE_CONFIG_CONFIG.exists()){
            reSetConfig();
        }
        _loadConfig();
        PATH_CONFIG_HUD = DIR_CONFIG +"\\NekoUI-Hud.xml";
        FILE_CONFIG_HUD = new File(PATH_CONFIG_HUD);
        if (!FILE_CONFIG_HUD.exists()){
            reSetHud();
        }
        _loadHud();
    }
    public static void _loadConfig(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(FILE_CONFIG_CONFIG);
            XmlCore xmlCore = new XmlCore(document);
            //Element config = document.getDocumentElement();
            NodeList n = document.getElementsByTagName("config");
            if (n.getLength() < 1){
                Debug.error("Config load : Tag can't find, load stop");
                return;
            }

            Element config = (Element) n.item(0);
            Element hud = xmlCore.getElement(config,"hud");
            hudId = xmlCore.getText(hud,"id");
            showMinecraftHud = _Sys.getBoolean(xmlCore.getText(hud,"showMinecraftHud"));
            //mob
            Element mob = xmlCore.getElement(hud,"mob");
            hudMobMode = _Sys.getBoolean(xmlCore.getText(mob,"mode"));
            hudMobDynamicDisplay = _Sys.getBoolean(xmlCore.getText(mob,"dynamicDisplay"));
            hudMobPoiShowRadius = _Sys.getInt(xmlCore.getText(mob,"poiShowRadius"));
            hudMobPoiSize = _Sys.getInt(xmlCore.getText(mob,"poiSize"));
            hudMobPoiRadius = _Sys.getInt(xmlCore.getText(mob,"poiRadius"));
            hudMobPoiDynamicSizeMid = _Sys.getInt(xmlCore.getText(mob,"poiDynamicSizeMid"));
            hudMobPoiDynamicRadiusMid = _Sys.getInt(xmlCore.getText(mob,"poiDynamicRadiusMid"));
            hudMobPoiDynamicRadiusMid *= hudMobPoiDynamicRadiusMid;
            hudMobPoiDynamicSizeClose = _Sys.getInt(xmlCore.getText(mob,"poiDynamicSizeClose"));
            hudMobPoiDynamicRadiusClose = _Sys.getInt(xmlCore.getText(mob,"poiDynamicRadiusClose"));
            hudMobPoiDynamicRadiusClose *= hudMobPoiDynamicRadiusClose;

        } catch (Exception e) {
            Debug.error(e.getMessage());
            //e.printStackTrace();
        }
    }
    public static void _loadHud(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(FILE_CONFIG_HUD);
            XmlCore xmlCore = new XmlCore(document);
            //Element config = document.getDocumentElement();

            NodeList n = document.getElementsByTagName(hudId);
            if (n.getLength() < 1){
                Debug.error("Tag can't find, load stop");
                return;
            }

            Element config = (Element) n.item(0);
            loadInfoKey(xmlCore,config);
            loadInfoIcon(xmlCore,config);
            loadInfoText(xmlCore,config);

            Element hud = xmlCore.getElement(config,"hud");

            Element info = xmlCore.getElement(hud,"info");
            hudInfoMode = _Sys.getBoolean(xmlCore.getText(info,"mode"));
            hudInfoX = _Sys.getInt(xmlCore.getText(info,"x"));
            hudInfoY = _Sys.getInt(xmlCore.getText(info,"y"));
            hudInfoSpace = xmlCore.getText(info,"space");

            Element item = xmlCore.getElement(hud,"item");
            hudItemMode = _Sys.getBoolean(xmlCore.getText(item,"mode"));
            hudItemDynamicDisplay = _Sys.getBoolean(xmlCore.getText(item,"dynamicDisplay"));
            hudItemX = _Sys.getInt(xmlCore.getText(item,"x"));
            hudItemY = _Sys.getInt(xmlCore.getText(item,"y"));
            hudItemSpace = _Sys.getInt(xmlCore.getText(item,"space"));



        } catch (Exception e) {
            Debug.error(e.getMessage());
        }
    }

    private static void reSetConfig(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();
            XmlCore xmlCore = new XmlCore(document);


            Element aDefault = xmlCore.addElement("config");
            Element hud = xmlCore.addElement("hud",aDefault);
            xmlCore.addElement("showMinecraftHud",hud,"enable");
            xmlCore.addElement("id",hud,"default");

            Element mob = xmlCore.addElement("mob",hud);
            xmlCore.addElement("mode",mob,"enable");
            xmlCore.addElement("dynamicDisplay",mob,"enable");
            xmlCore.addElement("poiShowRadius",mob,"80");
            xmlCore.addElement("poiSize",mob,"3");
            xmlCore.addElement("poiRadius",mob,"22");
            xmlCore.addElement("poiDynamicSizeMid",mob,"7");
            xmlCore.addElement("poiDynamicRadiusMid",mob,"11");
            xmlCore.addElement("poiDynamicSizeClose",mob,"11");
            xmlCore.addElement("poiDynamicRadiusClose",mob,"5");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(FILE_CONFIG_CONFIG);
            transformer.transform(source, result);
        } catch (Exception e) {
            Debug.error(e.getMessage());
        }
    }
    private static void reSetHud(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();
            XmlCore xmlCore = new XmlCore(document);
            Element aDefault = xmlCore.addElement("default");
            Element hud = xmlCore.addElement("hud",aDefault);


            resetInfoKey(xmlCore,aDefault);
            resetInfoText(xmlCore,aDefault);
            resetInfoIcon(xmlCore,aDefault);

            Element info = xmlCore.addElement("info",hud);
            xmlCore.addElement("mode",info,"enable");
            xmlCore.addElement("x",info,"3");
            xmlCore.addElement("y",info,"39");
            xmlCore.addElement("space",info,"auto");


            Element item = xmlCore.addElement("item",hud);
            xmlCore.addElement("mode",item,"enable");
            xmlCore.addElement("dynamicDisplay",item,"enable");
            xmlCore.addElement("x",item,"7");
            xmlCore.addElement("y",item,"24");
            xmlCore.addElement("space",item,"17");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(FILE_CONFIG_HUD);
            transformer.transform(source, result);
        } catch (Exception e) {
            Debug.error(e.getMessage());
        }
    }
    private static void loadInfoKey(XmlCore core,Element element){
        Element infoKey = core.getElement(element,"infoKey");
        NodeList tags = infoKey.getElementsByTagName("s");
        infoKeys = new CustomDataTypes_InfoConfig_Key[tags.getLength()];
        for (int i = 0; i < tags.getLength(); i++) {
            Element sElement = (Element) tags.item(i);
            if (infoKeys[i] == null){
                infoKeys[i] = new CustomDataTypes_InfoConfig_Key();
            }
            infoKeys[i].setDat(sElement);
        }
    }
    private static void resetInfoKey(XmlCore core,Element element){
        Element infoKey = core.addElement("infoKey",element);
        //addInfoKey(core,infoKey, new String[]{"icon","speed","0","0","15","0","336699","2","Attributes#Name#minecraft:generic.movement_speed#Base"});
        addInfoKey(core,infoKey, new String[]{
                "icon","damage","0","15","15","15","336699","0","atkWithItem"
        });
        addInfoKey(core,infoKey, new String[]{
                "icon","exp","0","30","15","30","336699","1","XpLevel"
        });
        addInfoKey(core,infoKey, new String[]{
                "icon","hunger","0","40","15","40","336699","0","hunger"
        });
        addInfoKey(core,infoKey, new String[]{
                "icon","luck","0","50","15","50","336699","0","luck"
        });
        addInfoKey(core,infoKey, new String[]{
                "icon","health","0","60","15","60","336699","0","health"
        });
    }
    private static void loadInfoText(XmlCore core,Element element){
        Element infoTxt = core.getElement(element,"infoText");
        NodeList txts = infoTxt.getElementsByTagName("txt");
        infoTexts.clear();
        for (int i = 0; i < txts.getLength(); i++) {
            Element sElement = (Element) txts.item(i);
            CustomDataTypes_InfoConfig_Text customDataTypesInfoConfigText = new CustomDataTypes_InfoConfig_Text();
            customDataTypesInfoConfigText.setDat(sElement);
            infoTexts.put(sElement.getElementsByTagName("id").item(0).getTextContent(),
                    customDataTypesInfoConfigText);
        }
    }
    private static void resetInfoText(XmlCore core,Element element){
        Element infoText = core.addElement("infoText",element);
        addInfoText(core,infoText,"lvl","level:","669900");
        addInfoText(core,infoText,"health","Health:","669900");
        addInfoText(core,infoText,"speed","Speed:","669900");
    }
    private static void loadInfoIcon(XmlCore core,Element element){
        Element infoIco = core.getElement(element,"infoIcon");
        NodeList icos = infoIco.getElementsByTagName("ico");
        infoIcons.clear();
        for (int i = 0; i < icos.getLength(); i++) {
            Element sElement = (Element) icos.item(i);
            CustomDataTypes_InfoConfig_Icon cuI=new CustomDataTypes_InfoConfig_Icon();
            cuI.setDat(sElement);
            infoIcons.put(sElement.getElementsByTagName("id").item(0).getTextContent(),cuI);
        }
    }
    private static void resetInfoIcon(XmlCore core,Element element){
        Element infoIcon = core.addElement("infoIcon",element);
        addInfoIcon(core,infoIcon,"exp","10","10","minecraft","textures/item/experience_bottle.png");
        addInfoIcon(core,infoIcon,"health","10","10","minecraft","textures/mob_effect/regeneration.png");
        addInfoIcon(core,infoIcon,"hunger","10","10","minecraft","textures/mob_effect/hunger.png");
        addInfoIcon(core,infoIcon,"luck","10","10","minecraft","textures/mob_effect/luck.png");
        addInfoIcon(core,infoIcon,"damage","10","10","minecraft","textures/mob_effect/strength.png");
        addInfoIcon(core,infoIcon,"speed","10","10","minecraft","textures/mob_effect/speed.png");
    }
    private static void addInfoKey(XmlCore core,Element element,String[] key){
        Element sk = core.addElement("s",element);
        core.addElement("tipType",sk,key[0]);
        core.addElement("tipId",sk,key[1]);
        core.addElement("tipX",sk,key[2]);
        core.addElement("tipY",sk,key[3]);
        core.addElement("infoX",sk,key[4]);
        core.addElement("infoY",sk,key[5]);
        core.addElement("infoColor",sk,key[6]);
        core.addElement("keyType",sk,key[7]);
        //core.addElement("keys",sk,key[8]);
        if (!Objects.equals(key[7], "2")) {
            core.addElement("key",sk,key[8]);
        }else {
            Element ks = core.addElement("keys", sk);
            Element k = core.addElement("key", ks);
            core.addElement("valueType", k, key[8]);
            core.addElement("key", k, key[9]);
            if (Objects.equals(key[7], "2")) {
                core.addElement("needKey", k, key[10]);
                core.addElement("needEqual", k, key[11]);
            }
        }
    }
    private static void addInfoText(XmlCore core,Element element,String id ,String con ,String color){
        Element st = core.addElement("txt",element);
        core.addElement("id",st,id);
        core.addElement("con",st,con);
        core.addElement("color",st,color);
    }
    private static void addInfoIcon(XmlCore core,Element element,String id ,String width ,String height ,String mod ,String path){
        Element si = core.addElement("ico",element);
        core.addElement("id",si,id);
        core.addElement("width",si,width);
        core.addElement("height",si,height);
        core.addElement("mod",si,mod);
        core.addElement("path",si,path);
    }
}
