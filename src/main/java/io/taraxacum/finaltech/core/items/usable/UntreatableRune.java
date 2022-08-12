package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.factory.TickerTaskRunner;
import io.taraxacum.finaltech.core.task.effect.UntreatableEffect;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class UntreatableRune extends UsableSlimefunItem {
    private int time = FinalTech.getValueManager().getOrDefault(200, "items", SlimefunUtil.getIdFormatName(UntreatableRune.class), "time");
    private double range = FinalTech.getValueManager().getOrDefault(8.0, "items", SlimefunUtil.getIdFormatName(UntreatableRune.class), "range");

    public UntreatableRune(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        // TODO particle
        Location location = player.getLocation();
        for(Entity entity : player.getNearbyEntities(this.range, this.range, this.range)) {
            if(entity instanceof LivingEntity livingEntity) {
                TickerTaskRunner.applyOrAddTo(new UntreatableEffect(this.time, 1), livingEntity, this.getAddon().getJavaPlugin());
            }
        }
        TickerTaskRunner.applyOrAddTo(new UntreatableEffect(this.time, 1), player, this.getAddon().getJavaPlugin());
    }
}
