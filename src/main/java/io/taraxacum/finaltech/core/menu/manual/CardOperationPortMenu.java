package io.taraxacum.finaltech.core.menu.manual;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.items.machine.manual.CardOperationTable;
import io.taraxacum.finaltech.core.items.unusable.*;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import io.taraxacum.finaltech.util.slimefun.SfItemUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class CardOperationPortMenu extends AbstractManualMachineMenu {
    private static final int[] BORDER = new int[] {3, 4, 5, 12, 14, 21, 22, 23, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[] {0, 1, 2, 6, 7, 8, 9, 11, 15, 17, 18, 19, 20, 24, 25, 26};
    private static final int[] OUTPUT_BORDER = new int[] {30, 31, 32, 39, 41, 48, 49, 50};
    private static final int[] INPUT_SLOT = new int[] {10, 16};
    private static final int[] OUTPUT_SLOT = new int[] {40};
    private static final int CRAFT_SLOT = 13;
    private final ItemStack CRAFT_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            ConfigUtil.getStatusMenuName(FinalTech.getLanguageManager(), this.getID()),
            ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this.getID()));

    private static final List<Craft> CRAFT_LIST = new ArrayList<>();
    {
        CRAFT_LIST.add(new Craft() {
            @Override
            public boolean canCraft(@Nullable ItemStack item1, @Nullable ItemStack item2) {
                if (!ItemStackUtil.isItemNull(item1) && !ItemStackUtil.isItemNull(item2) && StorageCardItem.isValid(item1) && StorageCardItem.isValid(item2)) {
                    ItemStack stringItem1 = StringItemUtil.parseItemInCard(item1);
                    ItemStack stringItem2 = StringItemUtil.parseItemInCard(item2);
                    return ItemStackUtil.isItemSimilar(stringItem1, stringItem2);
                }
                return false;
            }

            @Override
            public void doUpdateIcon(@Nonnull ItemStack iconItem) {
                iconItem.setType(Material.GREEN_STAINED_GLASS_PANE);
                ItemStackUtil.setLore(iconItem, FinalTech.getLanguageStringArray("items", CardOperationPortMenu.this.getID(), "action1-icon", "lore"));
            }

            @Override
            public boolean craft(@Nullable ItemStack item1, @Nullable ItemStack item2, @Nonnull Inventory inventory, int outputSlot) {
                if (!ItemStackUtil.isItemNull(item1) && !ItemStackUtil.isItemNull(item2) && item1.hasItemMeta() && item2.hasItemMeta()) {
                    ItemMeta itemMeta1 = item1.getItemMeta();
                    ItemMeta itemMeta2 = item2.getItemMeta();
                    if (StorageCardItem.isValid(itemMeta1) && StorageCardItem.isValid(itemMeta2)) {
                        ItemStack stringItem1 = StringItemUtil.parseItemInCard(itemMeta1);
                        ItemStack stringItem2 = StringItemUtil.parseItemInCard(itemMeta2);
                        if (ItemStackUtil.isItemSimilar(stringItem1, stringItem2)) {
                            String amount1 = StringItemUtil.parseAmountInCard(itemMeta1);
                            String amount2 = StringItemUtil.parseAmountInCard(itemMeta2);
                            ItemStack outputItem = StorageCardItem.newItem();
                            StringItemUtil.setItemInCard(outputItem, stringItem1, StringNumberUtil.add(amount1, amount2));
                            StorageCardItem.updateLore(outputItem);
                            item1.setAmount(item1.getAmount() - 1);
                            item2.setAmount(item2.getAmount() - 1);
                            inventory.setItem(outputSlot, outputItem);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        CRAFT_LIST.add(new Craft() {
            @Override
            public boolean canCraft(@Nullable ItemStack item1, @Nullable ItemStack item2) {
                if (!ItemStackUtil.isItemNull(item1) && CopyCardItem.isValid(item1) && Shell.isValid(item2)) {
                    return true;
                } else return !ItemStackUtil.isItemNull(item2) && CopyCardItem.isValid(item2) && Shell.isValid(item1);
            }

            @Override
            public void doUpdateIcon(@Nonnull ItemStack iconItem) {
                iconItem.setType(Material.GREEN_STAINED_GLASS_PANE);
                ItemStackUtil.setLore(iconItem, FinalTech.getLanguageStringArray("items", CardOperationPortMenu.this.getID(), "action2-icon", "lore"));
            }

            @Override
            public boolean craft(@Nullable ItemStack item1, @Nullable ItemStack item2, @Nonnull Inventory inventory, int outputSlot) {
                if (!ItemStackUtil.isItemNull(item1) && CopyCardItem.isValid(item1) && Shell.isValid(item2)) {
                    item2.setAmount(item2.getAmount() - 1);
                    ItemStack outputItem = ItemStackUtil.cloneItem(item1);
                    outputItem.setAmount(1);
                    inventory.setItem(outputSlot, outputItem);
                } else if (!ItemStackUtil.isItemNull(item2) && CopyCardItem.isValid(item2) && Shell.isValid(item1)) {
                    item1.setAmount(item1.getAmount() - 1);
                    ItemStack outputItem = ItemStackUtil.cloneItem(item2);
                    outputItem.setAmount(1);
                    inventory.setItem(outputSlot, outputItem);
                    return true;
                }
                return false;
            }
        });
        CRAFT_LIST.add(new Craft() {
            @Override
            public boolean canCraft(@Nullable ItemStack item1, @Nullable ItemStack item2) {
                if (!ItemStackUtil.isItemNull(item1) && StorageCardItem.isValid(item1) && StringNumberUtil.compare(StringItemUtil.parseAmountInCard(item1), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT)) >= 0) {
                    return true;
                } else if (!ItemStackUtil.isItemNull(item2) && StorageCardItem.isValid(item2) && StringNumberUtil.compare(StringItemUtil.parseAmountInCard(item2), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT)) >= 0) {
                    return true;
                }
                return false;
            }

            @Override
            public void doUpdateIcon(@Nonnull ItemStack iconItem) {
                iconItem.setType(Material.GREEN_STAINED_GLASS_PANE);
                ItemStackUtil.setLore(iconItem, FinalTech.getLanguageStringArray("items", CardOperationPortMenu.this.getID(), "action3-icon", "lore"));
            }

            @Override
            public boolean craft(@Nullable ItemStack item1, @Nullable ItemStack item2, @Nonnull Inventory inventory, int outputSlot) {
                ItemStack storageCardItem = null;
                ItemMeta storageCardItemMeta = null;
                if (!ItemStackUtil.isItemNull(item1) && StorageCardItem.isValid(item1) && StringNumberUtil.compare(StringItemUtil.parseAmountInCard(item1), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT)) >= 0) {
                    storageCardItem = item1;
                    storageCardItemMeta = item1.getItemMeta();
                } else if (!ItemStackUtil.isItemNull(item2) && StorageCardItem.isValid(item2) && StringNumberUtil.compare(StringItemUtil.parseAmountInCard(item2), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT)) >= 0) {
                    storageCardItem = item2;
                    storageCardItemMeta = item2.getItemMeta();
                } else {
                    return false;
                }
                if (storageCardItemMeta != null) {
                    Player player = null;
                    for (HumanEntity humanEntity : inventory.getViewers()) {
                        if (humanEntity instanceof Player) {
                            player = (Player) humanEntity;
                            break;
                        }
                    }
                    ItemStack outputItem = Annular.newItem(storageCardItem, player);
                    outputItem.setAmount(1);
                    StringItemUtil.setAmountInCard(storageCardItem, StringNumberUtil.sub(StringItemUtil.parseAmountInCard(storageCardItemMeta), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT)));
                    StorageCardItem.updateLore(storageCardItem);
                    inventory.setItem(outputSlot, outputItem);
                    return true;
                }
                return false;
            }
        });
        CRAFT_LIST.add(new Craft() {
            @Override
            public boolean canCraft(@Nullable ItemStack item1, @Nullable ItemStack item2) {
                if (Singularity.isValid(item1) && Spirochete.isValid(item2)) {
                    return true;
                } else return Spirochete.isValid(item1) && Singularity.isValid(item2);
            }

            @Override
            public void doUpdateIcon(@Nonnull ItemStack iconItem) {
                iconItem.setType(Material.GREEN_STAINED_GLASS_PANE);
                ItemStackUtil.setLore(iconItem, FinalTech.getLanguageStringArray("items", CardOperationPortMenu.this.getID(), "action4-icon", "lore"));
            }

            @Override
            public boolean craft(@Nullable ItemStack item1, @Nullable ItemStack item2, @Nonnull Inventory inventory, int outputSlot) {
                if (this.canCraft(item1, item2)) {
                    item1.setAmount(item1.getAmount() - 1);
                    item2.setAmount(item2.getAmount() - 1);
                    Player player = null;
                    for (HumanEntity humanEntity : inventory.getViewers()) {
                        if (humanEntity instanceof Player) {
                            player = (Player) humanEntity;
                            break;
                        }
                    }
                    inventory.setItem(outputSlot, ItemPhony.newItem(item1, item2, player));
                    return true;
                }
                return false;
            }
        });
        CRAFT_LIST.add(new Craft() {
            @Override
            public boolean canCraft(@Nullable ItemStack item1, @Nullable ItemStack item2) {
                if (Singularity.isValid(item1) || Singularity.isValid(item2) || Spirochete.isValid(item1) || Spirochete.isValid(item2)) {
                    return true;
                }
                return false;
            }

            @Override
            public void doUpdateIcon(@Nonnull ItemStack iconItem) {
                iconItem.setType(Material.GREEN_STAINED_GLASS_PANE);
                ItemStackUtil.setLore(iconItem, FinalTech.getLanguageStringArray("items", CardOperationPortMenu.this.getID(), "action5-icon", "lore"));
            }

            @Override
            public boolean craft(@Nullable ItemStack item1, @Nullable ItemStack item2, @Nonnull Inventory inventory, int outputSlot) {
                if (Singularity.isValid(item1) || Spirochete.isValid(item1)) {
                    item1.setAmount(item1.getAmount() - 1);
                    Player player = null;
                    for (HumanEntity humanEntity : inventory.getViewers()) {
                        if (humanEntity instanceof Player) {
                            player = (Player) humanEntity;
                            break;
                        }
                    }
                    ItemStack outputItem = Shell.newItem(item1, player);
                    inventory.setItem(outputSlot, outputItem);
                } else if (Singularity.isValid(item2) || Spirochete.isValid(item2)) {
                    item2.setAmount(item2.getAmount() - 1);
                    Player player = null;
                    for (HumanEntity humanEntity : inventory.getViewers()) {
                        if (humanEntity instanceof Player) {
                            player = (Player) humanEntity;
                            break;
                        }
                    }
                    ItemStack outputItem = Shell.newItem(item2, player);
                    inventory.setItem(outputSlot, outputItem);
                }
                return false;
            }
        });
        CRAFT_LIST.add(new Craft() {
            @Override
            public boolean canCraft(@Nullable ItemStack item1, @Nullable ItemStack item2) {
                return CopyCardItem.isValid(item1) || CopyCardItem.isValid(item2);
            }

            @Override
            public void doUpdateIcon(@Nonnull ItemStack iconItem) {
                iconItem.setType(Material.GREEN_STAINED_GLASS_PANE);
                ItemStackUtil.setLore(iconItem, FinalTech.getLanguageStringArray("items", CardOperationPortMenu.this.getID(), "action6-icon", "lore"));
            }

            @Override
            public boolean craft(@Nullable ItemStack item1, @Nullable ItemStack item2, @Nonnull Inventory inventory, int outputSlot) {
                if (CopyCardItem.isValid(item1)) {
                    item1.setAmount(item1.getAmount() - 1);
                    Player player = null;
                    for (HumanEntity humanEntity : inventory.getViewers()) {
                        if (humanEntity instanceof Player) {
                            player = (Player) humanEntity;
                            break;
                        }
                    }
                    inventory.setItem(outputSlot, Annular.newItem(item1, player));
                } else if (CopyCardItem.isValid(item2)) {
                    item2.setAmount(item2.getAmount() - 1);
                    Player player = null;
                    for (HumanEntity humanEntity : inventory.getViewers()) {
                        if (humanEntity instanceof Player) {
                            player = (Player) humanEntity;
                            break;
                        }
                    }
                    inventory.setItem(outputSlot, Annular.newItem(item2, player));
                }
                return false;
            }
        });
    }

    public CardOperationPortMenu(@Nonnull AbstractMachine machine) {
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
        this.addItem(CRAFT_SLOT, CRAFT_ICON);
        this.addMenuClickHandler(CRAFT_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(CRAFT_SLOT, ((player, i, itemStack, clickAction) -> {
            CardOperationPortMenu.this.doFunction(blockMenu.toInventory());
            return false;
        }));
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        if(!ItemStackUtil.isItemNull(inventory.getItem(this.getOutputSlot()[0]))) {
            inventory.setItem(CRAFT_SLOT, CRAFT_ICON);
            return;
        }
        ItemStack inputItem1 = inventory.getItem(INPUT_SLOT[0]);
        ItemStack inputItem2 = inventory.getItem(INPUT_SLOT[1]);
        ItemStack iconItem = inventory.getItem(CRAFT_SLOT);
        boolean work = false;
        for (Craft craft : CRAFT_LIST) {
            if (craft.canCraft(inputItem1, inputItem2)) {
                craft.doUpdateIcon(iconItem);
                work = true;
                break;
            }
        }
        if (!work) {
            inventory.setItem(CRAFT_SLOT, CRAFT_ICON);
        }
    }

    private void doFunction(@Nonnull Inventory inventory) {
        if(!ItemStackUtil.isItemNull(inventory.getItem(this.getOutputSlot()[0]))) {
            inventory.setItem(CRAFT_SLOT, CRAFT_ICON);
            return;
        }
        ItemStack inputItem1 = inventory.getItem(INPUT_SLOT[0]);
        ItemStack inputItem2 = inventory.getItem(INPUT_SLOT[1]);
        if (ItemStackUtil.isItemNull(inputItem1) && ItemStackUtil.isItemNull(inputItem2)) {
            return;
        }
        for (Craft craft : CRAFT_LIST) {
            if (craft.craft(inputItem1, inputItem2, inventory, OUTPUT_SLOT[0])) {
                break;
            }
        }
    }

    private interface Craft {
        boolean canCraft(@Nullable ItemStack item1, @Nullable ItemStack item2);

        void doUpdateIcon(@Nonnull ItemStack iconItem);

        boolean craft(@Nullable ItemStack item1, @Nullable ItemStack item2, @Nonnull Inventory inventory, int outputSlot);
    }
}
