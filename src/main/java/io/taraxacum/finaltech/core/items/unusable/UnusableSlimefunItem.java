package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.taraxacum.finaltech.core.items.AbstractMySlimefunItem;
import io.taraxacum.finaltech.util.MachineUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class UnusableSlimefunItem extends AbstractMySlimefunItem {
    public UnusableSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler(MachineUtil.BLOCK_PLACE_HANDLER_DENY);
        this.addItemHandler((ItemUseHandler) PlayerRightClickEvent::cancel);
    }

    public UnusableSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, @Nullable ItemStack recipeOutput) {
        super(itemGroup, item, recipeType, recipe, recipeOutput);
    }
}
