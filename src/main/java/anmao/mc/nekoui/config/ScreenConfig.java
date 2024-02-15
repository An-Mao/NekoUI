package anmao.mc.nekoui.config;

import anmao.mc.amlib.system._File;
import anmao.mc.nekoui.NekoUI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScreenConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String configFile = _File.getFilePath(NekoUI.ConfigDir +"screen-elements.json") ;
    private static Map<String ,ScreenJsonData> jsonData = new HashMap<>();


    public static void init(){
        System.out.println("screen config file ::"+jsonData);
        File file = new File(configFile);
        if (!file.exists()){
            reset();
        }
        load();
        System.out.println("screen config data ::"+jsonData);
    }
    private static void reset(){
        String jsonString = """
                {
                  "1": {
                    "pos": [1,0,1],
                    "element": [
                      {
                        "type": 0,
                        "parameter": "minecraft:textures/item/experience_bottle.png"
                      },
                      {
                        "type": 1,
                        "parameter": "exp"
                      },
                      {
                        "type": 2,
                        "parameter": "player_exp"
                      },
                      {
                        "type": 3,
                        "parameter": [
                          "test","test2"
                        ]
                      }
                    ]
                  },
                  "2": {
                    "pos": [2,0,1],
                    "element": [
                      {
                        "type": 0,
                        "parameter": "minecraft:textures/item/experience_bottle.png"
                      }
                    ]
                  },
                  "3": {
                    "pos": [1,0,2],
                    "element": [
                      {
                        "type": 3,
                        "parameter": [
                          "test","test2"
                        ]
                      }
                    ]
                  }
                }""";
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(jsonString);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    private static void load(){
        Gson gson = new Gson();
        try (Reader reader = new FileReader(configFile)) {
            jsonData = gson.fromJson(reader, new TypeToken<Map<String, ScreenElement>>() {}.getType());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
    static class ScreenJsonData{
        private int[] pos;
        private List<ScreenElement> elements;

        public int[] getPos() {
            return pos;
        }

        public void setPos(int[] pos) {
            this.pos = pos;
        }

        public List<ScreenElement> getElements() {
            return elements;
        }

        public void setElements(List<ScreenElement> elements) {
            this.elements = elements;
        }
    }
    static class ScreenElement{
        private int[] pos;
        private int type;
        private Object parameter;

        public int[] getPos() {
            return pos;
        }

        public void setPos(int[] pos) {
            this.pos = pos;
        }

        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }
        public Object getParameter() {
            return parameter;
        }
        public void setParameter(Object parameter) {
            this.parameter = parameter;
        }
    }
}
