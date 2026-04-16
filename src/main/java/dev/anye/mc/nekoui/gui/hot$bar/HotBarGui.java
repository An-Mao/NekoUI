package dev.anye.mc.nekoui.gui.hot$bar;

import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.hotbar.HotBarConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HotBarGui extends HotBarConfig{
    public static final String id = "hot_bar";
    public static final Identifier RESOURCE_LOCATION =  Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,id);

    private static final Identifier itemSlot =  Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot.png");
    private static final Identifier itemSelet = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select.png");
    private static final Identifier[] itemSelects = {
            Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_1.png"),
            Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_2.png"),
            Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_3.png"),
            Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_4.png"),
            Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_5.png"),
            Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"textures/hud/item/slot_select_6.png")
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

    public static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker partialTick){
        if (!INSTANCE.getDatas().isEnable()) return;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui)return;
        if (HotBarSys.isOutTime() && INSTANCE.getDatas().isDynamicDisplay()) return;
        Level clientLevel = minecraft.level;
        LocalPlayer localPlayer = minecraft.player;
        if (clientLevel != null && localPlayer != null) {
            if (clientLevel.isClientSide()){





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
                NonNullList<ItemStack> oItems = opi.getNonEquipmentItems();
                int i =0;
                ItemStack item = localPlayer.getOffhandItem();
                if (item != ItemStack.EMPTY) {
                    guiGraphics.blit(RenderPipelines.GUI_TEXTURED,itemSlot, startX, startY, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
                    guiGraphics.item(item, startX, startY);
                    renderItemCountAndDamage(guiGraphics,item);
                }
                for (ItemStack itemStack : oItems){
                    if (i < 9){
                        addSpace();
                        if (i == opi.getSelectedSlot())
                        {
                            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,itemSelects[itemSelectIndex],startX,startY,0,0, imageWidth, imageHeight, imageWidth, imageHeight);
                        }else {
                            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,itemSlot,startX,startY,0,0, imageWidth, imageHeight, imageWidth, imageHeight);
                        }
                        //ri(guiGraphics,itemStack);
                        guiGraphics.item(itemStack,startX,startY);
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
    private static void renderItemCountAndDamage(GuiGraphicsExtractor guiGraphics,ItemStack itemStack){
        guiGraphics.itemDecorations(Minecraft.getInstance().font, itemStack,startX,startY);
    }
}
