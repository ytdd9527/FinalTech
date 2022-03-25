package io.taraxacum.finaltech.machine.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.VoidMenu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class OverloadCoreMachine extends AbstractAreaMachine implements RecipeItem {
    private static final int RANGE = 16;
    private final List<MachineRecipe> machineRecipeList = new ArrayList<>();
    public OverloadCoreMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
    }

    @Override
    protected int getRange() {
        return RANGE;
    }

    @Override
    protected void function(@Nonnull Location location, @Nonnull Config config) {
        if(BlockStorage.hasBlockInfo(location)) {
            Config locationInfo = BlockStorage.getLocationInfo(location);
            if(locationInfo.contains("id")) {
                String id = locationInfo.getString("id");
                if(id.contains("OVER")) {
                    return;
                }
                SlimefunItem item = SlimefunItem.getById(id);
                if(item != null) {
                    BlockTicker blockTicker = item.getBlockTicker();
                    Location l = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
                    if(blockTicker == null) {
                    } else if (blockTicker.isSynchronized()) {
                        Slimefun.runSync(() -> blockTicker.tick(l.getBlock(), item, locationInfo));
                    } else {
                        blockTicker.tick(l.getBlock(), item, locationInfo);
                    }
                }
            }
        }
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
        return new VoidMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public List<MachineRecipe> getMachineRecipes() {
        return this.machineRecipeList;
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
