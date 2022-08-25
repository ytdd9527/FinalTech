package io.taraxacum.finaltech.core.items.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.ItemAmountWrapper;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.api.factory.ItemValueTable;
import io.taraxacum.finaltech.core.items.machine.manual.AbstractManualMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.EquivalentExchangeTableMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EquivalentExchangeTable extends AbstractManualMachine implements RecipeItem {
    private final int retryTimes = FinalTech.getValueManager().getOrDefault(3, "items", SlimefunUtil.getIdFormatName(EquivalentExchangeTable.class), "retry-times");
    private static final String KEY = "value";

    public EquivalentExchangeTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return MachineUtil.simpleBlockBreakerHandler(this, EquivalentExchangeTableMenu.PARSE_ITEM_SLOT);
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new EquivalentExchangeTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        String value = config.contains(KEY) ? config.getString(KEY) : StringNumberUtil.ZERO;
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if(ItemStackUtil.isItemNull(item)) {
                continue;
            }
            if (ItemStackUtil.isItemSimilar(item, FinalTechItems.UNORDERED_DUST)) {
                if (MachineUtil.itemCount(blockMenu.toInventory(), this.getOutputSlot()) == this.getOutputSlot().length) {
                    continue;
                }
                this.doCraft(blockMenu, config);
                value = config.getString(KEY);
                item.setAmount(item.getAmount() - 1);
                continue;
            }
            SlimefunItem sfItem = SlimefunItem.getByItem(item);
            if (sfItem != null) {
                value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueTable.getInstance().getOrCalItemInputValue(sfItem), String.valueOf(item.getAmount())));
                item.setAmount(0);
                config.setValue(KEY, value);
            }
        }
        BlockStorage.addBlockInfo(block.getLocation(), KEY, value);
        if (blockMenu.hasViewer()) {
            this.getMachineMenu().updateInventory(blockMenu.toInventory(), block.getLocation());
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void doCraft(@Nonnull BlockMenu blockMenu, @Nonnull Config config) {
        String value = config.contains(KEY) ? config.getString(KEY) : StringNumberUtil.ZERO;
        List<String> valueList = new ArrayList<>(ItemValueTable.getInstance().getValueItemListOutputMap().keySet());
        Collections.shuffle(valueList);
        int i = 0;
        for (String targetValue : valueList) {
            if(i++ > this.retryTimes) {
                break;
            }
            if (StringNumberUtil.compare(value, targetValue) >= 0) {
                List<String> idList = ItemValueTable.getInstance().getValueItemListOutputMap().get(targetValue);
                String id = idList.get((int) (Math.random() * idList.size()));
                SlimefunItem slimefunItem = SlimefunItem.getById(id);
                if(slimefunItem == null || slimefunItem instanceof MultiBlockMachine) {
                    continue;
                }
                ItemStack item = new CustomItemStack(slimefunItem.getItem(), 1);
                if (MachineUtil.calMaxMatch(blockMenu.toInventory(), this.getOutputSlot(), List.of(new ItemAmountWrapper(item))) >= 1) {
                    blockMenu.pushItem(item, this.getOutputSlot());
                    value = StringNumberUtil.sub(value, targetValue);
                    break;
                }
            }
        }
        config.setValue(KEY, value);
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.retryTimes));
    }
}
