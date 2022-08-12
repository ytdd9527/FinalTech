package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.Random;

public class MagicHypnotic extends UsableSlimefunItem{
    public MagicHypnotic(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        PotionEffectType[] allPotionEffectType = PotionEffectType.values();
        PotionEffectType randomPotionEffectType = allPotionEffectType[new Random().nextInt(allPotionEffectType.length)];
        boolean hasPotionEffect = false;
        for(PotionEffect potionEffect : player.getActivePotionEffects()) {
            if(potionEffect.getType().equals(randomPotionEffectType)) {
                hasPotionEffect = true;
                player.removePotionEffect(potionEffect.getType());
                player.addPotionEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration() + player.getLevel() + 1, potionEffect.getAmplifier()));
                break;
            }
        }
        if(!hasPotionEffect) {
            player.addPotionEffect(new PotionEffect(randomPotionEffectType, player.getLevel() + 1, 0));
        }
        ItemStack item = playerRightClickEvent.getItem();
        item.setAmount(item.getAmount() - 1);
    }
}
