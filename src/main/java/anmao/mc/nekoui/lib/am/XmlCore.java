package anmao.mc.nekoui.lib.am;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.annotation.Nullable;
import java.util.Objects;

public class XmlCore {
    private static Document DOCUMENT;
    public XmlCore(Document document){
        DOCUMENT = document;
    }

    public Element addElement(String s){
        return addElement(s,null,null);
    }
    public Element addElement(String s, @Nullable Element element){
        return addElement(s,element,null);
    }
    public Element addElement(String s, @Nullable String textNode){
        return addElement(s,null,textNode);
    }
    public Element addElement(String s, @Nullable Element element, @Nullable String textNode){
        Element a = DOCUMENT.createElement(s);
        if (textNode != null){
            Text text = DOCUMENT.createTextNode(textNode);
            a.appendChild(text);
        }
        Objects.requireNonNullElse(element, DOCUMENT).appendChild(a);
        return a;
    }
    public Element getElement(Element element,String s){
        return getElement(element,s,0);
    }
    public Element getElement(Element element,String s,int index){
        return (Element) element.getElementsByTagName(s).item(index);
    }
    public String getText(Element element,String s){
        return getText(element,s,0);
    }
    public String getText(Element element,String s,int index){
        return element.getElementsByTagName(s).item(index).getTextContent();
    }
}
