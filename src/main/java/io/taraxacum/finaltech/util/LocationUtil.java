package io.taraxacum.finaltech.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class LocationUtil {


    public static Location stringToLocation(String locationString) {
        World world = null;
        Double x = null;
        Double y = null;
        Double z = null;
        Float pitch = null;
        Float yaw = null;
        for(String value : locationString.split(";")) {
            if(value.startsWith("world")) {
                world = Bukkit.getWorld(value.substring("world".length() + 1));
            }
            if(value.startsWith("pitch")) {
                pitch = Float.parseFloat(value.substring("pitch".length() + 1));
                continue;
            }
            if(value.startsWith("yaw")) {
                yaw = Float.parseFloat(value.substring("yaw".length() + 1));
                continue;
            }
            if(value.startsWith("x")) {
                x = Double.parseDouble(value.substring("x".length() + 1));
                continue;
            }
            if(value.startsWith("y")) {
                y = Double.parseDouble(value.substring("y".length() + 1));
                continue;
            }
            if(value.startsWith("z")) {
                z = Double.parseDouble(value.substring("z".length() + 1));
                continue;
            }
        }
        if(world != null && x != null && y != null && z != null) {
            if(yaw != null && pitch != null) {
                return new Location(world, x, y, z, yaw, pitch);
            }
            return new Location(world, x, y, z);
        }
        return null;
    }

    public static String locationToString(Location location) {
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
