package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.OrderedDustFactoryStoneMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.slimefun.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class DustFactoryStone extends AbstractMachine implements RecipeItem {
    public DustFactoryStone(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new OrderedDustFactoryStoneMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        if (BlockTickerUtil.hasSleep(config)) {
            double sleep = BlockTickerUtil.getSleep(config);
            if (--sleep <= 0) {
                BlockTickerUtil.setSleep(config, null);
            } else {
                BlockTickerUtil.setSleep(config, sleep);
            }
            return;
        }

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (MachineUtil.slotCount(blockMenu.toInventory(), this.getInputSlot()) != this.getInputSlot().length) {
            return;
        }

        Set<Integer> amountList = new HashSet<>(this.getInputSlot().length);
        ItemWrapper firstItem = new ItemWrapper(blockMenu.getItemInSlot(this.getInputSlot()[0]));
        boolean allSameItem = true;

        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            amountList.add(item.getAmount());
            if (allSameItem && !ItemStackUtil.isItemSimilar(firstItem, item)) {
                allSameItem = false;
            }
        }
        for (int slot : this.getInputSlot()) {
            blockMenu.replaceExistingItem(slot, ItemStackUtil.AIR);
        }
        if (amountList.size() == this.getInputSlot().length && allSameItem) {
            blockMenu.pushItem(FinalTechItems.ORDERED_DUST.clone(), JavaUtil.shuffle(this.getOutputSlot()));
            BlockTickerUtil.setSleep(config, 1.0);
        } else if (Math.random() < (double)(amountList.size()) / this.getInputSlot().length) {
            blockMenu.pushItem(FinalTechItems.UNORDERED_DUST.clone(), JavaUtil.shuffle(this.getOutputSlot()));
            BlockTickerUtil.setSleep(config, 1.0);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.format("%.2f", 100.0 / this.getInputSlot().length));
    }
}
