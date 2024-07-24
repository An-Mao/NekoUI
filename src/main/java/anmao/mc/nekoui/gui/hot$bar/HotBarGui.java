package anmao.mc.nekoui.gui.hot$bar;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.hotbar.HotBarConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HotBarGui extends HotBarConfig{
    public static final String id = "hot_bar";

    private  final ResourceLocation itemSlot = ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/hud/item/slot.png");
    private  final ResourceLocation itemSelet = ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/hud/item/slot_select.png");
    private  final ResourceLocation[] itemSelects = {
            ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/hud/item/slot_select_1.png"),
            ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/hud/item/slot_select_2.png"),
            ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/hud/item/slot_select_3.png"),
            ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/hud/item/slot_select_4.png"),
            ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/hud/item/slot_select_5.png"),
            ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/hud/item/slot_select_6.png")
    };
    private  int itemSelectIndex = 0;
    private  int tick = 0;
    private  final int color = 0xffffff;
    private final int imageHeight = 16;
    private final int imageWidth = 16;
    private int startX,startY;
    private void addSpace(){
        switch (INSTANCE.getDatas().getDirection()){
            case "horizontal" -> startX += INSTANCE.getDatas().getSpace();
            case "vertical" -> startY += INSTANCE.getDatas().getSpace();
        }
    }
    public final int screenWidth;
    public final int screenHeight;
    public final Minecraft minecraft;
    public HotBarGui(Minecraft minecraft){
        this.minecraft = minecraft;
        screenWidth = minecraft.getWindow().getScreenWidth()/2;
        screenHeight = minecraft.getWindow().getScreenHeight()/2;
    }

    public void render(GuiGraphics guiGraphics, DeltaTracker partialTick){
        if (HotBarSys.isOutTime() && INSTANCE.getDatas().isDynamicDisplay()) {
            return;
        }
        Level clientLevel = Minecraft.getInstance().level;
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (clientLevel != null && localPlayer != null) {
            if (clientLevel.isClientSide){

                startX = switch (INSTANCE.getDatas().getStartX()) {
                    default -> 0;
                    case "center" -> screenWidth / 2;
                    case "right" -> screenWidth;
                };
                startX += INSTANCE.getDatas().getX();
                startY = switch (INSTANCE.getDatas().getStartY()) {
                    default -> 0;
                    case "center" -> screenHeight / 2;
                    case "bottom" -> screenHeight;
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
    private void setItemSelectIndex(){
        tick ++;
        if (tick > 200){
            tick = 0;
        }
        itemSelectIndex = tick / 40;
    }
    private void renderItemCountAndDamage(GuiGraphics guiGraphics,ItemStack itemStack){
        guiGraphics.renderItemDecorations(Minecraft.getInstance().font, itemStack,startX,startY);
    }

}
