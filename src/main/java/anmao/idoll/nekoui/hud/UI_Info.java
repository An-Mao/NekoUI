package anmao.idoll.nekoui.hud;

import anmao.idoll.nekoui.NekoUI;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.text.Normalizer;
import java.util.List;

public class UI_Info {
    private static final ResourceLocation MOB_POI = new ResourceLocation(NekoUI.MODID,"textures/ui/info/poi.png");

    public static Vec3 v(Vec3 pos,Vec3 focus, double r){
        Vec3 v = pos.subtract(focus);
        return new Vec3(v.x,0,v.z).normalize().scale(r).add(pos);

    }
    public static final IGuiOverlay UI_INFO = ((gui, poseStack, partialTick, screenWidth, screenHeight)->{
        //
        int sx = screenWidth / 2;
        int sy = screenHeight / 2;
        //System.out.println("-------------uinfo--------------");

        Level olevel = Minecraft.getInstance().level;
        LocalPlayer oplayer = Minecraft.getInstance().player;


        if (olevel != null && oplayer != null){
            if (olevel.isClientSide){

                List<Mob> oent = olevel.getEntities(EntityTypeTest.forClass(Mob.class), oplayer.getBoundingBox().inflate(20), Entity::isAlive);

                int rr = 80;
                Vec3 forward = oplayer.getForward();
                Vec3 opv = oplayer.position().multiply(forward);//.multiply(forward);
                for (Mob omob : oent){
                    if (omob != null ){
                        Vec3 omp = omob.position();
                        /*
                        //omp = omp.multiply(forward);
                        omp = omp.subtract(opv);
                        omp = omp.normalize();
                        Vec3 omobv = omp.normalize();
                        double ox = sx - omp.x * rr;
                        double oz = sy - omp.z * rr;
                        System.out.println(omp+":::ox:" + omp.x + "|oz:"+omp.z);
                        double ax = omp.x - opv.x;
                        double az = omp.z - opv.z;
                        double d = Math.sqrt(ax * ax + az * az);
                        double scale = rr / d;
                        double scalex = ax * scale;
                        double scalez = az * scale;
                        int ox = (int) (sx + scalex);
                        int oz = (int) (sy + scalez);
                        poseStack.blit(MOB_POI, ox, oz,0,0,12,12,12,12);
                         */
                        Vec3 omobv = v(omp,opv,rr);
                        poseStack.blit(MOB_POI, sx + (int)  omobv.x, sy + (int) omobv.z,0,0,12,12,12,12);

                        //d = Math.sqrt(ox * ox + )
                        /*
                        if (opx > ox){
                            if (opz >oz){
                                System.out.println("> hou >");
                            }else {
                                System.out.println("> hou2 <=");
                            }
                        }else {
                            if (opz >oz){
                                System.out.println("> qian >");
                            }else {
                                System.out.println("> qian2 <=");
                            }
                        }

                         */
                        //poseStack.blit(MOB_POI,sx - 94 ,sy - 54,0,0,12,12,12,12);
                            /*
                            if (opx > ox && opz > oz){
                                System.out.println("> 后 >");
                            }
                            if (opx > ox && opz <= oz){
                                System.out.println("> 右 <=");
                            }
                            if (opx <= ox && opz > oz){
                                System.out.println("<= 左 >");
                            }
                            if (opx <= ox && opz <= oz){
                                System.out.println("<= 前 <=");
                            }

                             */
                        //double a = omob.getX() - opx;
                        //double b = omob.getZ() - opz;
                        //System.out.println("x:"+a);
                        //System.out.println("z:"+b);
                    }
                }
            }
        }

    });
}
