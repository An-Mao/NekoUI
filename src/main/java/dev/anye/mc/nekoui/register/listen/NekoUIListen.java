package dev.anye.mc.nekoui.register.listen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;

import dev.anye.mc.cores.am.listen.Listen;
import dev.anye.mc.nekoui.NekoUI;

public class NekoUIListen extends Listen {
	public static final String HOME_PAGE = "assets/nekoui/html/index.html";
	public NekoUIListen() {
		super(NekoUI.MOD_ID);
	}

	@Override
	public void context(HttpExchange httpExchange) {
		try{
			switch (httpExchange.getRequestMethod()) {
				case GET:{
					this.sendResource(httpExchange, HOME_PAGE, this.mime(HOME_PAGE));
				}
					break;
				case POST:{
					String postRawData = new String(httpExchange.getRequestBody().readAllBytes(),StandardCharsets.UTF_8);
					this.logger.debug("neko ui post data => {}",postRawData);

				}
					break;
				default:
					break;
			}
		} catch (IOException _) {
			//
		}
	}

	public record PostData(String type,String token) {
	}
}
