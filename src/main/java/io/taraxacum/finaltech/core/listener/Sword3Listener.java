package io.taraxacum.finaltech.core.listener;

import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;

public class Sword3Listener implements Listener {

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.getDamager() instanceof Player && entityDamageByEntityEvent.getEntity() instanceof LivingEntity && EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK.equals(entityDamageByEntityEvent.getCause()) && ItemStackUtil.isItemSimilar(((Player) entityDamageByEntityEvent.getDamager()).getInventory().getItemInMainHand(), FinalTechItems.SWORD3)) {
            LivingEntity entity = (LivingEntity)entityDamageByEntityEvent.getEntity();
            for (PotionEffect potionEffect : entity.getActivePotionEffects()) {
                entity.removePotionEffect(potionEffect.getType());
                if (potionEffect.getDuration() > 100) {
                    entity.addPotionEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration() - 100, potionEffect.getAmplifier()));
                }
            }
        }
    }
}
