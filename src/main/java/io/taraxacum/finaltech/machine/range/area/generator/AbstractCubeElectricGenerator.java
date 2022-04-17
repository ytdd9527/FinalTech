package io.taraxacum.finaltech.machine.range.area.generator;

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
import io.taraxacum.finaltech.menu.StatusL2Menu;
import io.taraxacum.finaltech.menu.StatusMenu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Final_ROOT
 */
public abstract class AbstractCubeElectricGenerator extends AbstractCubeMachine implements RecipeItem {
    protected static final String KEY = "energy-charge";
    public AbstractCubeElectricGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler();
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_ALLOW;
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        String extraEnergy = this.getElectricity();
        for(int slot : this.getInputSlots()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if(!ItemStackUtil.isItemNull(item) && ItemStackUtil.isItemSimilar(item, this.getItem())) {
                //todo
                for(int i = 0; i < item.getAmount(); i++) {
                    extraEnergy = StringNumberUtil.add(extraEnergy, this.getElectricity());
                }
            }
        }
        AtomicReference<String> energyCharge = new AtomicReference<>(StringNumberUtil.ZERO);
        String finalExtraEnergy = extraEnergy;
        int count = this.function(block, this.getRange(), location -> {
            if(BlockStorage.hasBlockInfo(location)) {
                Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                if(energyComponentConfig.contains(SlimefunUtil.KEY_ID)) {
                    SlimefunItem item = SlimefunItem.getById(energyComponentConfig.getString(SlimefunUtil.KEY_ID));
                    if(item instanceof EnergyNetComponent) {
                        int componentCapacity = ((EnergyNetComponent) item).getCapacity();
                        if(componentCapacity == 0) {
                            return 0;
                        }
                        String componentEnergy = energyComponentConfig.contains(KEY) ? energyComponentConfig.getString(KEY) : StringNumberUtil.ZERO;
                        if(StringNumberUtil.easilyCompare(componentEnergy, String.valueOf(componentCapacity)) >= 0) {
                            return 0;
                        }
                        String transferEnergy = StringNumberUtil.min(StringNumberUtil.sub(String.valueOf(componentCapacity), componentEnergy), finalExtraEnergy);
                        if(StringNumberUtil.easilyCompare(transferEnergy, StringNumberUtil.ZERO) > 0) {
                            componentEnergy = StringNumberUtil.add(componentEnergy, transferEnergy);
                            energyCharge.set(StringNumberUtil.add(energyCharge.get(), transferEnergy));
                            SlimefunUtil.setCharge(energyComponentConfig, componentEnergy);
                            return 1;
                        }
                    }
                }
            }
            return 0;
        });
        blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.CENTER_SLOT);
        ItemStackUtil.setLore(item,
                "§7当前生效的机器= " + count,
                "§7实际发电量= " + energyCharge + "J");
        if (count == 0) {
            item.setType(Material.RED_STAINED_GLASS_PANE);
        } else {
            item.setType(Material.GREEN_STAINED_GLASS_PANE);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        //todo 优化说明
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

    protected abstract String getElectricity();

    protected abstract int getRange();
}
