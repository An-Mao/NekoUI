package anmao.mc.nekoui.hud;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.CC;
import anmao.mc.nekoui.constant._MC;
import anmao.mc.nekoui.constant._Math;
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

public class HUD_Mob {
    public static final String id = "hud_mob";
    private static final ResourceLocation MOB_POI = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/poi.png");
    private static final ResourceLocation MOB_PLAYER = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/player.png");
    private static final ResourceLocation MOB_ANIMAL = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/animal.png");
    private static final ResourceLocation MOB_MONSTER = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/monster.png");
    private static final ResourceLocation MOB_OTHER = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/other.png");

    public static Vec3 v(Vec3 pos,Vec3 focus, double r){
        Vec3 v = pos.subtract(focus);
        return new Vec3(v.x,0,v.z).normalize().scale(r).add(pos);

    }
    public static final IGuiOverlay UI_INFO = ((gui, guiGraphics, partialTick, screenWidth, screenHeight)->{
        int sx = screenWidth / 2;
        int sy = screenHeight / 2;
        Level olevel = _MC.MC.level;
        LocalPlayer oplayer = _MC.MC.player;
        if (olevel != null && oplayer != null){
            if (olevel.isClientSide){
                List<Mob> oent = olevel.getEntities(EntityTypeTest.forClass(Mob.class), oplayer.getBoundingBox().inflate(CC.hudMobPoiRadius), Entity::isAlive);
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
                            g = g - _Math.TWICE_PI;
                        }
                        if (g < -Math.PI){
                            g = g + _Math.TWICE_PI;
                        }
                        int ox = (int) ( CC.hudMobPoiShowRadius * Math.cos(g));
                        int oz = (int) ( CC.hudMobPoiShowRadius * Math.sin(g));
                        int or = chickRadius(cx, cz);
                        //ResourceLocation show = MOB_OTHER;
                        if (omob instanceof Animal){
                            //show = MOB_ANIMAL;
                            guiGraphics.blit(MOB_ANIMAL,sx + oz, sy - ox,0,0,or,or,or,or);
                        } else if (omob instanceof Monster) {
                            guiGraphics.blit(MOB_MONSTER,sx + oz, sy - ox,0,0,or,or,or,or);
                        }else {
                            guiGraphics.blit(MOB_OTHER, sx + oz, sy -ox, 0, 0, or,or,or,or);
                        }
                    }
                }
            }
        }

    });

    private static int chickRadius(double cx, double cz) {
        int or = CC.hudMobPoiSize;
        if (CC.hudMobDynamicDisplay){
            double xxzz = cx * cx + cz * cz;
            if (xxzz <= CC.hudMobPoiDynamicRadiusClose ){
                or =  CC.hudMobPoiDynamicSizeClose;
            }else if (xxzz <= CC.hudMobPoiDynamicRadiusMid){
                or = CC.hudMobPoiDynamicSizeMid;
            }
        }
        return or;
    }
}
