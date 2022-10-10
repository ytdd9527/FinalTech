package io.taraxacum.finaltech.core.listener;

import io.taraxacum.finaltech.core.items.unusable.Box;
import io.taraxacum.finaltech.setup.FinalTechItems;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * @see io.taraxacum.finaltech.core.items.unusable.Box
 * @author Final_ROOT
 */
public class BoxListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();
        Location location = player.getLocation();
        World world = location.getWorld();
        if(world != null) {
            int maxHeight = world.getMaxHeight();
            if(location.getY() >= maxHeight + Box.HEIGHT) {
                world.dropItem(location, FinalTechItems.BOX);
            }
        }

    }
}
