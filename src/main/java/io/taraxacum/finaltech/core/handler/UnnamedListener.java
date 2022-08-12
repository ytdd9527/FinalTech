package io.taraxacum.finaltech.core.handler;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class UnnamedListener implements Listener {

    public void onPlayerMove(PlayerMoveEvent playerMoveEvent) {
        Bukkit.getLogger().warning("player moved!");
        Location location = playerMoveEvent.getTo();
        Config config = BlockStorage.getLocationInfo(location);
        if(config != null) {

        }
    }
}
