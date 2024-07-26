package anmao.mc.nekoui.gui;

import anmao.mc.amlib.component.ComponentStyle;
import anmao.dev.core.format._FormatToString;
import anmao.mc.nekoui.config.screen$element.ScreenElementConfig;
import anmao.mc.nekoui.player.PlayerInfo;
import com.google.gson.JsonObject;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenElementGui extends ScreenElementConfig {
    public final String id = "screen_element";
    public final int screenWidth;
    public final int screenHeight;
    public ScreenElementGui(Minecraft minecraft){
        screenHeight = minecraft.getWindow().getScreenHeight() / 2;
        screenWidth = minecraft.getWindow().getScreenWidth() / 2;
    }



    public void render (GuiGraphics guiGraphics, DeltaTracker pPartialTick){
        Level clientLevel = Minecraft.getInstance().level;
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (clientLevel != null && clientLevel.isClientSide && localPlayer != null) {
            I.getDatas().forEach((s, screenJsonData) -> {
                int startX = switch (screenJsonData.getX()) {
                    default -> 0;
                    case "center" -> screenWidth / 2;
                    case "right" -> screenWidth;
                };
                int startY = switch (screenJsonData.getY()) {
                    default -> 0;
                    case "center" -> screenHeight / 2;
                    case "bottom" -> screenHeight;
                };
                int[] pos = screenJsonData.getPos();
                if (pos.length == 3) {
                    int x = startX + pos[0], y = startY + pos[1], z = pos[2];
                    screenJsonData.getElements().forEach(screenElement -> {
                        int[] ePos = screenElement.getPos();
                        if (ePos.length == 2) {
                            int dx = x + ePos[0], dy = y + ePos[1];
                            int type = screenElement.getType();
                            JsonObject p = screenElement.getParameter().getAsJsonObject();
                            if (type == 0) {
                                guiGraphics.blit(ResourceLocation.tryBuild(p.get("mod").getAsString(), p.get("path").getAsString()), dx, dy, 0, 0, p.get("width").getAsInt(), p.get("height").getAsInt(), p.get("width").getAsInt(), p.get("height").getAsInt());
                            } else {
                                String key = p.get("key").getAsString();
                                if (!key.isEmpty()) {
                                    String str = switch (type) {
                                        default -> "error type";
                                        case 1 -> key;
                                        case 2 -> PlayerInfo.getPlayerDat(key);
                                        case 3 -> _FormatToString.numberToString(PlayerInfo.getPlayerAttribute(key));
                                    };
                                    String color = p.get("color").getAsString();
                                    if (color.equals("rainbow")){
                                        guiGraphics.drawString(Minecraft.getInstance().font, ComponentStyle.Flash(str,clientLevel.getDayTime()),dx,dy,0);
                                    }else {
                                        guiGraphics.drawString(Minecraft.getInstance().font, str, dx, dy, Integer.parseInt(color, 16));
                                    }
                                }
                            }
                        }
                    });
                }
            });
        }
    }
}
