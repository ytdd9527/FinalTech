package io.taraxacum.finaltech.core.items.usable.accelerate;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.usable.UsableSlimefunItem;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.common.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * An item that will accelerate or charge machine.
 * While it has both the function, it will charge machine every times the machine is accelerated.
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractMachineActivateCard extends UsableSlimefunItem implements RecipeItem {
    public AbstractMachineActivateCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();
        PlayerInteractEvent interactEvent = playerRightClickEvent.getInteractEvent();
        Block block = interactEvent.getClickedBlock();
        if (block == null) {
            return;
        }
        Player player = playerRightClickEvent.getPlayer();
        Location location = interactEvent.getClickedBlock().getLocation();
        Config config = BlockStorage.getLocationInfo(location);
        if (config.contains(SlimefunUtil.KEY_ID)) {
            if(!SlimefunUtil.hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                //todo
                // 可配置化
                player.sendRawMessage("您似乎没有在此处使用该物品的权限");
                return;
            }
            if(!this.conditionMatch(player)) {
                //todo
                // 可配置化
                player.sendRawMessage("您似乎无法使用该物品");
                return;
            }
            if (this.consume()) {
                ItemStack item = playerRightClickEvent.getItem();
                item.setAmount(item.getAmount() - 1);
            }
            SlimefunItem slimefunItem = SlimefunItem.getById(config.getString(SlimefunUtil.KEY_ID));
            if (slimefunItem != null) {
                boolean chargeable = slimefunItem instanceof EnergyNetComponent && this.energy() > 0;
                String capacity = StringNumberUtil.ZERO;
                String chargeEnergy = StringNumberUtil.ZERO;
                if (chargeable) {
                    capacity = String.valueOf(((EnergyNetComponent) slimefunItem).getCapacity());
                    if (StringNumberUtil.ZERO.equals(capacity)) {
                        chargeable = false;
                    }
                    if (chargeable) {
                        int energyValue = (int) this.energy();
                        double energyPercentage = this.energy() - energyValue;
                        if (energyValue > 0) {
                            chargeEnergy = String.valueOf(energyValue);
                        }
                        if (energyPercentage > 0 && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) slimefunItem).getEnergyComponentType())) {
                            chargeEnergy = StringNumberUtil.add(chargeEnergy, String.valueOf((int) (((EnergyNetComponent) slimefunItem).getCapacity() * energyPercentage)));
                        }
                    }
                }

                BlockTicker blockTicker = slimefunItem.getBlockTicker();
                if (blockTicker != null && chargeable) {
                    for (int i = 0, limit = this.consume() ? this.times() : this.times() * playerRightClickEvent.getItem().getAmount(); i < limit; i++) {
                        String energy = SlimefunUtil.getCharge(config);
                        energy = StringNumberUtil.add(energy, chargeEnergy);
                        energy = StringNumberUtil.min(energy, capacity);
                        SlimefunUtil.setCharge(config, energy);
                        SlimefunUtil.runBlockTicker(blockTicker, block, slimefunItem, config);
                    }
                } else if (blockTicker != null) {
                    for (int i = 0, limit = this.consume() ? this.times() : this.times() * playerRightClickEvent.getItem().getAmount(); i < limit; i++) {
                        SlimefunUtil.runBlockTicker(blockTicker, block, slimefunItem, config);
                    }
                } else if (chargeable) {
                    String chargeEnergyResult = StringNumberUtil.add(chargeEnergy, chargeEnergy);
                    for (int i = 1, limit = this.consume() ? this.times() : this.times() * playerRightClickEvent.getItem().getAmount(); i < limit; i++) {
                        chargeEnergyResult = StringNumberUtil.add(chargeEnergyResult, chargeEnergy);
                    }
                    if (this.times() == 0) {
                        if (!this.consume()) {
                            chargeEnergyResult = StringNumberUtil.mul(chargeEnergyResult, String.valueOf(playerRightClickEvent.getItem().getAmount()));
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

    /**
     * @return If using it will consume itself
     */
    protected abstract boolean consume();

    /**
     * If it can work.
     * May cost player's health or exp;
     * @param player
     * @return
     */
    protected abstract boolean conditionMatch(@Nonnull Player player);
}
