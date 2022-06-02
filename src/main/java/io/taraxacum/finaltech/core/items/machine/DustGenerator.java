package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.OrderDustGeneratorMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class DustGenerator extends AbstractMachine implements RecipeItem, EnergyNetProvider {
    public static final String KEY_COUNT = "count";
    public static final String KEY_MAX = "max";
    public static final int LIMIT = Integer.MAX_VALUE / 4;

    public DustGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), KEY_COUNT, "0");
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), KEY_MAX, "0");
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
        return new OrderDustGeneratorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Location location = block.getLocation();

        int count = Integer.parseInt(config.getValue(KEY_COUNT).toString());
        int max = Integer.parseInt(config.getValue(KEY_MAX).toString());
        int oldCount = count;
        int oldMax = max;
        boolean work = false;
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemSimilar(item, FinalTechItems.UNORDERED_DUST)) {
                item.setAmount(item.getAmount() - 1);
                count = Math.min(count + 1, LIMIT);
                if (count > max) {
                    max = count;
                }
                work = true;
                break;
            } else if (ItemStackUtil.isItemSimilar(item, FinalTechItems.ORDERED_DUST)) {
                item.setAmount(item.getAmount() - 1);
                if (count < max) {
                    count = (count + max) / 2;
                    max = count;
                } else {
                    count = Math.min(count + 1, LIMIT);
                    if (count > max) {
                        max = count;
                    }
                }
                work = true;
                break;
            } else if (ItemStackUtil.isItemSimilar(item, FinalTechItems.PHONY)) {
                item.setAmount(item.getAmount() - 1);
                count = this.getCapacity();
                max = this.getCapacity();
                work = true;
                break;
            }
        }
        if (!work) {
            count = 0;
        }

        if (max != oldMax || count != oldCount) {
            if (max != oldMax) {
                BlockStorage.addBlockInfo(location, KEY_MAX, String.valueOf(max));
            }
            if (count != oldCount) {
                BlockStorage.addBlockInfo(location, KEY_COUNT, String.valueOf(count));
            }
            this.updateMenu(blockMenu, block);
        }
        if (count > 0) {
            this.addCharge(location, count);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location location, @Nonnull Config config) {
        int charge = this.getCharge(location);
        this.setCharge(location, 0);
        return charge;
    }

    @Override
    public int getCapacity() {
        return LIMIT;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        ItemStack item = blockMenu.getItemInSlot(OrderDustGeneratorMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item,
                TextUtil.COLOR_NORMAL + "当前发电量= " + TextUtil.COLOR_NUMBER + BlockStorage.getLocationInfo(block.getLocation(), DustGenerator.KEY_COUNT) + "J/t",
                TextUtil.COLOR_NORMAL + "已达到的最大发电量= " + TextUtil.COLOR_NUMBER + BlockStorage.getLocationInfo(block.getLocation(), DustGenerator.KEY_MAX) + "J/t");
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "每 " + TextUtil.COLOR_NUMBER + String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0) + "秒" + TextUtil.COLOR_NORMAL + " 消耗一个 " + FinalTechItems.ORDERED_DUST.getDisplayName() + TextUtil.COLOR_NORMAL + " 或 " + FinalTechItems.UNORDERED_DUST.getDisplayName() + TextUtil.COLOR_NORMAL + " 并使发电量加 " + TextUtil.COLOR_NUMBER + "1J/t",
                TextUtil.COLOR_NORMAL + "未消耗成功时 发电量置零",
                "",
                TextUtil.COLOR_NORMAL + "最大发电量 " + TextUtil.COLOR_NUMBER + this.getCapacity() + "J/t");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "电力回溯",
                "",
                TextUtil.COLOR_NORMAL + "消耗 " + FinalTechItems.ORDERED_DUST.getDisplayName() + TextUtil.COLOR_NORMAL + " 时",
                TextUtil.COLOR_NORMAL + "若当前发电量小于已达到的最大发电量",
                TextUtil.COLOR_NORMAL + "则将当前发电量提升至当前发电量与已达到的最大发电量的平均值",
                TextUtil.COLOR_NORMAL + "然后重置已达到的最大发电量");
    }
}
