package anmao.mc.nekoui.hud;

import anmao.mc.nekoui.Config;
import anmao.mc.nekoui.NekoUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class UI_Player {
    private static final Font FONT = Minecraft.getInstance().font;
    private static final ResourceLocation[] R_HEALTH = {
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_null.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_1.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_2.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_3.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_4.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_5.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_6.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_7.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_8.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_9.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/health_full.png"),
    };
    private static final ResourceLocation[] R_FOOD = {
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_null.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_1.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_2.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_3.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_4.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_5.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_6.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_7.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_8.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_9.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/food_full.png"),
    };
    private static final ResourceLocation IMAGE_LV = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/player/lv.png");
    private static final int SIZE_LV =32;
    private static final int SIZE_LVO = SIZE_LV / 2;
    public static final IGuiOverlay UI_PLAYER = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (!Config.playerInfo) {
            return;
        }
        LocalPlayer oplayer = Minecraft.getInstance().player;
        if (oplayer != null) {
            float oph = oplayer.getHealth();
            float opmh = oplayer.getMaxHealth();
            int imageR = 32;
            int imageRt = imageR / 2;
            //screen
            int sx = screenWidth / 2;
            int sy = screenHeight - imageR - 5;
            int strH = 4;

            int healthX = sx - imageR - 32;
            int healthY = sy;
            int foodX = sx + 32;
            int foodY = sy;
            //null
            guiGraphics.blit(R_HEALTH[0], healthX, healthY, 0, 0, imageR, imageR, imageR, imageR);
            guiGraphics.blit(R_FOOD[0], foodX, foodY, 0, 0, imageR, imageR, imageR, imageR);
            //data
            int oh = (int) ((oph / opmh) * 10);
            if (oh > 10) {
                oh = 10;
            }
            if (oh > 0) {
                guiGraphics.blit(R_HEALTH[oh], healthX, healthY, 0, 0, imageR, imageR, imageR, imageR);
                if (Config.playerShowHealthNumber) {
                    int healthStrX = healthX + imageRt - strH - strH;
                    int healthStrY = healthY + imageRt - strH;
                    guiGraphics.drawString(FONT, String.valueOf(oph), healthStrX, healthStrY, 0xff0000);
                }
            }
            oph = oplayer.getFoodData().getFoodLevel();
            opmh = oplayer.getFoodData().getLastFoodLevel();
            oh = (int) ((oph / opmh) * 10);
            if (oh > 10) {
                oh = 10;
            }
            if (oh > 0) {
                guiGraphics.blit(R_FOOD[oh], foodX, foodY, 0, 0, imageR, imageR, imageR, imageR);
                if (Config.playerShowFoodNumber) {
                    int foodStrX = foodX + imageRt - strH - strH;
                    int foodStrY = foodY + imageRt - strH;
                    guiGraphics.drawString(FONT, String.valueOf(oph), foodStrX, foodStrY, 0x000000);
                }
            }
            int lvX = healthX - SIZE_LV;
            int lcY = screenHeight - 5 - SIZE_LV;
            guiGraphics.blit(IMAGE_LV, lvX, lcY, 0, 0, SIZE_LV, SIZE_LV, SIZE_LV, SIZE_LV);
            guiGraphics.drawString(FONT, String.valueOf(oplayer.experienceLevel), lvX + 5, lcY+SIZE_LVO -4, 0x006666);
        }
    });
}
