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
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

public class EnergizedChargeBase extends AbstractFaceMachine{
    private final double effective = FinalTech.getValueManager().getOrDefault(0.25, "items", SlimefunUtil.getIdFormatName(EnergizedChargeBase.class), "effective");

    public EnergizedChargeBase(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        AtomicInteger storedEnergy = new AtomicInteger(0);
        AtomicInteger chargeEnergy = new AtomicInteger(0);
        int count = this.function(block, 1, location -> {
            Config targetConfig = BlockStorage.getLocationInfo(location);
            if(targetConfig.contains(SlimefunUtil.KEY_ID)) {
                String targetSlimefunId = targetConfig.getString(SlimefunUtil.KEY_ID);
                SlimefunItem targetSlimefunItem = SlimefunItem.getById(targetSlimefunId);
                if(targetSlimefunItem instanceof EnergyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) targetSlimefunItem).getEnergyComponentType())) {
                    int capacity = ((EnergyNetComponent) targetSlimefunItem).getCapacity();
                    storedEnergy.set(Integer.parseInt(SlimefunUtil.getCharge(targetConfig)));
                    chargeEnergy.set(capacity * EnergizedChargeBase.this.effective > capacity - storedEnergy.get() ? capacity - storedEnergy.get() : (int)(capacity * EnergizedChargeBase.this.effective));
                    if(chargeEnergy.get() > 0) {
                        SlimefunUtil.setCharge(config, storedEnergy.get() + chargeEnergy.get());
                        return 1;
                    }
                }
            }
            return 0;
        });

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if(blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, storedEnergy.get(), chargeEnergy.get());
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int storedEnergy, int chargeEnergy) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item, SlimefunUtil.updateMenuLore(FinalTech.getLanguageManager(), this,
                String.valueOf(storedEnergy),
                String.valueOf(chargeEnergy)));
    }

    @Nonnull
    @Override
    protected BlockFace getBlockFace() {
        return null;
    }
}
