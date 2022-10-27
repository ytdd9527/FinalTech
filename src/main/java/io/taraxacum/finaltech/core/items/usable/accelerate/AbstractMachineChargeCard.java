package io.taraxacum.finaltech.core.items.usable.accelerate;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.items.usable.UsableSlimefunItem;
import io.taraxacum.finaltech.util.ParticleUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import io.taraxacum.finaltech.util.slimefun.PermissionUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractMachineChargeCard extends UsableSlimefunItem {
    public AbstractMachineChargeCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        if(player.isDead()) {
            return;
        }

        Location location = block.getLocation();
        Config config = BlockStorage.getLocationInfo(location);
        if (!config.contains(ConstantTableUtil.CONFIG_ID)) {
            return;
        }

        if (!PermissionUtil.checkPermission(player, location, Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
            // TODO: message
            player.sendRawMessage(FinalTech.getLanguageString("messages", "no-permission", "location"));
            return;
        }

        if (!this.conditionMatch(player)) {
            player.sendRawMessage(FinalTech.getLanguageString("messages", "no-condition", "player"));
            return;
        }

        SlimefunItem slimefunItem = SlimefunItem.getById(config.getString(ConstantTableUtil.CONFIG_ID));
        if(slimefunItem instanceof EnergyNetComponent && ((EnergyNetComponent) slimefunItem).getCapacity() > 0) {
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
            int chargeEnergy = (int) this.energy();
            if(!EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) slimefunItem).getEnergyComponentType())) {
                chargeEnergy += (int)((this.energy() - (int) this.energy()) * capacity);
            }
            if (!this.consume()) {
                chargeEnergy *= playerRightClickEvent.getItem().getAmount();
            }
            int storedEnergy = ((EnergyNetComponent) slimefunItem).getCharge(location);
            chargeEnergy = chargeEnergy / 2 + storedEnergy / 2 > Integer.MAX_VALUE / 2 ? Integer.MAX_VALUE : chargeEnergy + storedEnergy;
            ((EnergyNetComponent) slimefunItem).setCharge(location, Math.min(capacity, chargeEnergy));
        }
    }

    protected abstract double energy();

    /**
     * @return If using it will consume itself
     */
    protected abstract boolean consume();

    /**
     * If it can work.
     * May be designed to cost player's health or exp.
     */
    protected abstract boolean conditionMatch(@Nonnull Player player);
}
