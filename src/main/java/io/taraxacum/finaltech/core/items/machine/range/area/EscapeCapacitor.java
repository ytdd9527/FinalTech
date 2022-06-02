package io.taraxacum.finaltech.core.items.machine.range.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class EscapeCapacitor extends AbstractCubeMachine implements EnergyNetComponent, RecipeItem {
    public final static int RANGE = 4;
    public static final int CAPACITOR = Integer.MAX_VALUE / 2;
    public final static double LOSS = 16;

    public EscapeCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler();
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location blockLocation = block.getLocation();
        int charge = Integer.parseInt(SlimefunUtil.getCharge(config));
        int count = 0;
        AtomicInteger maxEnergy = new AtomicInteger();
        AtomicInteger totalEnergy = new AtomicInteger();
        int validCharge = (int)(charge / LOSS);
        if (charge > 0) {
            count = this.function(block, RANGE, location -> {
                if (BlockStorage.hasBlockInfo(location)) {
                    Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                    if (energyComponentConfig.contains(SlimefunUtil.KEY_ID)) {
                        SlimefunItem item = SlimefunItem.getById(energyComponentConfig.getString(SlimefunUtil.KEY_ID));
                        if (item instanceof EnergyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) item).getEnergyComponentType())) {
                            int componentCapacity = ((EnergyNetComponent) item).getCapacity();
                            if (componentCapacity == 0) {
                                return 0;
                            }
                            int componentEnergy = Integer.parseInt(SlimefunUtil.getCharge(energyComponentConfig));
                            int transferEnergy = Math.min(componentCapacity - componentEnergy, validCharge);
                            if (transferEnergy > 0) {
                                SlimefunUtil.setCharge(location, String.valueOf(transferEnergy + componentEnergy));
                                maxEnergy.set(Math.max(maxEnergy.get(), transferEnergy));
                                totalEnergy.addAndGet(transferEnergy);
                                return 1;
                            }
                        }
                    }
                }
                return 0;
            });
        }
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item,
                TextUtil.COLOR_NORMAL + "当前存储电量= " + TextUtil.COLOR_NUMBER + charge + "J",
                TextUtil.COLOR_NORMAL + "当前生效的机器= " + TextUtil.COLOR_NUMBER + count + "J",
                TextUtil.COLOR_NORMAL + "实际传输量= " + TextUtil.COLOR_NUMBER + totalEnergy + "J",
                TextUtil.COLOR_NORMAL + "单机器最大传输量= " + TextUtil.COLOR_NUMBER + maxEnergy + "J");
        charge = charge - (int) (maxEnergy.get() * LOSS);
        charge = Math.max(charge, 0);
        SlimefunUtil.setCharge(blockLocation, String.valueOf(charge));
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CAPACITOR;
    }

    @Override
    public int getCapacity() {
        return CAPACITOR;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "搜索范围内的机器",
                TextUtil.COLOR_NORMAL + "根据自身的存电量 使这些机器充电",
                "",
                TextUtil.COLOR_NORMAL + "每次为周围机器充电 自身存电量只会降低一次",
                TextUtil.COLOR_NORMAL + "降低数值为单个机器最大充电量的 " + TextUtil.COLOR_NUMBER + "16倍",
                "",
                TextUtil.COLOR_NORMAL + "可存储电量= " + TextUtil.COLOR_NUMBER + this.getCapacity() + "J");
    }
}
