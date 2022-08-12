package io.taraxacum.finaltech.core.items.machine.range.point;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EquivalentConcept extends AbstractPointMachine {
    public static final String KEY_LIFE = "l";
    public static final String KEY_RANGE = "r";
    public static final String KEY_SLEEP = "t";
    public static final double ATTENUATION_RATE = FinalTech.getValueManager().getOrDefault(0.95, "items", SlimefunUtil.getIdFormatName(EquivalentConcept.class), "attenuation-rate");
    public static final double LIFE = FinalTech.getValueManager().getOrDefault(8.0, "items", SlimefunUtil.getIdFormatName(EquivalentConcept.class), "average-random");
    public static final int RANGE = FinalTech.getValueManager().getOrDefault(2, "items", SlimefunUtil.getIdFormatName(EquivalentConcept.class), "average-range");

    public EquivalentConcept(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return null;
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        double life = LIFE;
        if(config.contains(KEY_LIFE)) {
            life = Double.parseDouble(config.getString(KEY_LIFE));
        }
        if(life < 1) {
            Location blockLocation = block.getLocation();
            Slimefun.getBlockDataService().setBlockData(block, FinalTechItems.JUSTIFIABILITY.getItemId());
            BlockStorage.addBlockInfo(blockLocation, SlimefunUtil.KEY_ID, FinalTechItems.JUSTIFIABILITY.getItemId(), true);
            BlockStorage.addBlockInfo(blockLocation, KEY_SLEEP, null);
            BlockStorage.addBlockInfo(blockLocation, KEY_RANGE, null);
            BlockStorage.addBlockInfo(blockLocation, KEY_LIFE, null);
            return;
        }
        if(config.contains(KEY_SLEEP)) {
            int time = Integer.parseInt(config.getString(KEY_SLEEP));
            if(time-- > 0) {
                config.setValue(KEY_SLEEP, String.valueOf(time));
                return;
            }
        }
        int range = RANGE;
        if(config.contains(KEY_RANGE)) {
            range = Integer.parseInt(config.getString(KEY_RANGE));
        }
        int count = 0;
        double r = life;
        while (r > 1) {
            count++;
            final double finalRandom = r--;
            final int finalRange = range;
            this.function(block, range, location -> {
                FinalTech.getLocationRunnableFactory().waitThenRun(() -> {
                    Block targetBlock = location.getBlock();
                    if(!BlockStorage.hasBlockInfo(location)) {
                        if(targetBlock.getType().isAir()) {
                            Slimefun.getBlockDataService().setBlockData(targetBlock, EquivalentConcept.this.getId());
                            BlockStorage.addBlockInfo(location, SlimefunUtil.KEY_ID, EquivalentConcept.this.getId(), true);
                            BlockStorage.addBlockInfo(location, KEY_LIFE, String.valueOf(finalRandom * ATTENUATION_RATE));
                            BlockStorage.addBlockInfo(location, KEY_RANGE, String.valueOf(finalRange + 1));
                            BlockStorage.addBlockInfo(location, KEY_SLEEP, String.valueOf((int) finalRandom));
                            JavaPlugin javaPlugin = EquivalentConcept.this.getAddon().getJavaPlugin();
                            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> targetBlock.setType(EquivalentConcept.this.getItem().getType()));
                        }
                    }
                }, location);
                return 0;
            });
        }
        if(count > 0) {
            BlockStorage.addBlockInfo(block, KEY_LIFE, String.valueOf(0));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    protected Location getTargetLocation(@Nonnull Location location, int range) {
        int y = location.getBlockY() - range + new Random().nextInt(range + range);
        y = Math.min(location.getWorld().getMaxHeight(), y);
        y = Math.max(location.getWorld().getMinHeight(), y);
        return new Location(location.getWorld(), location.getX() - range + new Random().nextInt(range + range), y, location.getZ() - range + new Random().nextInt(range + range));
    }
}
