package anmao.mc.nekoui.lib.dat;

import org.w3c.dom.Element;

import java.util.Objects;

public class CustomDataTypes_InfoConfig_Key {
    private int tipType;
    private String tipId;
    private int tipX;
    private int tipY;
    private int infoX;
    private int infoY;
    private int infoColor;
    private int keyType;
    private String key;
    private String[] keyArray;
    public void setDat(Element element){
        if (Objects.equals(element.getElementsByTagName("tipType").item(0).getTextContent(), "icon")){
            this.tipType = 1;
        }else {
            this.tipType = 0;
        }
        this.tipId =  element.getElementsByTagName("tipId").item(0).getTextContent();
        this.tipX = Integer.parseInt(element.getElementsByTagName("tipX").item(0).getTextContent());
        this.tipY = Integer.parseInt(element.getElementsByTagName("tipY").item(0).getTextContent());
        this.infoX = Integer.parseInt(element.getElementsByTagName("infoX").item(0).getTextContent());
        this.infoY = Integer.parseInt(element.getElementsByTagName("infoY").item(0).getTextContent());
        this.infoColor = Integer.parseInt(element.getElementsByTagName("infoColor").item(0).getTextContent(), 16);
        this.keyType = Integer.parseInt(element.getElementsByTagName("keyType").item(0).getTextContent());
        this.key =  element.getElementsByTagName("key").item(0).getTextContent();
        this.keyArray = this.key.split("#");
    }

    public String[] getKeyArray() {
        return keyArray;
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
