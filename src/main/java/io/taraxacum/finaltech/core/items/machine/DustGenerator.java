package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.OrderDustGeneratorMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
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

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f发电",
                "",
                "&f输入无序尘埃",
                "&f使其发电量+1J/t",
                "",
                "&f输入有序尘埃",
                "&f使其发电量+1J/t",
                "&f或者使其发电量增加与最大发电量差值的一半",
                "&f然后重置最大发电量");
        this.registerDescriptiveRecipe("&f供电规则",
                "",
                "&f如果某次未输入任何有效物品",
                "&f重置其发电量为 0");
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        ItemStack item = blockMenu.getItemInSlot(OrderDustGeneratorMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item,
                "§7当前发电量= §e" + BlockStorage.getLocationInfo(block.getLocation(), DustGenerator.KEY_COUNT) + "J/t",
                "§7最大发电量= §e" + BlockStorage.getLocationInfo(block.getLocation(), DustGenerator.KEY_MAX) + "J/t");
    }
}
