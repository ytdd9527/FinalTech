package io.taraxacum.finaltech.api.dto;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.Location;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public record LocationWithConfig(@Nonnull Location location, @Nonnull Config config) {
    public Location getLocation() {
        return this.location;
    }

    public Config getConfig() {
        return this.config;
    }
}
