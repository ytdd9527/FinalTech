package io.taraxacum.finaltech.util;

import io.taraxacum.finaltech.FinalTech;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LocationUtil {
    private static final NamespacedKey KEY = new NamespacedKey(FinalTech.getInstance(), "location");

    public static Location parseLocationInItem(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return null;
        }
        ItemMeta itemMeta = item.getItemMeta();
        return LocationUtil.parseLocationInItem(itemMeta);
    }
    public static Location parseLocationInItem(@Nonnull ItemMeta itemMeta) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(KEY, PersistentDataType.STRING)) {
            String locationString = persistentDataContainer.get(KEY, PersistentDataType.STRING);
            return LocationUtil.stringToLocation(locationString);
        }
        return null;
    }

    public static boolean saveLocationToItem(@Nullable ItemStack item, @Nonnull Location location) {
        if (ItemStackUtil.isItemNull(item)) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(KEY, PersistentDataType.STRING, LocationUtil.locationToString(location));
        item.setItemMeta(itemMeta);
        return true;
    }

    public static boolean updateLocationItem(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(KEY, PersistentDataType.STRING)) {
            String locationString = persistentDataContainer.get(KEY, PersistentDataType.STRING);
            Location location = LocationUtil.stringToLocation(locationString);
            List<String> loreList = new ArrayList<>();
            loreList.add(TextUtil.colorRandomString("记录的坐标"));
            loreList.add(TextUtil.colorRandomString("world= " + location.getWorld().getName()));
            loreList.add(TextUtil.colorRandomString("x= " + String.format("%.2f", location.getX())));
            loreList.add(TextUtil.colorRandomString("y= " + String.format("%.2f", location.getY())));
            loreList.add(TextUtil.colorRandomString("z= " + String.format("%.2f", location.getZ())));
            itemMeta.setLore(loreList);
            item.setItemMeta(itemMeta);
            return true;
        } else {
            return false;
        }
    }

    @Nonnull
    public static Location getCenterLocation(@Nonnull Block block) {
        Location location = block.getLocation();
        location.setX(location.getBlockX() + 0.5);
        location.setY(location.getBlockY() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);
        return location;
    }

    @Nonnull
    public static Set<Location> parseLocation(@Nonnull Block... blocks) {
        Set<Location> locationSet = new HashSet<>(blocks.length);
        for(Block block : blocks) {
            locationSet.add(block.getLocation());
        }
        return locationSet;
    }

    @Nonnull
    public static Set<Location> parseLocation(@Nonnull List<Block> blocks) {
        Set<Location> locationSet = new HashSet<>(blocks.size());
        for(Block block : blocks) {
            locationSet.add(block.getLocation());
        }
        return locationSet;
    }

    @Nullable
    public static Location stringToLocation(@Nonnull String locationString) {
        World world = null;
        Double x = null;
        Double y = null;
        Double z = null;
        Float pitch = null;
        Float yaw = null;
        for (String value : locationString.split(";")) {
            if (value.startsWith("world")) {
                world = FinalTech.getInstance().getServer().getWorld(value.substring("world".length() + 1));
            }
            if (value.startsWith("pitch")) {
                pitch = Float.parseFloat(value.substring("pitch".length() + 1));
                continue;
            }
            if (value.startsWith("yaw")) {
                yaw = Float.parseFloat(value.substring("yaw".length() + 1));
                continue;
            }
            if (value.startsWith("x")) {
                x = Double.parseDouble(value.substring("x".length() + 1));
                continue;
            }
            if (value.startsWith("y")) {
                y = Double.parseDouble(value.substring("y".length() + 1));
                continue;
            }
            if (value.startsWith("z")) {
                z = Double.parseDouble(value.substring("z".length() + 1));
                continue;
            }
        }
        if (world != null && x != null && y != null && z != null) {
            if (yaw != null && pitch != null) {
                return new Location(world, x, y, z, yaw, pitch);
            }
            return new Location(world, x, y, z);
        }
        return null;
    }

    @Nonnull
    public static String locationToString(@Nonnull Location location) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("world:");
        stringBuilder.append(location.getWorld().getName());
        stringBuilder.append(";");

        stringBuilder.append("x:");
        stringBuilder.append(location.getX());
        stringBuilder.append(";");

        stringBuilder.append("y:");
        stringBuilder.append(location.getY());
        stringBuilder.append(";");

        stringBuilder.append("z:");
        stringBuilder.append(location.getZ());
        stringBuilder.append(";");

        stringBuilder.append("pitch:");
        stringBuilder.append(location.getPitch());
        stringBuilder.append(";");

        stringBuilder.append("yaw:");
        stringBuilder.append(location.getYaw());
        stringBuilder.append(";");

        return stringBuilder.toString();
    }
}
