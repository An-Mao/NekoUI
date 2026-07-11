package dev.anye.mc.nekoui.register.listen;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.am.listen.Listen;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.config.ban_screen.BanScreenConfig;
import dev.anye.mc.nekoui.config.health_bar.HealthBarConfig;
import dev.anye.mc.nekoui.config.hide_hud.HideHudConfig;
import dev.anye.mc.nekoui.config.hotbar.HotBarConfig;
import dev.anye.mc.nekoui.config.menu.MenuPageConfig;
import dev.anye.mc.nekoui.config.menu.MenuPageIO;
import dev.anye.mc.nekoui.config.menu.MenuProjectIO;
import dev.anye.mc.nekoui.config.menu.MenuScreenConfig;
import dev.anye.mc.nekoui.config.mob_direction.MobDirectionConfig;
import dev.anye.mc.nekoui.config.screen_element.ScreenRenderIO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NekoUIListen extends Listen {
	public static final String PATH = "assets/nekoui/html/";
	public static final String HOME_PAGE = 	PATH + "index.html";

	public static final String SCREEN_ELEMENT = "screenElement";
	public static final String MENU_PAGE = "menuPage";
	public static final String MENU_PROJECT = "menuProject";



	public NekoUIListen() {
		super(PATH, NekoUI.MOD_ID);
	}

	@Override
	public void post(HttpExchange exchange) {
		try {
			String postRawData = new String(exchange.getRequestBody().readAllBytes(),StandardCharsets.UTF_8);
			this.logger.debug("neko ui post data => {}",postRawData);
			PostData postData = gson.fromJson(postRawData, PostData.class);
			if (postData.type.contains(":")){
				String[] p = postData.type.split(":");
				if (p.length == 2) {
					switch (p[0]) {
						case "load" -> config(exchange, p[1], null);
						case "save" -> config(exchange, p[1], postData.p());
						case "list" -> {
							switch (p[1]) {
								case SCREEN_ELEMENT -> _files(exchange, Configs.ConfigDir_ScreenElement);
								case MENU_PAGE -> _files(exchange, Configs.ConfigDir_MenuPage);
								case MENU_PROJECT -> _files(exchange, Configs.ConfigDir_MenuProject);
								default -> missType(exchange);
							}
						}
						default -> missType(exchange);
					}
				}else if (p.length == 3){
					switch (p[0]) {
						case "load" -> subConfig(exchange, p[1],p[2],null);
						case "save" -> subConfig(exchange, p[1],p[2], postData.p());
						case null, default -> missType(exchange);
					}
				}
			}
		}catch(IOException e){
			logger.warn("Error load \n{}",e.getMessage());
		}
	}

	public void subConfig(HttpExchange exchange,String p,String s,String data) throws IOException {
		if (p.isBlank() || s.isBlank()) missData(exchange);
		else {
			switch (p){
				case SCREEN_ELEMENT -> _config(exchange,new ScreenRenderIO(s),data);
				case MENU_PAGE -> _config(exchange,new MenuPageIO(s),data);
				case MENU_PROJECT-> _config(exchange,new MenuProjectIO(s),data);
				default -> missData(exchange);
			}
		}
	}


	public void config(HttpExchange exchange,String p,String data) throws IOException {
		switch (p){
			case "config" -> _config(exchange,Config.INSTANCE,data);
			case "banScreen" -> _config(exchange,BanScreenConfig.I,data);
			case "healthBar" -> _config(exchange,HealthBarConfig.I,data);
			case "hideHub" -> _config(exchange,HideHudConfig.I,data);
			case "hotBar" -> _config(exchange,HotBarConfig.INSTANCE,data);
			case "menuScreen" -> _config(exchange,MenuScreenConfig.INSTANCE,data);
			case "menuPage" -> _config(exchange,MenuPageConfig.I,data);
			case "mobDirection" -> _config(exchange,MobDirectionConfig.I,data);
			case null, default -> missType(exchange);
		}
	}
	public void saveSuccess(HttpExchange exchange) throws IOException {
		sendJson(exchange,200,ok("saved"));
	}
	public void missData(HttpExchange exchange) throws IOException {
		sendJson(exchange,200,error("400","miss data"));
	}
	public void missType(HttpExchange exchange) throws IOException {
		sendJson(exchange,200,error("400","miss type"));
	}

	public void _config(HttpExchange exchange,_JsonConfig<?> config,String data) throws IOException {
		if (data == null) _load(exchange,config);
		else _save(exchange,config,data);
	}
	public <T> void _save(HttpExchange exchange,_JsonConfig<T> config,String newData) throws IOException {
		T configData = gson.fromJson(newData, new TypeToken<>() {});
		if (configData == null) missData(exchange);
		else {
			config.save(configData);
			saveSuccess(exchange);
		}

	}
	public void _load(HttpExchange exchange,_JsonConfig<?> config) throws IOException {
		sendJson(exchange,200,gson.toJsonTree(config.getData()));
	}
	public void _files(HttpExchange exchange,String dir) throws IOException {
		List<String> files = new ArrayList<>();
		_File.getFiles(dir, ".json").forEach(p->files.add(p.getFileName().toString()));
		sendJson(exchange,200,gson.toJsonTree(files));
	}

	public record PostData(String type,String token,String p) {
	}
}