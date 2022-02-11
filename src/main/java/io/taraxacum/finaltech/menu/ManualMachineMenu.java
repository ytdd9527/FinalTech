package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.machine.AbstractManualMachine;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.cargo.Icon;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class ManualMachineMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {32, 39, 41, 48, 50};
    private static final int[] CONTAIN = new int[] {
             0,  1,  2,  3,  4,  5,  6,  7,  8,
             9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29,             33, 34, 35,
            36, 37, 38,             42, 43, 44,
            45, 46, 47,             51, 52, 53};
    private static final int[] INPUT_SLOTS = new int[] {
            0,  1,  2,  3,  4,  5,  6,  7,  8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26
    };
    private static final int[] OUTPUT_SLOTS = new int[] {
            27, 28, 29,             33, 34, 35,
            36, 37, 38,             42, 43, 44,
            45, 46, 47,             51, 52, 53
    };
    private static final int STOCK_SLOT = 40;
    private static final int CRAFT_SLOT = 49;

    public static final int INFO_SLOT = 31;

    private static final int PREVIOUS_SLOT = 30;
    private static final int NEXT_SLOT = 32;

    public static final String KEY = "offset";

    private List<MachineRecipe> machineRecipes;
    public ManualMachineMenu(@Nonnull String id, @Nonnull String title, AbstractManualMachine machine) {
        super(id, title, machine);
        machineRecipes = new ArrayList<>();
    }

    @Override
    public void init() {
        super.init();
        this.addItem(STOCK_SLOT, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&7点击堆叠",
                "&e左键 尝试堆叠上方的物品"));
        this.addItem(CRAFT_SLOT, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&7点击合成",
                "&e左键 &b合成64次，但只会合成一种物品",
                "&e右键 &b合成64次，可能合成多种物品",
                "&eshift+左键 &b合成无限次，但只会合成一种物品",
                "&eshift+右键 &b合成无限次，可能合成多种物品"));

        this.addMenuClickHandler(INFO_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(PREVIOUS_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "&7上一个物品"));
        this.addItem(NEXT_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "&7下一个物品"));
    }

    @Override
    public void newInstance(BlockMenu blockMenu, Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuOpeningHandler((player -> {
            BlockStorage.addBlockInfo(block.getLocation(), KEY, "0");
            updateMenu(blockMenu, block);
        }));
        for(int slot : INPUT_SLOTS) {
            blockMenu.addMenuClickHandler(slot, ((player, i, itemStack, clickAction) -> {
                BlockStorage.addBlockInfo(block, AbstractManualMachine.KEY, AbstractManualMachine.VALUE_UPDATE);
                return true;
            }));
        }
        blockMenu.addMenuClickHandler(PREVIOUS_SLOT, ((player, i, itemStack, clickAction) -> {
            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            offset = Math.max(offset - 1, 0);
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(offset));
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualMachine.KEY, AbstractManualMachine.VALUE_ALLOW);
            updateMenu(blockMenu, block);
            return false;
        }));
        blockMenu.addMenuClickHandler(NEXT_SLOT, ((player, i, itemStack, clickAction) -> {
            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            offset = offset + 1;
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(offset));
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualMachine.KEY, AbstractManualMachine.VALUE_ALLOW);
            updateMenu(blockMenu, block);
            return false;
        }));
        blockMenu.addMenuClickHandler(STOCK_SLOT, ((player, i, itemStack, clickAction) -> {
            MachineUtil.stockSlots(blockMenu, INPUT_SLOTS);
            return false;
        }));
        blockMenu.addMenuClickHandler(CRAFT_SLOT, ((player, i, itemStack, clickAction) -> {
            List<ItemStack> list = new ArrayList<>();
            for(int slot : CONTAIN) {
                ItemStack item = blockMenu.getItemInSlot(slot);
                if(!ItemStackUtil.isItemNull(item)) {
                    list.add(item);
                }
            }
            int amount = clickAction.isShiftClicked() ? 3456 : 64;
            int value = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            int offset = 0;
            boolean work = false;
            for(MachineRecipe machineRecipe : machineRecipes) {
                int find = 0;
                for(ItemStack recipeInput : machineRecipe.getInput()) {
                    boolean itemFind = false;
                    for(ItemStack item : list) {
                        if(SlimefunUtils.isItemSimilar(item, recipeInput, true, true)) {
                            itemFind = true;
                            break;
                        }
                    }
                    if(itemFind) {
                        find++;
                    }
                }
                if(find == machineRecipe.getInput().length) {
                    if(offset++ < value) {
                        continue;
                    }
                    MachineRecipe resultRecipe = MachineUtil.matchRecipe(list, machineRecipe, blockMenu.toInventory(), OUTPUT_SLOTS, amount);
                    if(resultRecipe != null) {
                        for(ItemStack item : resultRecipe.getOutput()) {
                            blockMenu.pushItem(item, OUTPUT_SLOTS);
                        }
                        work = true;
                        if(!clickAction.isRightClicked()) {
                            break;
                        }
                    }
                }
            }
            if(work) {
                BlockStorage.addBlockInfo(block.getLocation(), AbstractManualMachine.KEY, AbstractManualMachine.VALUE_ALLOW);
                updateMenu(blockMenu, block);
            }
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
        if(AbstractManualMachine.VALUE_DENY.equals(BlockStorage.getLocationInfo(block.getLocation(), AbstractManualMachine.KEY))) {
            return;
        } else {
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualMachine.KEY, AbstractManualMachine.VALUE_DENY);
        }
        List<ItemStack> list = new ArrayList<>();
        for(int slot : getInputSlots()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if(!ItemStackUtil.isItemNull(item)) {
                list.add(item);
            }
        }
        int offset = 0;
        ItemStack showItem = Icon.BORDER_ICON;
        String s = BlockStorage.getLocationInfo(block.getLocation(), KEY);
        int value = s == null ? 0 : Integer.parseInt(s);
        for(MachineRecipe recipe : this.machineRecipes) {
            int find = 0;
            for(ItemStack recipeInput : recipe.getInput()) {
                boolean itemFind = false;
                for(ItemStack item : list) {
                    if(SlimefunUtils.isItemSimilar(item, recipeInput, true, true)) {
                        itemFind = true;
                        break;
                    }
                }
                if(itemFind) {
                    find++;
                }
            }
            if(find == recipe.getInput().length) {
                showItem = new ItemStack(recipe.getOutput()[0]);
                if(offset < value) {
                    offset++;
                    continue;
                }
                ItemStackUtil.addLoreToLast(showItem, "§8匹配的产物");
                blockMenu.replaceExistingItem(ManualMachineMenu.INFO_SLOT, showItem);
                return;
            }
        }
        BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(Math.max(offset - 1, 0)));
        blockMenu.replaceExistingItem(ManualMachineMenu.INFO_SLOT, showItem);
    }

    public void setMachineRecipes(List<MachineRecipe> machineRecipes) {
        this.machineRecipes = machineRecipes;
    }
}
