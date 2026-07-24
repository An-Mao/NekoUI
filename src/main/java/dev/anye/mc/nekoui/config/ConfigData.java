package dev.anye.mc.nekoui.config;

public class ConfigData {
	boolean putDefault;
	boolean renderScreenElement;
	boolean outputGuiId;
	boolean outputScreenPathName;
	boolean menu;
	boolean autoPage;
	public static ConfigData DEFAULT = new ConfigData(
			true,
			true,
			false,
			false,
			true,
			false
	);
	public ConfigData(
			boolean putDefault,
			boolean renderScreenElement,
			boolean outputGuiId,
			boolean outputScreenPathName,
			boolean menu,
			boolean autoPage){
		this.putDefault = putDefault;
		this.renderScreenElement = renderScreenElement;
		this.outputGuiId = outputGuiId;
		this.outputScreenPathName = outputScreenPathName;
		this.menu = menu;
		this.autoPage = autoPage;
	}
	public boolean putDefault(){
		return putDefault;
	}
	public void setPutDefault(boolean putDefault){
		this.putDefault = putDefault;
	}
	public boolean renderScreenElement(){
		return renderScreenElement;
	}
	public void setRenderScreenElement(boolean renderScreenElement){
		this.renderScreenElement = renderScreenElement;
	}
	public boolean outputGuiId(){
		return outputGuiId;
	}
	public void setOutputGuiId(boolean outputGuiId){
		this.outputGuiId = outputGuiId;
	}
	public boolean outputScreenPathName(){
		return outputScreenPathName;
	}
	public void setOutputScreenPathName(boolean outputScreenPathName){
		this.outputScreenPathName = outputScreenPathName;
	}
	public boolean menu(){
		return menu;
	}
	public void setMenu(boolean menu){
		this.menu = menu;
	}
	public boolean autoPage(){
		return autoPage;
	}
	public void setAutoPage(boolean autoPage){
		this.autoPage = autoPage;
	}
}
