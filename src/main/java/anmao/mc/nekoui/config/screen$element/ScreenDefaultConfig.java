package anmao.mc.nekoui.config.screen$element;

public class ScreenDefaultConfig {
    public static final String DefaultConfig = """
                {
                     "custom_id_name": {
                       "x": "left",
                       "y": "center",
                       "pos": [10,0,0],
                       "elements": [
                         {
                           "pos": [0,0],
                           "type": 0,
                           "parameter": {
                             "mod": "minecraft",
                             "path":"textures/mob_effect/strength.png",
                             "width": 10,
                             "height": 10
                           }
                         },
                         {
                           "pos": [10,0],
                           "type": 2,
                           "parameter": {
                             "key": "atkWithItem",
                             "color": "336699"
                           }
                         }
                       ]
                     },
                    "exp": {
                      "x": "left",
                      "y": "center",
                      "pos": [10,12,0],
                      "elements": [
                        {
                          "pos": [0,0],
                          "type": 0,
                          "parameter": {
                            "mod": "minecraft",
                            "path":"textures/item/experience_bottle.png",
                            "width": 10,
                            "height": 10
                          }
                        },
                        {
                          "pos": [10,0],
                          "type": 2,
                          "parameter": {
                            "key": "lvl",
                            "color": "336699"
                          }
                        }
                      ]
                    },
                    "hunger": {
                      "x": "left",
                      "y": "center",
                      "pos": [10,24,0],
                      "elements": [
                        {
                          "pos": [0,0],
                          "type": 0,
                          "parameter": {
                            "mod": "minecraft",
                            "path":"textures/mob_effect/hunger.png",
                            "width": 10,
                            "height": 10
                          }
                        },
                        {
                          "pos": [10,0],
                          "type": 2,
                          "parameter": {
                            "key": "hunger",
                            "color": "336699"
                          }
                        }
                      ]
                    },
                    "luck": {
                      "x": "left",
                      "y": "center",
                      "pos": [10,36,0],
                      "elements": [
                        {
                          "pos": [0,0],
                          "type": 0,
                          "parameter": {
                            "mod": "minecraft",
                            "path":"textures/mob_effect/luck.png",
                            "width": 10,
                            "height": 10
                          }
                        },
                        {
                          "pos": [10,0],
                          "type": 2,
                          "parameter": {
                            "key": "luck",
                            "color": "336699"
                          }
                        }
                      ]
                    },
                    "speed": {
                      "x": "left",
                      "y": "center",
                      "pos": [10,48,0],
                      "elements": [
                        {
                          "pos": [0,0],
                          "type": 0,
                          "parameter": {
                            "mod": "minecraft",
                            "path":"textures/mob_effect/speed.png",
                            "width": 10,
                            "height": 10
                          }
                        },
                        {
                          "pos": [10,0],
                          "type": 2,
                          "parameter": {
                            "key": "speed",
                            "color": "336699"
                          }
                        }
                      ]
                    },
                    "health": {
                      "x": "left",
                      "y": "center",
                      "pos": [10,60,0],
                      "elements": [
                        {
                          "pos": [0,0],
                          "type": 0,
                          "parameter": {
                            "mod": "minecraft",
                            "path":"textures/mob_effect/regeneration.png",
                            "width": 10,
                            "height": 10
                          }
                        },
                        {
                          "pos": [10,0],
                          "type": 2,
                          "parameter": {
                            "key": "health",
                            "color": "336699"
                          }
                        }
                      ]
                    }
                   }""";
}
