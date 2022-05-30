package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapperAmount;
import io.taraxacum.finaltech.core.factory.ItemValueMap;
import io.taraxacum.finaltech.core.items.machine.manual.AbstractManualMachine;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.EquivalentExchangeTableMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquivalentExchangeTable extends AbstractManualMachine {
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

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu.hasViewer()) {
            this.getMachineMenu().updateMenu(blockMenu, block);
        }
        String value = config.contains(KEY) ? config.getString(KEY) : StringNumberUtil.ZERO;
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemSimilar(item, FinalTechItems.ORDERED_DUST)) {
                this.doCraft(blockMenu, config);
                value = config.getString(KEY);
                item.setAmount(item.getAmount() - 1);
                continue;
            }
            SlimefunItem sfItem = SlimefunItem.getByItem(item);
            if (sfItem != null) {
                value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueMap.getOrCalItemInputValue(sfItem), String.valueOf(item.getAmount())));
                item.setAmount(0);
            }
        }
        BlockStorage.addBlockInfo(block.getLocation(), KEY, value);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new EquivalentExchangeTableMenu(this);
    }

    //todo
    // support plugin reload
    private void doCraft(@Nonnull BlockMenu blockMenu, @Nonnull Config config) {
        String value = config.contains(KEY) ? config.getString(KEY) : StringNumberUtil.ZERO;
        List<String> valueList = new ArrayList<>(ItemValueMap.VALUE_ITEM_LIST_OUTPUT_MAP.keySet());
        Collections.shuffle(valueList);
        for (String targetValue : valueList) {
            if (MachineUtil.itemCount(blockMenu, this.getOutputSlot()) == this.getOutputSlot().length) {
                break;
            }
            if (StringNumberUtil.compare(value, targetValue) >= 0) {
                List<String> idList = ItemValueMap.VALUE_ITEM_LIST_OUTPUT_MAP.get(targetValue);
                String id = idList.get((int) (Math.random() * idList.size()));
                ItemStack item = new CustomItemStack(SlimefunItem.getById(id).getItem(), 1);
                if (MachineUtil.calMaxMatch(blockMenu, this.getOutputSlot(), List.of(new ItemStackWithWrapperAmount(item))) >= 1) {
                    blockMenu.pushItem(item, this.getOutputSlot());
                    value = StringNumberUtil.sub(value, targetValue);
                }
            }
        }
        BlockStorage.addBlockInfo(blockMenu.getLocation(), KEY, value);
    }
}
