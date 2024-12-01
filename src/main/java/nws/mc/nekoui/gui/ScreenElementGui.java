package nws.mc.nekoui.gui;

import com.google.gson.JsonObject;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.dev.core.color._ColorSupport;
import nws.dev.core.format._FormatToString;
import nws.dev.core.javascript._EasyJS;
import nws.mc.cores.helper.component.ComponentStyle;
import nws.mc.nekoui.NekoUI;
import nws.mc.nekoui.config.Configs;
import nws.mc.nekoui.config.screen$element.ScreenElementConfig;
import nws.mc.nekoui.config.screen$element.ScreenElementDataElement;
import nws.mc.nekoui.player.PlayerInfo;

@OnlyIn(Dist.CLIENT)
public class ScreenElementGui extends ScreenElementConfig {
    public static final String id = "screen_element";
    public static final ResourceLocation RESOURCE_LOCATION =  ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,id);
    public static void render (GuiGraphics guiGraphics, DeltaTracker pPartialTick){
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui)return;
        Level clientLevel = minecraft.level;
        LocalPlayer localPlayer = minecraft.player;
        if (clientLevel != null && clientLevel.isClientSide && localPlayer != null) {
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            I.getDatas().forEach((s, screenJsonData) -> {
                int startX = switch (screenJsonData.getX()) {
                    case "center" -> screenWidth / 2;
                    case "right" -> screenWidth;
                    default -> 0;
                };
                int startY = switch (screenJsonData.getY()) {
                    case "center" -> screenHeight / 2;
                    case "bottom" -> screenHeight;
                    default -> 0;
                };
                int[] pos = screenJsonData.getPos();
                if (pos.length == 3) {
                    int x = startX + pos[0], y = startY + pos[1], z = pos[2];
                    screenJsonData.getElements().forEach(screenElement -> {
                        int[] ePos = screenElement.getPos();
                        if (ePos.length == 2) {
                            int dx = x + ePos[0], dy = y + ePos[1];
                            int type = Integer.parseInt(screenElement.getType());
                            JsonObject p = screenElement.getParameter().getAsJsonObject();

                            ScreenElementDataElement.Type t = screenElement.getTypeEnum();
                            String tmpS = "";
                            switch (t){
                                case Image ->
                                        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(p.get("mod").getAsString(), p.get("path").getAsString()), dx, dy, 0, 0, p.get("width").getAsInt(), p.get("height").getAsInt(), p.get("width").getAsInt(), p.get("height").getAsInt());
                                case Self ->
                                        tmpS = p.get("key").getAsString();
                                case Custom ->
                                        tmpS = PlayerInfo.getPlayerDat(p.get("key").getAsString());
                                case PlayerData ->
                                        tmpS = _FormatToString.formatValue(PlayerInfo.getPlayerAttribute(p.get("key").getAsString()), 2);
                                case Js ->
                                {
                                    String key = p.get("key").getAsString();
                                    tmpS = _EasyJS.creat()
                                            .addParameter("x", dx)
                                            .addParameter("y", dy)
                                            .addParameter("screenWidth", screenWidth)
                                            .addParameter("screenHeight", screenHeight)
                                            .addParameter("server", Minecraft.getInstance().getCurrentServer())
                                            .addParameter("player", localPlayer)
                                            .addParameter("playerNbt", localPlayer.getPersistentData())
                                            .addParameter("guiGraphics", guiGraphics)
                                            .addParameter("minecraft", Minecraft.getInstance())
                                            .runFile(Configs.ConfigDir_JS + key).toString();
                                }
                                case Slot ->
                                        guiGraphics.renderItem(localPlayer.getInventory().getItem(Integer.parseInt(p.get("key").getAsString())),dx,dy);
                            }
                            if (!tmpS.isEmpty()) {
                                String color = p.get("color").getAsString();
                                if (color.equals("rainbow")){
                                    guiGraphics.drawString(Minecraft.getInstance().font, ComponentStyle.Flash(tmpS,clientLevel.getDayTime()),dx,dy,0);
                                }else {
                                    guiGraphics.drawString(Minecraft.getInstance().font, tmpS, dx, dy, _ColorSupport.HexToColor(color));
                                }
                            }
                        }
                    });
                }
            });
        }
    }

}
