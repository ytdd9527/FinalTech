package io.taraxacum.finaltech.core.item.machine.range.point.face;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.AbstractEnergyProvider;
import io.github.thebusybiscuit.slimefun4.implementation.operations.FuelOperation;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.VoidMenu;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class FuelOperator extends AbstractFaceMachine implements RecipeItem {
    private final List<String> notAllowedId = ConfigUtil.getItemStringList(this, "not-allowed-id");

    public FuelOperator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new VoidMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        this.function(block, 1, location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config targetConfig = BlockStorage.getLocationInfo(location);
                if (targetConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                    String targetSlimefunId = targetConfig.getString(ConstantTableUtil.CONFIG_ID);
                    if(!this.notAllowedId.contains(targetSlimefunId)) {
                        SlimefunItem targetSlimefunItem = SlimefunItem.getById(targetSlimefunId);
                        if (targetSlimefunItem instanceof EnergyNetProvider && targetSlimefunItem instanceof MachineProcessHolder machineProcessHolder) {
                            BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(targetSlimefunId), () -> FuelOperator.this.doCharge(machineProcessHolder, location), location);
                        }
                    }
                }
            }
            return 0;
        });
    }

    private void doCharge(@Nonnull MachineProcessHolder<FuelOperation> MachineProcessHolder, @Nonnull Location location) {
        MachineOperation machineOperation = MachineProcessHolder.getMachineProcessor().getOperation(location);
        if(machineOperation == null) {
            machineOperation = new FuelOperation(new MachineFuel(2, new ItemStack(Material.COBBLESTONE)));
            MachineProcessHolder.getMachineProcessor().startOperation(location, (FuelOperation) machineOperation);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    protected BlockFace getBlockFace() {
        return BlockFace.UP;
    }

    @Override
    public void registerDefaultRecipes() {
        for (SlimefunItem slimefunItem : Slimefun.getRegistry().getAllSlimefunItems()) {
            if (!this.notAllowedId.contains(slimefunItem.getId()) && slimefunItem instanceof AbstractEnergyProvider && slimefunItem instanceof MachineProcessHolder) {
                this.registerDescriptiveRecipe(slimefunItem.getItem());
            }
        }
    }
}
