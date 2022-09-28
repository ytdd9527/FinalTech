package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.ParticleUtil;
import io.taraxacum.finaltech.util.slimefun.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MachineConfigurator extends UsableSlimefunItem implements RecipeItem {
    private final Set<String> allowedItemId = new HashSet<>(ConfigUtil.getItemStringList(this, "allowed-item-id"));
    private final Map<String, Set<String>> ignoreInfoMap;

    public MachineConfigurator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.ignoreInfoMap = new HashMap<>(this.allowedItemId.size());
        for(String itemId : this.allowedItemId) {
            this.ignoreInfoMap.put(itemId, new HashSet<>(ConfigUtil.getItemStringList(this, "allowed-item-id", itemId)));
            this.ignoreInfoMap.get(itemId).add(ConstantTableUtil.CONFIG_ID);
            this.ignoreInfoMap.get(itemId).add("energy-charge");
        }
    }

    /**
     * The function the item will do
     * while a player hold the item and right click.
     *
     * @param playerRightClickEvent
     */
    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        Optional<Block> clickedBlock = playerRightClickEvent.getClickedBlock();
        if(clickedBlock.isPresent()) {
            Block block = clickedBlock.get();
            Location location = block.getLocation();
            if(PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), location, Interaction.BREAK_BLOCK, Interaction.INTERACT_BLOCK, Interaction.PLACE_BLOCK)) {
                if(BlockStorage.hasBlockInfo(location)) {
                    Config config = BlockStorage.getLocationInfo(location);
                    if(config.contains(ConstantTableUtil.CONFIG_ID)) {
                        String itemId = config.getString(ConstantTableUtil.CONFIG_ID);
                        SlimefunItem slimefunItem = SlimefunItem.getById(itemId);
                        if(this.allowedItemId.contains(config.getString(ConstantTableUtil.CONFIG_ID)) && slimefunItem != null && !slimefunItem.isDisabled()) {
                            if(playerRightClickEvent.getPlayer().isSneaking()) {
                                // save data

                                ItemStack item = playerRightClickEvent.getItem();
                                if(item.hasItemMeta()) {
                                    ItemMeta itemMeta = item.getItemMeta();
                                    PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
                                    Set<String> ignoreInfoSet = this.ignoreInfoMap.get(itemId);

                                    for(String key : config.getKeys()) {
                                        if(!ignoreInfoSet.contains(key)) {
                                            persistentDataContainer.set(new NamespacedKey(this.addon.getJavaPlugin(), key), PersistentDataType.STRING, config.getString(key));
                                        }
                                    }
                                    item.setItemMeta(itemMeta);
                                    ItemStackUtil.setLore(item, slimefunItem.getItemName());

                                    ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
                                }
                            } else {
                                // load data

                                ItemStack item = playerRightClickEvent.getItem();
                                if(item.hasItemMeta()) {
                                    ItemMeta itemMeta = item.getItemMeta();
                                    PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
                                    Set<String> ignoreInfoSet = this.ignoreInfoMap.get(itemId);

                                    for(NamespacedKey namespacedKey : persistentDataContainer.getKeys()) {
                                        String key = namespacedKey.getKey();
                                        if(!ignoreInfoSet.contains(key)) {
                                            String value = persistentDataContainer.get(namespacedKey, PersistentDataType.STRING);
                                            config.setValue(key, value);
                                        }
                                    }

                                    ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
