package anmao.mc.nekoui.lib.dat;

import anmao.mc.nekoui.lib.am.XmlGet;
import org.w3c.dom.Element;

public class CustomDataTypes_InfoConfig_Key_AK {
    private final int TYPE_USUAL = 0;
    private final int TYPE_NEED_KEY = 1;
    private final XmlGet xmlGet = new XmlGet();
    private int type;
    private int valueType;
    private String key;
    private String needKey;
    private String needValue;
    public void setDat(Element element){
        this.type = xmlGet.getInt(element,"keyType");
        this.valueType = xmlGet.getInt(element,"valueType");
        this.key = xmlGet.getText(element,"key");
        if (this.type == TYPE_NEED_KEY){
            this.needKey = xmlGet.getText(element,"needKey");
            this.needValue = xmlGet.getText(element,"needValue");
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

    public String getNeedValue() {
        return needValue;
    }
}
