package dev.anye.mc.nekoui.util;

import com.mojang.blaze3d.platform.InputConstants;
import dev.anye.mc.nekoui.NekoUI;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyBinding {
    public static final ResourceLocation KEY_CATEGORY_RES = ResourceLocation.fromNamespaceAndPath(NekoUI.MOD_ID,"nekoui") ;//"key.category.nekoui.mc";
    public static final KeyMapping.Category KEY_CATEGORY = new KeyMapping.Category(KEY_CATEGORY_RES);
    //public static final String KEY_CATEGORY = "key.category.nekoui.mc";
    public static final String KEY_MENU = "key.nekoui.open_menu";
    public static final String KEY_SET_MENU = "key.nekoui.open_set_menu";

    public static final KeyMapping OPEN_MENU = new KeyMapping(KEY_MENU, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_TAB, KEY_CATEGORY);
    public static final KeyMapping OPEN_SET_MENU = new KeyMapping(KEY_SET_MENU, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_BACKSLASH, KEY_CATEGORY);
}
