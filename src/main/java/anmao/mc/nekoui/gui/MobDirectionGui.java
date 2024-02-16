package anmao.mc.nekoui.gui;

import anmao.mc.amlib.math._MathCDT;
import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.mob$direction.MobDirectionConfig;
import anmao.mc.nekoui.constant._MC;
import net.minecraft.client.gui.GuiGraphics;
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
                int startX = screenWidth / 2;
                int startY = screenHeight / 2;
                List<Mob> mobs = olevel.getEntities(EntityTypeTest.forClass(Mob.class), localPlayer.getBoundingBox().inflate(mobDirectionConfig.getPoiRadius()), Entity::isAlive);
                Vec3 playerForward = localPlayer.getForward();
                Vec3 playerPosition = localPlayer.position();
                for (Mob mob : mobs){
                    if (mob != null ){
                        Vec3 mobPosition = mob.position();
                        double cx = mobPosition.x - playerPosition.x;
                        double cz = mobPosition.z - playerPosition.z;
                        double g = Math.atan2(cz,cx) - Math.atan2(playerForward.z,playerForward.x);
                        if (g > Math.PI){
                            g = g - _MathCDT.TWICE_PI;
                        }
                        if (g < -Math.PI){
                            g = g + _MathCDT.TWICE_PI;
                        }
                        int ox = (int) ( mobDirectionConfig.getPoiShowRadius() * Math.cos(g));
                        int oz = (int) ( mobDirectionConfig.getPoiShowRadius() * Math.sin(g));
                        int imageSize;
                        if (mobDirectionConfig.isDynamicDisplay()) {
                            double distance = playerPosition.distanceTo(mobPosition);
                            distance *= mobDirectionConfig.getRatio();
                            imageSize = (int) (mobDirectionConfig.getPoiSize() + distance);
                            imageSize = Math.min(imageSize , mobDirectionConfig.getPoiMaxSize());
                            imageSize = Math.max(imageSize , mobDirectionConfig.getPoiMinSize());
                        }else {
                            imageSize = mobDirectionConfig.getPoiSize();
                        }
                        if (mob instanceof Animal){
                            drawPoint(guiGraphics,MOB_ANIMAL,startX + oz, startY - ox,imageSize);
                        } else if (mob instanceof Monster) {
                            drawPoint(guiGraphics,MOB_MONSTER,startX + oz, startY - ox,imageSize);
                        }else {
                            drawPoint(guiGraphics,MOB_OTHER,startX + oz, startY - ox,imageSize);
                        }
                    }
                }
            }
        }
    });
    private static void drawPoint(GuiGraphics guiGraphics,ResourceLocation res,int x,int y,int imageSize){
        guiGraphics.blit(res,x,y,0,0,imageSize,imageSize,imageSize,imageSize);
    }
}
