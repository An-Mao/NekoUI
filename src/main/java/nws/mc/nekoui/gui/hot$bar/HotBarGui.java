package nws.mc.nekoui.gui.hot$bar;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.mc.nekoui.NekoUI;
import nws.mc.nekoui.config.hotbar.HotBarConfig;

@OnlyIn(Dist.CLIENT)
public class HotBarGui extends HotBarConfig{
    public static final String id = "hot_bar";
    public static final ResourceLocation RESOURCE_LOCATION =  ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,id);

    private static final ResourceLocation itemSlot =  ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot.png");
    private static final ResourceLocation itemSelet = ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select.png");
    private static final ResourceLocation[] itemSelects = {
            ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_1.png"),
            ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_2.png"),
            ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_3.png"),
            ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_4.png"),
            ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_5.png"),
            ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_6.png")
    };
    private static int itemSelectIndex = 0;
    private static int tick = 0;
    private static final int color = 0xffffff;
    private static final int imageHeight = 16;
    private static final int imageWidth = 16;
    private static int startX,startY;
    private static void addSpace(){
        switch (INSTANCE.getDatas().getDirection()){
            case "horizontal" -> startX += INSTANCE.getDatas().getSpace();
            case "vertical" -> startY += INSTANCE.getDatas().getSpace();
        }
    }

    public static void render(GuiGraphics guiGraphics, DeltaTracker partialTick){
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui)return;
        if (HotBarSys.isOutTime() && INSTANCE.getDatas().isDynamicDisplay()) return;
        Level clientLevel = minecraft.level;
        LocalPlayer localPlayer = minecraft.player;
        if (clientLevel != null && localPlayer != null) {
            if (clientLevel.isClientSide){
                int screenWidth = minecraft.getWindow().getGuiScaledWidth();
                int screenHeight = minecraft.getWindow().getGuiScaledHeight();

                startX = switch (INSTANCE.getDatas().getStartX()) {
                    case "center" -> screenWidth / 2;
                    case "right" -> screenWidth;
                    default -> 0;
                };
                startX += INSTANCE.getDatas().getX();
                startY = switch (INSTANCE.getDatas().getStartY()) {
                    case "center" -> screenHeight / 2;
                    case "bottom" -> screenHeight;
                    default -> 0;
                };
                startY += INSTANCE.getDatas().getY();

                Inventory opi = localPlayer.getInventory();
                NonNullList<ItemStack> oItems = opi.items;
                int i =0;
                ItemStack item = localPlayer.getOffhandItem();
                if (item != ItemStack.EMPTY) {
                    guiGraphics.blit(itemSlot, startX, startY, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
                    guiGraphics.renderItem(item, startX, startY);
                    renderItemCountAndDamage(guiGraphics,item);
                }
                for (ItemStack itemStack : oItems){
                    if (i < 9){
                        addSpace();
                        if (i == opi.selected)
                        {
                            guiGraphics.blit(itemSelects[itemSelectIndex],startX,startY,0,0, imageWidth, imageHeight, imageWidth, imageHeight);
                        }else {
                            guiGraphics.blit(itemSlot,startX,startY,0,0, imageWidth, imageHeight, imageWidth, imageHeight);
                        }
                        //ri(guiGraphics,itemStack);
                        guiGraphics.renderItem(itemStack,startX,startY);
                        renderItemCountAndDamage(guiGraphics,itemStack);
                        i++;
                    }else {
                        break;
                    }
                }
                setItemSelectIndex();
            }
        }
    }
    private static void setItemSelectIndex(){
        tick ++;
        if (tick > 200){
            tick = 0;
        }
        itemSelectIndex = tick / 40;
    }
    private static void renderItemCountAndDamage(GuiGraphics guiGraphics,ItemStack itemStack){
        guiGraphics.renderItemDecorations(Minecraft.getInstance().font, itemStack,startX,startY);
    }

}
