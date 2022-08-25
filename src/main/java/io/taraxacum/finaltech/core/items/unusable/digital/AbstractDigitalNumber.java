package io.taraxacum.finaltech.core.items.unusable.digital;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.items.unusable.UnusableSlimefunItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDigitalNumber extends UnusableSlimefunItem implements DigitalItem {
    public static final Map<Integer, ItemStack> INTEGER_ITEM_STACK_MAP = new HashMap<>(16);

    public AbstractDigitalNumber(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        INTEGER_ITEM_STACK_MAP.put(this.getDigit(), this.getItem());
    }
}
