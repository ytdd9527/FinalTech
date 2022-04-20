package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.manual.craft.AbstractCraftManualMachine;
import io.taraxacum.finaltech.dto.AdvancedCraftUtil;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public class ManualMachineMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {32, 39, 41, 48, 50};
    private static final int[] INPUT_SLOTS = new int[] {
            0,  1,  2,  3,  4,  5,  6,  7,  8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29,             33, 34, 35,
            36, 37, 38,             42, 43, 44,
            45, 46, 47,             51, 52, 53
    };
    private static final int[] OUTPUT_SLOTS = new int[] {
            27, 28, 29,             33, 34, 35,
            36, 37, 38,             42, 43, 44,
            45, 46, 47,             51, 52, 53,
             0,  1,  2,  3,  4,  5,  6,  7,  8,
             9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26
    };
    private static final int INFO_SLOT = 31;
    private static final int PREVIOUS_SLOT = 30;
    private static final int NEXT_SLOT = 32;
    private static final int STOCK_SLOT = 40;
    private static final int CRAFT_SLOT = 49;

    private static final ItemStack INFO_ICON = new CustomItemStack(Material.TARGET, "§f介绍",
            "",
            "§f点击合成即可进行合成",
            "",
            "§f目标合成产物的显示可能略有延迟",
            "§f这不会影响实际合成效果",
            "",
            "§f每隔约" + Slimefun.getTickerTask().getTickRate() + "秒会刷新一次目标合成产物");

    public static final String KEY = "offset";

    private List<MachineRecipe> machineRecipeList = new ArrayList<>();
    public ManualMachineMenu(@Nonnull String id, @Nonnull String title, AbstractCraftManualMachine machine) {
        super(id, title, machine);
    }

    @Override
    public void init() {
        super.init();
        this.addMenuClickHandler(INFO_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(PREVIOUS_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "§7上一个物品"));
        this.addMenuClickHandler(PREVIOUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(NEXT_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "§7下一个物品"));
        this.addMenuClickHandler(NEXT_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(STOCK_SLOT, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "§7点击堆叠",
                "§e左键 尝试堆叠物品"));
        this.addMenuClickHandler(STOCK_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(CRAFT_SLOT, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "§7点击合成",
                "§e左键 §b合成64次，但只会合成一种物品",
                "§e右键 §b合成64次，可能合成多种物品",
                "§eshift+左键 §b合成3456次，但只会合成一种物品",
                "§eshift+右键 §b合成3456次，可能合成多种物品"));
        this.addMenuClickHandler(CRAFT_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(BlockMenu blockMenu, Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuOpeningHandler((player -> {
            updateMenu(blockMenu, block);
        }));
        blockMenu.addMenuClickHandler(PREVIOUS_SLOT, ((player, i, itemStack, clickAction) -> {
            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            offset = Math.max(offset - 1, 0);
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(offset));
            updateMenu(blockMenu, block);
            return false;
        }));
        blockMenu.addMenuClickHandler(NEXT_SLOT, ((player, i, itemStack, clickAction) -> {
            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            offset = offset + 1;
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(offset));
            updateMenu(blockMenu, block);
            return false;
        }));
        blockMenu.addMenuClickHandler(STOCK_SLOT, ((player, i, itemStack, clickAction) -> {
            MachineUtil.stockSlots(blockMenu, INPUT_SLOTS);
            return false;
        }));
        blockMenu.addMenuClickHandler(CRAFT_SLOT, ((player, i, itemStack, clickAction) -> {
            Map<Integer, ItemStackWithWrapper> itemWithWrapperMap = new HashMap<>(INPUT_SLOTS.length);
            for (int slot : INPUT_SLOTS) {
                ItemStack item = blockMenu.getItemInSlot(slot);
                if (!ItemStackUtil.isItemNull(item)) {
                    itemWithWrapperMap.put(slot, new ItemStackWithWrapper(item));
                }
            }
            int amount = clickAction.isShiftClicked() ? (3456) : 64;
            int offsetAbs = 0;
            List<AdvancedCraftUtil> craftList = new ArrayList<>(this.machineRecipeList.size());
            AdvancedCraftUtil craft = null;
            for (int offset = 0, offsetValue = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY)); offset <= offsetValue; offset++) {
                craft = AdvancedCraftUtil.calCraft(blockMenu, INPUT_SLOTS, this.machineRecipeList, amount, offsetAbs);
                if (craft == null) {
                    updateMenu(blockMenu, block);
                    return false;
                }
                if (offsetAbs >= this.machineRecipeList.indexOf(craft.getMachineRecipe())) {
                    break;
                }
                offsetAbs = this.machineRecipeList.indexOf(craft.getMachineRecipe()) + 1;
                craftList.add(craft);
            }
            if (craft == null) {
                updateMenu(blockMenu, block);
                return false;
            }
            craft.setMatchCount(MachineUtil.maxMatch(blockMenu.toInventory(), OUTPUT_SLOTS, craft.getMachineRecipe().getOutput(), craft.getMatchCount()));
            if (craft.getMatchCount() > 0) {
                craft.consumeItem(blockMenu.toInventory());
                for (ItemStack outputItem : craft.calEnlargeMachineRecipe().getOutput()) {
                    blockMenu.pushItem(ItemStackUtil.cloneItem(outputItem), OUTPUT_SLOTS);
                }
                amount -= craft.getMatchCount();
            }
            if (clickAction.isRightClicked()) {
                while (amount > 0) {
                    craft = AdvancedCraftUtil.calCraft(blockMenu, INPUT_SLOTS, this.machineRecipeList, amount, offsetAbs);
                    if (craft == null) {
                        break;
                    }
                    craft.setMatchCount(MachineUtil.maxMatch(blockMenu.toInventory(), OUTPUT_SLOTS, craft.getMachineRecipe().getOutput(), craft.getMatchCount()));
                    if (craft.getMatchCount() > 0) {
                        craft.consumeItem(blockMenu.toInventory());
                        for (ItemStack outputItem : craft.calEnlargeMachineRecipe().getOutput()) {
                            blockMenu.pushItem(ItemStackUtil.cloneItem(outputItem), OUTPUT_SLOTS);
                        }
                        amount -= craft.getMatchCount();
                    } else {
                        break;
                    }
                    offsetAbs = (this.machineRecipeList.indexOf(craft.getMachineRecipe()) + 1) % this.machineRecipeList.size();
                }
            }
            updateMenu(blockMenu, block);
            return false;
        }));
    }

    @Override
    public int[] getBorder() {
        return BORDER;
    }

    @Override
    public int[] getInputBorder() {
        return new int[0];
    }

    @Override
    public int[] getOutputBorder() {
        return new int[0];
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public void updateMenu(BlockMenu blockMenu, Block block) {
        Config config = BlockStorage.getLocationInfo(block.getLocation());
        List<ItemStackWithWrapper> itemWithWrapperList = new ArrayList<>(INPUT_SLOTS.length);
        for (int slot : INPUT_SLOTS) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item)) {
                itemWithWrapperList.add(new ItemStackWithWrapper(item));
            }
        }
        int offset = 0;
        ItemStack showItem = INFO_ICON;
        int offsetValue = Integer.parseInt(config.getValue(KEY).toString());
        for (MachineRecipe recipe : this.machineRecipeList) {
            int find = 0;
            for (ItemStack recipeInputItem : recipe.getInput()) {
                ItemStackWithWrapper recipeInputItemWhitWrapper = new ItemStackWithWrapper(recipeInputItem);
                for (ItemStackWithWrapper itemWithWrapper : itemWithWrapperList) {
                    if (itemWithWrapper.getItemStack().getAmount() >= recipeInputItem.getAmount() && ItemStackUtil.isItemSimilar(itemWithWrapper.getItemStackWrapper(), recipeInputItemWhitWrapper.getItemStackWrapper())) {
                        find++;
                        break;
                    }
                }
            }
            if (find == recipe.getInput().length) {
                showItem = ItemStackUtil.cloneItem(recipe.getOutput()[0]);
                if (offset < offsetValue) {
                    offset++;
                    continue;
                }
                ItemStackUtil.addLoreToLast(showItem, "§8匹配的产物");
                blockMenu.replaceExistingItem(ManualMachineMenu.INFO_SLOT, showItem);
                return;
            }
        }
        config.setValue(KEY, String.valueOf(Math.max(offset - 1, 0)));
        blockMenu.replaceExistingItem(ManualMachineMenu.INFO_SLOT, showItem);
    }

    public void setMachineRecipeList(List<MachineRecipe> machineRecipeList) {
        this.machineRecipeList = machineRecipeList;
    }
}
