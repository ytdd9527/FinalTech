package io.taraxacum.finaltech.machine.area.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.machine.area.AbstractAreaMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.VoidMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
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
public abstract class AbstractAreaElectricGenerator extends AbstractAreaMachine implements RecipeItem {
    protected static final String KEY = "energy-charge";
    private final List<MachineRecipe> RECIPE = new ArrayList<>();
    public AbstractAreaElectricGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
    }

    protected abstract int getElectricity();

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
    protected void function(@Nonnull Location location, @Nonnull Config config) {
        if(BlockStorage.hasBlockInfo(location)) {
            Config locationInfo = BlockStorage.getLocationInfo(location);
            if(locationInfo.contains("id")) {
                SlimefunItem item = SlimefunItem.getById(locationInfo.getString("id"));
                if(item instanceof EnergyNetComponent) {
                    int capacity = ((EnergyNetComponent) item).getCapacity();
                    if(capacity == 0) {
                        return;
                    }
                    int charge = locationInfo.contains(KEY) ? Integer.parseInt(locationInfo.getString(KEY)) : 0;
                    charge = Math.min(charge + getElectricity(), capacity);
                    charge = charge < 0 ? capacity : charge;
                    BlockStorage.addBlockInfo(location, KEY, String.valueOf(charge));
                }
            }
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
