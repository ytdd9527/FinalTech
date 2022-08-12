package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.ParticleUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

import javax.annotation.Nonnull;

public class StaffElementalLine extends UsableSlimefunItem{
    private final double shortRange = FinalTech.getValueManager().getOrDefault(2.5, "items", SlimefunUtil.getIdFormatName(StaffElementalLine.class), "short-range");
    private final double longRange = FinalTech.getValueManager().getOrDefault(16, "items", SlimefunUtil.getIdFormatName(StaffElementalLine.class), "long-range");

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
        Location targetLocation = null;
        boolean teleport = false;
        RayTraceResult rayTraceResult;
        if(player.isSneaking()) {
            rayTraceResult = player.rayTraceBlocks(this.longRange);
            if(rayTraceResult != null) {
                Block hitBlock = rayTraceResult.getHitBlock();
                BlockFace hitBlockFace = rayTraceResult.getHitBlockFace();
                if(hitBlock != null && hitBlockFace != null) {
                    Block targetBlock = hitBlock.getRelative(hitBlockFace);
                    targetLocation = LocationUtil.getCenterLocation(targetBlock);
                    teleport = true;
                } else if(rayTraceResult.getHitEntity() != null) {
                    Entity hitEntity = rayTraceResult.getHitEntity();
                    targetLocation = hitEntity.getLocation();
                    teleport = true;
                } else {
                    targetLocation = rayTraceResult.getHitPosition().toLocation(player.getWorld());
                }
            }
        } else {
            rayTraceResult = player.rayTraceBlocks(this.shortRange);
            if(rayTraceResult != null) {
                targetLocation = rayTraceResult.getHitPosition().toLocation(player.getWorld());
                teleport = true;
            }
        }
        if(targetLocation != null) {
            if(teleport) {
                targetLocation.setPitch(playerLocation.getPitch());
                targetLocation.setYaw(playerLocation.getYaw());
                player.teleport(targetLocation);
            }
            targetLocation.setY(targetLocation.getY() + player.getEyeHeight());
            ParticleUtil.drawLineByDistance(Particle.GLOW, 0, 0.1, playerLocation, targetLocation);
        }
    }
}
