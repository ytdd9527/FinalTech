package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.ConfigUtil;
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
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class DustFactoryStone extends AbstractMachine implements RecipeItem {
    private final double sleep = ConfigUtil.getOrDefaultItemSetting(4, this, "sleep");
    public DustFactoryStone(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                Block block = e.getBlock();
                Location location = block.getLocation();
                BlockTickerUtil.setSleep(location, String.valueOf(DustFactoryStone.this.sleep * DustFactoryStone.this.sleep));
            }
        };
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
            BlockTickerUtil.subSleep(config);
            return;
        }

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (MachineUtil.slotCount(blockMenu.toInventory(), this.getInputSlot()) != this.getInputSlot().length) {
            return;
        }

        Set<Integer> amountList = new HashSet<>(this.getInputSlot().length);
        ItemWrapper firstItem = new ItemWrapper(blockMenu.getItemInSlot(this.getInputSlot()[0]));
        boolean allSameItem = true;
        int maxAmount = firstItem.getItemStack().getAmount();
        int minAmount = firstItem.getItemStack().getAmount();

        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            amountList.add(item.getAmount());
            if (allSameItem && !ItemStackUtil.isItemSimilar(firstItem, item)) {
                allSameItem = false;
            }
            if(maxAmount < item.getAmount()) {
                maxAmount = item.getAmount();
            } else if(minAmount > item.getAmount()) {
                minAmount = item.getAmount();
            }
        }

        int sleep = 64 - maxAmount + minAmount;

        for (int slot : this.getInputSlot()) {
            blockMenu.replaceExistingItem(slot, ItemStackUtil.AIR);
        }
        if (amountList.size() == this.getInputSlot().length && allSameItem) {
            blockMenu.pushItem(FinalTechItems.ORDERED_DUST.clone(), JavaUtil.shuffle(this.getOutputSlot()));
            BlockTickerUtil.setSleep(config, FinalTech.getRandom().nextDouble(this.sleep + sleep));
        } else if (Math.random() < (double)(amountList.size()) / this.getInputSlot().length) {
            blockMenu.pushItem(FinalTechItems.UNORDERED_DUST.clone(), JavaUtil.shuffle(this.getOutputSlot()));
            BlockTickerUtil.setSleep(config, FinalTech.getRandom().nextDouble(this.sleep + sleep));
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
