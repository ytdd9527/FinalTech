package io.taraxacum.finaltech.item;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.WeaponUseHandler;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class UnOrderSword extends SlimefunItem {
    public UnOrderSword(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(new WeaponUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
            public void onHit(@Nonnull EntityDamageByEntityEvent entityDamageByEntityEvent, @Nonnull Player player, @Nonnull ItemStack itemStack) {
                entityDamageByEntityEvent.setCancelled(true);
                if (!SlimefunUtil.hasPermission(entityDamageByEntityEvent.getEntity(), player)) {
                    return;
                }
                if (!Material.WOODEN_SWORD.equals(itemStack.getType())) {
                    return;
                }
                double damage = entityDamageByEntityEvent.getOriginalDamage(EntityDamageEvent.DamageModifier.BASE);
                damage = Math.max(damage, entityDamageByEntityEvent.getDamage());
                damage = Math.max(damage, entityDamageByEntityEvent.getFinalDamage());
                damage += itemStack.getDurability();
                Entity entity = entityDamageByEntityEvent.getEntity();
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).setHealth(Math.max(((LivingEntity) entity).getHealth() - damage, 0.01));
                }
            }
        });
    }
}
