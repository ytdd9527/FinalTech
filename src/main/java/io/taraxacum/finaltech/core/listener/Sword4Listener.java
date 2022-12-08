package io.taraxacum.finaltech.core.listener;

import io.taraxacum.finaltech.core.task.effect.UntreatableEffect;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.task.TaskTicker;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class Sword4Listener implements Listener {

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.getDamager() instanceof Player && entityDamageByEntityEvent.getEntity() instanceof LivingEntity livingEntity && EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK.equals(entityDamageByEntityEvent.getCause()) && ItemStackUtil.isItemSimilar(((Player) entityDamageByEntityEvent.getDamager()).getInventory().getItemInMainHand(), FinalTechItems.SWORD4)) {
            TaskTicker.applyOrAddTo(new UntreatableEffect(100, 1), livingEntity, LivingEntity.class);
        }
    }
}
