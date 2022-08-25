package io.taraxacum.finaltech.core.items.usable.accelerate;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.items.usable.UsableSlimefunItem;
import io.taraxacum.finaltech.util.ParticleUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractMachineActivateCard extends UsableSlimefunItem {
    public AbstractMachineActivateCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();
        Block block = playerRightClickEvent.getInteractEvent().getClickedBlock();
        if (block == null) {
            return;
        }
        Player player = playerRightClickEvent.getPlayer();
        if (player.isDead()) {
            return;
        }
        Location location = block.getLocation();
        Config config = BlockStorage.getLocationInfo(location);
        if (config.contains(SlimefunUtil.KEY_ID)) {
            if (!SlimefunUtil.checkPermission(player, location, Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                player.sendRawMessage(FinalTech.getLanguageManager().getString("messages", "no-permission", "location"));
                return;
            }
            if (BlockStorage.hasInventory(block)) {
                BlockMenu blockMenu = BlockStorage.getInventory(location);
                if (!blockMenu.canOpen(block, player)) {
                    player.sendRawMessage(FinalTech.getLanguageManager().getString("messages", "no-permission", "location"));
                    return;
                }
            }
            if (!this.conditionMatch(player)) {
                player.sendRawMessage(FinalTech.getLanguageManager().getString("messages", "no-condition", "player"));
                return;
            }

            SlimefunItem slimefunItem = SlimefunItem.getById(config.getString(SlimefunUtil.KEY_ID));
            if (slimefunItem == null) {
                return;
            }
            BlockTicker blockTicker = slimefunItem.getBlockTicker();

            if(blockTicker != null && FinalTech.isAntiAccelerateSlimefunItem(slimefunItem.getId())) {
                return;
            }

            if(blockTicker != null && slimefunItem instanceof EnergyNetComponent) {
                int time;
                if (this.consume()) {
                    if(playerRightClickEvent.getItem().getAmount() > 0) {
                        ItemStack item = playerRightClickEvent.getItem();
                        item.setAmount(item.getAmount() - 1);
                        time = this.times();
                    } else {
                        return;
                    }
                } else {
                    time = this.times() * playerRightClickEvent.getItem().getAmount();
                }
                int capacity = ((EnergyNetComponent) slimefunItem).getCapacity();
                AtomicInteger chargeEnergy = new AtomicInteger((int) this.energy() + (int)((this.energy() - (int) this.energy()) * capacity));
                if (!this.consume()) {
                    chargeEnergy.set(chargeEnergy.get() * playerRightClickEvent.getItem().getAmount());
                }
                ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
                BlockTicker blockTickerWrapper = new BlockTicker() {
                    @Override
                    public boolean isSynchronized() {
                        return blockTicker.isSynchronized();
                    }

                    @Override
                    public void tick(Block b, SlimefunItem item, Config data) {
                        for (int i = 0; i < time; i++) {
                            int storedEnergy = ((EnergyNetComponent) slimefunItem).getCharge(location);
                            chargeEnergy.set(chargeEnergy.get() / 2 + storedEnergy / 2 > Integer.MAX_VALUE / 2 ? Integer.MAX_VALUE : chargeEnergy.get() + storedEnergy);
                            ((EnergyNetComponent) slimefunItem).setCharge(location, Math.min(capacity, chargeEnergy.get()));
                            blockTicker.tick(b, item, data);
                        }
                    }
                };
                if(FinalTech.isAsyncSlimefunItem(slimefunItem.getId())) {
                    SlimefunUtil.runBlockTicker(FinalTech.getLocationRunnableFactory(), blockTickerWrapper, block, slimefunItem, config, location);
                } else {
                    SlimefunUtil.runBlockTickerLocal(this.getAddon().getJavaPlugin(), blockTickerWrapper, block, slimefunItem, config);
                }
            } else if(blockTicker != null) {
                // this slimefun item have
                int time;
                if (this.consume()) {
                    if(playerRightClickEvent.getItem().getAmount() > 0) {
                        ItemStack item = playerRightClickEvent.getItem();
                        item.setAmount(item.getAmount() - 1);
                        time = this.times();
                    } else {
                        return;
                    }
                } else {
                    time = this.times() * playerRightClickEvent.getItem().getAmount();
                }
                ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
                BlockTicker blockTickerWrapper = new BlockTicker() {
                    @Override
                    public boolean isSynchronized() {
                        return blockTicker.isSynchronized();
                    }

                    @Override
                    public void tick(Block b, SlimefunItem item, Config data) {
                        for (int i = 0; i < time; i++) {
                            blockTicker.tick(b, item, data);
                        }
                    }
                };
                if(FinalTech.isAsyncSlimefunItem(slimefunItem.getId())) {
                    SlimefunUtil.runBlockTicker(FinalTech.getLocationRunnableFactory(), blockTickerWrapper, block, slimefunItem, config, location);
                } else {
                    SlimefunUtil.runBlockTickerLocal(this.getAddon().getJavaPlugin(), blockTickerWrapper, block, slimefunItem, config);
                }
            } else if(slimefunItem instanceof EnergyNetComponent) {
                // this slimefun item is energy net component
                if (((EnergyNetComponent) slimefunItem).getCapacity() <= 0) {
                    return;
                }
                if (this.consume()) {
                    if(playerRightClickEvent.getItem().getAmount() > 0) {
                        ItemStack item = playerRightClickEvent.getItem();
                        item.setAmount(item.getAmount() - 1);
                    } else {
                        return;
                    }
                }
                ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
                int capacity = ((EnergyNetComponent) slimefunItem).getCapacity();
                int chargeEnergy = (int) this.energy() + (int)((this.energy() - (int) this.energy()) * capacity);
                if (!this.consume()) {
                    chargeEnergy *= playerRightClickEvent.getItem().getAmount();
                }
                int storedEnergy = ((EnergyNetComponent) slimefunItem).getCharge(location);
                chargeEnergy = chargeEnergy / 2 + storedEnergy / 2 > Integer.MAX_VALUE / 2 ? Integer.MAX_VALUE : chargeEnergy + storedEnergy;
                ((EnergyNetComponent) slimefunItem).setCharge(location, Math.min(capacity, chargeEnergy));
            }
        }
    }

    protected abstract double energy();

    protected abstract int times();

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
