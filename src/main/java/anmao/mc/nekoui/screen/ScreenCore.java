package anmao.mc.nekoui.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenCore extends Screen {
    public final String translateId;
    protected ScreenCore(String translateId) {
        super(Component.translatable(translateId+".title"));
        this.translateId = translateId +".";
    }
    public Component getComponent(String s){
        return Component.translatable(this.translateId + s);
    }
}
