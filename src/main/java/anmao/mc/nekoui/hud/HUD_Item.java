package anmao.mc.nekoui.hud;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.am._Sys;
import anmao.mc.nekoui.constant._MC;
import anmao.mc.nekoui.lib.AM;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Matrix4f;

import java.util.Collection;

public class HUD_Item {
    public static final String id = "hud_item";

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
    private static void addRow(){
        startX += 17;
    }

    private static void addLine(){
        startY += 24;
    }

    public static final IGuiOverlay UI = ((gui, guiGraphics, partialTick, screenWidth, screenHeight)->{
        //int offhandItemX = screenWidth / 2 - 20;
        //int handItemY = screenHeight - 50;
        //int mainHandItemX = screenWidth / 2 + 4;
        //
        if (_Sys.isOutTime()) {
            return;
        }
        startX = 0;
        startY = screenHeight - 24;
        Level clientLevel = _MC.MC.level;
        LocalPlayer localPlayer = _MC.MC.player;
        if (clientLevel != null && localPlayer != null) {
            if (clientLevel.isClientSide){


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
                        addRow();
                        if (i == opi.selected)
                        {
                            guiGraphics.blit(itemSelects[itemSelectIndex],startX,startY,0,0, imageWidth, imageHeight, imageWidth, imageHeight);
                        }else {
                            guiGraphics.blit(itemSlot,startX,startY,0,0, imageWidth, imageHeight, imageWidth, imageHeight);
                        }
                        ri(guiGraphics,itemStack);
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
        int count = itemStack.getCount();
        if (count >1 && count < 10) {
            guiGraphics.drawString(_MC.FONT, String.valueOf(count), startX + 10, startY+8, color);
        }else if (count >= 10) {
            guiGraphics.drawString(_MC.FONT, String.valueOf(count), startX + 8, startY+8, color);
        }else {
            if (itemStack.getDamageValue() > 0) {
                float d = (1 - (float) itemStack.getDamageValue() / itemStack.getMaxDamage()) * 100F;
                guiGraphics.drawString(_MC.FONT, String.format("%.0f", d) + "%", startX + 1 , startY+8, color);
            }
        }
    }
    private static void ri(GuiGraphics guiGraphics, ItemStack stack) {
        if (!stack.isEmpty()) {
            PoseStack pose = guiGraphics.pose();
            BakedModel bakedmodel = _MC.MC.getItemRenderer().getModel(stack, null, null, 0);
            pose.pushPose();
            pose.translate((float)(startX + 8), (float)(startY + 8), -100F);

            try {
                pose.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                pose.scale(12.0F, 12.0F, 12.0F);
                boolean flag = !bakedmodel.usesBlockLight();
                if (flag) {
                    Lighting.setupForFlatItems();
                }

                _MC.MC.getItemRenderer().render(stack, ItemDisplayContext.GUI, false, pose, guiGraphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
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
