package io.taraxacum.finaltech.core.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.task.effect.VoidCurse;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.task.TaskTicker;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * @see io.taraxacum.finaltech.core.item.unusable.Shine
 * @author Final_ROOT
 * @since 2.0
 */
public class ShineListener implements Listener {
    private final Map<Player, Integer> deathCount = new HashMap<>();
    private final Map<Player, Integer> obtainCount = new HashMap<>();

    /**
     * Player will lose all "shine" while dead in low place.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();

        boolean haveBox = false;
        List<ItemStack> shineItemList = new ArrayList<>();
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (!haveBox && ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.BOX)) {
                haveBox = true;
            }
            if (ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.SHINE)) {
                shineItemList.add(itemStack);
            }
        }

        boolean inCurse = false;
        boolean inLowPlace = false;
        Location location = playerDeathEvent.getEntity().getLocation();
        if (location.getY() < location.getWorld().getMinHeight() - 64) {
            inLowPlace = true;
        }
        if(TaskTicker.has(player, LivingEntity.class, VoidCurse.ID)) {
            inCurse = true;
        }

        if (haveBox || inCurse || inLowPlace) {
            for (ItemStack itemStack : shineItemList) {
                itemStack.setAmount(0);
            }
        }

        if (haveBox && inCurse || inLowPlace) {
            playerDeathEvent.setKeepInventory(false);
        }

        if(inCurse || inLowPlace) {
            if(!this.deathCount.containsKey(player)) {
                this.deathCount.put(player, 1);
            } else {
                this.deathCount.put(player, this.deathCount.get(player) + 1);
            }

            if(player.getLastDamageCause() != null && EntityDamageEvent.DamageCause.VOID.equals(player.getLastDamageCause().getCause())) {
                playerDeathEvent.setDeathMessage(FinalTech.getLanguageString("effect", "VOID_CURSE", "message", "death").replace("{1}", player.getName()));
            }

            if(inCurse) {
                Optional<PlayerProfile> playerProfile = PlayerProfile.find(player);
                if(playerProfile.isPresent()) {
                    for (ItemStack itemStack : player.getInventory().getContents()) {
                        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
                        if(slimefunItem != null && slimefunItem.getResearch() != null) {
                            playerProfile.get().setResearched(slimefunItem.getResearch(), false);
                        }
                    }
                }

                player.sendMessage(FinalTech.getLanguageString("items", "FINALTECH_SHINE", "message"));
            }
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
                    if (!haveBox && ItemStackUtil.isItemSimilar(FinalTechItems.BOX, itemStack)) {
                        haveBox = true;
                    } else if(ItemStackUtil.isItemSimilar(FinalTechItems.SHINE, itemStack)) {
                        shineCount += itemStack.getAmount();
                    }
                }
                if (haveBox || shineCount > 0) {
                    int obtain = this.obtainCount.getOrDefault(player, 1);
                    if (!player.isFlying() && haveBox && !player.isDead()) {
                        Vector nowVector = player.getVelocity().clone();
                        Location nowLocation = player.getLocation();
                        Location newLocation = nowLocation.add(FinalTech.getRandom().nextDouble() * 32 * shineCount * (1 + 0.05 * obtain) - 16 * shineCount * (1 + 0.05 * obtain), 0, FinalTech.getRandom().nextDouble() * 32 * shineCount * (1 + 0.05 * obtain) - 16 * shineCount * (1 + 0.05 * obtain));
                        player.teleport(newLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
                        player.setVelocity(nowVector);

                        PlayerInventory playerInventory = player.getInventory();
                        int[] ints = JavaUtil.generateRandomInts(playerInventory.getSize() - playerInventory.getArmorContents().length);
                        for (int anInt : ints) {
                            if (ItemStackUtil.isItemNull(playerInventory.getItem(anInt))) {
                                playerInventory.setItem(anInt, ItemStackUtil.cloneItem(FinalTechItems.SHINE, shineCount + 1));
                                break;
                            }
                        }
                        obtain++;
                        this.obtainCount.put(player, obtain);
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

                    player.setHealth(Math.max(player.getHealth() * 0.9 - player.getMaxHealth() * 0.1 * equipmentCount - player.getMaxHealth() * 0.05 * effectCount - shineCount * 2, 0));

                    int deathCount = this.deathCount.getOrDefault(player, 0);

                    TaskTicker.applyOrAddTo(new VoidCurse(shineCount * 20 + 100 + deathCount * 10 + obtain * 2, 1), player, LivingEntity.class);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent playerTeleportEvent) {
        Player player = playerTeleportEvent.getPlayer();
        Location location = player.getLocation();
        World world = location.getWorld();
        if(world == null || location.getY() < world.getMinHeight() || TaskTicker.has(player, LivingEntity.class, VoidCurse.ID)) {
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.SHINE)) {
                    itemStack.setAmount(0);
                }
            }
        }
    }
}
