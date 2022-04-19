package io.taraxacum.finaltech.item.usable.accelerate;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.item.usable.UsableSlimefunItem;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public abstract class MachineActivateCard extends UsableSlimefunItem {
    public MachineActivateCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();
        PlayerInteractEvent interactEvent = playerRightClickEvent.getInteractEvent();
        Block block = interactEvent.getClickedBlock();
        if(block == null) {
            return;
        }
        Location location = interactEvent.getClickedBlock().getLocation();
        Config config = BlockStorage.getLocationInfo(location);
        if(config.contains(SlimefunUtil.KEY_ID)) {
            if(this.consume()) {
                ItemStack item = playerRightClickEvent.getItem();
                item.setAmount(item.getAmount() - 1);
            }
            SlimefunItem slimefunItem = SlimefunItem.getById(config.getString(SlimefunUtil.KEY_ID));
            if(slimefunItem != null) {

                boolean chargeable = slimefunItem instanceof EnergyNetComponent && this.energy() > 0;
                String capacity = StringNumberUtil.ZERO;
                String chargeEnergy = StringNumberUtil.ZERO;
                if(chargeable) {
                    capacity = String.valueOf(((EnergyNetComponent) slimefunItem).getCapacity());
                    if(StringNumberUtil.ZERO.equals(capacity)) {
                        chargeable = false;
                    }
                    if(chargeable) {
                        int energyValue = (int) this.energy();
                        double energyPercentage = this.energy() - energyValue;
                        if(energyValue > 0) {
                            chargeEnergy = String.valueOf(energyValue);
                        }
                        if(energyPercentage > 0 && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) slimefunItem).getEnergyComponentType())) {
                            chargeEnergy = StringNumberUtil.add(chargeEnergy, String.valueOf((int) (((EnergyNetComponent) slimefunItem).getCapacity() * energyPercentage)));
                        }
                    }
                }

                BlockTicker blockTicker = slimefunItem.getBlockTicker();

                if(blockTicker != null && chargeable) {
                    for(int i = 0, limit = this.consume() ? this.times() : this.times() * playerRightClickEvent.getItem().getAmount(); i < limit; i++) {
                        String energy = SlimefunUtil.getCharge(config);
                        energy = StringNumberUtil.add(energy, chargeEnergy);
                        energy = StringNumberUtil.min(energy, capacity);
                        SlimefunUtil.setCharge(config, energy);
                        SlimefunUtil.runBlockTicker(blockTicker, block, slimefunItem, config);
                    }
                } else if(blockTicker != null) {
                    for(int i = 0, limit = this.consume() ? this.times() : this.times() * playerRightClickEvent.getItem().getAmount(); i < limit; i++) {
                        SlimefunUtil.runBlockTicker(blockTicker, block, slimefunItem, config);
                    }
                } else if(chargeable) {
                    String chargeEnergyResult = StringNumberUtil.add(chargeEnergy, chargeEnergy);
                    for(int i = 1, limit = this.consume() ? this.times() : this.times() * playerRightClickEvent.getItem().getAmount(); i < limit; i++) {
                        chargeEnergyResult = StringNumberUtil.add(chargeEnergyResult, chargeEnergy);
                    }
                    if(this.times() == 0) {
                        //todo
                        // 性能优化
                        if(!this.consume()) {
                            for(int i = 1; i < playerRightClickEvent.getItem().getAmount(); i++) {
                                chargeEnergyResult = StringNumberUtil.add(chargeEnergyResult, chargeEnergy);
                            }
                        }
                    }
                    chargeEnergy = chargeEnergyResult;

                    String energy = SlimefunUtil.getCharge(config);
                    energy = StringNumberUtil.add(energy, chargeEnergy);
                    energy = StringNumberUtil.min(energy, capacity);
                    SlimefunUtil.setCharge(config, energy);
                }
            }
        }
    }

    protected abstract int times();

    protected abstract double energy();

    protected abstract boolean consume();
}
