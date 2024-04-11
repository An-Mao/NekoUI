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
                }""", new TypeToken<>(){});
    }
    public void setData(Map<String, MenuData> data){
        this.datas = data;
    }
}
