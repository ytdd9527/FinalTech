package io.taraxacum.finaltech.core.item.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.libs.slimefun.dto.ItemValueTable;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.EquivalentExchangeTableMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EquivalentExchangeTable extends AbstractManualMachine implements RecipeItem {
    private final String key = "value";

    public EquivalentExchangeTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
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
        String value = config.contains(this.key) ? config.getString(this.key) : StringNumberUtil.ZERO;
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(item)) {
                continue;
            }
            if (ItemStackUtil.isItemSimilar(item, FinalTechItemStacks.UNORDERED_DUST)) {
                if (MachineUtil.slotCount(blockMenu.toInventory(), this.getOutputSlot()) == this.getOutputSlot().length) {
                    continue;
                }
                value = this.doCraft(value, blockMenu, item.getAmount());
                item.setAmount(0);
                continue;
            }
            SlimefunItem sfItem = SlimefunItem.getByItem(item);
            if (sfItem != null) {
                value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueTable.getInstance().getOrCalItemInputValue(sfItem), String.valueOf(item.getAmount())));
                item.setAmount(0);
            }
        }

        BlockStorage.addBlockInfo(block.getLocation(), this.key, value);

        if (blockMenu.hasViewer()) {
            this.getMachineMenu().updateInventory(blockMenu.toInventory(), block.getLocation());
        }
    }

    private String doCraft(@Nonnull String value, @Nonnull BlockMenu blockMenu, int amount) {
        List<SlimefunItem> slimefunItemList = Slimefun.getRegistry().getAllSlimefunItems();
        int searchedTime = 0;
        SlimefunItem searchedSlimefunItem = null;
        String searchedValue = null;

        for (int i = 0, retryTimes = value.length(); i < retryTimes; i++) {
            SlimefunItem slimefunItem = slimefunItemList.get(FinalTech.getRandom().nextInt(slimefunItemList.size()));
            String targetValue = ItemValueTable.getInstance().getOrCalItemOutputValue(slimefunItem);

            if (StringNumberUtil.compare(value, targetValue) >= 0) {
                List<String> idList = ItemValueTable.getInstance().getValueItemListOutputMap().get(targetValue);
                if(idList == null || idList.isEmpty()) {
                    continue;
                }
                String id = idList.get((int) (Math.random() * idList.size()));
                slimefunItem = SlimefunItem.getById(id);
                if (slimefunItem == null || slimefunItem instanceof MultiBlockMachine) {
                    continue;
                }

                if(searchedValue == null || StringNumberUtil.ZERO.equals(targetValue) || StringNumberUtil.compare(targetValue, searchedValue) > 0) {
                    searchedSlimefunItem = slimefunItem;
                    searchedValue = targetValue;
                }
                if(++searchedTime >= amount) {
                    break;
                }
            }
        }

        if(searchedSlimefunItem != null) {
            ItemStack item = ItemStackUtil.cloneItem(searchedSlimefunItem.getItem(), 1);
            if (MachineUtil.calMaxMatch(blockMenu.toInventory(), this.getOutputSlot(), List.of(new ItemAmountWrapper(item))) >= 1) {
                blockMenu.pushItem(item, this.getOutputSlot());
                if(StringNumberUtil.ZERO.equals(searchedValue)) {
                    value = StringNumberUtil.ZERO;
                } else {
                    value = StringNumberUtil.sub(value, searchedValue);
                }
            }
        }

        return value;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
