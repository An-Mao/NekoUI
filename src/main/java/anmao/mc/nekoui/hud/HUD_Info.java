package anmao.mc.nekoui.hud;

import anmao.mc.nekoui.config.CC;
import anmao.mc.nekoui.constant._MC;
import anmao.mc.nekoui.lib.AM;
import anmao.mc.nekoui.lib.am._Sys;
import anmao.mc.nekoui.lib.dat.CD_IS;
import anmao.mc.nekoui.lib.dat.RXYI;
import anmao.mc.nekoui.lib.dat.TXYI;
import anmao.mc.nekoui.lib.player.PlayerInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Collection;
import java.util.Objects;

public class HUD_Info {
    public static final String id = "hud_info";
    private static final ResourceLocation playerLevel = new ResourceLocation(_MC.ID,"textures/item/experience_bottle.png");
    private static final ResourceLocation playerHealth = new ResourceLocation(_MC.ID,"textures/mob_effect/regeneration.png");
    private static final ResourceLocation playerHunger = new ResourceLocation(_MC.ID,"textures/mob_effect/hunger.png");
    private static final ResourceLocation playerLuck = new ResourceLocation(_MC.ID,"textures/mob_effect/luck.png");
    private static final ResourceLocation playerDamage = new ResourceLocation(_MC.ID,"textures/mob_effect/strength.png");
    private static final ResourceLocation playerSpeed = new ResourceLocation(_MC.ID,"textures/mob_effect/speed.png");

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
                for (CD_IS ik : CC.infoKeys){
                    if (ik.getType() == 0){
                        String t = CC.infoTexts.get(ik.getId());
                        guiGraphics.drawString(_MC.FONT,t , ik.getX(), ik.getY(), color);
                        guiGraphics.drawString(_MC.FONT,PlayerInfo.getDat(ik.getSa()) , ik.getX(), ik.getI(), color);
                    }else if (ik.getType() == 1){
                        ResourceLocation resourceLocation = CC.infoIcons.get(ik.getId());
                        guiGraphics.blit(resourceLocation, ik.getX(), ik.getY(), 0, 0, imageSize, imageSize, imageSize, imageSize);
                        guiGraphics.drawString(_MC.FONT,PlayerInfo.getDat(ik.getSa()) , ik.getX(),ik.getI(), color);
                    }
                }
                guiGraphics.blit(playerLevel, startX, startY, 0, 0, imageSize, imageSize, imageSize, imageSize);
                addLine();
                str = String.valueOf(localPlayer.experienceLevel);
                drawStr(guiGraphics,str);


                newLine();
                guiGraphics.blit(playerHealth, startX, startY, 0, 0, imageSize, imageSize, imageSize, imageSize);
                addLine();
                str = String.format("%.2f",localPlayer.getHealth()) + "/" + localPlayer.getMaxHealth();
                drawStr(guiGraphics,str);


                newLine();
                guiGraphics.blit(playerHunger, startX, startY, 0, 0, imageSize, imageSize, imageSize, imageSize);
                addLine();
                str = String.valueOf(localPlayer.getFoodData().getFoodLevel());
                drawStr(guiGraphics,str);


                newLine();
                Collection<AttributeModifier> att = localPlayer.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
                double baseDamage = localPlayer.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
                double itemDamage = AM.getAdddamage(att);
                guiGraphics.blit(playerDamage, startX, startY, 0, 0, imageSize, imageSize, imageSize, imageSize);
                addLine();
                str = (baseDamage + itemDamage) + "(" +baseDamage+"+" +itemDamage+ ")";
                drawStr(guiGraphics,str);


                newLine();
                guiGraphics.blit(playerLuck, startX, startY, 0, 0, imageSize, imageSize, imageSize, imageSize);
                addLine();
                str = String.valueOf(localPlayer.getLuck());
                drawStr(guiGraphics,str);

                newLine();
                guiGraphics.blit(playerSpeed, startX, startY, 0, 0, imageSize, imageSize, imageSize, imageSize);
                addLine();
                str = String.format("%.2f",localPlayer.getSpeed());
                drawStr(guiGraphics,str);
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
