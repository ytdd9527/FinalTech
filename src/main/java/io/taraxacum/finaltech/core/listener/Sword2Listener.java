package io.taraxacum.finaltech.core.listener;

import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Sword2Listener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerAttack(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if(entityDamageByEntityEvent.getDamager() instanceof Player && entityDamageByEntityEvent.getEntity() instanceof LivingEntity && EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK.equals(entityDamageByEntityEvent.getCause()) && ItemStackUtil.isItemSimilar(((Player) entityDamageByEntityEvent.getDamager()).getInventory().getItemInMainHand(), FinalTechItems.SWORD2)) {
            double damage = entityDamageByEntityEvent.getDamage();
            AttributeInstance maxHealthAttributeInstance = ((LivingEntity) entityDamageByEntityEvent.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if(maxHealthAttributeInstance != null) {
                damage += maxHealthAttributeInstance.getValue() * 0.04;
            }
            entityDamageByEntityEvent.setDamage(damage);
        }
    }
}
