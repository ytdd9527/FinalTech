package io.taraxacum.finaltech.core.item.machine.range.line;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.ConfigurationWorkerMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class ConfigurationCopier extends AbstractLineMachine implements RecipeItem {
    private final int range = ConfigUtil.getOrDefaultItemSetting(16, this, "range");

    public ConfigurationCopier(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
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

    @Nullable
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ConfigurationWorkerMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);

        if(!ItemStackUtil.isItemNull(blockMenu.getItemInSlot(this.getOutputSlot()[0]))) {
            return;
        }

        int digit = 0;
        ItemStack digitalItemStack = blockMenu.getItemInSlot(ConfigurationWorkerMenu.DIGITAL_SLOT);
        SlimefunItem digitalItem = SlimefunItem.getByItem(digitalItemStack);
        if(digitalItem instanceof DigitalItem) {
            digit = ((DigitalItem) digitalItem).getDigit();
        }

        ItemStack itemConfigurator = blockMenu.getItemInSlot(this.getInputSlot()[0]);
        SlimefunItem machineConfigurator = SlimefunItem.getByItem(itemConfigurator);
        if(machineConfigurator == null || !FinalTechItems.MACHINE_CONFIGURATOR.getItemId().equals(machineConfigurator.getId())) {
            return;
        }

        BlockData blockData = block.getState().getBlockData();
        if(blockData instanceof Directional directional) {
            BlockFace blockFace = directional.getFacing();

            Block targetBlock = block;
            Location targetLocation = targetBlock.getLocation();
            boolean work = false;

            if(digit == 0) {
                for(int i = 0; i < this.range; i++) {
                    targetBlock = targetBlock.getRelative(blockFace);
                    targetLocation = targetBlock.getLocation();
                    if(BlockStorage.hasBlockInfo(targetLocation)) {
                        work = true;
                        break;
                    }
                }
            } else {
                targetBlock = block.getRelative(blockFace, digit);
                targetLocation = targetBlock.getLocation();
                if(BlockStorage.hasBlockInfo(targetLocation)) {
                    work = true;
                }
            }

            Config targetConfig = BlockStorage.getLocationInfo(targetLocation);
            String itemId = targetConfig.getString(ConstantTableUtil.CONFIG_ID);

            ItemStack outputItem = ItemStackUtil.cloneItem(itemConfigurator);
            outputItem.setAmount(1);
            if(work && itemId != null) {
                final Block finalTargetBlock = targetBlock;
                Runnable runnable = () -> {
                    if(ItemConfigurationUtil.saveConfigToItem(outputItem, targetConfig)) {
                        SlimefunItem targetItem = SlimefunItem.getById(itemId);
                        if(targetItem != null) {
                            ItemStackUtil.setLore(outputItem, targetItem.getItemName());
                        }

                        if (blockMenu.hasViewer()) {
                            JavaPlugin javaPlugin = ConfigurationCopier.this.getAddon().getJavaPlugin();
                            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.GLOW, 0, finalTargetBlock));
                        }
                    }
                    blockMenu.pushItem(outputItem, ConfigurationCopier.this.getOutputSlot()[0]);
                    itemConfigurator.setAmount(itemConfigurator.getAmount() - 1);
                };

                if(FinalTech.isAsyncSlimefunItem(itemId)) {
                    FinalTech.getLocationRunnableFactory().waitThenRun(runnable, location, targetLocation);
                } else {
                    runnable.run();
                }
            } else {
                blockMenu.pushItem(outputItem, this.getOutputSlot()[0]);
                itemConfigurator.setAmount(itemConfigurator.getAmount() - 1);
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
