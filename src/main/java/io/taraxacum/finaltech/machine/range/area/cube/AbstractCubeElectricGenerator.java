package io.taraxacum.finaltech.machine.range.area.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.machine.range.area.AbstractCubeMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Final_ROOT
 */
public abstract class AbstractCubeElectricGenerator extends AbstractCubeMachine implements RecipeItem {
    protected static final String KEY = "energy-charge";
    private final List<MachineRecipe> RECIPE = new ArrayList<>();
    public AbstractCubeElectricGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
    }

    protected abstract String getElectricity();

    protected abstract int getRange();

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
        AtomicReference<String> electric = new AtomicReference<>(StringNumberUtil.ZERO);
        int count = this.function(block, getRange(), location -> {
            if(BlockStorage.hasBlockInfo(location)) {
                Config locationInfo = BlockStorage.getLocationInfo(location);
                if(locationInfo.contains(SlimefunUtil.KEY_ID)) {
                    SlimefunItem item = SlimefunItem.getById(locationInfo.getString(SlimefunUtil.KEY_ID));
                    if(item instanceof EnergyNetComponent) {
                        int capacity = ((EnergyNetComponent) item).getCapacity();
                        if(capacity == 0) {
                            return 0;
                        }
                        String charge = locationInfo.contains(KEY) ? locationInfo.getString(KEY) : StringNumberUtil.ZERO;
                        if(StringNumberUtil.easilyCompare(charge, String.valueOf(capacity)) >= 0) {
                            return 0;
                        }
                        String e = StringNumberUtil.min(StringNumberUtil.sub(String.valueOf(capacity), charge), getElectricity());
                        if(StringNumberUtil.easilyCompare(e, StringNumberUtil.ZERO) > 0) {
                            charge = StringNumberUtil.add(charge, e);
                            electric.set(StringNumberUtil.add(electric.get(), e));
                            BlockStorage.addBlockInfo(location, KEY, String.valueOf(charge));
                            return 1;
                        }
                    }
                }
            }
            return 0;
        });
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.CENTER_SLOT);
        ItemStackUtil.setLore(item,
                "§7当前生效的机器= " + count,
                "§7实际发电量= " + electric + "J");
        if (count == 0) {
            item.setType(Material.YELLOW_STAINED_GLASS_PANE);
        } else {
            item.setType(Material.GREEN_STAINED_GLASS_PANE);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public List<MachineRecipe> getMachineRecipes() {
        return RECIPE;
    }

    @Override
    public void registerDefaultRecipes() {
        registerDescriptiveRecipe("&f供电量",
                "",
                "&f供电量=" + this.getElectricity() + "J/t",
                "&f即对范围内的每个机器",
                "&f每粘液刻都会提供" + this.getElectricity() + "J 的电量");
        registerDescriptiveRecipe("&f供电范围",
                "",
                "&f传输半径=" + this.getRange() + "格",
                "&f即以自身为中心",
                "&f边长" + (this.getRange() * 2 + 1) + "格（含）的正方体区域");
    }
}
