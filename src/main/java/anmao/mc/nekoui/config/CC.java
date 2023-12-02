package anmao.mc.nekoui.config;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.lib.am.XmlCore;
import anmao.mc.nekoui.lib.am._Sys;
import anmao.mc.nekoui.lib.dat.CustomDataTypes_InfoConfig_Key;
import anmao.mc.nekoui.lib.dat.CustomDataTypes_InfoConfig_Text;
import net.minecraft.resources.ResourceLocation;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CC {
    private static File CONFIG_FILE;
    private static String DIR_RUN;
    private static String DIR_CONFIG;

    private static String FILE_CONFIG_ITEM;
    private static Map<String,Element> kv = new HashMap<>();

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
    public static HashMap<String, ResourceLocation> infoIcons = new HashMap<>();
    public static HashMap<String, CustomDataTypes_InfoConfig_Text> infoTexts = new HashMap<>();
    public static void _start(){
        DIR_RUN = System.getProperty("user.dir");
        DIR_CONFIG = DIR_RUN+"\\NekoConfig";
        File folder = new File(DIR_CONFIG);
        if (!folder.exists()){
            if (!folder.mkdir()){
                NekoUI.LOGGER.debug("Create Dir failed");
                //System.exit(-9999);
            }
        }
        FILE_CONFIG_ITEM = DIR_CONFIG +"\\NekoUI.xml";
        CONFIG_FILE = new File(FILE_CONFIG_ITEM);
        if (!CONFIG_FILE.exists()){
            reSet();
        }
        _load();
    }
    public static void _load(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(CONFIG_FILE);

            XmlCore xmlCore = new XmlCore(document);
            Element config = document.getDocumentElement();
            //------------------------------------------
            Element infoKey = xmlCore.getElement(config,"infoKey");
            NodeList tags = infoKey.getElementsByTagName("s");
            infoKeys = new CustomDataTypes_InfoConfig_Key[tags.getLength()];
            for (int i = 0; i < tags.getLength(); i++) {
                Element sElement = (Element) tags.item(i);
                if (infoKeys[i] == null){
                    infoKeys[i] = new CustomDataTypes_InfoConfig_Key();
                }
                infoKeys[i].setDat(sElement);
            }
            System.out.println("----------------------------");
            System.out.println(Arrays.toString(infoKeys));


            //-------------------------------------------
            Element infoIco = xmlCore.getElement(config,"infoIcon");
            NodeList icos = infoIco.getElementsByTagName("ico");
            infoIcons.clear();
            for (int i = 0; i < icos.getLength(); i++) {
                Element sElement = (Element) icos.item(i);
                infoIcons.put(sElement.getElementsByTagName("id").item(0).getTextContent(),
                            new ResourceLocation(
                                    sElement.getElementsByTagName("mod").item(0).getTextContent(),
                                    sElement.getElementsByTagName("path").item(0).getTextContent()));
            }
            System.out.println("----------------------------");
            System.out.println(infoIcons);



            //--------------------------------------------
            Element infoTxt = xmlCore.getElement(config,"infoText");
            NodeList txts = infoTxt.getElementsByTagName("txt");
            infoTexts.clear();
            for (int i = 0; i < txts.getLength(); i++) {
                Element sElement = (Element) txts.item(i);
                CustomDataTypes_InfoConfig_Text customDataTypesInfoConfigText = new CustomDataTypes_InfoConfig_Text();
                customDataTypesInfoConfigText.setDat(sElement);
                infoTexts.put(sElement.getElementsByTagName("id").item(0).getTextContent(),
                        customDataTypesInfoConfigText);
            }
            System.out.println("----------------------------");
            System.out.println(infoTexts);
            Element hud = xmlCore.getElement(config,"hud");
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
            e.printStackTrace();
        }
    }

    private static void reSet(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();
            XmlCore xmlCore = new XmlCore(document);
            Element aDefault = xmlCore.addElement("default");
            Element hud = xmlCore.addElement("hud",aDefault);

            Element infoKey = xmlCore.addElement("infoKey",aDefault);
            //
            Element sk = xmlCore.addElement("s",infoKey);
            xmlCore.addElement("tipType",sk,"text");
            xmlCore.addElement("tipId",sk,"speed");
            xmlCore.addElement("tipX",sk,"0");
            xmlCore.addElement("tipY",sk,"0");
            xmlCore.addElement("infoX",sk,"0");
            xmlCore.addElement("infoY",sk,"0");
            xmlCore.addElement("infoColor",sk,"336699");
            xmlCore.addElement("keyType",sk,"2");
            xmlCore.addElement("key",sk,"Attributes#Name#minecraft:generic.movement_speed#Base");
            //
            sk = xmlCore.addElement("s",infoKey);
            xmlCore.addElement("tipType",sk,"icon");
            xmlCore.addElement("tipId",sk,"health");
            xmlCore.addElement("tipX",sk,"0");
            xmlCore.addElement("tipY",sk,"0");
            xmlCore.addElement("infoX",sk,"0");
            xmlCore.addElement("infoY",sk,"0");
            xmlCore.addElement("infoColor",sk,"336699");
            xmlCore.addElement("keyType",sk,"1");
            xmlCore.addElement("key",sk,"Health");
            //
            sk = xmlCore.addElement("s",infoKey);
            xmlCore.addElement("tipType",sk,"text");
            xmlCore.addElement("tipId",sk,"lvl");
            xmlCore.addElement("tipX",sk,"0");
            xmlCore.addElement("tipY",sk,"0");
            xmlCore.addElement("infoX",sk,"0");
            xmlCore.addElement("infoY",sk,"0");
            xmlCore.addElement("infoColor",sk,"336699");
            xmlCore.addElement("keyType",sk,"1");
            xmlCore.addElement("key",sk,"XpLevel");

            Element infoText = xmlCore.addElement("infoText",aDefault);
            Element st = xmlCore.addElement("txt",infoText);
            xmlCore.addElement("id",st,"lvl");
            xmlCore.addElement("con",st,"level:");
            xmlCore.addElement("color",st,"669900");
            //
            st = xmlCore.addElement("txt",infoText);
            xmlCore.addElement("id",st,"health");
            xmlCore.addElement("con",st,"Health:");
            xmlCore.addElement("color",st,"669900");
            //
            st = xmlCore.addElement("txt",infoText);
            xmlCore.addElement("id",st,"speed");
            xmlCore.addElement("con",st,"Speed:");
            xmlCore.addElement("color",st,"669900");

            Element infoIcon = xmlCore.addElement("infoIcon",aDefault);
            Element si = xmlCore.addElement("ico",infoIcon);
            xmlCore.addElement("id",si,"exp");
            xmlCore.addElement("mod",si,"minecraft");
            xmlCore.addElement("path",si,"textures/item/experience_bottle.png");
            //
            si = xmlCore.addElement("ico",infoIcon);
            xmlCore.addElement("id",si,"health");
            xmlCore.addElement("mod",si,"minecraft");
            xmlCore.addElement("path",si,"textures/mob_effect/regeneration.png");

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
            StreamResult result = new StreamResult(CONFIG_FILE);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
