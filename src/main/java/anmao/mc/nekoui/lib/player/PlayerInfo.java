package anmao.mc.nekoui.lib.player;

import anmao.mc.nekoui.constant._MC;
import net.minecraft.world.entity.player.Player;

public class PlayerInfo {
    public static Player clientPlayer = _MC.MC.player;
    public static void getDat(Player player){
        System.out.println(clientPlayer.getAbilities());
    }
}
