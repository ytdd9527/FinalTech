package io.taraxacum.finaltech.core.items.machine.range.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.LocationWithConfig;
import io.taraxacum.finaltech.api.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.unusable.ItemPhony;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixAccelerator extends AbstractCubeMachine implements AntiAccelerationMachine, RecipeItem {
    private final int capacity = FinalTech.getValueManager().getOrDefault(Integer.MAX_VALUE / 4, "items", SlimefunUtil.getIdFormatName(MatrixAccelerator.class), "capacity");
    public static final int RANGE = FinalTech.getValueManager().getOrDefault(2, "items", SlimefunUtil.getIdFormatName(MatrixAccelerator.class), "range");

    public MatrixAccelerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location blockLocation = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(blockLocation);
        ItemStack matchItem = blockMenu.getItemInSlot(this.getInputSlot()[0]);

        int accelerate = 0;
        int range = RANGE;

        SlimefunItem machineItem = null;
        String machineId = null;
        BlockTicker blockTicker = null;

        if (ItemStackUtil.isItemNull(matchItem)) {
            accelerate = 1;
        } else if (ItemPhony.isValid(matchItem)) {
            int amount = matchItem.getAmount();
            while (amount > 0) {
                accelerate++;
                range++;
                amount /= 2;
            }
        } else {
            machineItem = SlimefunItem.getByItem(matchItem);
            if (machineItem != null) {
                blockTicker = machineItem.getBlockTicker();
                if (blockTicker == null) {
                    this.updateMenu(blockMenu, 0, 0);
                    return;
                }
                machineId = machineItem.getId();
                accelerate = matchItem.getAmount();
            } else {
                this.updateMenu(blockMenu, 0, 0);
                return;
            }
        }

        Map<Integer, List<LocationWithConfig>> machineConfigMap = new HashMap<>(range * 3);
        String finalMachineId = machineId;
        int count = this.function(block, range, location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config machineConfig = BlockStorage.getLocationInfo(location);
                if (machineConfig.contains(SlimefunUtil.KEY_ID)) {
                    if (finalMachineId == null || finalMachineId.equals(machineConfig.getString(SlimefunUtil.KEY_ID))) {
                        int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                        List<LocationWithConfig> machineConfigList = machineConfigMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2));
                        machineConfigList.add(new LocationWithConfig(location.clone(), machineConfig));
                        return 1;
                    }
                }
            }
            return 0;
        });

        int accelerateTimeCount = 0;
        int accelerateMachineCount = 0;

        if (machineId != null) {
            if (count == 0) {
                this.updateMenu(blockMenu, 0, 0);
                return;
            }
            accelerate /= count;
        }

        List<LocationWithConfig> locationConfigList;
        Config machineConfig;
        Location machineLocation;
        Block machineBlock;
        for (int distance = 1; distance <= range * 3; distance++) {
            locationConfigList = machineConfigMap.get(distance);
            if (locationConfigList != null) {
                Collections.shuffle(locationConfigList);
                if (machineId != null) {
                    for (LocationWithConfig locationConfig : locationConfigList) {
                        machineConfig = locationConfig.getConfig();
                        machineLocation = locationConfig.getLocation();
                        machineBlock = machineLocation.getBlock();
                        for (int i = 0; i < accelerate; i++) {
                            if(FinalTech.getForceSlimefunMultiThread() && FinalTech.getMultiThreadLevel() >= 1) {
                                SlimefunUtil.runBlockTicker(FinalTech.getLocationRunnableFactory(), blockTicker, machineBlock, machineItem, machineConfig);
                            } else {
                                SlimefunUtil.runBlockTickerLocal(this.getAddon().getJavaPlugin(), blockTicker, machineBlock, machineItem, machineConfig);
                            }
                            accelerateTimeCount++;
                        }
                        accelerateMachineCount++;
                    }
                } else {
                    for (LocationWithConfig locationConfig : locationConfigList) {
                        machineConfig = locationConfig.getConfig();
                        machineLocation = locationConfig.getLocation();
                        machineId = machineConfig.getString(SlimefunUtil.KEY_ID);
                        machineItem = SlimefunItem.getById(machineId);
                        if(machineItem != null) {
                            blockTicker = machineItem.getBlockTicker();
                            machineBlock = machineLocation.getBlock();
                            for (int i = 0; i < accelerate; i++) {
                                if(FinalTech.getForceSlimefunMultiThread() && FinalTech.getMultiThreadLevel() >= 1) {
                                    SlimefunUtil.runBlockTicker(FinalTech.getLocationRunnableFactory(), blockTicker, machineBlock, machineItem, machineConfig);
                                } else {
                                    SlimefunUtil.runBlockTickerLocal(this.getAddon().getJavaPlugin(), blockTicker, machineBlock, machineItem, machineConfig);
                                }
                                accelerateTimeCount++;
                            }
                            accelerateMachineCount++;
                        }
                    }
                    machineId = null;
                }
            }
        }

        this.updateMenu(blockMenu, accelerateTimeCount, accelerateMachineCount);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int accelerateTimeCount, int accelerateMachineCount) {
        if(blockMenu.hasViewer()) {
            ItemStack item = blockMenu.getItemInSlot(StatusL2Menu.STATUS_SLOT);
            ItemStackUtil.setLore(item,
                    TextUtil.COLOR_NORMAL + "生效的机器= " + TextUtil.COLOR_NUMBER + accelerateMachineCount + "个",
                    TextUtil.COLOR_NORMAL + "生效的总次数= " + TextUtil.COLOR_NUMBER + accelerateTimeCount + "次");
        }
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "使周围的机器工作效率提升",
                TextUtil.COLOR_NORMAL + "并使其每次加速前补充 " + TextUtil.COLOR_NUMBER + "全部" + TextUtil.COLOR_NORMAL + " 的电量");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "定向加速",
                "",
                TextUtil.COLOR_NORMAL + "放入粘液科技机器对应的物品",
                TextUtil.COLOR_NORMAL + "使周围 " + TextUtil.COLOR_NUMBER + RANGE + "格" + TextUtil.COLOR_NORMAL + " 范围内的对应机器工作效率提升",
                TextUtil.COLOR_NORMAL + "单个机器的工作效率提升量 取决于",
                TextUtil.COLOR_NORMAL + "放入的物品数量 和 总共加速的机器数量");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "全量加速",
                "",
                TextUtil.COLOR_NORMAL + "放入 " + FinalTechItems.PHONY.getDisplayName(),
                TextUtil.COLOR_NORMAL + "使周围 " + TextUtil.COLOR_NUMBER + (RANGE + 1) + " + log2(物品数量)格" + TextUtil.COLOR_NORMAL + " 范围内的机器效率提升",
                TextUtil.COLOR_NORMAL + "效率提升量为 " + TextUtil.COLOR_NUMBER + "1 + log2(物品数量)次");
    }
}
