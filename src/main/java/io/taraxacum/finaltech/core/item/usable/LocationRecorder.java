package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.plugin.util.PlayerUtil;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.PermissionUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.finaltech.util.LocationUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LocationRecorder extends UsableSlimefunItem implements RecipeItem {
    public LocationRecorder(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        PlayerInteractEvent interactEvent = playerRightClickEvent.getInteractEvent();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        if (playerRightClickEvent.getPlayer().isSneaking()) {
            Block block = interactEvent.getClickedBlock();
            if (block != null) {
                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.GLOW, 0, block));
                if(PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), block.getLocation(), Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                    ItemStack item = playerRightClickEvent.getItem();
                    LocationUtil.saveLocationToItem(item, block.getLocation());
                    LocationUtil.updateLocationItem(item);
                    PlayerUtil.updateIdInItem(item, playerRightClickEvent.getPlayer(), true);
                } else {
                    PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), block.getLocation(), Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK);
                }
            }
        } else {
            Location location = LocationUtil.parseLocationInItem(playerRightClickEvent.getItem());
            if (location == null) {
                return;
            }

            Block block = location.getBlock();
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.GLOW, 0, block));

            Player player = playerRightClickEvent.getPlayer();
            if (!PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), location, Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                player.sendRawMessage(FinalTech.getLanguageString("message", "no-permission", "location"));
            }

            if (BlockStorage.hasInventory(block)) {
                BlockMenu blockMenu = BlockStorage.getInventory(block);
                if (blockMenu.canOpen(block, player)) {
                    blockMenu.open(player);
                } else {
                    player.sendRawMessage(FinalTech.getLanguageString("message", "no-permission", "location"));
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
