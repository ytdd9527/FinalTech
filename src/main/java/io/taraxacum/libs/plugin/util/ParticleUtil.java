package io.taraxacum.libs.plugin.util;

import io.taraxacum.common.util.JavaUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ParticleUtil {
    private static final double[] BLOCK_CUBE_OFFSET_X = new double[] {0, 1, 0, 0, 1, 1, 0, 1};
    private static final double[] BLOCK_CUBE_OFFSET_Y = new double[] {0, 0, 1, 0, 1, 0, 1, 1};
    private static final double[] BLOCK_CUBE_OFFSET_Z = new double[] {0, 0, 0, 1, 0, 1, 1, 1};

    public static void drawLineByTotalAmount(@Nonnull Particle particle, int totalAmount, @Nonnull Location... locations) {
        for (int i = 0; i < locations.length; i++) {
            if ((i + 1) < locations.length) {
                Location location1 = locations[i];
                Location location2 = locations[i + 1];

                if (totalAmount < 1 || location1.getWorld() == null || location1.getWorld() != location2.getWorld()) {
                    return;
                }
                World world = location1.getWorld();
                double[] x = JavaUtil.disperse(totalAmount, location1.getX(), location2.getX());
                double[] y = JavaUtil.disperse(totalAmount, location1.getY(), location2.getY());
                double[] z = JavaUtil.disperse(totalAmount, location1.getZ(), location2.getZ());
                for (int j = 0; j < totalAmount; j++) {
                    world.spawnParticle(particle, x[j], y[j], z[j], 1);
                }
            }
        }
    }
    public static void drawLineByTotalAmount(@Nonnull Particle particle, int totalAmount, @Nonnull List<Location> locationList) {
        Location[] locations = new Location[locationList.size()];
        for (int i = 0; i < locations.length; i++) {
            locations[i] = locationList.get(i);
        }
        ParticleUtil.drawLineByTotalAmount(particle, totalAmount, locations);
    }

    public static void drawLineByDistance(@Nonnull Plugin plugin, @Nonnull Particle particle, long interval, double distance, @Nonnull Location... locations) {
        int time = 0;
        for (int i = 0; i < locations.length; i++) {
            if ((i + 1) < locations.length) {
                Location location1 = locations[i];
                Location location2 = locations[i + 1];

                if (distance == 0 || location1.getWorld() == null || location1.getWorld() != location2.getWorld()) {
                    return;
                }
                World world = location1.getWorld();
                double x = location1.getX();
                double y = location1.getY();
                double z = location1.getZ();

                double d = location1.distance(location2);
                double px = (location2.getX() - location1.getX()) / (d / distance);
                double py = (location2.getY() - location1.getY()) / (d / distance);
                double pz = (location2.getZ() - location1.getZ()) / (d / distance);

                if(time < 50) {
                    for (int j = 0; j < d / distance; j++) {
                        world.spawnParticle(particle, x, y, z, 1);
                        x += px;
                        y += py;
                        z += pz;
                    }
                } else {
                    double fx = x;
                    double fy = y;
                    double fz = z;
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        double x1 = fx;
                        double y1 = fy;
                        double z1 = fz;
                        for (int j = 0; j < d / distance; j++) {
                            world.spawnParticle(particle, x1, y1, z1, 1);
                            x1 += px;
                            y1 += py;
                            z1 += pz;
                        }
                    }, time / 50);
                }
                time += interval;
            }
        }
    }
    public static void drawLineByDistance(@Nonnull Plugin plugin, @Nonnull Particle particle, long interval, double distance, @Nonnull List<Location> locationList) {
        Location[] locations = new Location[locationList.size()];
        for (int i = 0; i < locations.length; i++) {
                locations[i] = locationList.get(i);
        }
        ParticleUtil.drawLineByDistance(plugin, particle, interval, distance, locations);
    }

    public static void drawCubeByBlock(@Nonnull Plugin plugin, @Nonnull Particle particle, long interval, Block... blocks) {
        int time = 0;
        for (Block block : blocks) {
            Location location = block.getLocation();
            World world = location.getWorld();
            if (world == null) {
                continue;
            }
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            if(time < 50) {
                for (int i = 0; i < BLOCK_CUBE_OFFSET_X.length; i++) {
                    world.spawnParticle(particle, x + BLOCK_CUBE_OFFSET_X[i], y + BLOCK_CUBE_OFFSET_Y[i], z + BLOCK_CUBE_OFFSET_Z[i], 1);
                }
            } else {
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    for (int i = 0; i < BLOCK_CUBE_OFFSET_X.length; i++) {
                        world.spawnParticle(particle, x + BLOCK_CUBE_OFFSET_X[i], y + BLOCK_CUBE_OFFSET_Y[i], z + BLOCK_CUBE_OFFSET_Z[i], 1);
                    }
                }, time / 50);
            }
            time += interval;
        }
    }
    public static void drawCubeByBlock(@Nonnull Plugin plugin, @Nonnull Particle particle, long interval, List<Block> blockList) {
        Block[] blocks = new Block[blockList.size()];
        for (int i = 0; i < blockList.size(); i++) {
            blocks[i] = blockList.get(i);
        }
        ParticleUtil.drawCubeByBlock(plugin, particle, interval, blocks);
    }
}
