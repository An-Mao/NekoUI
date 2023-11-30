package anmao.mc.nekoui.hud;

import anmao.mc.nekoui.Config;
import anmao.mc.nekoui.NekoUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class UI_Item {
    private static final int RADIUS = 16;
    private static final double PIE_A = 0.017453292519943295D;

    private static final double[] R = {
            40,80,120,
            160,-160,-120,
            -80,-40,0
    };
    private static final double[] RD ={
            0.6981317007977318,
            1.3962634015954636,
            2.0943951023931953,
            2.792526803190927,
            -2.792526803190927,
            -2.0943951023931953,
            -1.3962634015954636,
            -0.6981317007977318,
            0.0
    };
    private static final double G = 40;
    private static final ResourceLocation R_ITEM = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/item/itemui.png");
    private static final ResourceLocation ITEM_SLOT = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/item/itemslot.png");
    private static final ResourceLocation ITEM_SELET = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/item/seletslot.png");
    public static final IGuiOverlay UI_ITEM = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (!Config.playerInfo){
            return;
        }
        LocalPlayer oplayer = Minecraft.getInstance().player;
        if (oplayer != null) {
            int imageSize = 16;
            int sx = screenWidth / 2 - 32;
            int sy = screenHeight - 64;
            //System.out.println("---------------item---------------");
            //guiGraphics.blit(R_ITEM,sx,sy,0,0,64,64,64,64);
            Inventory opi = oplayer.getInventory();
            NonNullList<ItemStack> oitems = opi.items;
            //System.out.println("::::"+opi.selected);
            int i =0;
            int ox = sx + 20;
            int oy = sy;
            double a;
            guiGraphics.renderItem(oplayer.getMainHandItem(),sx+24,sy+22);
            for (ItemStack itemStack : oitems){
                if (i < 9){
                    a = getDegreePai(R[i]-30);
                    ox += getX(RADIUS,a);
                    oy += getY(RADIUS,a);
                    if (i == opi.selected)
                    {
                        guiGraphics.blit(ITEM_SELET,ox,oy,0,0,imageSize,imageSize,imageSize,imageSize);
                    }else {
                        guiGraphics.blit(ITEM_SLOT,ox,oy,0,0,imageSize,imageSize,imageSize,imageSize);
                    }
                    guiGraphics.renderItem(itemStack,ox,oy);
                }
                i++;
            }
            //guiGraphics.renderItem(Minecraft.getInstance().player, ItemStack.EMPTY,100,100,0);

        }
    });
    public static int getX (int radius , double dpai){
        return (int) (radius * Math.cos(dpai));
    }
    public static int getY (int radius , double dpai){
        return (int) (radius * Math.sin(dpai));
    }
    public static double getDegreePai(double degree){
        if (degree >= 180){
            degree -= 360;
        }
        return degree * PIE_A;
    }
}
