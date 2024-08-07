package anmao.mc.nekoui.gui;

import anmao.dev.core.math._MathCDT;
import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.mob$direction.MobDirectionConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MobDirectionGui extends MobDirectionConfig{
    public static final String id = "mob_direction";
    private static final double additionalAngle = -135.0 * Math.PI / 180.0;
    private static final ResourceLocation MOB_POI = new ResourceLocation(NekoUI.MOD_ID,"textures/ui/info/poi.png");

    public static Vec3 v(Vec3 pos,Vec3 focus, double r){
        Vec3 v = pos.subtract(focus);
        return new Vec3(v.x,0,v.z).normalize().scale(r).add(pos);

    }
    public static final IGuiOverlay GUI = ((gui, guiGraphics, partialTick, screenWidth, screenHeight)->{
        Level olevel = Minecraft.getInstance().level;
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (olevel != null && localPlayer != null){
            if (olevel.isClientSide){
                int startX = screenWidth / 2;
                int startY = screenHeight / 2;
                List<Mob> mobs = olevel.getEntities(EntityTypeTest.forClass(Mob.class), localPlayer.getBoundingBox().inflate(I.getDatas().getPoiRadius()), Entity::isAlive);
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
                        int ox = (int) ( I.getDatas().getPoiShowRadius() * Math.cos(g));
                        int oz = (int) ( I.getDatas().getPoiShowRadius() * Math.sin(g));

                        g += additionalAngle;
                        int imageSize;
                        if (I.getDatas().isDynamicDisplay()) {
                            double distance = playerPosition.distanceTo(mobPosition);
                            distance *= I.getDatas().getRatio();
                            imageSize = (int) (I.getDatas().getPoiSize() + distance);
                            imageSize = Mth.clamp(imageSize,I.getDatas().getPoiMinSize(), I.getDatas().getPoiMaxSize());
                        }else {
                            imageSize = I.getDatas().getPoiSize();
                        }
                        PoseStack pose = guiGraphics.pose();
                        pose.pushPose();
                        pose.translate(startX + oz, startY - ox,0);
                        pose.mulPose(new Quaternionf(new AxisAngle4d(g,0,0,1)));
                        setRenderColor(mob);
                        /*
                        LivingEntity target = mob.getTarget();
                        if (target != null){
                            System.out.println("target UUID::"+target.getUUID());
                            System.out.println("localPlayer UUID::"+localPlayer.getUUID());
                            if (mob.getTarget().getUUID() == localPlayer.getUUID()){
                                RenderSystem.setShaderColor(255,0,0,1.0F);
                            }
                        }else {
                            setRenderColor(mob);
                        }
                        /*
                        if (mob instanceof Animal){
                            drawPoint(guiGraphics,MOB_POI,imageSize);
                        } else if (mob instanceof Monster) {
                            drawPoint(guiGraphics,MOB_MONSTER,imageSize);
                        }else {
                            drawPoint(guiGraphics,MOB_OTHER,imageSize);
                        }

                         */
                        drawPoint(guiGraphics,MOB_POI,imageSize);
                        pose.popPose();
                    }
                }
            }
        }
    });
    private static void setRenderColor(LivingEntity livingEntity){
        if (livingEntity instanceof AgeableMob || livingEntity instanceof WaterAnimal){
            RenderSystem.setShaderColor(0,255,0,255);
        } else if (livingEntity instanceof Monster || livingEntity instanceof FlyingMob || livingEntity instanceof Slime) {
            RenderSystem.setShaderColor(255,0,0,255);
        }else {
            RenderSystem.setShaderColor(64,64,64,255);
        }

    }
    private static void drawPoint(GuiGraphics guiGraphics,ResourceLocation res,int imageSize){
        guiGraphics.blit(res,0,0,0,0,imageSize,imageSize,imageSize,imageSize);
    }
    private static void drawPoint(GuiGraphics guiGraphics,ResourceLocation res,int x,int y,int imageSize){
        guiGraphics.blit(res,x,y,0,0,imageSize,imageSize,imageSize,imageSize);
    }
}
