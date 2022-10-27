package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.ParticleUtil;
import io.taraxacum.finaltech.util.VectorUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class StaffElementalLine extends UsableSlimefunItem implements RecipeItem {
    private final double shortRange = ConfigUtil.getOrDefaultItemSetting(4, this, "short-range");
    private final double longRange = ConfigUtil.getOrDefaultItemSetting(16, this, "long-range");

    public StaffElementalLine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        Location playerLocation = player.getEyeLocation();
        Location targetLocation = player.getLocation();
        boolean teleport = false;
        RayTraceResult rayTraceResult;
        if (player.isSneaking()) {
            rayTraceResult = player.rayTraceBlocks(this.longRange);
            if (rayTraceResult != null) {
                Block hitBlock = rayTraceResult.getHitBlock();
                BlockFace hitBlockFace = rayTraceResult.getHitBlockFace();
                if (hitBlock != null && hitBlockFace != null) {
                    Block targetBlock = hitBlock.getRelative(hitBlockFace);
                    targetLocation = LocationUtil.getCenterLocation(targetBlock);
                    targetLocation.setY(targetLocation.getY() - player.getEyeHeight());
                    teleport = true;
                } else if (rayTraceResult.getHitEntity() != null) {
                    Entity hitEntity = rayTraceResult.getHitEntity();
                    targetLocation = hitEntity.getLocation();
                    teleport = true;
                }
            } else {
                Vector vector = VectorUtil.toLength(VectorUtil.fromYawPitch(playerLocation.getYaw(), playerLocation.getPitch()), this.longRange);
                targetLocation.add(vector);

                playerLocation.add(VectorUtil.toLength(VectorUtil.getTrueRandom(), FinalTech.getRandom().nextDouble() * this.longRange / 2 + this.longRange / 2));
            }
        } else {
            rayTraceResult = player.rayTraceBlocks(this.shortRange);
            if (rayTraceResult != null) {
                targetLocation = rayTraceResult.getHitPosition().toLocation(player.getWorld());
                targetLocation.setY(targetLocation.getY() - player.getEyeHeight());
                teleport = true;
            } else {
                Vector vector = VectorUtil.toLength(VectorUtil.fromYawPitch(playerLocation.getYaw(), playerLocation.getPitch()), this.shortRange);
                targetLocation.add(vector);
                teleport = true;
            }
        }
        if (teleport) {
            targetLocation.setPitch(playerLocation.getPitch());
            targetLocation.setYaw(playerLocation.getYaw());
            if (!targetLocation.getWorld().getBlockAt(targetLocation).getType().isAir()) {
                targetLocation.setY(Math.ceil(targetLocation.getY()));
            }
            if (!targetLocation.getWorld().getBlockAt(new Location(targetLocation.getWorld(), targetLocation.getBlockX() + 0.5, targetLocation.getBlockY() + 0.5, targetLocation.getBlockZ() + 0.5)).getType().isAir()) {
                targetLocation.setY(Math.ceil(targetLocation.getY() + 0.1));
            }
            player.teleport(targetLocation);
        }
        targetLocation.setY(targetLocation.getY() + player.getEyeHeight());
        ParticleUtil.drawLineByDistance(Particle.GLOW, 0, 0.1, playerLocation, targetLocation);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.shortRange),
                String.valueOf(this.longRange));
    }
}
