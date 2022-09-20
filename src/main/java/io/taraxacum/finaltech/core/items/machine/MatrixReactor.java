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
import io.taraxacum.finaltech.util.TextUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import io.taraxacum.finaltech.util.slimefun.SfItemUtil;
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

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixReactor extends AbstractMachine implements RecipeItem {
    private final String KEY_ITEM = "item";
    private final String KEY_COUNT = "count";
    private final int DIFFICULTY = ConfigUtil.getOrDefaultItemSetting(72, this, "difficulty");

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
        ItemStack item = blockMenu.getItemInSlot(MatrixReactorMenu.OTHER_ITEM_INPUT_SLOT[0]);

        if (ItemStackUtil.isItemNull(item)) {
            BlockStorage.addBlockInfo(location, KEY_ITEM, null);
            BlockStorage.addBlockInfo(location, KEY_COUNT, "0");
            this.updateMenu(blockMenu, config);
            return;
        } else if (!this.allowedItem(item)) {
            BlockStorage.addBlockInfo(location, KEY_ITEM, null);
            BlockStorage.addBlockInfo(location, KEY_COUNT, "0");
            this.updateMenu(blockMenu, config);
            Slimefun.runSync(() -> blockMenu.dropItems(location, MatrixReactorMenu.OTHER_ITEM_INPUT_SLOT));
            return;
        }

        ItemStack stringItem = null;
        if (config.contains(KEY_ITEM)) {
            String itemString = config.getString(KEY_ITEM);
            stringItem = ItemStackUtil.stringToItemStack(itemString);
        }

        if (ItemStackUtil.isItemNull(stringItem) || !ItemStackUtil.isItemSimilar(item, stringItem)) {
            ItemStack orderedDustItem = null;
            ItemStack unorderedDustItem = null;
            for (int slot : MatrixReactorMenu.ORDERED_DUST_INPUT_SLOT) {
                if (ItemStackUtil.isItemSimilar(blockMenu.getItemInSlot(slot), FinalTechItems.ORDERED_DUST)) {
                    orderedDustItem = blockMenu.getItemInSlot(slot);
                    break;
                }
            }
            for (int slot : MatrixReactorMenu.UNORDERED_DUST_INPUT_SLOT) {
                if (ItemStackUtil.isItemSimilar(blockMenu.getItemInSlot(slot), FinalTechItems.UNORDERED_DUST)) {
                    unorderedDustItem = blockMenu.getItemInSlot(slot);
                    break;
                }
            }

            if (ItemStackUtil.isItemNull(orderedDustItem) || ItemStackUtil.isItemNull(unorderedDustItem)) {
                BlockStorage.addBlockInfo(location, KEY_ITEM, null);
                BlockStorage.addBlockInfo(location, KEY_COUNT, "0");
            } else {
                orderedDustItem.setAmount(orderedDustItem.getAmount() - 1);
                unorderedDustItem.setAmount(unorderedDustItem.getAmount() - 1);
                BlockStorage.addBlockInfo(location, KEY_ITEM, ItemStackUtil.itemStackToString(item));

                int count;
                if (ItemPhony.isValid(blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]))) {
                    ItemStack itemPhony = blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]);
                    itemPhony.setAmount(itemPhony.getAmount() - 1);
                    count = 0;
                } else {
                    count = FinalTech.getRandom().nextBoolean() ? 1 : 0;
                }

                BlockStorage.addBlockInfo(location, KEY_COUNT, String.valueOf(count));
            }
            this.updateMenu(blockMenu, config);
        } else {
            ItemStack orderedDustItem = null;
            ItemStack unorderedDustItem = null;
            for (int slot : MatrixReactorMenu.ORDERED_DUST_INPUT_SLOT) {
                if (ItemStackUtil.isItemSimilar(blockMenu.getItemInSlot(slot), FinalTechItems.ORDERED_DUST)) {
                    orderedDustItem = blockMenu.getItemInSlot(slot);
                    break;
                }
            }
            for (int slot : MatrixReactorMenu.UNORDERED_DUST_INPUT_SLOT) {
                if (ItemStackUtil.isItemSimilar(blockMenu.getItemInSlot(slot), FinalTechItems.UNORDERED_DUST)) {
                    unorderedDustItem = blockMenu.getItemInSlot(slot);
                    break;
                }
            }

            int count = config.contains(KEY_COUNT) ? Integer.parseInt(config.getString(KEY_COUNT)) : 0;
            if (ItemStackUtil.isItemNull(orderedDustItem) || ItemStackUtil.isItemNull(unorderedDustItem)) {
                count = count > 0 ? count - 1 : 0;
                BlockStorage.addBlockInfo(location, KEY_COUNT, String.valueOf(count));
            } else {
                orderedDustItem.setAmount(orderedDustItem.getAmount() - 1);
                unorderedDustItem.setAmount(unorderedDustItem.getAmount() - 1);
                if (ItemPhony.isValid(blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]))) {
                    ItemStack itemPhony = blockMenu.getItemInSlot(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]);
                    itemPhony.setAmount(itemPhony.getAmount() - 1);
                    count++;
                } else {
                    count = FinalTech.getRandom().nextBoolean() ? count - 1 : count + 1;
                }
                if (count + item.getAmount() >= DIFFICULTY) {
                    ItemStack existedItem = blockMenu.getItemInSlot(this.getOutputSlot()[0]);
                    if (ItemStackUtil.isItemNull(existedItem)) {
                        ItemStack outputItem = ItemStackUtil.cloneItem(item);
                        outputItem.setAmount(1);
                        blockMenu.replaceExistingItem(this.getOutputSlot()[0], outputItem);
                        BlockStorage.addBlockInfo(location, KEY_ITEM, null);
                        BlockStorage.addBlockInfo(location, KEY_COUNT, "0");
                        this.updateMenu(blockMenu, config);
                        return;
                    } else if (existedItem.getAmount() < existedItem.getMaxStackSize() && ItemStackUtil.isItemSimilar(existedItem, item)) {
                        existedItem.setAmount(existedItem.getAmount() + 1);
                        BlockStorage.addBlockInfo(location, KEY_ITEM, null);
                        BlockStorage.addBlockInfo(location, KEY_COUNT, "0");
                        this.updateMenu(blockMenu, config);
                        return;
                    }
                    count = count < DIFFICULTY ? count + 1 : DIFFICULTY;
                }
                count = Math.max(count, 0);
                BlockStorage.addBlockInfo(location, KEY_COUNT, String.valueOf(count));
                this.updateMenu(blockMenu, config);
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Config config) {
        ItemStack item = blockMenu.getItemInSlot(MatrixReactorMenu.OTHER_ITEM_INPUT_SLOT[0]);
        ItemStack iconItem = blockMenu.getItemInSlot(MatrixReactorMenu.STATUS_SLOT);
        if (ItemStackUtil.isItemNull(item)) {
            ItemStackUtil.setLore(iconItem, FinalTech.getLanguageManager().replaceStringArray(FinalTech.getLanguageManager().getStringArray("items", SfItemUtil.getIdFormatName(this.getClass())),
                    String.valueOf(DIFFICULTY - item.getAmount())));
        } else {
            String count = config.contains(this.KEY_COUNT) ? config.getString(this.KEY_COUNT) : "0";
            ItemStackUtil.setLore(iconItem, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                    count,
                    String.valueOf(DIFFICULTY - item.getAmount())));
        }
    }

    private boolean allowedItem(@Nonnull ItemStack item) {
        if(Tag.SHULKER_BOXES.isTagged(item.getType()) || Material.BUNDLE.equals(item.getType())) {
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
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0),
                String.valueOf(this.DIFFICULTY));
    }
}
