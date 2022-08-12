package io.taraxacum.finaltech.core.handler;

import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
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
        for(ItemStack itemStack : playerDeathEvent.getEntity().getInventory().getContents()) {
            if(!haveBox && ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.BOX)) {
                haveBox = true;
            }
            if(ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.SHINE)) {
                shineItemList.add(itemStack);
            }
        }
        boolean inLowLocation = false;
        Location location = playerDeathEvent.getEntity().getLocation();
        if(location.getY() < location.getWorld().getMinHeight() - 64) {
            inLowLocation = true;
        }
        if(haveBox || inLowLocation) {
            for(ItemStack itemStack : shineItemList) {
                itemStack.setAmount(0);
            }
        }
        if(haveBox && inLowLocation) {
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
            if(entity instanceof Player) {
                boolean haveBox = false;
                for(ItemStack itemStack : ((Player) entity).getInventory().getContents()) {
                    if(ItemStackUtil.isItemSimilar(FinalTechItems.BOX, itemStack) || ItemStackUtil.isItemSimilar(FinalTechItems.SHINE, itemStack)) {
                        haveBox = true;
                        break;
                    }
                }
                if(haveBox) {
                    if(!((Player) entity).isFlying()) {
                        ((Player) entity).getInventory().addItem(ItemStackUtil.cloneItem(FinalTechItems.SHINE));
                    }
                    EntityEquipment equipment = ((Player) entity).getEquipment();
                    int count = 1;
                    if(equipment != null) {
                        for(ItemStack item : equipment.getArmorContents()) {
                            if(!ItemStackUtil.isItemNull(item)) {
                                count++;
                            }
                        }
                    }
                    ((Player) entity).setHealth(Math.max(((Player) entity).getHealth() * 0.9 - ((Player) entity).getMaxHealth() * 0.1 * count, 0));
                    for(PotionEffect potionEffect : ((Player) entity).getActivePotionEffects()) {
                        ((Player) entity).removePotionEffect(potionEffect.getType());
                        if(potionEffect.getDuration() > 1 && potionEffect.getAmplifier() >= 1) {
                            ((Player) entity).addPotionEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration() / 2, potionEffect.getAmplifier() - 1));
                            ((Player) entity).setHealth(Math.max(((Player) entity).getHealth() - ((Player) entity).getMaxHealth() * 0.05, 0));
                            if(entity.isDead()) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent entityTeleportEvent) {
        Location sourceLocation = entityTeleportEvent.getFrom();
        if(sourceLocation.getWorld() != null) {
            World world = sourceLocation.getWorld();
            if(sourceLocation.getY() < world.getMinHeight() - 64) {
                if (EntityType.PLAYER.equals(entityTeleportEvent.getEntityType())) {
                    Entity entity = entityTeleportEvent.getEntity();
                    if(entity instanceof Player) {
                        for(ItemStack itemStack : ((Player) entity).getInventory().getContents()) {
                            if(ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.SHINE)) {
                                itemStack.setAmount(0);
                            }
                        }
                    }
                }
            }
        }
    }
}
