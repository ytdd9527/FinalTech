package io.taraxacum.finaltech.core.listener;

import io.taraxacum.common.api.RunnableLockFactory;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.task.effect.UntreatableEffect;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.task.TickerTaskRunner;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * @see io.taraxacum.finaltech.core.items.unusable.Shine
 * @author Final_ROOT
 * @since 2.0
 */
public class ShineListener implements Listener {

    /**
     * Player will lose all item shines while dead in low place.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {
        boolean haveBox = false;
        List<ItemStack> shineItemList = new ArrayList<>();
        for (ItemStack itemStack : playerDeathEvent.getEntity().getInventory().getContents()) {
            if (!haveBox && ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.BOX)) {
                haveBox = true;
            }
            if (ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.SHINE)) {
                shineItemList.add(itemStack);
            }
        }
        boolean inLowLocation = false;
        Location location = playerDeathEvent.getEntity().getLocation();
        if (location.getY() < location.getWorld().getMinHeight() - 64) {
            inLowLocation = true;
        }
        if (haveBox || inLowLocation) {
            for (ItemStack itemStack : shineItemList) {
                itemStack.setAmount(0);
            }
        }
        if (haveBox && inLowLocation) {
            playerDeathEvent.setKeepInventory(false);
        }
    }

    /**
     * Player will get a shine while holding item box.
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent entityDamageEvent) {
        if (EntityDamageEvent.DamageCause.VOID.equals(entityDamageEvent.getCause()) && EntityType.PLAYER.equals(entityDamageEvent.getEntityType())) {
            Entity entity = entityDamageEvent.getEntity();
            if (entity instanceof Player player) {
                boolean haveBox = false;
                int shineCount = 0;
                for (ItemStack itemStack : player.getInventory().getContents()) {
                    if (ItemStackUtil.isItemSimilar(FinalTechItems.BOX, itemStack)) {
                        haveBox = true;
                    } else if(ItemStackUtil.isItemSimilar(FinalTechItems.SHINE, itemStack)) {
                        shineCount += itemStack.getAmount();
                    }
                }
                if (haveBox || shineCount > 0) {
                    if (!player.isFlying() && haveBox) {
                        player.getInventory().addItem(ItemStackUtil.cloneItem(FinalTechItems.SHINE));
                    }

                    player.closeInventory();

                    int effectCount = 0;
                    for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                        player.removePotionEffect(potionEffect.getType());
                        if (potionEffect.getDuration() > 1 && potionEffect.getAmplifier() >= 1) {
                            player.addPotionEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration() / 2, potionEffect.getAmplifier() - 1));
                        }
                        effectCount++;
                    }

                    EntityEquipment equipment = player.getEquipment();
                    int equipmentCount = 1;
                    if (equipment != null) {
                        for (ItemStack item : equipment.getArmorContents()) {
                            if (!ItemStackUtil.isItemNull(item)) {
                                equipmentCount++;
                            }
                        }
                    }
                    player.setHealth(Math.max(player.getHealth() * 0.9 - player.getMaxHealth() * 0.1 * equipmentCount - player.getMaxHealth() * 0.05 * effectCount - shineCount * 1.5, 0));

                    TickerTaskRunner.applyOrAddTo(new UntreatableEffect(shineCount * 20 + 20, 1), player, FinalTech.getInstance());
                }
            }
        }
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent entityTeleportEvent) {
        Location sourceLocation = entityTeleportEvent.getFrom();
        if (sourceLocation.getWorld() != null) {
            World world = sourceLocation.getWorld();
            if (sourceLocation.getY() < world.getMinHeight() - 64) {
                if (EntityType.PLAYER.equals(entityTeleportEvent.getEntityType())) {
                    Entity entity = entityTeleportEvent.getEntity();
                    if (entity instanceof Player) {
                        for (ItemStack itemStack : ((Player) entity).getInventory().getContents()) {
                            if (ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.SHINE)) {
                                itemStack.setAmount(0);
                            }
                        }
                    }
                }
            }
        }
    }
}
