package io.taraxacum.finaltech.machine.range.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.StatusMenu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 */
@Deprecated
public class OverloadCoreMachine extends AbstractCubeMachine implements RecipeItem {
    private static final int RANGE = 16;
    public OverloadCoreMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
            }
        };
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_ALLOW;
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        int count = this.function(block, RANGE, location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config locationInfo = BlockStorage.getLocationInfo(location);
                if (locationInfo.contains(SlimefunUtil.KEY_ID)) {
                    String id = locationInfo.getString(SlimefunUtil.KEY_ID);
                    if (id.contains("OVER")) {
                        return 0;
                    }
                    SlimefunItem item = SlimefunItem.getById(id);
                    if (item != null) {
                        BlockTicker blockTicker = item.getBlockTicker();
                        if (blockTicker == null) {
                        } else if (blockTicker.isSynchronized()) {
                            Location l = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
                            Slimefun.runSync(() -> blockTicker.tick(l.getBlock(), item, locationInfo));
                            return 1;
                        } else {
                            Location l = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
                            blockTicker.tick(l.getBlock(), item, locationInfo);
                            return 1;
                        }
                    }
                }
            }
            return 0;
        });
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.CENTER_SLOT);
        ItemStackUtil.setLore(item, "§7当前生效的机器= " + count);
        if (count == 0) {
            item.setType(Material.YELLOW_STAINED_GLASS_PANE);
        } else {
            item.setType(Material.GREEN_STAINED_GLASS_PANE);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f效果",
                "",
                "&f范围内",
                "&f所有粘液科技机器的工作效率翻倍");
        this.registerDescriptiveRecipe("&f有效范围",
                "",
                "&f以自身为中心",
                "&f边长为" + (RANGE * 2 + 1) + "格（含）的正方体区域");
        this.registerDescriptiveRecipe("&f可生效的机器",
                "",
                "&f兼容绝大部分机器",
                "&f包括来自其他粘液科技附属的机器",
                "&f不适用于[" + FinalTechItems.OVERCLOCK_FRAME_MACHINE.getDisplayName() + "&f]、[" + FinalTechItems.OVERLOAD_CORE_MACHINE.getDisplayName() + "&f]");
    }
}
