package io.taraxacum.finaltech.util;

import io.taraxacum.common.util.JavaUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import javax.annotation.Nonnull;

public class ParticleUtil {

    public static void drawLineByTotalAmount(@Nonnull Particle particle, int totalAmount, @Nonnull Location location1, @Nonnull Location location2) {
        if(totalAmount < 1 || location1.getWorld() == null || location1.getWorld() != location2.getWorld()) {
            return;
        }
        World world = location1.getWorld();
        double[] x = JavaUtil.disperse(totalAmount, location1.getX(), location2.getX());
        double[] y = JavaUtil.disperse(totalAmount, location1.getY(), location2.getY());
        double[] z = JavaUtil.disperse(totalAmount, location1.getZ(), location2.getZ());
        for(int i = 0; i < totalAmount; i++) {
            world.spawnParticle(particle, x[i], y[i], z[i], 1);
        }
    }

    public static void drawLineByTotalAmount(@Nonnull Particle particle, int totalAmount, @Nonnull Location... locations) {
        for(int i = 0; i < locations.length; i++) {
            if((i + 1) < locations.length) {
                ParticleUtil.drawLineByTotalAmount(particle, totalAmount, locations[i], locations[i + 1]);
            }
        }
    }

    public static void drawLineByDistance(@Nonnull Particle particle, int distance, @Nonnull Location location1, @Nonnull Location location2) {
        if(distance == 0 || location1.getWorld() == null || location1.getWorld() != location2.getWorld()) {
            return;
        }
        World world = location1.getWorld();
        double x = location1.getX();
        double y = location1.getY();
        double z = location1.getZ();

        double d = location1.distance(location2);
        double px = (location2.getX() - location1.getX()) * (d / distance);
        double py = (location2.getY() - location1.getY()) * (d / distance);
        double pz = (location2.getZ() - location1.getZ()) * (d / distance);

        for(int i = 0; i < d / distance; i++) {
            world.spawnParticle(particle, x, y, z, 1);
            x += px;
            y += py;
            z += pz;
        }
    }

    public static void drawLineByDistance(@Nonnull Particle particle, int distance, @Nonnull Location... locations) {
        for(int i = 0; i < locations.length; i++) {
            if((i + 1) < locations.length) {
                ParticleUtil.drawLineByDistance(particle, distance, locations[i], locations[i + 1]);
            }
        }
    }
}
