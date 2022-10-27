package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.unusable.ItemPhony;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.MatrixReactorMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixReactor extends AbstractMachine implements RecipeItem {
    private final String KEY_ITEM = "item";
    private final String KEY_COUNT = "count";
    private final int difficulty = ConfigUtil.getOrDefaultItemSetting(72, this, "difficulty");

    public MatrixReactor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return MachineUtil.simpleBlockBreakerHandler(this, MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new MatrixReactorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Location location = block.getLocation();
        ItemStack item = blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_INPUT_SLOT[0]);

        if (ItemStackUtil.isItemNull(item)) {
            BlockStorage.addBlockInfo(location, KEY_ITEM, null);
            BlockStorage.addBlockInfo(location, KEY_COUNT, "0");
            if (blockMenu.hasViewer()) {
                this.updateMenu(blockMenu, config);
            }
            return;
        } else if (!this.allowedItem(item)) {
            BlockStorage.addBlockInfo(location, KEY_ITEM, null);
            BlockStorage.addBlockInfo(location, KEY_COUNT, "0");
            if (blockMenu.hasViewer()) {
                this.updateMenu(blockMenu, config);
            }
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> blockMenu.dropItems(location, MatrixReactorMenu.ITEM_INPUT_SLOT));
            return;
        }

        ItemStack stringItem = null;
        if (config.contains(KEY_ITEM)) {
            String itemString = config.getString(KEY_ITEM);
            stringItem = ItemStackUtil.stringToItemStack(itemString);
        }

        boolean match = true;

        int[] orderedDustItemSlots = new int[MatrixReactorMenu.ORDERED_DUST_INPUT_SLOT.length];
        int[] unorderedDustItemSlots = new int[MatrixReactorMenu.UNORDERED_DUST_INPUT_SLOT.length];

        int amount = item.getAmount();
        int orderedDustItemSlotsP = 0;
        int orderedDustItemCount = 0;
        int unorderedDustItemSlotsP = 0;
        int unorderedDustItemCount = 0;
        for (int slot : MatrixReactorMenu.ORDERED_DUST_INPUT_SLOT) {
            if (ItemStackUtil.isItemSimilar(blockMenu.getItemInSlot(slot), FinalTechItems.ORDERED_DUST)) {
                orderedDustItemSlots[orderedDustItemSlotsP++] = slot;
                orderedDustItemCount += blockMenu.getItemInSlot(slot).getAmount();
                if (orderedDustItemCount > amount) {
                    break;
                }
            }
        }
        if (orderedDustItemCount >= amount) {
            for (int slot : MatrixReactorMenu.UNORDERED_DUST_INPUT_SLOT) {
                if (ItemStackUtil.isItemSimilar(blockMenu.getItemInSlot(slot), FinalTechItems.UNORDERED_DUST)) {
                    unorderedDustItemSlots[unorderedDustItemSlotsP++] = slot;
                    unorderedDustItemCount += blockMenu.getItemInSlot(slot).getAmount();
                    if (unorderedDustItemCount > amount) {
                        break;
                    }
                }
            }
        }
        if (orderedDustItemCount < amount || unorderedDustItemCount < amount) {
            match = false;
        }

        if (!match) {
            int count = config.contains(KEY_COUNT) ? Integer.parseInt(config.getString(KEY_COUNT)) : 0;
            count = count > 0 ? count - 1 : 0;
            BlockStorage.addBlockInfo(location, KEY_COUNT, String.valueOf(count));
        } else  {
            orderedDustItemCount = amount;
            for (int slot : orderedDustItemSlots) {
                ItemStack itemStack = blockMenu.getItemInSlot(slot);
                int n = Math.min(itemStack.getAmount(), orderedDustItemCount);
                itemStack.setAmount(itemStack.getAmount() - n);
                orderedDustItemCount -= n;
                if (orderedDustItemCount == 0) {
                    break;
                }
            }

            unorderedDustItemCount = amount;
            for (int slot : unorderedDustItemSlots) {
                ItemStack itemStack = blockMenu.getItemInSlot(slot);
                int n = Math.min(itemStack.getAmount(), unorderedDustItemCount);
                itemStack.setAmount(itemStack.getAmount() - n);
                unorderedDustItemCount -= n;
                if (unorderedDustItemCount == 0) {
                    break;
                }
            }

            if (ItemStackUtil.isItemNull(stringItem) || !ItemStackUtil.isItemSimilar(item, stringItem) || item.getAmount() != stringItem.getAmount()) {
                BlockStorage.addBlockInfo(location, KEY_ITEM, ItemStackUtil.itemStackToString(item));

                int count;
                if (ItemPhony.isValid(blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]))) {
                    ItemStack itemPhony = blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]);
                    itemPhony.setAmount(itemPhony.getAmount() - 1);
                    count = 1;
                } else {
                    count = FinalTech.getRandom().nextBoolean() ? 1 : 0;
                }

                BlockStorage.addBlockInfo(location, KEY_COUNT, String.valueOf(count));
            } else {
                int count = config.contains(KEY_COUNT) ? Integer.parseInt(config.getString(KEY_COUNT)) : 0;
                if (ItemPhony.isValid(blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0])) && blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]).getAmount() >= amount + count && amount + count <= ConstantTableUtil.ITEM_MAX_STACK) {
                    ItemStack itemPhony = blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]);
                    itemPhony.setAmount(itemPhony.getAmount() - count - amount);
                    count++;
                } else {
                    count = FinalTech.getRandom().nextBoolean() ? count - 1 : count + 1;
                }

                if (count + item.getAmount() >= this.difficulty) {
                    ItemStack existedItem = blockMenu.getItemInSlot(this.getOutputSlot()[0]);
                    if (ItemStackUtil.isItemNull(existedItem)) {
                        ItemStack outputItem = ItemStackUtil.cloneItem(item);
                        outputItem.setAmount(1);
                        blockMenu.replaceExistingItem(this.getOutputSlot()[0], outputItem);
                        BlockStorage.addBlockInfo(location, KEY_ITEM, null);
                        BlockStorage.addBlockInfo(location, KEY_COUNT, "0");
                        if (blockMenu.hasViewer()) {
                            this.updateMenu(blockMenu, config);
                        }
                        return;
                    } else if (existedItem.getAmount() < existedItem.getMaxStackSize() && ItemStackUtil.isItemSimilar(existedItem, item)) {
                        existedItem.setAmount(existedItem.getAmount() + 1);
                        BlockStorage.addBlockInfo(location, KEY_ITEM, null);
                        BlockStorage.addBlockInfo(location, KEY_COUNT, "0");
                        if (blockMenu.hasViewer()) {
                            this.updateMenu(blockMenu, config);
                        }
                        return;
                    }
                    count = count < this.difficulty ? count + 1 : this.difficulty;
                }

                count = Math.max(count, 0);
                BlockStorage.addBlockInfo(location, KEY_COUNT, String.valueOf(count));
            }
        }

        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, config);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Config config) {
        ItemStack item = blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_INPUT_SLOT[0]);
        ItemStack iconItem = blockMenu.getItemInSlot(MatrixReactorMenu.STATUS_SLOT);
        if (ItemStackUtil.isItemNull(item)) {
            ItemStackUtil.setLore(iconItem, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                    "0",
                    String.valueOf(this.difficulty)));
        } else {
            String count = config.contains(this.KEY_COUNT) ? config.getString(this.KEY_COUNT) : "0";
            ItemStackUtil.setLore(iconItem, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                    count,
                    String.valueOf(this.difficulty - item.getAmount())));
        }
    }

    private boolean allowedItem(@Nonnull ItemStack item) {
        if (Tag.SHULKER_BOXES.isTagged(item.getType()) || Material.BUNDLE.equals(item.getType())) {
            return false;
        }
        if (item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            if (persistentDataContainer.getKeys().size() > 0) {
                for (NamespacedKey namespacedKey : persistentDataContainer.getKeys()) {
                    if (!"slimefun".equals(namespacedKey.getNamespace())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.difficulty),
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }
}
