package anmao.mc.nekoui.lib;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Collection;

public class AM {
    public static double getAdddamage(Collection<AttributeModifier> attlist) {
        double dadd = 0;
        double dbase = 0;
        double dtotal = 1;
        for (AttributeModifier al : attlist) {
            if(al.getOperation() == AttributeModifier.Operation.ADDITION){
                dadd += al.getAmount();
            } else if (al.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                dbase += al.getAmount();
            } else if (al.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                dtotal *= 1.0D + al.getAmount();
            }
        }
        return (dadd + dadd * dbase) * dtotal;
    }
}
