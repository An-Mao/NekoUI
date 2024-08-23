package anmao.mc.nekoui.config.page;

import anmao.dev.core.json.JsonConfig;
import anmao.mc.nekoui.config.Configs;
import com.google.gson.reflect.TypeToken;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class PageConfig extends JsonConfig<Map<String, PageData>> {
    public static final String file = Configs.ConfigDir+"pages.json";
    public static final PageConfig INSTANCE = new PageConfig();
    public PageConfig() {
        super(file, defaultConfig,  new TypeToken<>(){});
    }

    public PageData getPageData(String key) {
        return this.getDatas().get(key);
    }





    private static final String defaultConfig = """
            {
              "page1": {
                "title": "Item Page",
                "projectNumber": 9,
                "innerRadius": 15,
                "outerRadius": 70,
                "projects": {
                  "1": {
                    "key": "2",
                    "textNormalColor": "auto",
                    "textHighlightColor": "auto",
                    "backgroundNormalColor": "auto",
                    "backgroundHighlightColor": "auto",
                    "textColor": "project1",
                    "normalColor": "project1",
                    "HighlightColor": "project1"
                  },
                  "0": {
                    "key": "1",
                    "textNormalColor": "auto",
                    "textHighlightColor": "auto",
                    "backgroundNormalColor": "auto",
                    "backgroundHighlightColor": "auto"
                  },
                  "2": {
                    "key": "3",
                    "textNormalColor": "auto",
                    "textHighlightColor": "auto",
                    "backgroundNormalColor": "auto",
                    "backgroundHighlightColor": "auto"
                  },
                  "3": {
                    "key": "4",
                    "textNormalColor": "auto",
                    "textHighlightColor": "auto",
                    "backgroundNormalColor": "auto",
                    "backgroundHighlightColor": "auto"
                  },
                  "4": {
                    "key": "5",
                    "textNormalColor": "auto",
                    "textHighlightColor": "auto",
                    "backgroundNormalColor": "auto",
                    "backgroundHighlightColor": "auto"
                  },
                  "5": {
                    "key": "6",
                    "textNormalColor": "auto",
                    "textHighlightColor": "auto",
                    "backgroundNormalColor": "auto",
                    "backgroundHighlightColor": "auto"
                  },
                  "8": {
                    "key": "9",
                    "textNormalColor": "auto",
                    "textHighlightColor": "auto",
                    "backgroundNormalColor": "auto",
                    "backgroundHighlightColor": "auto"
                  },
                  "6": {
                    "key": "7",
                    "textNormalColor": "auto",
                    "textHighlightColor": "auto",
                    "backgroundNormalColor": "auto",
                    "backgroundHighlightColor": "auto"
                  },
                  "7": {
                    "key": "8",
                    "textNormalColor": "auto",
                    "textHighlightColor": "auto",
                    "backgroundNormalColor": "auto",
                    "backgroundHighlightColor": "auto"
                  }
                }
              },
              "page2": {
                "title": "Command",
                "projectNumber": 7,
                "innerRadius": 20,
                "outerRadius": 80,
                "projects": {
                  "0": {
                    "key": "11",
                    "textNormalColor": "0xff00ffff",
                    "textHighlightColor": "0xff00ff00",
                    "backgroundNormalColor": "0x70000000",
                    "backgroundHighlightColor": "0x50ffffff"
                  },
                  "1": {
                    "key": "12",
                    "textNormalColor": "0xff00ffff",
                    "textHighlightColor": "0xff00ff00",
                    "backgroundNormalColor": "0x70000000",
                    "backgroundHighlightColor": "0x50ffffff"
                  }
                }
              },
              "page3": {
                "title": "Msg Page",
                "projectNumber": 5,
                "innerRadius": 20,
                "outerRadius": 80,
                "projects": {
                  "0": {
                    "key": "10",
                    "textNormalColor": "0xff00ffff",
                    "textHighlightColor": "0xff00ff00",
                    "backgroundNormalColor": "0x70000000",
                    "backgroundHighlightColor": "0x50ffffff"
                  }
                }
              }
            }""";
}
