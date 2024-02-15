package anmao.mc.nekoui.lib.dat;

import anmao.mc.nekoui.lib.am.XmlCore;
import anmao.mc.nekoui.lib.am.XmlGet;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Objects;

public class CustomDataTypes_InfoConfig_Key {
    private final int TYPE_KEY_CUSTOM = 0;
    private final int TYPE_KEY_ONE = 1;
    private final int TYPE_KEY_MORE = 2;
    private final int TYPE_TIP_TEXT = 0;
    private final int TYPE_TIP_ICON = 1;
    private final XmlGet xmlGet = new XmlGet();
    private int tipType;
    private String tipId;
    private int tipX;
    private int tipY;
    private int infoX;
    private int infoY;
    private int infoColor;
    private int keyType;
    private String key;
    private CustomDataTypes_InfoConfig_Key_AK[] kk;
    public void setDat(Element element){
        if (Objects.equals(xmlGet.getText(element,"tipType"), "icon")){
            this.tipType = TYPE_TIP_ICON;
        }else {
            this.tipType = TYPE_TIP_TEXT;
        }
        this.tipId = xmlGet.getText(element,"tipId");
        this.tipX = xmlGet.getInt(element,"tipX");
        this.tipY = xmlGet.getInt(element,"tipY");
        this.infoX = xmlGet.getInt(element,"infoX");
        this.infoY = xmlGet.getInt(element,"infoY");
        this.infoColor = xmlGet.getIntHex(element,"infoColor");
        this.keyType = xmlGet.getInt(element,"keyType");
        if (this.keyType == TYPE_KEY_MORE) {
            Element k = xmlGet.getElement(element,"keys");
            NodeList keys = k.getElementsByTagName("k");
            this.kk = new CustomDataTypes_InfoConfig_Key_AK[keys.getLength()];
            for (int i = 0; i < keys.getLength(); i++) {
                Element ck = (Element) keys.item(i);
                if (this.kk[i] == null){
                    this.kk[i] = new CustomDataTypes_InfoConfig_Key_AK();
                }
                this.kk[i].setDat(ck);
            }
        }else {
            this.key = xmlGet.getText(element,"key");
            //this.keyArray = this.key.split("#");
        }
    }

    public CustomDataTypes_InfoConfig_Key_AK[] getKk() {
        return kk;
    }
    public boolean isIcon(){
        return  this.tipType == 1;
    }

    public int getTipType() {
        return tipType;
    }

    public String getTipId() {
        return tipId;
    }

    public int getTipX() {
        return tipX;
    }

    public int getTipY() {
        return tipY;
    }

    public int getInfoX() {
        return infoX;
    }

    public int getInfoY() {
        return infoY;
    }

    public int getInfoColor() {
        return infoColor;
    }

    public int getKeyType() {
        return keyType;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
