package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LocationRecorder extends UsableSlimefunItem {
    private static final String KEY = "location";
    private static final NamespacedKey NAMESPACED_KEY = new NamespacedKey(JavaPlugin.getPlugin(FinalTech.class), KEY);
    private static final String DEFAULT_LORE = TextUtil.COLOR_NEGATIVE + "未记录坐标";

    public LocationRecorder(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        PlayerInteractEvent interactEvent = playerRightClickEvent.getInteractEvent();
        Block block = interactEvent.getClickedBlock();
        if(playerRightClickEvent.getPlayer().isSneaking()) {
            if(block != null && SlimefunUtil.hasPermission(playerRightClickEvent.getPlayer(), block.getLocation(), Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                LocationRecorder.setLocation(playerRightClickEvent.getItem(), block.getLocation());
            }
        } else {
            ItemStack item = playerRightClickEvent.getItem();
            ItemMeta itemMeta = item.getItemMeta();
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            if(persistentDataContainer.has(NAMESPACED_KEY, PersistentDataType.STRING)) {
                String locationString = persistentDataContainer.get(NAMESPACED_KEY, PersistentDataType.STRING);
                Bukkit.getLogger().info(locationString);
                Location targetLocation = LocationUtil.stringToLocation(locationString);
                Player player = playerRightClickEvent.getPlayer();
                if(SlimefunUtil.hasPermission(playerRightClickEvent.getPlayer(), targetLocation, Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                    Block targetBlock = targetLocation.getBlock();
                    if(BlockStorage.hasInventory(targetBlock)) {
                        player.openInventory(BlockStorage.getInventory(targetBlock).toInventory());
                    } else {
                        Slimefun.runSync(() -> {
                            if(PaperLib.getBlockState(targetBlock, false).getState() instanceof InventoryHolder) {
                                InventoryView inventoryView = player.openInventory(((InventoryHolder) PaperLib.getBlockState(targetBlock, false).getState()).getInventory());
                            }
                        });
                    }
                } else {
                    player.sendRawMessage("您似乎没有在此处使用该物品的权限");
                }
            }
        }
    }

    public static void setLocation(@Nonnull ItemStack item, @Nullable Location location) {
        ItemMeta itemMeta = item.getItemMeta();
        if(location != null) {
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            persistentDataContainer.set(NAMESPACED_KEY, PersistentDataType.STRING, LocationUtil.locationToString(location));
            List<String> loreList = new ArrayList<>();
            loreList.add(TextUtil.colorPseudorandomString("记录的坐标"));
            loreList.add(TextUtil.colorPseudorandomString("world= " + location.getWorld().getName()));
            loreList.add(TextUtil.colorPseudorandomString("x= " + String.format("%.2f", location.getX())));
            loreList.add(TextUtil.colorPseudorandomString("y= " + String.format("%.2f", location.getY())));
            loreList.add(TextUtil.colorPseudorandomString("z= " + String.format("%.2f", location.getZ())));
            itemMeta.setLore(loreList);

        }
        if(location == null) {
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            persistentDataContainer.remove(NAMESPACED_KEY);
            List<String> loreList = new ArrayList<>();
            loreList.add(DEFAULT_LORE);
            itemMeta.setLore(loreList);
        }
        item.setItemMeta(itemMeta);
    }

    @Nullable
    public static Location getLocation(@Nonnull ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if(persistentDataContainer.has(NAMESPACED_KEY, PersistentDataType.STRING)) {
            String locationString = persistentDataContainer.get(NAMESPACED_KEY, PersistentDataType.STRING);
            return LocationUtil.stringToLocation(locationString);
        } else {
            return null;
        }
    }
}
