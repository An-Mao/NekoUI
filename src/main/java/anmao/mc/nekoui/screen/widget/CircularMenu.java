package anmao.mc.nekoui.screen.widget;

import anmao.mc.amlib.mc.screen.widget.CircularWidget;
import anmao.mc.amlib.mc.screen.widget.DT_ListBoxData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
@OnlyIn(Dist.CLIENT)
public class CircularMenu extends CircularWidget {
    public CircularMenu(int x, int y, int w, int h, Component message, DT_ListBoxData... data) {
        super(x, y, w, h, message, data);
    }

    public CircularMenu(int x, int y, int w, int h, Component message, List<DT_ListBoxData> data) {
        super(x, y, w, h, message, data);
    }

    public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, Component message, DT_ListBoxData... data) {
        super(x, y, w, h, sectors, innerRadius, outerRadius, message, data);
    }

    public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, Component message, List<DT_ListBoxData> data) {
        super(x, y, w, h, sectors, innerRadius, outerRadius, message, data);
    }

    public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, int highlightColor, int normalColor, Component message, DT_ListBoxData... data) {
        super(x, y, w, h, sectors, innerRadius, outerRadius, highlightColor, normalColor, message, data);
    }

    public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, int highlightColor, int normalColor, Component message, List<DT_ListBoxData> data) {
        super(x, y, w, h, sectors, innerRadius, outerRadius, highlightColor, normalColor, message, data);
    }

    @Override
    protected void drawName(GuiGraphics guiGraphics, double rad, String name, int color, float size) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        String[] parts = name.split("\\?");
        if (parts.length == 2){
            pose.mulPose(Axis.ZP.rotation((float) -rad));
            double r = getFanTextInnerSpace() + (outerRadius - innerRadius) / 2d;
            double x =  Math.cos(rad) * r;
            double y =  Math.sin(rad) * r;
            pose.translate(x,y,0);
            pose.scale(size,size,size);
            switch (parts[0]){
                case "slot" -> {
                    LocalPlayer player = Minecraft.getInstance().player;
                    if (player != null){
                        ItemStack item = player.getInventory().getItem(Integer.parseInt(parts[1]));
                        guiGraphics.renderItem(item,-8,-8);
                        guiGraphics.renderItemDecorations(font,item,-8,-8);
                    }
                }
                case "item" -> {
                    Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(parts[1]));
                    if (item != null){
                        guiGraphics.renderItem(new ItemStack(item),-8,-8);
                    }
                }
                case "image" -> {
                    ResourceLocation res = ResourceLocation.tryParse(parts[1]);
                    if (res != null){
                        guiGraphics.blit(res,-8,-8,0,0,0,16,16,16,16);
                    }
                }
            }
        }else {
            pose.scale(size,size,size);
            drawTextName(guiGraphics,name,color);
        }
        pose.popPose();
    }
}
