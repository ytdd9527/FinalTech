package io.taraxacum.finaltech.core.listener;

import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class Sword1Listener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerAttack(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.getDamager() instanceof Player && entityDamageByEntityEvent.getEntity() instanceof LivingEntity && EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK.equals(entityDamageByEntityEvent.getCause()) && ItemStackUtil.isItemSimilar(((Player) entityDamageByEntityEvent.getDamager()).getInventory().getItemInMainHand(), FinalTechItems.SWORD1)) {
            EntityDamageByEntityEvent newEvent = new EntityDamageByEntityEvent(entityDamageByEntityEvent.getDamager(), entityDamageByEntityEvent.getEntity(), EntityDamageEvent.DamageCause.VOID, entityDamageByEntityEvent.getDamage());
            entityDamageByEntityEvent.setCancelled(true);
            Bukkit.getPluginManager().callEvent(newEvent);
        }
    }
}
