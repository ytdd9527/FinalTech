package io.taraxacum.finaltech.core.items.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.machine.range.cube.AbstractCubeMachine;
import io.taraxacum.finaltech.core.items.unusable.StorageCardItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.common.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractCubeElectricGenerator extends AbstractCubeMachine implements RecipeItem {
    protected static final String KEY = "energy-charge";

    public AbstractCubeElectricGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_ALLOW;
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        String extraEnergy = this.getElectricity();
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item)) {
                String amount = String.valueOf(item.getAmount());
                if(StorageCardItem.isValid(item)) {
                    amount = StringItemUtil.parseAmountInCard(item);
                    item = StringItemUtil.parseItemInCard(item);
                }
                if(ItemStackUtil.isItemSimilar(item, this.getItem())) {
                    extraEnergy = StringNumberUtil.add(extraEnergy, StringNumberUtil.mul(this.getElectricity(), amount));
                }
            }
        }
        AtomicReference<String> energyCharge = new AtomicReference<>(StringNumberUtil.ZERO);
        String finalExtraEnergy = extraEnergy;
        int count = this.function(block, this.getRange(), location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                if (energyComponentConfig.contains(SlimefunUtil.KEY_ID)) {
                    SlimefunItem item = SlimefunItem.getById(energyComponentConfig.getString(SlimefunUtil.KEY_ID));
                    if (item instanceof EnergyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) item).getEnergyComponentType())) {
                        int componentCapacity = ((EnergyNetComponent) item).getCapacity();
                        if (componentCapacity == 0) {
                            return 0;
                        }
                        String componentEnergy = energyComponentConfig.contains(KEY) ? energyComponentConfig.getString(KEY) : StringNumberUtil.ZERO;
                        if (StringNumberUtil.compare(componentEnergy, String.valueOf(componentCapacity)) >= 0) {
                            return 0;
                        }
                        String transferEnergy = StringNumberUtil.min(StringNumberUtil.sub(String.valueOf(componentCapacity), componentEnergy), finalExtraEnergy);
                        if (StringNumberUtil.compare(transferEnergy, StringNumberUtil.ZERO) > 0) {
                            componentEnergy = StringNumberUtil.add(componentEnergy, transferEnergy);
                            energyCharge.set(StringNumberUtil.add(energyCharge.get(), transferEnergy));
                            SlimefunUtil.setCharge(energyComponentConfig, componentEnergy);
                            return 1;
                        }
                    }
                }
            }
            return 0;
        });
        blockMenu = BlockStorage.getInventory(block);
        if(blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, count, energyCharge.get());
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int count, @Nonnull String energyCharge) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item,
                TextUtil.COLOR_NORMAL + "当前生效的机器= " + TextUtil.COLOR_NUMBER + count + "个",
                TextUtil.COLOR_NORMAL + "实际发电量= " + TextUtil.COLOR_NUMBER + energyCharge + "J");
        ItemStackUtil.setLore(item, SlimefunUtil.updateMenuLore(FinalTech.getLanguageManager(), this,
                String.valueOf(count),
                energyCharge));
        if (count == 0) {
            item.setType(Material.RED_STAINED_GLASS_PANE);
        } else {
            item.setType(Material.GREEN_STAINED_GLASS_PANE);
        }
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.getElectricity()),
                String.valueOf(this.getRange()),
                String.valueOf(String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0)));
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "每 " + TextUtil.COLOR_NUMBER + String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0) + "秒" + TextUtil.COLOR_NORMAL + " 对周围 " + TextUtil.COLOR_NUMBER + this.getRange() + "格" + TextUtil.COLOR_NORMAL + " 的机器进行充电",
                TextUtil.COLOR_NORMAL + "充电量为 " + TextUtil.COLOR_NUMBER + this.getElectricity() + "J",
                TextUtil.COLOR_NEGATIVE + "无法作用于电容");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "可扩展",
                "",
                TextUtil.COLOR_NORMAL + "放入相同的机器物品",
                TextUtil.COLOR_NORMAL + "或放入存有相同机器物品的存储卡",
                TextUtil.COLOR_NORMAL + "根据物品数量 提升该机器的供电量");
    }

    protected abstract String getElectricity();

    protected abstract int getRange();
}
