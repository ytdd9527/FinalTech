package io.taraxacum.finaltech.util;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.Location;

/**
 * @author Final_ROOT
 */
public class LocationWithConfig {
    private Location location;
    private Config config;

    public LocationWithConfig() {

    }

    public LocationWithConfig(Location location, Config config) {
        this.location = location;
        this.config = config;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
