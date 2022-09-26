package io.taraxacum.finaltech.core.items.machine.range.point.face;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.slimefun.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class OverloadChargeBase extends AbstractFaceMachine implements RecipeItem {
    private final double effective = ConfigUtil.getOrDefaultItemSetting(0.1, this, "effective");
    private final double maxLimit = ConfigUtil.getOrDefaultItemSetting(2, this, "max-limit");

    public OverloadChargeBase(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        this.function(block, 1, location -> {
            if(BlockStorage.hasBlockInfo(location)) {
                Config targetConfig = BlockStorage.getLocationInfo(location);
                if(targetConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                    String targetSlimefunId = targetConfig.getString(ConstantTableUtil.CONFIG_ID);
                    BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(targetSlimefunId), () -> OverloadChargeBase.this.doCharge(block, targetConfig), location);
                } else {
                    BlockMenu blockMenu = BlockStorage.getInventory(block);
                    if(blockMenu.hasViewer()) {
                        this.updateMenu(blockMenu, 0, 0);
                    }
                }
            }
            return 0;
        });
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void doCharge(@Nonnull Block block, @Nonnull Config config) {
        int storedEnergy = 0;
        int chargeEnergy = 0;

        String slimefunItemId = config.getString(ConstantTableUtil.CONFIG_ID);
        SlimefunItem slimefunItem = SlimefunItem.getById(slimefunItemId);
        if(slimefunItem instanceof EnergyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) slimefunItem).getEnergyComponentType()) && !EnergyNetComponentType.GENERATOR.equals(((EnergyNetComponent) slimefunItem).getEnergyComponentType())) {
            int capacity = ((EnergyNetComponent) slimefunItem).getCapacity();
            int maxValue = Integer.MAX_VALUE / OverloadChargeBase.this.maxLimit > capacity ? (int)(capacity * OverloadChargeBase.this.maxLimit) : Integer.MAX_VALUE;
            storedEnergy = Integer.parseInt(EnergyUtil.getCharge(config));
            chargeEnergy = maxValue - capacity * OverloadChargeBase.this.effective > storedEnergy ? (int)(capacity * OverloadChargeBase.this.effective) : (int)(maxValue - capacity * OverloadChargeBase.this.effective);
            if(chargeEnergy > 0) {
                EnergyUtil.setCharge(config, storedEnergy + chargeEnergy);
            }
        }

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if(blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, storedEnergy, chargeEnergy);
        }
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int storedEnergy, int chargeEnergy) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                String.valueOf(storedEnergy),
                String.valueOf(chargeEnergy)));
    }

    @Nonnull
    @Override
    protected BlockFace getBlockFace() {
        return BlockFace.UP;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.effective),
                String.valueOf(this.maxLimit));
    }
}
