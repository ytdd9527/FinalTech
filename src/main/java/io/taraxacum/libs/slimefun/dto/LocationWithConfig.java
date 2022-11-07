package io.taraxacum.libs.slimefun.dto;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.Location;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public record LocationWithConfig(@Nonnull Location location, @Nonnull Config config) {
    @Nonnull
    public Location getLocation() {
        return this.location;
    }
    @Nonnull
    public Config getConfig() {
        return this.config;
    }
}
