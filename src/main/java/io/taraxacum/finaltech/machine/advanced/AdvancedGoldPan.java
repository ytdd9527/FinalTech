package io.taraxacum.finaltech.machine.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.tools.GoldPan;
import io.github.thebusybiscuit.slimefun4.implementation.items.tools.NetherGoldPan;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.machine.AbstractAdvanceMachine;
import io.taraxacum.finaltech.menu.AdvancedMachineMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class AdvancedGoldPan extends AbstractAdvanceMachine {
    private static final ItemStack GRAVEL = new ItemStack(Material.GRAVEL);
    private static final ItemStack SOUL_SAND = new ItemStack(Material.SOUL_SAND);
    public AdvancedGoldPan(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
    }

    @Override
    protected MachineRecipe findNextRecipe(BlockMenu inv) {
        GoldPan goldPan = SlimefunItems.GOLD_PAN.getItem(GoldPan.class);
        NetherGoldPan netherGoldPan = SlimefunItems.NETHER_GOLD_PAN.getItem(NetherGoldPan.class);

        int amount = MachineUtil.updateQuantityModule(inv, AdvancedMachineMenu.MODULE_SLOT, AdvancedMachineMenu.INFO_SLOT);

        for (int slot : getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);

            if (SlimefunUtils.isItemSimilar(item, GRAVEL, true, false)) {
                if(item.getAmount() < amount) {
                    amount = item.getAmount();
                }
                ItemStack input = new ItemStack(GRAVEL);
                ItemStack output = new ItemStack(goldPan.getRandomOutput());
                input.setAmount(amount);
                output.setAmount(amount);
                MachineRecipe recipe = new MachineRecipe(0, new ItemStack[] {
                        input
                }, new ItemStack[] {
                        output
                });
                if(InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    inv.consumeItem(slot, amount);
                    return recipe;
                }
            } else if (SlimefunUtils.isItemSimilar(item, SOUL_SAND, true, false)) {
                if(item.getAmount() < amount) {
                    amount = item.getAmount();
                }
                ItemStack input = new ItemStack(SOUL_SAND);
                ItemStack output = new ItemStack(netherGoldPan.getRandomOutput());
                input.setAmount(amount);
                output.setAmount(amount);
                MachineRecipe recipe = new MachineRecipe(0, new ItemStack[] {
                        input
                }, new ItemStack[] {
                        output
                });

                if (InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    inv.consumeItem(slot, amount);
                    return recipe;
                }
            }
        }
        return null;
    }
}
