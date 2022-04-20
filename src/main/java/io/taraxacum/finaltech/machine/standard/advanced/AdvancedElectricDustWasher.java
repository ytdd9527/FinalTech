package io.taraxacum.finaltech.machine.standard.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.multiblocks.OreWasher;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.taraxacum.finaltech.menu.standard.AdvancedMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class AdvancedElectricDustWasher extends AbstractAdvanceMachine {
    public AdvancedElectricDustWasher(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {

    }

    //todo
    @Override
    protected MachineRecipe matchRecipe(BlockMenu blockMenu, int index) {
        OreWasher oreWasher = SlimefunItems.ORE_WASHER.getItem(OreWasher.class);

        int amount = MachineUtil.updateQuantityModule(blockMenu, AdvancedMachineMenu.MODULE_SLOT, AdvancedMachineMenu.INFO_SLOT);

        for (int slot : getInputSlots()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemSimilar(item, SlimefunItems.SIFTED_ORE)) {
                if (item.getAmount() < amount) {
                    amount = item.getAmount();
                }
                ItemStack output = new ItemStack(oreWasher.getRandomDust());
                ItemStack input = new ItemStack(SlimefunItems.SIFTED_ORE);
                input.setAmount(amount);
                output.setAmount(amount);
                MachineRecipe recipe = new MachineRecipe(0, new ItemStack[] { input}, new ItemStack[] { output});
                if (output.getType() != Material.AIR && InvUtils.fitAll(blockMenu.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    blockMenu.consumeItem(slot, amount);
                    return recipe;
                }
            } else if (ItemStackUtil.isItemSimilar(item, SlimefunItems.PULVERIZED_ORE)) {
                // todo
            }
        }
        return null;
    }
}
