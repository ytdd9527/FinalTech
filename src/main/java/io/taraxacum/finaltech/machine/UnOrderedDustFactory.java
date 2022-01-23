package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.abstractItem.FinalBasicMachine;
import io.taraxacum.finaltech.abstractItem.FinalMachine;
import io.taraxacum.finaltech.abstractItem.MachineMenu;
import io.taraxacum.finaltech.core.UnOrderedDustCraftingOperation;
import io.taraxacum.finaltech.menu.UnOrderedDustFactoryMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.FinalUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class UnOrderedDustFactory extends FinalMachine {

    public static final int MATCH_DIFFICULTY = 16;
    public static final int INPUT_DIFFICULTY = 1024;

    public UnOrderedDustFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected MachineMenu setMachineMenu() {
        return new UnOrderedDustFactoryMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        UnOrderedDustCraftingOperation currentOperation = (UnOrderedDustCraftingOperation)this.getMachineProcessor().getOperation(b);

        for(int slot : this.getInputSlots()) {
            ItemStack inputItem = inv.getItemInSlot(slot);
            if(inputItem == null) {
                continue;
            }

            if(currentOperation == null) {
                this.getMachineProcessor().startOperation(b, new UnOrderedDustCraftingOperation(new MachineRecipe(0, new ItemStack[MATCH_DIFFICULTY], new ItemStack[] {FinalTechItems.UNORDERED_DUST})));
                currentOperation = (UnOrderedDustCraftingOperation)this.getMachineProcessor().getOperation(b);
            }
            currentOperation.addItem(inputItem);

            if(currentOperation.isFinished()) {
                inv.pushItem(new ItemStack(FinalTechItems.UNORDERED_DUST), this.getOutputSlots());
                this.getMachineProcessor().endOperation(b);
                currentOperation = null;
            }
            inv.consumeItem(slot, inputItem.getAmount());
        }

        if(currentOperation == null) {
            this.getMachineProcessor().startOperation(b, new UnOrderedDustCraftingOperation(new MachineRecipe(0, new ItemStack[MATCH_DIFFICULTY], new ItemStack[] {FinalTechItems.UNORDERED_DUST})));
            currentOperation = (UnOrderedDustCraftingOperation)this.getMachineProcessor().getOperation(b);
        }
        CustomItemStack progress = new CustomItemStack(Material.REDSTONE, "&f完成进度",
                "&7匹配的物品种类" + currentOperation.getMatchCount() + "/" + MATCH_DIFFICULTY,
                "&7输入的物品总数" + currentOperation.getInputCount() + "/" + INPUT_DIFFICULTY);
        inv.replaceExistingItem(22, progress);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "FINALTECH_ORDERED_DUST_FACTORY";
    }

    public static int getMatchDifficulty() {
        return MATCH_DIFFICULTY;
    }

    public static int getInputDifficulty() {
        return INPUT_DIFFICULTY;
    }
}
