package dev.anye.mc.nekoui.config.screen$element;

public class ScreenDefaultConfig {
    public static final String DefaultConfig = """
            {
              "fps": {
                "x": "left",
                "y": "top",
                "pos": [
                  2,
                  2,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 1,
                    "parameter": {
                      "key": "Fps:",
                      "color": "ffffffff"
                    }
                  },
                  {
                    "pos": [
                      22,
                      0
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "fps",
                      "color": "ffffffff"
                    }
                  }
                ]
              },
              "damage": {
                "x": "center",
                "y": "bottom",
                "pos": [
                  -10,
                  -62,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 0,
                    "parameter": {
                      "mod": "minecraft",
                      "path": "textures/mob_effect/strength.png",
                      "width": 10,
                      "height": 10
                    }
                  },
                  {
                    "pos": [
                      11,
                      1
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "atkWithItem",
                      "color": "rainbow"
                    }
                  }
                ]
              },
              "exp": {
                "x": "center",
                "y": "bottom",
                "pos": [
                  -70,
                  -51,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 0,
                    "parameter": {
                      "mod": "minecraft",
                      "path": "textures/item/experience_bottle.png",
                      "width": 10,
                      "height": 10
                    }
                  },
                  {
                    "pos": [
                      11,
                      1
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "lvl",
                      "color": "ffffffff"
                    }
                  }
                ]
              },
              "health": {
                "x": "center",
                "y": "bottom",
                "pos": [
                  -10,
                  -51,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 0,
                    "parameter": {
                      "mod": "minecraft",
                      "path": "textures/mob_effect/regeneration.png",
                      "width": 10,
                      "height": 10
                    }
                  },
                  {
                    "pos": [
                      11,
                      1
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "health",
                      "color": "ffffffff"
                    }
                  }
                ]
              },
              "hunger": {
                "x": "center",
                "y": "bottom",
                "pos": [
                  50,
                  -51,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 0,
                    "parameter": {
                      "mod": "minecraft",
                      "path": "textures/mob_effect/hunger.png",
                      "width": 10,
                      "height": 10
                    }
                  },
                  {
                    "pos": [
                      11,
                      1
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "hunger",
                      "color": "ffffffff"
                    }
                  }
                ]
              },
              "armor": {
                "x": "center",
                "y": "bottom",
                "pos": [
                  -90,
                  -40,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 0,
                    "parameter": {
                      "mod": "minecraft",
                      "path": "textures/item/iron_chestplate.png",
                      "width": 10,
                      "height": 10
                    }
                  },
                  {
                    "pos": [
                      11,
                      1
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "armor",
                      "color": "ffffffff"
                    }
                  }
                ]
              },
              "armor_toughness": {
                "x": "center",
                "y": "bottom",
                "pos": [
                  -50,
                  -40,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 0,
                    "parameter": {
                      "mod": "minecraft",
                      "path": "textures/item/netherite_chestplate.png",
                      "width": 10,
                      "height": 10
                    }
                  },
                  {
                    "pos": [
                      11,
                      1
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "armorToughness",
                      "color": "ffffffff"
                    }
                  }
                ]
              },
              "air": {
                "x": "center",
                "y": "bottom",
                "pos": [
                  -10,
                  -40,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 0,
                    "parameter": {
                      "mod": "minecraft",
                      "path": "textures/mob_effect/water_breathing.png",
                      "width": 10,
                      "height": 10
                    }
                  },
                  {
                    "pos": [
                      11,
                      1
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "airSupply",
                      "color": "ffffffff"
                    }
                  }
                ]
              },
              "luck": {
                "x": "center",
                "y": "bottom",
                "pos": [
                  30,
                  -40,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 0,
                    "parameter": {
                      "mod": "minecraft",
                      "path": "textures/mob_effect/luck.png",
                      "width": 10,
                      "height": 10
                    }
                  },
                  {
                    "pos": [
                      11,
                      1
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "luck",
                      "color": "rainbow"
                    }
                  }
                ]
              },
              "speed": {
                "x": "center",
                "y": "bottom",
                "pos": [
                  70,
                  -40,
                  0
                ],
                "elements": [
                  {
                    "pos": [
                      0,
                      0
                    ],
                    "type": 0,
                    "parameter": {
                      "mod": "minecraft",
                      "path": "textures/mob_effect/speed.png",
                      "width": 10,
                      "height": 10
                    }
                  },
                  {
                    "pos": [
                      11,
                      1
                    ],
                    "type": 2,
                    "parameter": {
                      "key": "speed",
                      "color": "ffffffff"
                    }
                  }
                ]
              }
            }""";
}
