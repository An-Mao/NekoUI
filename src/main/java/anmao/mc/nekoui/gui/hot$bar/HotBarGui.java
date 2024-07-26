package anmao.mc.nekoui.gui.hot$bar;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.hotbar.HotBarConfig;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class HotBarGui extends HotBarConfig{
    public static final String id = "hot_bar";

    private static final ResourceLocation itemSlot = new ResourceLocation(NekoUI.MOD_ID,"textures/hud/item/slot.png");
    private static final ResourceLocation itemSelet = new ResourceLocation(NekoUI.MOD_ID,"textures/hud/item/slot_select.png");
    private static final ResourceLocation[] itemSelects = {
            new ResourceLocation(NekoUI.MOD_ID,"textures/hud/item/slot_select_1.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/hud/item/slot_select_2.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/hud/item/slot_select_3.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/hud/item/slot_select_4.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/hud/item/slot_select_5.png"),
            new ResourceLocation(NekoUI.MOD_ID,"textures/hud/item/slot_select_6.png")
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

    public static final IGuiOverlay UI = ((gui, guiGraphics, partialTick, screenWidth, screenHeight)->{
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

                    ri(guiGraphics,item);
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
    });
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
    private static void ri(GuiGraphics guiGraphics, ItemStack stack) {
        if (!stack.isEmpty()) {
            PoseStack pose = guiGraphics.pose();
            BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0);
            pose.pushPose();
            pose.translate((float)(startX + 8), (float)(startY + 8), -100F);

            try {
                pose.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                pose.scale(12.0F, 12.0F, 12.0F);
                boolean flag = !bakedmodel.usesBlockLight();
                if (flag) {
                    Lighting.setupForFlatItems();
                }

                Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GUI, false, pose, guiGraphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
                guiGraphics.flush();
                if (flag) {
                    Lighting.setupFor3DItems();
                }
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.forThrowable(throwable, "Rendering item");
                CrashReportCategory crashreportcategory = crashreport.addCategory("Item being rendered");
                crashreportcategory.setDetail("Item Type", () -> String.valueOf(stack.getItem()));
                crashreportcategory.setDetail("Registry Name", () -> String.valueOf(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(stack.getItem())));
                crashreportcategory.setDetail("Item Damage", () -> String.valueOf(stack.getDamageValue()));
                crashreportcategory.setDetail("Item NBT", () -> String.valueOf(stack.getTag()));
                crashreportcategory.setDetail("Item Foil", () -> String.valueOf(stack.hasFoil()));
                throw new ReportedException(crashreport);
            }

            pose.popPose();
        }
    }

}
