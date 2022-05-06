package io.taraxacum.finaltech.items.machine.range.area.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.api.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.storage.StatusL2Menu;
import io.taraxacum.finaltech.menu.storage.StatusMenu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.common.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Final_ROOT
 */
public class MatrixGenerator extends AbstractCubeElectricGenerator implements AntiAccelerationMachine {
    protected static final String KEY = "energy-charge";

    public final static String ELECTRICITY = StringNumberUtil.VALUE_MAX;
    public final static int RANGE = 16;

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    public MatrixGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        World world = block.getWorld();
        int extraRange = 0;
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item) && ItemStackUtil.isItemSimilar(item, FinalTechItems.PHONY)) {
                int amount = item.getAmount() / 2;
                while (amount > 0) {
                    extraRange++;
                    amount /= 2;
                }
            }
        }
        AtomicReference<String> energyCharge = new AtomicReference<>(StringNumberUtil.ZERO);
        int count = this.function(block, extraRange + this.getRange(), location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                if (energyComponentConfig.contains(SlimefunUtil.KEY_ID)) {
                    String slimefunItemId = energyComponentConfig.getString(SlimefunUtil.KEY_ID);
                    if (slimefunItemId.equals(this.getId()) && !location.equals(block.getLocation())) {
                        //todo Event
                        Slimefun.runSync(() -> {
                            List<ItemStack> dropItemList = new ArrayList<>(this.getInputSlot().length + 1);
                            for (int slot : MatrixGenerator.this.getInputSlot()) {
                                ItemStack item = blockMenu.getItemInSlot(slot);
                                if (!ItemStackUtil.isItemNull(item)) {
                                    dropItemList.add(blockMenu.getItemInSlot(slot));
                                }
                            }
                            dropItemList.add(this.getItem());
                            block.setType(Material.AIR);
                            BlockStorage.clearBlockInfo(block.getLocation());
                            for (ItemStack item : dropItemList) {
                                block.getWorld().dropItem(block.getLocation(), item);
                            }
                        });
                        return -1;
                    }
                    SlimefunItem item = SlimefunItem.getById(energyComponentConfig.getString(SlimefunUtil.KEY_ID));
                    if (item instanceof EnergyNetComponent) {
                        int componentCapacity = ((EnergyNetComponent) item).getCapacity();
                        if (componentCapacity == 0) {
                            return 0;
                        }
                        String componentEnergy = energyComponentConfig.contains(KEY) ? energyComponentConfig.getString(KEY) : StringNumberUtil.ZERO;
                        if (StringNumberUtil.easilyCompare(componentEnergy, String.valueOf(componentCapacity)) >= 0) {
                            return 0;
                        }
                        String transferEnergy = StringNumberUtil.min(StringNumberUtil.sub(String.valueOf(componentCapacity), componentEnergy), this.getElectricity());
                        if (StringNumberUtil.easilyCompare(transferEnergy, StringNumberUtil.ZERO) > 0) {
                            componentEnergy = StringNumberUtil.add(componentEnergy, transferEnergy);
                            energyCharge.set(StringNumberUtil.add(energyCharge.get(), transferEnergy));
                            SlimefunUtil.setCharge(energyComponentConfig, componentEnergy);
                            world.spawnParticle(Particle.COMPOSTER, location.getX() + 0.5, location.getY() + 1, location.getZ() + 0.5, 1);
                            return 1;
                        }
                    }
                }
            }
            return 0;
        });
        //todo 说明优化
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item,
                "§7当前生效的机器= " + count,
                "§7实际发电量= " + energyCharge + "J");
        if (count == 0) {
            item.setType(Material.RED_STAINED_GLASS_PANE);
        } else {
            item.setType(Material.GREEN_STAINED_GLASS_PANE);
        }
    }

    @Override
    protected String getElectricity() {
        return ELECTRICITY;
    }

    @Override
    protected int getRange() {
        return RANGE;
    }

    @Override
    public void registerDefaultRecipes() {
        //todo 说明优化
        registerDescriptiveRecipe("&f供电量",
                "",
                "&f对范围内的每个机器",
                "&f使其存电量达到最大储电量");
        registerDescriptiveRecipe("&f供电范围",
                "",
                "&f传输半径=" + this.getRange() + "格",
                "&f即以自身为中心",
                "&f边长" + (this.getRange() * 2 + 1) + "格（含）的正方体区域");
    }
}
