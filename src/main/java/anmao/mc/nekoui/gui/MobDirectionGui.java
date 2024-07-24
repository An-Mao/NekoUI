package anmao.mc.nekoui.gui;

import anmao.mc.amlib.math._MathCDT;
import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.mob$direction.MobDirectionConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
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
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MobDirectionGui extends MobDirectionConfig{
    public final String id = "mob_direction";
    private final double additionalAngle = -135.0 * Math.PI / 180.0;
    private final ResourceLocation MOB_POI = ResourceLocation.tryBuild(NekoUI.MOD_ID,"textures/ui/info/poi.png");
    public final int screenWidth;
    public final int screenHeight;
    public final Minecraft minecraft;
    public MobDirectionGui(Minecraft minecraft){
        this.minecraft = minecraft;
        screenWidth = minecraft.getWindow().getScreenWidth() / 2;
        screenHeight = minecraft.getWindow().getScreenHeight() / 2;
    }
    public Vec3 v(Vec3 pos,Vec3 focus, double r){
        Vec3 v = pos.subtract(focus);
        return new Vec3(v.x,0,v.z).normalize().scale(r).add(pos);

    }
    public void render(GuiGraphics guiGraphics, DeltaTracker partialTick){
        Level olevel = Minecraft.getInstance().level;
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (olevel != null && localPlayer != null){
            if (olevel.isClientSide){
                int startX = screenWidth / 2;
                int startY = screenHeight / 2;
                //System.out.println("startX:"+startX+" startY:"+startY + "screenWidth:"+screenWidth+" screenHeight:"+screenHeight );
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
                        pose.translate(startX  + oz, startY  - ox,0);
                        //pose.translate(oz, - ox,0);
                        pose.mulPose(new Quaternionf(new AxisAngle4d(g,0,0,1)));
                        setRenderColor(mob,guiGraphics);

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
                        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
                    }
                }
            }
        }
    }
    private static void setRenderColor(LivingEntity livingEntity,GuiGraphics guiGraphics){
        if (livingEntity instanceof AgeableMob || livingEntity instanceof WaterAnimal){
            guiGraphics.setColor(0,255,0,255);
            //RenderSystem.setShaderColor(0,255,0,255);
        } else if (livingEntity instanceof Monster || livingEntity instanceof FlyingMob || livingEntity instanceof Slime) {
            guiGraphics.setColor(255,0,0,255);
            //RenderSystem.setShaderColor(255,0,0,255);
        }else {
            guiGraphics.setColor(64,64,64,255);
            //RenderSystem.setShaderColor(64,64,64,255);
        }

    }
    private static void drawPoint(GuiGraphics guiGraphics,ResourceLocation res,int imageSize){
        guiGraphics.blit(res,0,0,0,0,imageSize,imageSize,imageSize,imageSize);
    }
    private static void drawPoint(GuiGraphics guiGraphics,ResourceLocation res,int x,int y,int imageSize){
        guiGraphics.blit(res,x,y,0,0,imageSize,imageSize,imageSize,imageSize);
    }
}
