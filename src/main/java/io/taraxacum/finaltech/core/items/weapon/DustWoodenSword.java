//package io.taraxacum.finaltech.core.items.weapon;
//
//import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
//import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
//import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
//import io.github.thebusybiscuit.slimefun4.core.handlers.WeaponUseHandler;
//import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
//import io.taraxacum.finaltech.FinalTech;
//import io.taraxacum.finaltech.api.interfaces.RecipeItem;
//import io.taraxacum.finaltech.core.items.AbstractMySlimefunItem;
//import io.taraxacum.finaltech.util.ItemStackUtil;
//import io.taraxacum.finaltech.util.TextUtil;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.World;
//import org.bukkit.enchantments.Enchantment;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.EventPriority;
//import org.bukkit.event.entity.EntityDamageByEntityEvent;
//import org.bukkit.inventory.EntityEquipment;
//import org.bukkit.inventory.ItemStack;
//
//import javax.annotation.Nonnull;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//public class DustWoodenSword extends AbstractMySlimefunItem implements RecipeItem {
//    private static final Double EXTRA_DAMAGE = FinalTech.getValueManager().getOrDefault(0.1, "weapon", DustWoodenSword.class.getSimpleName(), "extra-damage");
//    private static final Integer TICK_REDUCE = FinalTech.getValueManager().getOrDefault(1, "weapon", DustWoodenSword.class.getSimpleName(), "tick-reduce");
//
//    public DustWoodenSword(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
//        super(itemGroup, item, recipeType, recipe);
//    }
//
//    @Override
//    public void preRegister() {
//        super.preRegister();
//        this.addItemHandler(new WeaponUseHandler() {
//            @Override
//            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
//            public void onHit(@Nonnull EntityDamageByEntityEvent entityDamageByEntityEvent, @Nonnull Player player, @Nonnull ItemStack itemStack) {
//                entityDamageByEntityEvent.setCancelled(true);
//                if (!(entityDamageByEntityEvent.getEntity() instanceof LivingEntity targetEntity)) {
//                    return;
//                }
//                if (targetEntity.getNoDamageTicks() > 0) {
//                    return;
//                }
//                if (!SlimefunUtil.checkPermission(player, targetEntity.getLocation(), Interaction.INTERACT_ENTITY, Interaction.ATTACK_ENTITY)) {
//                    return;
//                }
//                if (targetEntity instanceof Player && !SlimefunUtil.checkPermission(player, targetEntity.getLocation(), Interaction.ATTACK_PLAYER)) {
//                    return;
//                }
//                if (!DustWoodenSword.this.getItem().getType().equals(itemStack.getType())) {
//                    return;
//                }
//
//                double damage = entityDamageByEntityEvent.getFinalDamage();
//                int noDamageTick = targetEntity.getMaximumNoDamageTicks();
//                EntityEquipment equipment = targetEntity.getEquipment();
//                if(equipment != null) {
//                    equipment.getArmorContents();
//                    Set<Enchantment> enchantmentSet = new HashSet<>();
//                    int totalEnchantmentLevel = 0;
//                    for(ItemStack equipmentItem : equipment.getArmorContents()) {
//                        if(!ItemStackUtil.isItemNull(equipmentItem)) {
//                            for(Map.Entry<Enchantment, Integer> entry : equipmentItem.getEnchantments().entrySet()) {
//                                enchantmentSet.add(entry.getKey());
//                                totalEnchantmentLevel += entry.getValue();
//                            }
//                        }
//                    }
//                    damage += totalEnchantmentLevel * EXTRA_DAMAGE;
//                    noDamageTick = Math.max(noDamageTick - TICK_REDUCE * enchantmentSet.size(), 0);
//                }
//                damage = Math.min(targetEntity.getHealth() - 0.001, damage);
//                damage = Math.max(0, damage);
//                targetEntity.setHealth(targetEntity.getHealth() - damage);
//                targetEntity.setNoDamageTicks(noDamageTick);
//                targetEntity.setLastDamage(damage);
//                targetEntity.setLastDamageCause(entityDamageByEntityEvent);
//                World world = targetEntity.getLocation().getWorld();
//                if(world != null) {
//                    targetEntity.getLocation().getWorld().spawnParticle(Particle.CRIT, targetEntity.getLocation(), (int)(1 + Math.pow(damage, 0.5)));
//                    targetEntity.getLocation().getWorld().playSound(targetEntity.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, (float) Math.log(damage), 1);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void registerDefaultRecipes() {
//        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "尘埃化",
//                "",
//                TextUtil.COLOR_NORMAL + "造成伤害时",
//                TextUtil.COLOR_NORMAL + "不再触发常规的 自身攻击特效 与 目标受击特效",
//                TextUtil.COLOR_NORMAL + "直接对目标生命值造成减少效果",
//                "",
//                TextUtil.COLOR_NORMAL + "造成伤害不致死");
//        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "附魔紊乱",
//                "",
//                TextUtil.COLOR_NORMAL + "目标所穿装备的每一个附魔等级",
//                TextUtil.COLOR_NORMAL + "使自身攻击时造成的伤害额外增加 " + TextUtil.COLOR_NUMBER + DustWoodenSword.EXTRA_DAMAGE + "点" + TextUtil.COLOR_NORMAL + " 伤害");
//        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "附魔穿透",
//                "",
//                TextUtil.COLOR_NORMAL + "目标所穿装备的每一种附魔",
//                TextUtil.COLOR_NORMAL + "使自身攻击时造成的无敌时间减少 " + TextUtil.COLOR_NUMBER + (DustWoodenSword.TICK_REDUCE * 0.05) + "秒");
//    }
//}
