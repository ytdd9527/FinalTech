package io.taraxacum.finaltech.core.items.weapon;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
@Deprecated
public class UnOrderedSword extends SlimefunItem {
    public UnOrderedSword(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        super.preRegister();
//        this.addItemHandler(new WeaponUseHandler() {
//            @Override
//            @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
//            public void onHit(@Nonnull EntityDamageByEntityEvent entityDamageByEntityEvent, @Nonnull Player player, @Nonnull ItemStack itemStack) {
//                entityDamageByEntityEvent.setCancelled(true);
//                if (!SlimefunUtil.hasPermission(entityDamageByEntityEvent.getEntity(), player)) {
//                    return;
//                }
//                if (!Material.WOODEN_SWORD.equals(itemStack.getType())) {
//                    return;
//                }
//                double damage = entityDamageByEntityEvent.getOriginalDamage(EntityDamageEvent.DamageModifier.BASE);
//                damage = Math.max(damage, entityDamageByEntityEvent.getDamage());
//                damage = Math.max(damage, entityDamageByEntityEvent.getFinalDamage());
//                damage += itemStack.getDurability();
//                Entity entity = entityDamageByEntityEvent.getEntity();
//                if (entity instanceof LivingEntity) {
//                    ((LivingEntity) entity).setHealth(Math.max(((LivingEntity) entity).getHealth() - damage, 0.01));
//                }
//            }
//        });
    }
}
