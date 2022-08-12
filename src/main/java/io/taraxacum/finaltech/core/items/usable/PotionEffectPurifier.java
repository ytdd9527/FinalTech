package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;

public class PotionEffectPurifier extends UsableSlimefunItem {
    private final double horizontalRange = FinalTech.getValueManager().getOrDefault(16, "items", SlimefunUtil.getIdFormatName(PotionEffectPurifier.class), "horizontal-range");
    private final double verticalRange = FinalTech.getValueManager().getOrDefault(8, "items", SlimefunUtil.getIdFormatName(PotionEffectPurifier.class), "vertical-range");

    public PotionEffectPurifier(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     * The function the item will do
     * while a player hold the item and right click.
     *
     * @param playerRightClickEvent
     */
    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        Player player = playerRightClickEvent.getPlayer();
        if(player.isSneaking()) {
            Location location = player.getLocation();
            World world = location.getWorld();
            // PARTICAL EFFECT
            if(world != null) {
                for(Entity entity : world.getNearbyEntities(location, this.horizontalRange, this.verticalRange, this.horizontalRange, entity -> entity instanceof LivingEntity)) {
                    for(PotionEffect potionEffect : ((LivingEntity) entity).getActivePotionEffects()) {
                        ((LivingEntity) entity).removePotionEffect(potionEffect.getType());
                    }
                }
            }
        } else {
            for(PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
            ItemStack item = playerRightClickEvent.getItem();
            item.setAmount(item.getAmount() - 1);
        }
    }
}
