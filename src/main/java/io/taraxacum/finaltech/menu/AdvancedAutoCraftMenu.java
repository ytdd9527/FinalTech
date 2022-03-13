package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.cargo.Icon;
import io.taraxacum.finaltech.util.cargo.SlotSearchSize;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Final_ROOT
 */
public class AdvancedAutoCraftMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[]{9, 27, 45, 52};
    private static final int[] INPUT_BORDER = new int[]{10, 11, 12, 13, 14, 19, 23, 28, 32, 37, 41, 46, 47, 48, 49, 50};
    private static final int[] OUTPUT_BORDER = new int[]{24, 25, 26, 33, 35, 42, 43, 44};
    public static final int[] MACHINE_SLOT = new int[] {0, 1, 2, 3,4 ,5 ,6 ,7 ,8};

    public static final int[] ITEM_INPUT_SLOT = new int[] {20, 21, 22, 29, 30, 31, 38, 39, 40};
    public static final int[] ITEM_INPUT_SLOT_BIG = new int[] {10, 11, 12, 13, 14, 19, 20, 21, 22, 23, 28, 29, 30, 31, 32, 37, 38, 39, 40, 41, 46, 47, 48, 49, 50};
    public static final int RESULT_SLOT = 34;

    public static final int PARSE_SLOT = 51;
    public static final int PARSE_ITEM_SLOT = 53;

    public static final int INFO_SLOT = 18;
    public static final int MODULE_SLOT = 36;

    public static final int INPUT_SEARCH_SLOT = 15;
    public static final int OUTPUT_SEARCH_SLOT = 17;
    public static final int SEARCH_INFO_SLOT = 16;

    public static final ItemStack SEARCH_INFO_ICON = new CustomItemStack(Material.TARGET, "&7介绍",
            "",
            "&7仅支持自动合成部分粘液科技物品",
            "&7会读取该方块下方一格的容器的物品并尝试合成",
            "&7仅支持非原版容器",
            "",
            "&7在最上面一行放入机器",
            "&7可以实现多级合成",
            "",
            "&7左边=合成原料搜索范围",
            "&7右边=输出产物搜索范围");

    public static Map<ItemStack, List<MachineRecipe>> RECIPE_MAP = new HashMap<>(10);
    private static final List<RecipeType> RECIPE_TYPE_LIST = new ArrayList<>(10);
    static {
        RECIPE_TYPE_LIST.add(RecipeType.ANCIENT_ALTAR);
        RECIPE_TYPE_LIST.add(RecipeType.ARMOR_FORGE);
        RECIPE_TYPE_LIST.add(RecipeType.COMPRESSOR);
        RECIPE_TYPE_LIST.add(RecipeType.ENHANCED_CRAFTING_TABLE);
        RECIPE_TYPE_LIST.add(RecipeType.GRIND_STONE);
        RECIPE_TYPE_LIST.add(RecipeType.HEATED_PRESSURE_CHAMBER);
        RECIPE_TYPE_LIST.add(RecipeType.JUICER);
        RECIPE_TYPE_LIST.add(RecipeType.MAGIC_WORKBENCH);
        RECIPE_TYPE_LIST.add(RecipeType.ORE_CRUSHER);
        RECIPE_TYPE_LIST.add(RecipeType.PRESSURE_CHAMBER);
        RECIPE_TYPE_LIST.add(RecipeType.SMELTERY);

        RECIPE_MAP.put(FinalTechItems.MANUAL_ANCIENT_ALTAR, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_ANCIENT_ALTAR));
        RECIPE_MAP.put(FinalTechItems.MANUAL_ARMOR_FORGE, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_ARMOR_FORGE));
        RECIPE_MAP.put(FinalTechItems.MANUAL_COMPRESSOR, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_COMPRESSOR));
        RECIPE_MAP.put(FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE));
        RECIPE_MAP.put(FinalTechItems.MANUAL_GRIND_STONE, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_GRIND_STONE));
        RECIPE_MAP.put(FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER));
        RECIPE_MAP.put(FinalTechItems.MANUAL_JUICER, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_JUICER));
        RECIPE_MAP.put(FinalTechItems.MANUAL_MAGIC_WORKBENCH, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_MAGIC_WORKBENCH));
        RECIPE_MAP.put(FinalTechItems.MANUAL_ORE_CRUSHER, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_ORE_CRUSHER));
        RECIPE_MAP.put(FinalTechItems.MANUAL_PRESSURE_CHAMBER, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_PRESSURE_CHAMBER));
        RECIPE_MAP.put(FinalTechItems.MANUAL_SMELTERY, getMachineRecipesBySlimefunItemStack(FinalTechItems.MANUAL_SMELTERY));
    }

    public AdvancedAutoCraftMenu(@Nonnull String id, @Nonnull String title, AbstractMachine abstractMachine) {
        super(id, title, abstractMachine);
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
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    public void init() {
        super.init();
        for(int slot : ITEM_INPUT_SLOT) {
            this.addItem(slot, Icon.PARSE_FAILED_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(PARSE_SLOT, Icon.AUTO_CRAFT_PARSE_ICON);
        this.addMenuClickHandler(PARSE_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addMenuClickHandler(RESULT_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(INPUT_SEARCH_SLOT, SlotSearchSize.OUTPUTS_ONLY_ICON);
        this.addMenuClickHandler(INPUT_SEARCH_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(OUTPUT_SEARCH_SLOT, SlotSearchSize.INPUTS_ONLY_ICON);
        this.addMenuClickHandler(OUTPUT_SEARCH_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(SEARCH_INFO_SLOT, SEARCH_INFO_ICON);
        this.addMenuClickHandler(SEARCH_INFO_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(INFO_SLOT, Icon.QUANTITY_MODULE_ICON);
        this.addMenuClickHandler(INFO_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(BlockMenu blockMenu, Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(PARSE_SLOT, ((player, i, itemStack, clickAction) -> {
            updateMenu(blockMenu, block);
            return false;
        }));
        blockMenu.addMenuClickHandler(INPUT_SEARCH_SLOT, ((player, i, itemStack, clickAction) -> {
            String size = SlotSearchSize.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT));
            blockMenu.replaceExistingItem(INPUT_SEARCH_SLOT, SlotSearchSize.getIcon(size));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_INPUT, size);
            return false;
        }));
        blockMenu.addMenuClickHandler(OUTPUT_SEARCH_SLOT, ((player, i, itemStack, clickAction) -> {
            String size = SlotSearchSize.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT));
            blockMenu.replaceExistingItem(OUTPUT_SEARCH_SLOT, SlotSearchSize.getIcon(size));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT, size);
            return false;
        }));
        blockMenu.addMenuCloseHandler(player -> updateMenu(blockMenu, block));
        blockMenu.addMenuOpeningHandler(player -> updateMenu(blockMenu, block));
    }

    @Override
    protected void updateMenu(BlockMenu blockMenu, Block block) {
        if(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_INPUT, SlotSearchSize.VALUE_INPUTS_ONLY);
        }
        if(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT, SlotSearchSize.VALUE_OUTPUTS_ONLY);
        }
        blockMenu.replaceExistingItem(INPUT_SEARCH_SLOT, SlotSearchSize.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT)));
        blockMenu.replaceExistingItem(OUTPUT_SEARCH_SLOT, SlotSearchSize.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT)));

        ItemStack outputItem = blockMenu.getItemInSlot(PARSE_ITEM_SLOT);
        if(ItemStackUtil.isItemNull(outputItem)) {
            outputItem = blockMenu.getItemInSlot(RESULT_SLOT);
            if(ItemStackUtil.isItemNull(outputItem) || ItemStackUtil.isItemSimilar(outputItem, Icon.PARSE_FAILED_ICON)) {
                setParseFailedMenu(blockMenu);
                return;
            }
        }
        SlimefunItem slimefunItem = SlimefunItem.getByItem(outputItem);
        if(slimefunItem == null) {
            setParseFailedMenu(blockMenu);
            return;
        }
        ItemStack[] recipeInputs = null;
        for(RecipeType type : RECIPE_TYPE_LIST) {
            if(type.equals(slimefunItem.getRecipeType())) {
                List<ItemStack> list = new ArrayList<>(9);
                Collections.addAll(list, slimefunItem.getRecipe());
                list = ItemStackUtil.mergeSameItem(list);
                recipeInputs = ItemStackUtil.toArray(list);
            }
        }
        if(recipeInputs == null || recipeInputs.length == 0) {
            recipeInputs = new ItemStack[] {outputItem};
        }
        ItemStack[] lastValidRecipeInput = recipeInputs;
        for(int slot : MACHINE_SLOT) {
            ItemStack machineItem = blockMenu.getItemInSlot(slot);
            if(ItemStackUtil.isItemNull(machineItem)) {
                continue;
            }
            for(ItemStack machine : RECIPE_MAP.keySet()) {
                if(!ItemStackUtil.isItemSimilar(machineItem, machine)) {
                    continue;
                }
                List<MachineRecipe> machineRecipeList = RECIPE_MAP.get(machine);
                int count = 0;
                while(count++ < machineItem.getAmount()) {
                    ItemStack[] result = ItemStackUtil.mergeSameItem(getMultistageInputByMachineRecipe(recipeInputs, machineRecipeList));
                    boolean same = true;
                    if(result.length != recipeInputs.length) {
                        same = false;
                    } else {
                        for(int i = 0; i < result.length; i++) {
                            if(result[i].getAmount() != recipeInputs[i].getAmount() || !ItemStackUtil.isItemSimilar(result[i], recipeInputs[i])) {
                                same = false;
                                break;
                            }
                        }
                    }
                    if(same) {
                        break;
                    }
                    recipeInputs = result;
                    if(recipeInputs.length <= ITEM_INPUT_SLOT_BIG.length) {
                        lastValidRecipeInput = recipeInputs;
                    }
                }
            }
        }
        setParseSuccessMenu(blockMenu, lastValidRecipeInput, slimefunItem.getRecipeOutput());
    }

    private static ItemStack[] getMultistageInputByMachineRecipe(ItemStack[] input, List<MachineRecipe> machineRecipes) {
        List<ItemStack> list = new ArrayList<>();
        for(ItemStack item : input) {
            boolean match = false;
            for(MachineRecipe machineRecipe : machineRecipes) {
                if(machineRecipe.getOutput().length != 1) {
                    continue;
                }
                ItemStack output = machineRecipe.getOutput()[0];
                if(item.getAmount() < output.getAmount() || item.getAmount() % output.getAmount() != 0 || !ItemStackUtil.isItemSimilar(item, output)) {
                    continue;
                }
                match = true;
                int amount = item.getAmount() / output.getAmount();
                for(ItemStack recipeInput : machineRecipe.getInput()) {
                    int itemAmount = recipeInput.getAmount() * amount;
                    while (itemAmount > recipeInput.getMaxStackSize()) {
                        list.add(new CustomItemStack(recipeInput, recipeInput.getMaxStackSize()));
                        itemAmount -= recipeInput.getMaxStackSize();
                    }
                    list.add(new CustomItemStack(recipeInput, itemAmount));
                }
            }
            if(!match) {
                list.add(item);
            }
        }
        return ItemStackUtil.toArray(ItemStackUtil.mergeSameItem(list));
    }

    private ItemStack[] getMultistageInput(ItemStack[] input, RecipeType recipeType) {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack itemStack : input) {
            SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
            if (slimefunItem != null && slimefunItem.getRecipeType().equals(recipeType)) {
                int amount = itemStack.getAmount() / slimefunItem.getRecipeOutput().getAmount();
                if(amount == 0 || itemStack.getAmount() % slimefunItem.getRecipeOutput().getAmount() != 0) {
                    list.add(itemStack);
                    continue;
                }
                for (ItemStack item : slimefunItem.getRecipe()) {
                    if(ItemStackUtil.isItemNull(item)) {
                        continue;
                    }
                    int itemAmount = item.getAmount() * amount;
                    while (itemAmount > item.getMaxStackSize()) {
                        list.add(new CustomItemStack(item, item.getMaxStackSize()));
                        itemAmount -= item.getMaxStackSize();
                    }
                    list.add(new CustomItemStack(item, itemAmount * item.getAmount()));
                }
            } else {
                list.add(itemStack);
            }
        }
        return ItemStackUtil.toArray(ItemStackUtil.mergeSameItem(list));
    }

    private void setParseFailedMenu(BlockMenu blockMenu) {
        for(int slot : ITEM_INPUT_SLOT) {
            blockMenu.replaceExistingItem(slot, Icon.PARSE_FAILED_ICON);
        }
        blockMenu.replaceExistingItem(RESULT_SLOT, Icon.PARSE_FAILED_ICON);
        for(int slot : INPUT_BORDER) {
            blockMenu.replaceExistingItem(slot, Icon.INPUT_BORDER_ICON);
        }
    }

    private void setParseSuccessMenu(BlockMenu blockMenu, ItemStack[] items, ItemStack item) {
        int i;
        item = new ItemStack(item);
//        ItemStackUtil.addLore(item, "§8合成产物");
        if(items.length <= ITEM_INPUT_SLOT.length) {
            for(i = 0; i < items.length; i++) {
                ItemStack showItem = new ItemStack(items[i]);
//                ItemStackUtil.addLore(showItem, "§8合成原料");
                blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[i], showItem);
            }
            for(int j = i; j < ITEM_INPUT_SLOT.length; j++) {
                blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[j], Icon.PARSE_NULL_ICON);
            }
            for(int slot : INPUT_BORDER) {
                blockMenu.replaceExistingItem(slot, Icon.INPUT_BORDER_ICON);
            }
            blockMenu.replaceExistingItem(RESULT_SLOT, item);
        } else if(items.length <= ITEM_INPUT_SLOT.length + INPUT_BORDER.length) {
            for(i = 0; i < items.length; i++) {
                ItemStack showItem = new ItemStack(items[i]);
//                ItemStackUtil.addLore(showItem, "§8合成原料");
                blockMenu.replaceExistingItem(ITEM_INPUT_SLOT_BIG[i], showItem);
            }
            for(int j = i; j < ITEM_INPUT_SLOT_BIG.length; j++) {
                blockMenu.replaceExistingItem(ITEM_INPUT_SLOT_BIG[j], Icon.PARSE_NULL_ICON);
            }
            blockMenu.replaceExistingItem(RESULT_SLOT, item);
        }
    }

    private static List<MachineRecipe> getMachineRecipesBySlimefunItemStack(SlimefunItemStack slimefunItemStack) {
        SlimefunItem slimefunItem = SlimefunItem.getById(slimefunItemStack.getItemId());
        try {
            Method getMachineRecipes = slimefunItem.getClass().getMethod("getMachineRecipes");
            return (List<MachineRecipe>) getMachineRecipes.invoke(slimefunItem);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
