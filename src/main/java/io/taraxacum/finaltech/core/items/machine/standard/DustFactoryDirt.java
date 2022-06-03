package io.taraxacum.finaltech.core.items.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.api.operation.DustFactoryOperation;
import io.taraxacum.finaltech.core.menu.standard.DustFactoryMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class DustFactoryDirt extends AbstractStandardMachine {
    public static final int TYPE_DIFFICULTY = 16;
    public static final int AMOUNT_DIFFICULTY = 1024;

    public DustFactoryDirt(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new DustFactoryMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        DustFactoryOperation operation = (DustFactoryOperation)this.getMachineProcessor().getOperation(block);

        for (int slot : this.getInputSlot()) {
            ItemStack inputItem = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if (operation == null) {
                operation = new DustFactoryOperation();
                this.getMachineProcessor().startOperation(block, operation);
            }
            operation.addItem(inputItem);

            if (operation.isFinished()) {
                if (operation.isOrderedDust() && InvUtils.fitAll(blockMenu.toInventory(), new ItemStack[] {FinalTechItems.ORDERED_DUST}, this.getOutputSlot())) {
                    blockMenu.pushItem(new ItemStack(FinalTechItems.ORDERED_DUST), this.getOutputSlot());
                    this.getMachineProcessor().endOperation(block);
                    operation = null;
                } else if (InvUtils.fitAll(blockMenu.toInventory(), new ItemStack[] {FinalTechItems.UNORDERED_DUST}, this.getOutputSlot())) {
                    blockMenu.pushItem(new ItemStack(FinalTechItems.UNORDERED_DUST), this.getOutputSlot());
                    this.getMachineProcessor().endOperation(block);
                    operation = null;
                }
            }
            blockMenu.consumeItem(slot, inputItem.getAmount());
        }

        if (operation == null) {
            operation = new DustFactoryOperation();
            this.getMachineProcessor().startOperation(block, operation);
        }
        if(blockMenu.hasViewer()) {
            CustomItemStack progress = new CustomItemStack(Material.REDSTONE, TextUtil.COLOR_NORMAL + "完成进度",
                    TextUtil.COLOR_NORMAL + "匹配的物品种类= " + TextUtil.COLOR_NUMBER + operation.getTypeCount() + "/" + TYPE_DIFFICULTY,
                    TextUtil.COLOR_NORMAL + "输入的物品总数= " + TextUtil.COLOR_NUMBER + operation.getAmountCount() + "/" + AMOUNT_DIFFICULTY);
            blockMenu.replaceExistingItem(DustFactoryMenu.STATUS_SLOT, progress);
        }
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.UNORDERED_DUST.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "输入至少 " + TextUtil.COLOR_NUMBER + AMOUNT_DIFFICULTY + "个" + TextUtil.COLOR_NORMAL + " 任意物品",
                TextUtil.COLOR_NORMAL + "输入至少 " + TextUtil.COLOR_NUMBER + TYPE_DIFFICULTY + "种" + TextUtil.COLOR_NORMAL + " 不同物品",
                TextUtil.COLOR_NORMAL + "同时满足以上两个条件时生成一个 " + FinalTechItems.UNORDERED_DUST.getDisplayName());
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.ORDERED_DUST.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "输入恰好 " + TextUtil.COLOR_NUMBER + AMOUNT_DIFFICULTY + "个" + TextUtil.COLOR_NORMAL + " 任意物品",
                TextUtil.COLOR_NORMAL + "输入恰好 " + TextUtil.COLOR_NUMBER + TYPE_DIFFICULTY + "种" + TextUtil.COLOR_NORMAL + " 不同物品",
                TextUtil.COLOR_NORMAL + "同时满足以上两个条件时生成一个 " + FinalTechItems.ORDERED_DUST.getDisplayName());
    }
}
