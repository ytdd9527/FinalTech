package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LocationRecorder extends UsableSlimefunItem {
    public LocationRecorder(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        PlayerInteractEvent interactEvent = playerRightClickEvent.getInteractEvent();
        if (playerRightClickEvent.getPlayer().isSneaking()) {
            Block block = interactEvent.getClickedBlock();
            if (block != null && SlimefunUtil.hasPermission(playerRightClickEvent.getPlayer(), block.getLocation(), Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                ItemStack item = playerRightClickEvent.getItem();
                LocationUtil.saveLocationToItem(item, block.getLocation());
                LocationUtil.updateLocationItem(item);
                PlayerUtil.updateIdInItem(item, playerRightClickEvent.getPlayer(), true);
                ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
            }
        } else {
            Location location = LocationUtil.parseLocationInItem(playerRightClickEvent.getItem());
            if (location == null) {
                return;
            }

            Player player = playerRightClickEvent.getPlayer();
            if (!SlimefunUtil.hasPermission(playerRightClickEvent.getPlayer(), location, Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                player.sendRawMessage(TextUtil.colorRandomString("您似乎没有在此处使用该物品的权限"));
            }

            Block block = location.getBlock();
            if (BlockStorage.hasInventory(block)) {
                BlockMenu blockMenu = BlockStorage.getInventory(block);
                blockMenu.open(player);
                ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
            }
        }
    }
}
