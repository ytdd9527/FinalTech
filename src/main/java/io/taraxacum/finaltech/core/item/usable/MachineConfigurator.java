package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.common.util.ReflectionUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.finaltech.util.ItemConfigurationUtil;
import io.taraxacum.finaltech.util.PermissionUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MachineConfigurator extends UsableSlimefunItem implements RecipeItem {
    public MachineConfigurator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     * The function the item will do
     * while a player hold the item and right click.
     *
     * @param playerRightClickEvent
     */
    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        Optional<Block> clickedBlock = playerRightClickEvent.getClickedBlock();
        if (clickedBlock.isPresent()) {
            Block block = clickedBlock.get();
            Location location = block.getLocation();
            if (PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), location, Interaction.BREAK_BLOCK, Interaction.INTERACT_BLOCK, Interaction.PLACE_BLOCK) && BlockStorage.hasBlockInfo(location)) {
                ItemStack item = playerRightClickEvent.getItem();
                Config config = BlockStorage.getLocationInfo(location);
                String itemId = config.getString(ConstantTableUtil.CONFIG_ID);
                if (playerRightClickEvent.getPlayer().isSneaking()) {
                    // save data

                    SlimefunItem slimefunItem = SlimefunItem.getById(itemId);
                    if(slimefunItem != null && ItemConfigurationUtil.saveConfigToItem(item, config)) {
                        ItemStackUtil.setLore(item, slimefunItem.getItemName());

                        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.GLOW, 0, block));
                    }
                } else {
                    // load data

                    if(ItemConfigurationUtil.loadConfigFromItem(item, location)) {
                        ItemConfigurationUtil.extraSaveFunction(BlockStorage.getInventory(block), itemId);

                        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.GLOW, 0, block));
                    }
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        for(Map.Entry<String, Set<String>> entry : ItemConfigurationUtil.getGroupItemMap().entrySet()) {
            this.registerDescriptiveRecipe(Icon.BORDER_ICON);
            for(String id : entry.getValue()) {
                SlimefunItem slimefunItem = SlimefunItem.getById(id);
                if (slimefunItem != null) {
                    this.registerDescriptiveRecipe(slimefunItem.getItem());
                }
            }
            this.registerDescriptiveRecipe(Icon.BORDER_ICON);
        }
    }
}
