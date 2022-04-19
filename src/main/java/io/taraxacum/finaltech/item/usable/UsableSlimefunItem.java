package io.taraxacum.finaltech.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.taraxacum.finaltech.util.MachineUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public abstract class UsableSlimefunItem extends SlimefunItem {
    public UsableSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler(MachineUtil.BLOCK_PLACE_HANDLER_DENY);
        this.addItemHandler((ItemUseHandler) playerRightClickEvent -> UsableSlimefunItem.this.function(playerRightClickEvent));
    }

    protected abstract void function(PlayerRightClickEvent playerRightClickEvent);
}
