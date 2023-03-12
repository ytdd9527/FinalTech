package io.taraxacum.libs.slimefun.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class LocationInfo {
    private Location location;
    private final Config config;
    private final String id;
    private final SlimefunItem slimefunItem;

    private LocationInfo(@Nonnull Location location, @Nonnull Config config, @Nonnull String id, @Nonnull SlimefunItem slimefunItem) {
        this.location = location;
        this.config = config;
        this.id = id;
        this.slimefunItem = slimefunItem;
    }

    public void refresh() {

    }

    public void cloneLocation() {
        this.location = this.location.clone();
    }

    public Location getLocation() {
        return location;
    }

    public Config getConfig() {
        return config;
    }

    public String getId() {
        return id;
    }

    public SlimefunItem getSlimefunItem() {
        return slimefunItem;
    }

    @Nullable
    public static LocationInfo get(@Nonnull Location location) {
        Config config = BlockStorage.getLocationInfo(location);
        String id = config.getString("id");
        if(id != null) {
            SlimefunItem slimefunItem = SlimefunItem.getById(id);
            if(slimefunItem != null) {
                return new LocationInfo(location, config, id, slimefunItem);
            }
        }

        return null;
    }
}
