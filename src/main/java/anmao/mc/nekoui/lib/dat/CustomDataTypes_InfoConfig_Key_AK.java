package anmao.mc.nekoui.lib.dat;

import org.w3c.dom.Element;

public class CustomDataTypes_InfoConfig_Key_AK {
    private int type;
    private int valueType;
    private String key;
    private String needKey;
    private String needEqual;
    public void setDat(Element element){
        this.type = Integer.parseInt(element.getElementsByTagName("keyType").item(0).getTextContent());
        this.valueType = Integer.parseInt(element.getElementsByTagName("valueType").item(0).getTextContent());
        this.key = element.getElementsByTagName("key").item(0).getTextContent();
        if (this.type == 1){
            this.needKey = element.getElementsByTagName("needKey").item(0).getTextContent();
            this.needEqual = element.getElementsByTagName("needEqual").item(0).getTextContent();
        }
    }

    public int getType() {
        return type;
    }

    public int getValueType() {
        return valueType;
    }

    public String getKey() {
        return key;
    }

    public String getNeedKey() {
        return needKey;
    }

    public String getNeedEqual() {
        return needEqual;
    }
}
