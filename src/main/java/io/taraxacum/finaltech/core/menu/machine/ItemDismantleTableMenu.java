package io.taraxacum.finaltech.core.menu.machine;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.item.machine.manual.ItemDismantleTable;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.LanguageManager;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ItemDismantleTableMenu extends AbstractManualMachineMenu {
    private static final int[] BORDER = new int[] {3, 4, 12, 21, 22};
    private static final int[] INPUT_BORDER = new int[] {0, 1, 2, 9, 11, 18, 19, 20};
    private static final int[] OUTPUT_BORDER = new int[] {5, 14, 23};
    private static final int[] INPUT_SLOT = new int[] {10};
    private static final int[] OUTPUT_SLOT = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26};
    private static final int STATUS_SLOT = 13;

    public ItemDismantleTableMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    @Override
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }

    @Override
    public void init() {
        super.init();
        this.addItem(STATUS_SLOT, Icon.STATUS_ICON);
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);

        blockMenu.addMenuClickHandler(STATUS_SLOT, (player, slot, itemStack, action) -> {
            Config config = BlockStorage.getLocationInfo(block.getLocation());
            if(config.contains(ItemDismantleTable.KEY) && StringNumberUtil.compare(config.getString(ItemDismantleTable.KEY), ItemDismantleTable.COUNT) >= 0) {
                if (MachineUtil.isEmpty(blockMenu.toInventory(), ItemDismantleTableMenu.this.getOutputSlot())) {
                    ItemStack item = blockMenu.getItemInSlot(ItemDismantleTableMenu.this.getInputSlot()[0]);
                    SlimefunItem sfItem = SlimefunItem.getByItem(item);
                    if (sfItem != null && !ItemDismantleTable.NOT_ALLOWED_ID.contains(sfItem.getId()) && sfItem.getRecipeType().getMachine() != null && item.getAmount() >= sfItem.getRecipeOutput().getAmount() && sfItem.getRecipe().length <= ItemDismantleTableMenu.this.getOutputSlot().length && ItemDismantleTable.ALLOWED_RECIPE_TYPE.contains(sfItem.getRecipeType().getKey().getKey()) && ItemStackUtil.isEnchantmentSame(item, sfItem.getRecipeOutput()) && ItemStackUtil.isItemSimilar(item, sfItem.getRecipeOutput())) {
                        int amount = item.getAmount() / sfItem.getRecipeOutput().getAmount();
                        for (ItemStack outputItem : sfItem.getRecipe()) {
                            if (!ItemStackUtil.isItemNull(outputItem)) {
                                amount = Math.min(amount, outputItem.getMaxStackSize() / outputItem.getAmount());
                            }
                        }
                        item.setAmount(item.getAmount() - sfItem.getRecipeOutput().getAmount() * amount);
                        for (int i = 0; i < ItemDismantleTableMenu.this.getOutputSlot().length && i < sfItem.getRecipe().length; i++) {
                            if (!ItemStackUtil.isItemNull(sfItem.getRecipe()[i])) {
                                ItemStack outputItem = ItemStackUtil.cloneItem(sfItem.getRecipe()[i]);
                                ItemStack liquidCard = RecipeUtil.getLiquidCard(outputItem);
                                if (liquidCard != null) {
                                    outputItem = liquidCard;
                                }
                                outputItem.setAmount(outputItem.getAmount() * amount);
                                blockMenu.replaceExistingItem(ItemDismantleTableMenu.this.getOutputSlot()[i], outputItem);
                            }
                        }

                        config.setValue(ItemDismantleTable.KEY, StringNumberUtil.sub(config.getString(ItemDismantleTable.KEY), ItemDismantleTable.COUNT));
                    }
                }
            }

            return false;
        });
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        Config config = BlockStorage.getLocationInfo(location);

        ItemStack item = inventory.getItem(STATUS_SLOT);
        if(!ItemStackUtil.isItemNull(item)) {
            String count = config.contains(ItemDismantleTable.KEY) ? config.getString(ItemDismantleTable.KEY) : StringNumberUtil.ZERO;

            LanguageManager languageManager = FinalTech.getLanguageManager();
            ItemStackUtil.setLore(item, languageManager.replaceStringList(languageManager.getStringList("items", this.getID(), "status-icon", "lore"),
                    count,
                    ItemDismantleTable.COUNT));

            if(StringNumberUtil.compare(count, ItemDismantleTable.COUNT) >= 0) {
                item.setType(Material.GREEN_STAINED_GLASS_PANE);
            } else {
                item.setType(Material.RED_STAINED_GLASS_PANE);
            }
        }

    }
}
