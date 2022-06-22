package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
                player.sendRawMessage(TextUtil.COLOR_NEGATIVE + "您似乎没有在此处使用该物品的权限");
            }

            Block block = location.getBlock();
            if (BlockStorage.hasInventory(block)) {
                BlockMenu blockMenu = BlockStorage.getInventory(block);
                if(blockMenu.canOpen(block, player)) {
                    blockMenu.open(player);
                    ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
                } else {
                    player.sendRawMessage(TextUtil.COLOR_NEGATIVE + "您似乎没有在此处使用该物品的权限");
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_INITIATIVE + "使用方式",
                "",
                TextUtil.COLOR_ACTION + "[右键]" + TextUtil.COLOR_NORMAL + "方块",
                TextUtil.COLOR_NORMAL + "记录该方块的位置",
                "",
                TextUtil.COLOR_ACTION + "蹲下[右键]",
                TextUtil.COLOR_NORMAL + "打开对应位置的粘液科技机器");
        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "权限保护",
                "",
                TextUtil.COLOR_NORMAL + "该物品在记录方块位置的同时",
                TextUtil.COLOR_NORMAL + "会记录使用者的相关信息");
    }
}
