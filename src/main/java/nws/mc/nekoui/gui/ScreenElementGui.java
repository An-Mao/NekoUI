package nws.mc.nekoui.gui;

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
import nws.mc.nekoui.player.PlayerInfo;
import org.joml.Vector3i;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class ScreenElementGui {
    public static final String id = "screen_element";
    public static final ResourceLocation RESOURCE_LOCATION =  ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,id);
    public static final HashMap<String,ResourceLocation> Resources = new HashMap<>();
    public static void render (GuiGraphics guiGraphics, DeltaTracker pPartialTick){
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui)return;
        Level clientLevel = minecraft.level;
        LocalPlayer localPlayer = minecraft.player;
        if (clientLevel != null && clientLevel.isClientSide && localPlayer != null) {
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            Configs.ScreenRenders.forEach(screenRender -> {
                int startX = switch (screenRender.x()) {
                    case "center" -> screenWidth / 2;
                    case "right" -> screenWidth;
                    default -> 0;
                };
                int startY = switch (screenRender.y()) {
                    case "center" -> screenHeight / 2;
                    case "bottom" -> screenHeight;
                    default -> 0;
                };
                Vector3i pos = screenRender.pos();
                int x = startX + pos.x, y = startY + pos.y, z = pos.z;
                screenRender.elements().forEach(element -> {
                    Vector3i ePos = element.pos();
                    int dx = x + ePos.x, dy = y + ePos.y, dz = z + ePos.z;
                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().translate(0, 0, dz);
                    String tmpS = "";
                    switch (element.type()) {
                        case Image ->{
                            ResourceLocation res = Resources.getOrDefault(element.key(),ResourceLocation.tryParse(element.key()));
                            if (res != null)
                                guiGraphics.blit(res, dx, dy, 0, 0, element.width(), element.height(), element.width(), element.height());
                        }
                        case Self -> tmpS = element.key();
                        case Custom -> tmpS = PlayerInfo.getPlayerDat(element.key());
                        case PlayerData ->
                                tmpS = _FormatToString.formatValue(PlayerInfo.getPlayerAttribute(element.key()), 2);
                        case Js -> {
                            String key = element.key();
                            tmpS = _EasyJS.creat()
                                    .addParameter("x", dx)
                                    .addParameter("y", dy)
                                    .addParameter("z", dz)
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
                                guiGraphics.renderItem(localPlayer.getInventory().getItem(Integer.parseInt(element.key())), dx, dy);
                    }
                    if (!tmpS.isEmpty()) {
                        String color = element.color();
                        if (color.equals("rainbow")) {
                            guiGraphics.drawString(Minecraft.getInstance().font, ComponentStyle.Flash(tmpS, clientLevel.getDayTime()), dx, dy, 0);
                        } else {
                            guiGraphics.drawString(Minecraft.getInstance().font, tmpS, dx, dy, _ColorSupport.HexToColor(color));
                        }
                    }
                    guiGraphics.pose().popPose();
                });

            });
        }
    }
    private static final HashMap<String,Reader> fileTemp = new HashMap<>();
    public static Reader getJsFile(String name) throws FileNotFoundException {
        if (!fileTemp.containsKey(name)) fileTemp.put(name,new FileReader(Configs.ConfigDir_JS + name));
        return fileTemp.get(name);
    }

}
