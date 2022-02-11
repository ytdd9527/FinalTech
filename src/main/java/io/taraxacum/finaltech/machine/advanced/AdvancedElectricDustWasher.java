package io.taraxacum.finaltech.machine.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.multiblocks.OreWasher;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.machine.AbstractAdvanceMachine;
import io.taraxacum.finaltech.menu.AdvancedMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AdvancedElectricDustWasher extends AbstractAdvanceMachine {
    public AdvancedElectricDustWasher(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {

    }

    //todo
    @Override
    protected MachineRecipe findNextRecipe(BlockMenu inv) {

        OreWasher oreWasher = SlimefunItems.ORE_WASHER.getItem(OreWasher.class);

        int amount = MachineUtil.updateQuantityModule(inv, AdvancedMachineMenu.MODULE_SLOT, AdvancedMachineMenu.INFO_SLOT);

        for (int slot : getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);
            if (SlimefunUtils.isItemSimilar(item, SlimefunItems.SIFTED_ORE, true, false)) {
                if(item.getAmount() < amount) {
                    amount = item.getAmount();
                }
                ItemStack output = new ItemStack(oreWasher.getRandomDust());
                ItemStack input = new ItemStack(SlimefunItems.SIFTED_ORE);
                input.setAmount(amount);
                output.setAmount(amount);
                MachineRecipe recipe = new MachineRecipe(0, new ItemStack[] { input}, new ItemStack[] { output});
                if(output.getType() != Material.AIR && InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    inv.consumeItem(slot, amount);
                    return recipe;
                }
            } else if(SlimefunUtils.isItemSimilar(item, SlimefunItems.PULVERIZED_ORE, true, false)) {

            }
        }
        return null;
    }
}
