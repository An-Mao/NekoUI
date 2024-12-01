package nws.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.dev.core.json._JsonConfig;
import nws.mc.nekoui.config.Configs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class MenuConfig extends _JsonConfig<Map<String, MenuData>> {
    public static final String oldfile = Configs.ConfigDir+"menu.json";
    public static final String file = Configs.ConfigDir+"projects.json";
    public static final MenuConfig INSTANCE = new MenuConfig();
    public MenuConfig() {
        super(file, defaultConfig(), new TypeToken<>(){});
    }
    public void setData(Map<String, MenuData> data){
        this.datas = data;
    }

    @Override
    public Map<String, MenuData> getDatas() {
        if (datas== null) return new HashMap<>();
        return super.getDatas();
    }

    public static String defaultConfig() {
        File file = new File(oldfile);
        if (file.exists()) {
            Path path = Paths.get(oldfile);
            String content;
            try {
                content = new String(Files.readAllBytes(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!file.renameTo(new File( Configs.ConfigDir+"menu.del.json"))){
                if (!file.delete()){
                    throw new RuntimeException("delete old file error");
                }
            }
            return content;
        }
        return """
                {
                  "1": {
                    "name": "slot?0",
                    "type": 2,
                    "value": "49"
                  },
                  "2": {
                    "name": "slot?1",
                    "type": 2,
                    "value": "50"
                  },
                  "3": {
                    "name": "slot?2",
                    "type": 2,
                    "value": "51"
                  },
                  "4": {
                    "name": "slot?3",
                    "type": 2,
                    "value": "52"
                  },
                  "5": {
                    "name": "slot?4",
                    "type": 2,
                    "value": "53"
                  },
                  "6": {
                    "name": "slot?5",
                    "type": 2,
                    "value": "54"
                  },
                  "7": {
                    "name": "slot?6",
                    "type": 2,
                    "value": "55"
                  },
                  "8": {
                    "name": "slot?7",
                    "type": 2,
                    "value": "56"
                  },
                  "9": {
                    "name": "slot?8",
                    "type": 2,
                    "value": "57"
                  },
                  "10": {
                    "name": "send message",
                    "type": 0,
                    "value": "test"
                  },
                  "11": {
                    "name": "send command",
                    "type": 1,
                    "value": "time set day"
                  },
                  "12": {
                    "name": "item?minecraft:apple",
                    "type": 1,
                    "value": "time set night"
                  }
                }""";
    }
}
