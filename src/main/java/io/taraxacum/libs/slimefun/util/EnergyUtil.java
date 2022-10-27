package io.taraxacum.libs.slimefun.util;

import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EnergyUtil {
    public static String getCharge(@Nonnull Location location) {
        return BlockStorage.hasBlockInfo(location) ? EnergyUtil.getCharge(BlockStorage.getLocationInfo(location)) : "0";
    }
    public static String getCharge(@Nonnull Config config) {
        return config.contains(ConstantTableUtil.CONFIG_CHARGE) ? config.getString(ConstantTableUtil.CONFIG_CHARGE) : StringNumberUtil.ZERO;
    }
    public static void setCharge(@Nonnull Location location, @Nonnull String energy) {
        BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_CHARGE, energy);
    }
    public static void setCharge(@Nonnull Config config, @Nonnull String energy) {
        config.setValue(ConstantTableUtil.CONFIG_CHARGE, energy);
    }
    public static void setCharge(@Nonnull Config config, int energy) {
        config.setValue(ConstantTableUtil.CONFIG_CHARGE, String.valueOf(energy));
    }
}
