package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.tools.GoldPan;
import io.github.thebusybiscuit.slimefun4.implementation.items.tools.NetherGoldPan;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.FinalAdvanceMachine;
import io.taraxacum.finaltech.setup.FinalTechItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class AdvancedGoldPan extends FinalAdvanceMachine {
    private String machineIdentifier = "FINAL_ADVANCED_GOLD_PAN";

    public AdvancedGoldPan(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void registerDefaultRecipes() {

    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return this.machineIdentifier;
    }

    protected MachineRecipe findNextRecipe(BlockMenu inv) {

        ItemStack gravel = new ItemStack(Material.GRAVEL);
        ItemStack soulSand = new ItemStack(Material.SOUL_SAND);
        GoldPan goldPan = SlimefunItems.GOLD_PAN.getItem(GoldPan.class);
        NetherGoldPan netherGoldPan = SlimefunItems.NETHER_GOLD_PAN.getItem(NetherGoldPan.class);

        int amount = 1;
        ItemStack quantityModule = inv.getItemInSlot(31);
        if(quantityModule != null && SlimefunUtils.isItemSimilar(FinalTechItems.QUANTITY_MODULE, quantityModule, true, false)) {
            amount = quantityModule.getAmount();
        }

        CustomItemStack progress = new CustomItemStack(Material.REDSTONE, "&f可升级模块",
                "&7该机器可以进行升级",
                "&7当前效率：" + amount);
        inv.replaceExistingItem(22, progress);

        for (int slot : getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);

            if (SlimefunUtils.isItemSimilar(item, gravel, true, false)) {
                if(item.getAmount() < amount) {
                    amount = item.getAmount();
                }
                ItemStack input = new ItemStack(gravel);
                ItemStack output = new ItemStack(goldPan.getRandomOutput());
                input.setAmount(amount);
                output.setAmount(amount);
                MachineRecipe recipe = new MachineRecipe(0, new ItemStack[] { gravel}, new ItemStack[] { output});
                if(output.getType() != Material.AIR && InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    inv.consumeItem(slot, amount);
                    return recipe;
                }
            } else if (SlimefunUtils.isItemSimilar(item, soulSand, true, false)) {
                if(item.getAmount() < soulSand.getAmount()) {
                    amount = item.getAmount();
                }
                ItemStack input = new ItemStack(soulSand);
                ItemStack output = new ItemStack(netherGoldPan.getRandomOutput());
                input.setAmount(amount);
                output.setAmount(amount);
                MachineRecipe recipe = new MachineRecipe(0, new ItemStack[] { input }, new ItemStack[] { output });

                if (output.getType() != Material.AIR && InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    inv.consumeItem(slot, amount);
                    return recipe;
                }

            }
        }

        return null;
    }
}
