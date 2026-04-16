package dev.anye.mc.nekoui.gui;

import dev.anye.core.color._ColorSupport;
import dev.anye.core.format._FormatToString;
import dev.anye.core.system._File;
import dev.anye.mc.cores.helper.component.ComponentStyle;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.other.CJS;
import dev.anye.mc.nekoui.player.PlayerInfo;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import org.joml.Vector3i;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;

public class ScreenElementGui {
    public static final String id                    = "screen_element";
    public static final Identifier RESOURCE_LOCATION = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,id);
    public static final HashMap<String,               Identifier> Resources = new HashMap<>();
    public static void render (GuiGraphicsExtractor guiGraphics, DeltaTracker pPartialTick){
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui)return;
        Level clientLevel = minecraft.level;
        LocalPlayer localPlayer = minecraft.player;
        if (clientLevel != null && clientLevel.isClientSide() && localPlayer != null) {
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
                    guiGraphics.pose().pushMatrix();
                    guiGraphics.pose().translate(0, 0);
                    String tmpS = "";
                    switch (element.type()) {
                        case Image ->{
                            Identifier res = Resources.getOrDefault(element.key(),Identifier.tryParse(element.key()));
                            if (res != null)
                                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,res, dx, dy, 0, 0, element.width(), element.height(), element.width(), element.height());
                        }
                        case Self -> tmpS = element.key();
                        case Custom -> tmpS = PlayerInfo.getPlayerDat(element.key());
                        case PlayerData ->
                                tmpS = _FormatToString.formatValue(PlayerInfo.getPlayerAttribute(element.key()), 2);
                        case Js -> {
                            String key = element.key();
                            tmpS = CJS.E.addParameter("x", dx)
                                    .addParameter("y", dy)
                                    .addParameter("z", dz)
                                    .addParameter("screenWidth", screenWidth)
                                    .addParameter("screenHeight", screenHeight)
                                    .addParameter("server", Minecraft.getInstance().getCurrentServer())
                                    .addParameter("player", localPlayer)
                                    .addParameter("playerNbt", localPlayer.getPersistentData())
                                    .addParameter("guiGraphics", guiGraphics)
                                    .addParameter("minecraft", Minecraft.getInstance())
                                    .runFile(key, _File.getFilePath(Configs.ConfigDir_JS , key)).toString();
                        }
                        case Slot ->
                                guiGraphics.item(localPlayer.getInventory().getItem(Integer.parseInt(element.key())), dx, dy);
                    }
                    if (!tmpS.isEmpty()) {
                        String color = element.color();
                        ComponentStyle.RainbowType rainbowType = ComponentStyle.RainbowType.GetByName(color);
                        if (rainbowType != null) {
                            guiGraphics.text(minecraft.font, ComponentStyle.Flash(tmpS,rainbowType), dx, dy, 0xffffffff);
                        } else {
                            guiGraphics.text(minecraft.font, tmpS, dx, dy, _ColorSupport.HexToColor(color));
                        }
                    }
                    guiGraphics.pose().popMatrix();
                });

            });
        }
    }
    private static final HashMap<String,Reader> fileTemp = new HashMap<>();
    public static Reader getJsFile(String name) throws FileNotFoundException {
        if (!fileTemp.containsKey(name)) fileTemp.put(name,new FileReader(_File.getFilePath(Configs.ConfigDir_JS , name)));
        return fileTemp.get(name);
    }

}
