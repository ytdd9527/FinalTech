package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.FinalBasicMachine;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class OrderedDustFactory extends FinalBasicMachine {

    private static final int DIFFICULTY = 16;
    private final ItemStack[] itemStacks = new ItemStack[DIFFICULTY + getInputSlots().length];
    private int matchCount = 0;

    public OrderedDustFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        CraftingOperation currentOperation = this.getMachineProcessor().getOperation(b);

        if(currentOperation == null) {
            for(int slot : this.getInputSlots()) {
                ItemStack inputItem = inv.getItemInSlot(slot);
                boolean match = false;
                for(int i = 0; i < matchCount; i++) {
                    ItemStack item = itemStacks[i];
                    if(item != null && SlimefunUtils.isItemSimilar(inputItem, item, true, false)) {
                        match = true;
                        break;
                    }
                }
                if(match == false) {
                    itemStacks[matchCount++] = new ItemStack(inputItem);
                }
            }
        }
        if(matchCount >= DIFFICULTY) {
            for(ItemStack item : itemStacks) {
                item = null;
            }
            inv.pushItem(new ItemStack(Material.BEDROCK), this.getOutputSlots());
            matchCount = 0;
        }
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "FINALTECH_ORDERED_DUST_FACTORY";
    }

    @Override
    protected void tickBefore(Block block) {

    }

    @Override
    protected void tickAfter(Block block) {

    }
}
