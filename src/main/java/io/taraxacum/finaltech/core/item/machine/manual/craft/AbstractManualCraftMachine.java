package io.taraxacum.finaltech.core.item.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.machine.manual.AbstractManualMachine;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.ManualCraftMachineMenu;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractManualCraftMachine extends AbstractManualMachine implements RecipeItem, EnergyNetComponent {
    public static final String KEY_COUNT = "count";
    public static int COUNT_THRESHOLD = ConfigUtil.getOrDefaultItemSetting(Slimefun.getTickerTask().getTickRate() * 4, "ManualCraftMachine", "threshold");
    public static int CAPACITY = ConfigUtil.getOrDefaultItemSetting(9216, "ManualCraftMachine", "capacity");
    public static String CHARGE = String.valueOf(ConfigUtil.getOrDefaultItemSetting(1, "ManualCraftMachine", "charge"));

    public AbstractManualCraftMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), ManualCraftMachineMenu.KEY, "0");
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new ManualCraftMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu.hasViewer()) {
            this.getMachineMenu().updateInventory(blockMenu.toInventory(), block.getLocation());
        }

        String charge = EnergyUtil.getCharge(config);
        int intCharge = Integer.parseInt(charge) + 1;
        if(intCharge > CAPACITY / 2) {
            intCharge /= 2;
        }

        EnergyUtil.setCharge(block.getLocation(), String.valueOf(Math.min(intCharge, CAPACITY)));

        int count = Integer.parseInt(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT));
        if (count > 0) {
            count -= Slimefun.getTickerTask().getTickRate();
            count = Math.max(count, 0);
            config.setValue(KEY_COUNT, String.valueOf(count));

            if (count > COUNT_THRESHOLD) {
                Location location = block.getLocation();
                List<String> nameList = new ArrayList<>();
                for (HumanEntity humanEntity : blockMenu.toInventory().getViewers()) {
                    nameList.add(humanEntity.getName());
                }
                String warn = FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("items", "ManualCraftMachine", "warn"),
                        location.getWorld().getName(),
                        String.valueOf(location.getBlockX()),
                        String.valueOf(location.getBlockY()),
                        String.valueOf(location.getBlockZ()),
                        nameList.toString());
                this.getAddon().getJavaPlugin().getLogger().warning(warn);
            }
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
