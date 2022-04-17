package io.taraxacum.finaltech.machine.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.OrderDustGeneratorMenu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class OrderedDustGenerator extends AbstractElectricGenerator implements RecipeItem {
    public static final String KEY_COUNT = "count";
    public static final String KEY_MAX = "max";
    public static final int LIMIT = Integer.MAX_VALUE / 4;
    private OrderDustGeneratorMenu menu;

    public OrderedDustGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
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
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                Block block = blockBreakEvent.getBlock();
                BlockMenu blockMenu = BlockStorage.getInventory(block);
                blockMenu.dropItems(block.getLocation(), OrderedDustGenerator.this.getInputSlots());
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        this.menu = new OrderDustGeneratorMenu(this.getId(), this.getItemName(), this);
        return this.menu;
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
        for(int slot : this.getInputSlots()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if(ItemStackUtil.isItemSimilar(item, FinalTechItems.UNORDERED_DUST)) {
                item.setAmount(item.getAmount() - 1);
                count = Math.min(count + 1, LIMIT);
                if(count > max) {
                    max = count;
                }
                work = true;
                break;
            } else if(ItemStackUtil.isItemSimilar(item, FinalTechItems.ORDERED_DUST)) {
                item.setAmount(item.getAmount() - 1);
                if(count < max) {
                    count = (count + max) / 2;
                    max = count;
                } else {
                    count = Math.min(count + 1, LIMIT);
                    if(count > max) {
                        max = count;
                    }
                }
                work = true;
                break;
            }
        }
        if(!work) {
            count = 0;
        }

        if(max != oldMax || count != oldCount) {
            if(max != oldMax) {
                BlockStorage.addBlockInfo(location, KEY_MAX, String.valueOf(max));
            }
            if(count != oldCount) {
                BlockStorage.addBlockInfo(location, KEY_COUNT, String.valueOf(count));
            }
            menu.updateMenu(blockMenu, block);
        }
        if(count > 0) {
            this.addCharge(location, count);
        }
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
}
