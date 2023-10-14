package anmao.mc.nekoui.hud;

import anmao.mc.nekoui.Config;
import anmao.mc.nekoui.NekoUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.List;

public class UI_Info {
    public static final double TWICEPI = 2 * Math.PI;
    private static final ResourceLocation MOB_POI = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/poi.png");
    private static final ResourceLocation MOB_PLAYER = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/player.png");
    private static final ResourceLocation MOB_ANIMAL = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/animal.png");
    private static final ResourceLocation MOB_MONSTER = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/monster.png");
    private static final ResourceLocation MOB_OTHER = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/other.png");

    public static Vec3 v(Vec3 pos,Vec3 focus, double r){
        Vec3 v = pos.subtract(focus);
        return new Vec3(v.x,0,v.z).normalize().scale(r).add(pos);

    }
    public static final IGuiOverlay UI_INFO = ((gui, poseStack, partialTick, screenWidth, screenHeight)->{
        int sx = screenWidth / 2;
        int sy = screenHeight / 2;
        Level olevel = Minecraft.getInstance().level;
        LocalPlayer oplayer = Minecraft.getInstance().player;
        if (olevel != null && oplayer != null){
            if (olevel.isClientSide){
                List<Mob> oent = olevel.getEntities(EntityTypeTest.forClass(Mob.class), oplayer.getBoundingBox().inflate(Config.poiDetectionRadius), Entity::isAlive);
                Vec3 forward = oplayer.getForward();
                Vec3 opv = oplayer.position();
                for (Mob omob : oent){
                    if (omob != null ){
                        Vec3 omp = omob.position();
                        /*
                        //            N（0，-1）
                        // （-1，0）E    W（1，0）
                        //            S（0，1）
                        //（-0.6，-0.6）    （0.6，-0.6）
                        //（-0.6，0.6）     （0.6，0.6）
                         */
                        double cx = omp.x - opv.x;
                        double cz = omp.z - opv.z;
                        double g = Math.atan2(cz,cx) - Math.atan2(forward.z,forward.x);
                        if (g > Math.PI){
                            g = g - TWICEPI;
                        }
                        if (g < -Math.PI){
                            g = g + TWICEPI;
                        }
                        int ox = (int) ( Config.poiShowRadius * Math.cos(g));
                        int oz = (int) ( Config.poiShowRadius * Math.sin(g));
                        int or = Config.poiSize;
                        if (Config.poiDynamicSize){
                            if (cx * cx + cz * cz <= Config.poiDynamicRadius){
                                or = Config.poiSizeDynamic;
                            }
                        }

                        //ResourceLocation show = MOB_OTHER;
                        if (omob instanceof Animal){
                            //show = MOB_ANIMAL;
                            poseStack.blit(MOB_ANIMAL,sx + oz, sy - ox,0,0,or,or,or,or);
                        } else if (omob instanceof Monster) {
                            poseStack.blit(MOB_MONSTER,sx + oz, sy - ox,0,0,or,or,or,or);
                        }else {
                            poseStack.blit(MOB_OTHER, sx + oz, sy -ox, 0, 0, or,or,or,or);
                        }
                    }
                }
            }
        }

    });
}
