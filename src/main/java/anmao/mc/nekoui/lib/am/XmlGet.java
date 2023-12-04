package anmao.mc.nekoui.lib.am;

import org.w3c.dom.Element;

public class XmlGet {

    public Element getElement(Element element, String s){
        return getElement(element,s,0);
    }
    public Element getElement(Element element,String s,int index){
        return (Element) element.getElementsByTagName(s).item(index);
    }
    public int getInt(Element element, String s){
        return Integer.parseInt(getText(element, s));
    }
    public double getDouble(Element element, String s){
        return Double.parseDouble(getText(element, s));
    }
    public float getFloat(Element element, String s){
        return Float.parseFloat(getText(element, s));
    }
    public String getText(Element element,String s){
        return getText(element,s,0);
    }
    public String getText(Element element,String s,int index){
        return element.getElementsByTagName(s).item(index).getTextContent();
    }
}
