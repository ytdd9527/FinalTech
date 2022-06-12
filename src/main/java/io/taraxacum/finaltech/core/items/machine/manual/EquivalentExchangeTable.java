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
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapperAmount;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.api.factory.ItemValueTable;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.EquivalentExchangeTableMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
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
                value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueTable.getInstance().getOrCalItemInputValue(sfItem), String.valueOf(item.getAmount())));
                item.setAmount(0);
                config.setValue(KEY, value);
            }
        }
        BlockStorage.addBlockInfo(block.getLocation(), KEY, value);
        if (blockMenu.hasViewer()) {
            this.getMachineMenu().updateMenu(blockMenu, block);
        }
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
        List<String> valueList = new ArrayList<>(ItemValueTable.getInstance().getValueItemListOutputMap().keySet());
        Collections.shuffle(valueList);
        for (String targetValue : valueList) {
            if (MachineUtil.itemCount(blockMenu, this.getOutputSlot()) == this.getOutputSlot().length) {
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
                if (MachineUtil.calMaxMatch(blockMenu, this.getOutputSlot(), List.of(new ItemStackWithWrapperAmount(item))) >= 1) {
                    blockMenu.pushItem(item, this.getOutputSlot());
                    value = StringNumberUtil.sub(value, targetValue);
                }
            }
        }
        config.setValue(KEY, value);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "输入物品后 该物品会被转化为价值并被该机器记录",
                TextUtil.COLOR_NORMAL + "输入 " + FinalTechItems.ORDERED_DUST.getDisplayName() + TextUtil.COLOR_NORMAL + " 时 消耗随机价值并随机输出与消耗价值等价的物品");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "价值计算",
                "",
                TextUtil.COLOR_NORMAL + "将物品放于上方槽中 会显示该物品的价值",
                TextUtil.COLOR_NORMAL + "物品的输入价值和输出价值计算方式不同",
                TextUtil.COLOR_NORMAL + "输入价值= 物品可以被转换为多少价值",
                TextUtil.COLOR_NORMAL + "输出价值= 多少价值可以转换为该物品");
    }
}
