package io.taraxacum.finaltech.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class MenuViewer extends UsableSlimefunItem {
    private static final int INSERT_SLOT_VALUE = 1;
    private static final int WITHDRAW_SLOT_VALUE = 2;
    private static final int INSERT_AND_WITHDRAW_SLOT_VALUE = INSERT_SLOT_VALUE + WITHDRAW_SLOT_VALUE;

    private static final ItemStack VOID_ICON = new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, "§7不可交互槽", " ");
    private static final ItemStack INPUT_ICON = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, "§9输入槽", " ");
    private static final ItemStack OUTPUT_ICON = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "§6输出槽", " ");
    private static final ItemStack INPUT_AND_OUTPUT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, "§d输入输出槽", " ");

    public MenuViewer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        Player player = playerRightClickEvent.getPlayer();
        playerRightClickEvent.cancel();
        if(!playerRightClickEvent.getClickedBlock().isEmpty()) {
            Location location = playerRightClickEvent.getClickedBlock().get().getLocation();
            BlockMenu blockMenu = BlockStorage.getInventory(location);
            if(blockMenu != null) {
                BlockMenuPreset preset = blockMenu.getPreset();
                ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
                int[] slotsAccessedByItemTransportInsert = preset.getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, itemInOffHand);
                int[] slotsAccessedByItemTransportWithdraw = preset.getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, itemInOffHand);
                int[] viewSlot = new int[blockMenu.getContents().length];
                for(int slot : slotsAccessedByItemTransportInsert) {
                    if(slot >= 0 && slot < viewSlot.length) {
                        viewSlot[slot] += INSERT_SLOT_VALUE;
                    }
                }
                for(int slot : slotsAccessedByItemTransportWithdraw) {
                    if(slot >= 0 && slot < viewSlot.length) {
                        viewSlot[slot] += WITHDRAW_SLOT_VALUE;
                    }
                }
                ChestMenu chestMenu = new ChestMenu(preset.getTitle());
                for(int slot = 0; slot < viewSlot.length; slot++) {
                    if (viewSlot[slot] == INSERT_SLOT_VALUE) {
                        chestMenu.addItem(slot, INPUT_ICON);
                    } else if (viewSlot[slot] == WITHDRAW_SLOT_VALUE) {
                        chestMenu.addItem(slot, OUTPUT_ICON);
                    } else if (viewSlot[slot] == INSERT_AND_WITHDRAW_SLOT_VALUE) {
                        chestMenu.addItem(slot, INPUT_AND_OUTPUT_ICON);
                    } else {
                        chestMenu.addItem(slot, VOID_ICON);
                    }
                    chestMenu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
                }
                chestMenu.open(player);
            }
        }
    }
}
