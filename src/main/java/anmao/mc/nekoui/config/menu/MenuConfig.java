package anmao.mc.nekoui.config.menu;

import anmao.mc.amlib.json.JsonConfig;
import anmao.mc.nekoui.config.Configs;
import com.google.gson.reflect.TypeToken;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class MenuConfig extends JsonConfig<Map<String, MenuData>> {
    public static final String file = Configs.ConfigDir+"menu.json";
    public static final MenuConfig INSTANCE = new MenuConfig();
    public MenuConfig() {
        super(file, """
                {
                  "1": {
                    "name": "快速切换1",
                    "type": 2,
                    "value": "49"
                  },
                  "2": {
                    "name": "快速切换2",
                    "type": 2,
                    "value": "50"
                  },
                  "3": {
                    "name": "快速切换3",
                    "type": 2,
                    "value": "51"
                  },
                  "4": {
                    "name": "快速切换4",
                    "type": 2,
                    "value": "52"
                  },
                  "5": {
                    "name": "快速切换5",
                    "type": 2,
                    "value": "53"
                  },
                  "6": {
                    "name": "快速切换6",
                    "type": 2,
                    "value": "54"
                  },
                  "7": {
                    "name": "快速切换7",
                    "type": 2,
                    "value": "55"
                  },
                  "8": {
                    "name": "快速切换8",
                    "type": 2,
                    "value": "56"
                  },
                  "9": {
                    "name": "快速切换9",
                    "type": 2,
                    "value": "57"
                  },
                  "10": {
                    "name": "发送普通消息",
                    "type": 0,
                    "value": "test"
                  },
                  "11": {
                    "name": "发送指令消息",
                    "type": 1,
                    "value": "time set day"
                  }
                }""", new TypeToken<>(){});
    }
    public void setData(Map<String, MenuData> data){
        this.datas = data;
    }
}
