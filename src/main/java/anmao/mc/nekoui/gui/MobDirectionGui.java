package anmao.mc.nekoui.gui;

import anmao.mc.amlib.math._MathCDT;
import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.mob$direction.MobDirectionConfig;
import anmao.mc.nekoui.constant._MC;
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

public class MobDirectionGui extends MobDirectionConfig{
    public static final String id = "mob_direction";
    private static final ResourceLocation MOB_POI = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/poi.png");
    private static final ResourceLocation MOB_PLAYER = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/player.png");
    private static final ResourceLocation MOB_ANIMAL = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/animal.png");
    private static final ResourceLocation MOB_MONSTER = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/monster.png");
    private static final ResourceLocation MOB_OTHER = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/other.png");

    public static Vec3 v(Vec3 pos,Vec3 focus, double r){
        Vec3 v = pos.subtract(focus);
        return new Vec3(v.x,0,v.z).normalize().scale(r).add(pos);

    }
    public static final IGuiOverlay GUI = ((gui, guiGraphics, partialTick, screenWidth, screenHeight)->{
        Level olevel = _MC.MC.level;
        LocalPlayer localPlayer = _MC.MC.player;
        if (olevel != null && localPlayer != null){
            if (olevel.isClientSide){
                int sx = screenWidth / 2;
                int sy = screenHeight / 2;
                List<Mob> mobs = olevel.getEntities(EntityTypeTest.forClass(Mob.class), localPlayer.getBoundingBox().inflate(mobDirectionConfig.getPoiRadius()), Entity::isAlive);
                Vec3 forward = localPlayer.getForward();
                Vec3 playerPosition = localPlayer.position();
                for (Mob mob : mobs){
                    if (mob != null ){
                        Vec3 mobPosition = mob.position();
                        /*
                        //            N（0，-1）
                        // （-1，0）E    W（1，0）
                        //            S（0，1）
                        //（-0.6，-0.6）    （0.6，-0.6）
                        //（-0.6，0.6）     （0.6，0.6）
                         */
                        double cx = mobPosition.x - playerPosition.x;
                        double cz = mobPosition.z - playerPosition.z;
                        double g = Math.atan2(cz,cx) - Math.atan2(forward.z,forward.x);
                        if (g > Math.PI){
                            g = g - _MathCDT.TWICE_PI;
                        }
                        if (g < -Math.PI){
                            g = g + _MathCDT.TWICE_PI;
                        }
                        int ox = (int) ( mobDirectionConfig.getPoiShowRadius() * Math.cos(g));
                        int oz = (int) ( mobDirectionConfig.getPoiShowRadius() * Math.sin(g));
                        int or = mobDirectionConfig.chickRadius(cx, cz);
                        //ResourceLocation show = MOB_OTHER;
                        if (mob instanceof Animal){
                            //show = MOB_ANIMAL;
                            guiGraphics.blit(MOB_ANIMAL,sx + oz, sy - ox,0,0,or,or,or,or);
                        } else if (mob instanceof Monster) {
                            guiGraphics.blit(MOB_MONSTER,sx + oz, sy - ox,0,0,or,or,or,or);
                        }else {
                            guiGraphics.blit(MOB_OTHER, sx + oz, sy -ox, 0, 0, or,or,or,or);
                        }
                    }
                }
            }
        }

    });
}
