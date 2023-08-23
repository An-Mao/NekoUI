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
                Vec3 opv = oplayer.position();
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
                        /*
                        //            北（0，-1）
                        // （-1，0）西    东（1，0）
                        //            南（0，1）
                        //（-0.6，-0.6）    （0.6，-0.6）
                        //（-0.6，0.6）     （0.6，0.6）
                         */
                        double cx = omp.x - opv.x;
                        double cz = omp.z - opv.z;
                        double g = Math.atan2(cz,cx) - Math.atan2(forward.z,forward.x);
                        if (g > Math.PI){
                            g = g - 2 * Math.PI;
                        }
                        if (g < -Math.PI){
                            g = g + 2 * Math.PI;
                        }
                        /*
                        double at = Math.atan2(omobv.z,omobv.x);
                        double cg = at +g;
                        if (cg > Math.PI){
                            cg = cg - 2 * Math.PI;
                        }
                        if (cg < -Math.PI){
                            cg = cg + 2 * Math.PI;
                        }
                        double cr = Math.sqrt(omobv.x * omobv.x + omobv.z * omobv.z);
                        int sox = (int) (cr * Math.cos(cg));
                        int soz = (int) (cr * Math.sin(cg));
                        System.out.println("[F]"+g);
                         */
                        //System.out.println("[F]"+g * (180/Math.PI));
                        int ox = (int) ( rr * Math.cos(g));
                        int oz = (int) ( rr * Math.sin(g));
                        poseStack.blit(MOB_POI,sx +( oz), sy + (- ox),0,0,12,12,12,12);
                    }
                }
            }
        }

    });
}
