package anmao.mc.nekoui.hud;

import anmao.mc.nekoui.config.CC;
import anmao.mc.nekoui.constant._MC;
import anmao.mc.nekoui.lib.am._Sys;
import anmao.mc.nekoui.lib.dat.CustomDataTypes_InfoConfig_Icon;
import anmao.mc.nekoui.lib.dat.CustomDataTypes_InfoConfig_Key;
import anmao.mc.nekoui.lib.dat.CustomDataTypes_InfoConfig_Text;
import anmao.mc.nekoui.lib.player.PlayerInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Objects;

public class HUD_Info {
    public static final String id = "hud_info";

    private static final int color = 0xffffff;
    private static final int imageSize = 12;

    private static int startX,startY;
    private static void newRow(){
        startY += 36;
    }
    private static void addRow(){
        startY += 16;
    }
    private static void newLine(){
         startX += 3;
    }
    private static void addLine(){
         startX += 15;
    }
    private static void addStrWidth(int sw){
        startX += sw;
    }
    public static final IGuiOverlay UI = ((gui, guiGraphics, partialTick, screenWidth, screenHeight)->{
        startX = CC.hudInfoX;
        startY = screenHeight - CC.hudInfoY;
        if (CC.hudItemMode && CC.hudItemDynamicDisplay){
            if (_Sys.isOutTime() ){
                startY += CC.hudItemY;
            }
        }
        Level clientLevel = _MC.MC.level;
        LocalPlayer localPlayer = _MC.MC.player;
        if (clientLevel != null && localPlayer != null) {
            if (clientLevel.isClientSide){
                String str;
                for (CustomDataTypes_InfoConfig_Key ik : CC.infoKeys){
                    if (ik.isIcon()){
                        CustomDataTypes_InfoConfig_Icon ci = CC.infoIcons.get(ik.getTipId());
                        guiGraphics.blit(ci.getIcon(), ik.getTipX(), ik.getTipY(), 0, 0, ci.getW(), ci.getH(), ci.getW(), ci.getH());
                    }else{
                        CustomDataTypes_InfoConfig_Text t = CC.infoTexts.get(ik.getTipId());
                        guiGraphics.drawString(_MC.FONT,t.getTipText() , ik.getTipX(), ik.getTipY(), t.getColor());
                    }
                    str = switch (ik.getKeyType() ){
                        default -> "error type";
                        case 0 -> PlayerInfo.getPlayerDat(ik.getKey());
                        case 1 -> PlayerInfo.getPlayerNbtDat(ik.getKey());
                        case 2 -> PlayerInfo.getPlayerNbtDat(ik.getKk());
                    };
                    guiGraphics.drawString(_MC.FONT, str, ik.getInfoX(),ik.getInfoY(), ik.getInfoColor());
                }
            }
        }
    });
    private static void drawStr(GuiGraphics guiGraphics,String s ){
        guiGraphics.drawString(_MC.FONT, s, startX, startY + 1, color);
        int strWidth;
        if (Objects.equals(CC.hudInfoSpace, "auto")) {
            strWidth = (int) (s.length() * 3.5);
        }else {
            strWidth = Integer.parseInt(CC.hudInfoSpace);
        }
        addStrWidth(strWidth);
    }
}
