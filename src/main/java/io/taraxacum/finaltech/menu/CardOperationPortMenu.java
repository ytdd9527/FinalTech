package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.item.unusable.CopyCardItem;
import io.taraxacum.finaltech.item.unusable.StorageCardItem;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.util.menu.Icon;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class CardOperationPortMenu extends AbstractMachineMenu{
    private static final int[] BORDER = new int[] {3, 4, 5, 12, 14, 21, 22, 23, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[] {0, 1, 2, 6, 7, 8, 9, 11, 15, 17, 18, 19, 20, 24, 25, 26};
    private static final int[] OUTPUT_BORDER = new int[] {30, 31, 32, 39, 41, 48, 49, 50};
    private static final int[] INPUT_SLOT = new int[] {10, 16};
    private static final int[] OUTPUT_SLOT = new int[] {40};
    private static final int CRAFT_SLOT = 13;
    private static final ItemStack[] randomOutputStorageCardItem = new ItemStack[] {
            new ItemStack(FinalTechItems.STORAGE_ITEM_WHITE),
            new ItemStack(FinalTechItems.STORAGE_ITEM_ORANGE),
            new ItemStack(FinalTechItems.STORAGE_ITEM_MAGENTA),
            new ItemStack(FinalTechItems.STORAGE_ITEM_LIGHT_BLUE),
            new ItemStack(FinalTechItems.STORAGE_ITEM_YELLOW),
            new ItemStack(FinalTechItems.STORAGE_ITEM_LIME),
            new ItemStack(FinalTechItems.STORAGE_ITEM_PINK),
            new ItemStack(FinalTechItems.STORAGE_ITEM_GRAY),
            new ItemStack(FinalTechItems.STORAGE_ITEM_LIGHT_GRAY),
            new ItemStack(FinalTechItems.STORAGE_ITEM_CYAN),
            new ItemStack(FinalTechItems.STORAGE_ITEM_PURPLE),
            new ItemStack(FinalTechItems.STORAGE_ITEM_BLUE),
            new ItemStack(FinalTechItems.STORAGE_ITEM_BROWN),
            new ItemStack(FinalTechItems.STORAGE_ITEM_GREEN),
            new ItemStack(FinalTechItems.STORAGE_ITEM_RED),
            new ItemStack(FinalTechItems.STORAGE_ITEM_BLACK),
    };
    private static final ItemStack ERROR_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&c无法操作");
    private static final ItemStack MERGE_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&a合并复制卡");
    private static final ItemStack CRAFT_COPY_CARD_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&a制造复制卡");
    private static final ItemStack CRAFT_SHELL_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&a制造壳");
    private static final ItemStack COPY_COPY_CARD_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&a复制复制卡");

    public CardOperationPortMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
        super(id, title, machine);
    }

    @Override
    public int[] getBorder() {
        return BORDER;
    }

    @Override
    public int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    public int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOT;
    }

    @Override
    public void init() {
        super.init();
        this.addItem(CRAFT_SLOT, Icon.AUTO_CRAFT_PARSE_ICON);
        this.addMenuClickHandler(CRAFT_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(BlockMenu menu, Block block) {
        super.newInstance(menu, block);
        menu.addMenuClickHandler(CRAFT_SLOT, ((player, i, itemStack, clickAction) -> {
            if (!ItemStackUtil.isItemNull(menu.getItemInSlot(OUTPUT_SLOT[0])) || ItemStackUtil.isItemNull(menu.getItemInSlot(INPUT_SLOT[0])) || ItemStackUtil.isItemNull(menu.getItemInSlot(INPUT_SLOT[1]))) {
                return false;
            }
            ItemStack inputItem1 = menu.getItemInSlot(INPUT_SLOT[0]);
            ItemStack inputItem2 = menu.getItemInSlot(INPUT_SLOT[1]);
            ItemMeta itemMeta1 = inputItem1.getItemMeta();
            ItemMeta itemMeta2 = inputItem2.getItemMeta();

            boolean isStorageCardItem1 = StorageCardItem.isStorageCardItem(itemMeta1);
            boolean isStorageCardItem2 = StorageCardItem.isStorageCardItem(itemMeta2);
            if (isStorageCardItem1 && isStorageCardItem2) {
                ItemStack stringItem1 = StringItemUtil.parseItemInCard(itemMeta1);
                ItemStack stringItem2 = StringItemUtil.parseItemInCard(itemMeta2);
                boolean similar = ItemStackUtil.isItemSimilar(stringItem1, stringItem2);
                if (similar) {
                    String amount1 = StringItemUtil.parseAmountInCard(itemMeta1);
                    String amount2 = StringItemUtil.parseAmountInCard(itemMeta2);
                    ItemStack outputItem = new ItemStack(randomOutputStorageCardItem[(int)(Math.random() * randomOutputStorageCardItem.length)]);
                    StringItemUtil.setItemInCard(outputItem, stringItem1, StringNumberUtil.add(amount1, amount2));
                    StringItemUtil.updateStorageCardLore(outputItem);
                    StringItemUtil.updateStorageCardType(outputItem);
                    inputItem1.setAmount(inputItem1.getAmount() - 1);
                    inputItem2.setAmount(inputItem2.getAmount() - 1);
                    menu.replaceExistingItem(OUTPUT_SLOT[0], outputItem);
                }
                return false;
            } else if (isStorageCardItem1 || isStorageCardItem2) {
                boolean hasAllCompression = isStorageCardItem1 ? ItemStackUtil.isItemSimilar(inputItem2, FinalTechItems.ALL_COMPRESSION) : ItemStackUtil.isItemSimilar(inputItem1, FinalTechItems.ALL_COMPRESSION);
                if (hasAllCompression) {
                    String amount = StringItemUtil.parseAmountInCard(isStorageCardItem1 ? itemMeta1 : itemMeta2);
                    if (StringNumberUtil.easilyCompare(amount, String.valueOf(CopyCardItem.COPY_CARD_DIFFICULTY)) >= 0) {
                        ItemStack stringItem = StringItemUtil.parseItemInCard(isStorageCardItem1 ? itemMeta1 : itemMeta2);
                        ItemStack outputItem = CopyCardItem.newCopyCardItem(stringItem, "1");
                        StringItemUtil.setAmountInCard(isStorageCardItem1 ? inputItem1 : inputItem2, StringNumberUtil.sub(amount, String.valueOf(CopyCardItem.COPY_CARD_DIFFICULTY)));
                        StringItemUtil.updateStorageCardLore(isStorageCardItem1 ? inputItem1 : inputItem2);
                        StringItemUtil.updateStorageCardType(isStorageCardItem1 ? inputItem1 : inputItem2);
                        menu.replaceExistingItem(OUTPUT_SLOT[0], outputItem);
                    }
                }
                return false;
            }

            boolean isPartOfItemFake1 = ItemStackUtil.isItemSimilar(inputItem1, FinalTechItems.SINGULARITY) || ItemStackUtil.isItemSimilar(inputItem1, FinalTechItems.SPIROCHETE);
            boolean isPartOfItemFake2 = ItemStackUtil.isItemSimilar(inputItem2, FinalTechItems.SINGULARITY) || ItemStackUtil.isItemSimilar(inputItem2, FinalTechItems.SPIROCHETE);
            if (isPartOfItemFake1 || isPartOfItemFake2) {
                ItemStack outputItem = new ItemStack(FinalTechItems.SHELL);
                int amount = 0;
                if (isPartOfItemFake1) {
                    inputItem1.setAmount(inputItem1.getAmount() - 1);
                    amount++;
                }
                if (isPartOfItemFake2) {
                    inputItem2.setAmount(inputItem2.getAmount() - 1);
                    amount++;
                }
                outputItem.setAmount(amount);
                menu.replaceExistingItem(OUTPUT_SLOT[0], outputItem);
                return false;
            }

            boolean isCopyCardItem1 = CopyCardItem.isCopyCardItem(inputItem1);
            boolean isCopyCardItem2 = CopyCardItem.isCopyCardItem(inputItem2);
            boolean isShell1 = !isCopyCardItem1 && ItemStackUtil.isItemSimilar(inputItem1, FinalTechItems.SHELL);
            boolean isShell2 = !isCopyCardItem2 && ItemStackUtil.isItemSimilar(inputItem2, FinalTechItems.SHELL);
            if ((isCopyCardItem1 && isShell2) || (isCopyCardItem2 && isShell1)) {
                ItemStack outputItem;
                if (isCopyCardItem1) {
                    outputItem = new ItemStack(inputItem1);
                    inputItem2.setAmount(inputItem2.getAmount() - 1);
                } else {
                    outputItem = new ItemStack(inputItem2);
                    inputItem1.setAmount(inputItem1.getAmount() - 1);
                }
                outputItem.setAmount(1);
                menu.replaceExistingItem(OUTPUT_SLOT[0], outputItem);
                return false;
            }

            return false;
        }));
    }

    @Override
    public void updateMenu(BlockMenu menu, Block block) {
        if (!ItemStackUtil.isItemNull(menu.getItemInSlot(OUTPUT_SLOT[0])) || ItemStackUtil.isItemNull(menu.getItemInSlot(INPUT_SLOT[0])) || ItemStackUtil.isItemNull(menu.getItemInSlot(INPUT_SLOT[1]))) {
            menu.replaceExistingItem(CRAFT_SLOT, ERROR_ICON);
            return;
        }
        ItemStack inputItem1 = menu.getItemInSlot(INPUT_SLOT[0]);
        ItemStack inputItem2 = menu.getItemInSlot(INPUT_SLOT[1]);
        ItemMeta itemMeta1 = inputItem1.getItemMeta();
        ItemMeta itemMeta2 = inputItem2.getItemMeta();

        boolean isStorageCardItem1 = StorageCardItem.isStorageCardItem(itemMeta1);
        boolean isStorageCardItem2 = StorageCardItem.isStorageCardItem(itemMeta2);
        if (isStorageCardItem1 && isStorageCardItem2) {
            ItemStack stringItem1 = StringItemUtil.parseItemInCard(itemMeta1);
            ItemStack stringItem2 = StringItemUtil.parseItemInCard(itemMeta2);
            boolean similar = ItemStackUtil.isItemSimilar(stringItem1, stringItem2);
            if (similar) {
                menu.replaceExistingItem(CRAFT_SLOT, MERGE_ICON);
            }
            return;
        } else if (isStorageCardItem1 || isStorageCardItem2) {
            boolean hasAllCompression = isStorageCardItem1 ? ItemStackUtil.isItemSimilar(inputItem2, FinalTechItems.ALL_COMPRESSION) : ItemStackUtil.isItemSimilar(inputItem1, FinalTechItems.ALL_COMPRESSION);
            if (hasAllCompression) {
                String amount = StringItemUtil.parseAmountInCard(isStorageCardItem1 ? itemMeta1 : itemMeta2);
                if (StringNumberUtil.easilyCompare(amount, String.valueOf(CopyCardItem.COPY_CARD_DIFFICULTY)) >= 0) {
                    menu.replaceExistingItem(CRAFT_SLOT, CRAFT_COPY_CARD_ICON);
                }
            }
            return;
        }

        boolean isPartOfItemFake1 = ItemStackUtil.isItemSimilar(inputItem1, FinalTechItems.SINGULARITY) || ItemStackUtil.isItemSimilar(inputItem1, FinalTechItems.SPIROCHETE);
        boolean isPartOfItemFake2 = ItemStackUtil.isItemSimilar(inputItem2, FinalTechItems.SINGULARITY) || ItemStackUtil.isItemSimilar(inputItem2, FinalTechItems.SPIROCHETE);
        if (isPartOfItemFake1 || isPartOfItemFake2) {
            menu.replaceExistingItem(CRAFT_SLOT, CRAFT_SHELL_ICON);
            return;
        }

        boolean isCopyCardItem1 = CopyCardItem.isCopyCardItem(inputItem1);
        boolean isCopyCardItem2 = CopyCardItem.isCopyCardItem(inputItem2);
        boolean isShell1 = !isCopyCardItem1 && ItemStackUtil.isItemSimilar(inputItem1, FinalTechItems.SHELL);
        boolean isShell2 = !isCopyCardItem2 && ItemStackUtil.isItemSimilar(inputItem2, FinalTechItems.SHELL);
        if ((isCopyCardItem1 && isShell2) || (isCopyCardItem2 && isShell1)) {
            menu.replaceExistingItem(CRAFT_SLOT, COPY_COPY_CARD_ICON);
            return;
        }

        menu.replaceExistingItem(CRAFT_SLOT, ERROR_ICON);
    }
}
