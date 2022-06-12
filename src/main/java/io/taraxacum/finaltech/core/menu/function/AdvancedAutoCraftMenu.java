package io.taraxacum.finaltech.core.menu.function;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapperAmount;
import io.taraxacum.finaltech.api.factory.LocationRecipeRegistry;
import io.taraxacum.finaltech.api.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.items.unusable.CopyCardItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.helper.SlotSearchSize;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class AdvancedAutoCraftMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {36, 37};
    private static final int[] INPUT_BORDER = new int[] {18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int[] OUTPUT_BORDER = new int[] {33, 34, 35, 42, 44, 51, 52, 53};
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6 ,7 ,8 ,9 ,10, 11, 12, 13, 14, 15 ,16 ,17};
    private static final int[] OUTPUT_SLOT = new int[0];

    private static final int PARSE_SLOT = 46;
    public static final int PARSE_ITEM_SLOT = 45;
    public static final ItemStack PARSE_ICON = new CustomItemStack(Material.CRAFTING_TABLE, TextUtil.COLOR_STRESS + "点击尝试解析");

    private static final int[] ITEM_INPUT_SLOT = new int[] {30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int ITEM_OUTPUT_SLOT = 43;

    public static final int STATUS_SLOT = 28;
    public static final int MODULE_SLOT = 27;

    private static final int INPUT_SEARCH_SLOT = 29;
    private static final int OUTPUT_SEARCH_SLOT = 47;
    private static final int SEARCH_INFO_SLOT = 38;

    private static final ItemStack SEARCH_INFO_ICON = new CustomItemStack(Material.TARGET, TextUtil.COLOR_PASSIVE + "机制",
            "",
            TextUtil.COLOR_NORMAL + "多级解析粘液科技物品的合成配方",
            TextUtil.COLOR_NORMAL + "读取该方块下方一格的容器的物品并尝试合成" + TextUtil.COLOR_NEGATIVE + " 仅支持非原版容器",
            "",
            TextUtil.COLOR_NORMAL + "在上面两行放入机器",
            TextUtil.COLOR_NORMAL + "即按照顺序进行解析");


    private static final ItemStack PARSE_FAILED_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, TextUtil.COLOR_NEGATIVE + "解析失败", "&kYou_find_me!");
    private static final ItemStack PARSE_SUCCESS_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, TextUtil.COLOR_POSITIVE + "解析成功");
    private static final ItemStack PARSE_EXTEND_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, TextUtil.COLOR_ACTION + "点击查看详情");

    public static final Map<ItemStack, List<MachineRecipe>> RECIPE_MAP = new HashMap<>(10);
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

        RECIPE_MAP.put(FinalTechItems.MANUAL_ANCIENT_ALTAR, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_ANCIENT_ALTAR));
        RECIPE_MAP.put(FinalTechItems.MANUAL_ARMOR_FORGE, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_ARMOR_FORGE));
        RECIPE_MAP.put(FinalTechItems.MANUAL_COMPRESSOR, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_COMPRESSOR));
        RECIPE_MAP.put(FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE));
        RECIPE_MAP.put(FinalTechItems.MANUAL_GRIND_STONE, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_GRIND_STONE));
        RECIPE_MAP.put(FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER));
        RECIPE_MAP.put(FinalTechItems.MANUAL_JUICER, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_JUICER));
        RECIPE_MAP.put(FinalTechItems.MANUAL_MAGIC_WORKBENCH, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_MAGIC_WORKBENCH));
        RECIPE_MAP.put(FinalTechItems.MANUAL_ORE_CRUSHER, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_ORE_CRUSHER));
        RECIPE_MAP.put(FinalTechItems.MANUAL_PRESSURE_CHAMBER, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_PRESSURE_CHAMBER));
        RECIPE_MAP.put(FinalTechItems.MANUAL_SMELTERY, AdvancedAutoCraftMenu.getMachineRecipeList(FinalTechItems.MANUAL_SMELTERY));
    }

    public AdvancedAutoCraftMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
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
        for (int slot : ITEM_INPUT_SLOT) {
            this.addItem(slot, PARSE_FAILED_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(PARSE_SLOT, PARSE_ICON);
        this.addMenuClickHandler(PARSE_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addMenuClickHandler(ITEM_OUTPUT_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(INPUT_SEARCH_SLOT, SlotSearchSize.OUTPUT_HELPER.defaultIcon());
        this.addMenuClickHandler(INPUT_SEARCH_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(OUTPUT_SEARCH_SLOT, SlotSearchSize.INPUT_HELPER.defaultIcon());
        this.addMenuClickHandler(OUTPUT_SEARCH_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(SEARCH_INFO_SLOT, SEARCH_INFO_ICON);
        this.addMenuClickHandler(SEARCH_INFO_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(STATUS_SLOT, Icon.QUANTITY_MODULE_ICON);
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());

        for (int slot : ITEM_INPUT_SLOT) {
            this.addItem(slot, Icon.BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(ITEM_OUTPUT_SLOT, Icon.BORDER_ICON);
        this.addMenuClickHandler(ITEM_OUTPUT_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(PARSE_SLOT, ((player, i, itemStack, clickAction) -> {
            updateMenu(blockMenu, block);
            return false;
        }));
        blockMenu.addMenuClickHandler(INPUT_SEARCH_SLOT, SlotSearchSize.INPUT_HELPER.getHandler(blockMenu, block, this, INPUT_SEARCH_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_SEARCH_SLOT, SlotSearchSize.OUTPUT_HELPER.getHandler(blockMenu, block, this, OUTPUT_SEARCH_SLOT));
        blockMenu.addMenuCloseHandler(player -> updateMenu(blockMenu, block));
        blockMenu.addMenuOpeningHandler(player -> updateMenu(blockMenu, block));
    }

    @Override
    public void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        SlotSearchSize.INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_SEARCH_SLOT);
        SlotSearchSize.OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_SEARCH_SLOT);

        ItemStack parseItem = blockMenu.getItemInSlot(PARSE_ITEM_SLOT);
        if (ItemStackUtil.isItemNull(parseItem)) {
            parseItem = blockMenu.getItemInSlot(ITEM_OUTPUT_SLOT);
            if (ItemStackUtil.isItemNull(parseItem) || ItemStackUtil.isItemSimilar(parseItem, PARSE_FAILED_ICON)) {
                this.setParseFailedMenu(blockMenu);
                return;
            }
        }

        SlimefunItem slimefunItem = SlimefunItem.getByItem(parseItem);
        if (slimefunItem == null) {
            this.setParseFailedMenu(blockMenu);
            return;
        }

        List<ItemStackWithWrapperAmount> inputList = null;
        for (RecipeType type : RECIPE_TYPE_LIST) {
            if (type.equals(slimefunItem.getRecipeType())) {
                inputList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
                break;
            }
        }
        if (inputList == null || inputList.size() == 0) {
            this.setParseFailedMenu(blockMenu);
            return;
        }

        for (int slot : INPUT_SLOT) {
            ItemStack machineItem = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(machineItem)) {
                continue;
            }

            List<AdvancedMachineRecipe> machineRecipeList = null;
            for (ItemStack machine : RECIPE_MAP.keySet()) {
                if (ItemStackUtil.isItemSimilar(machineItem, machine)) {
                    machineRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(SlimefunItem.getByItem(machine).getClass());
                    break;
                }
            }
            if (machineRecipeList != null) {
                for (int i = 0; i < machineItem.getAmount(); i++) {
                    boolean work = false;
                    List<ItemStackWithWrapperAmount> inputListTemp = new ArrayList<>();
                    for (ItemStackWithWrapperAmount oldInputItem : inputList) {
                        for (AdvancedMachineRecipe advancedMachineRecipe : machineRecipeList) {
                            for (AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputList()) {
                                ItemStackWithWrapperAmount outputItem = advancedRandomOutput.getOutputItem().get(0);
                                if (advancedRandomOutput.getOutputItem().size() == 1 && oldInputItem.getAmount() >= outputItem.getAmount() && ItemStackUtil.isItemSimilar(oldInputItem, outputItem)) {
                                    int count = oldInputItem.getAmount() / outputItem.getAmount();
                                    for (ItemStackWithWrapperAmount inputItem : advancedMachineRecipe.getInput()) {
                                        ItemStackWithWrapperAmount.addToList(inputListTemp, inputItem, count);
                                    }
                                    oldInputItem.setAmount(oldInputItem.getAmount() - count * outputItem.getAmount());
                                    work = true;
                                }
                            }
                        }
                        if (oldInputItem.getAmount() > 0) {
                            ItemStackWithWrapperAmount.addToList(inputListTemp, oldInputItem);
                            oldInputItem.setAmount(0);
                        }
                    }
                    inputList = inputListTemp;
                    if (!work) {
                        break;
                    }
                }
            } else {
                if(CopyCardItem.isValid(machineItem)) {
                    ItemStack stringItem = StringItemUtil.parseItemInCard(machineItem);
                    String amount = StringItemUtil.parseAmountInCard(machineItem);
                    Iterator<ItemStackWithWrapperAmount> iterator = inputList.iterator();
                    while (iterator.hasNext()) {
                        ItemStackWithWrapperAmount inputItem = iterator.next();
                        if(ItemStackUtil.isItemSimilar(inputItem, stringItem)) {
                            if(StringNumberUtil.compare(amount, String.valueOf(inputItem.getAmount())) >= 0) {
                                iterator.remove();
                            } else {
                                inputItem.setAmount(inputItem.getAmount() - Integer.parseInt(amount));
                            }
                            break;
                        }
                    }
                }
            }
        }

        AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput = new AdvancedMachineRecipe.AdvancedRandomOutput(List.of(new ItemStackWithWrapperAmount(slimefunItem.getRecipeOutput())), 1);
        AdvancedMachineRecipe advancedMachineRecipe = new AdvancedMachineRecipe(inputList, List.of(advancedRandomOutput));
        this.setParseSuccessMenu(blockMenu, advancedMachineRecipe);
    }

    private void setParseFailedMenu(@Nonnull BlockMenu blockMenu) {
        LocationRecipeRegistry.getInstance().setRecipe(blockMenu.getLocation(), null);
        for (int slot : ITEM_INPUT_SLOT) {
            blockMenu.replaceExistingItem(slot, PARSE_FAILED_ICON);
            blockMenu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenu.addItem(ITEM_OUTPUT_SLOT, Icon.BORDER_ICON);
        blockMenu.addMenuClickHandler(ITEM_OUTPUT_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    private void setParseSuccessMenu(@Nonnull BlockMenu blockMenu, @Nonnull AdvancedMachineRecipe advancedMachineRecipe) {
        LocationRecipeRegistry.getInstance().setRecipe(blockMenu.getLocation(), advancedMachineRecipe);
        int i;
        for (i = 0; i < ITEM_INPUT_SLOT.length - 1; i++) {
            if (advancedMachineRecipe.getInput().size() > i) {
                int amount = advancedMachineRecipe.getInput().get(i).getAmount();
                CustomItemStack icon = new CustomItemStack(advancedMachineRecipe.getInput().get(i).getItemStack());
                icon.setAmount(Math.min(amount, 64));
                ItemStackUtil.addLoreToLast(icon, "§8数量= " + amount);
                blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[i], icon);
            } else {
                blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[i], PARSE_SUCCESS_ICON);
            }
            blockMenu.addMenuClickHandler(ITEM_INPUT_SLOT[i], ChestMenuUtils.getEmptyClickHandler());
        }
        if (advancedMachineRecipe.getInput().size() < ITEM_INPUT_SLOT.length) {
            blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[i], PARSE_SUCCESS_ICON);
        } else if (advancedMachineRecipe.getInput().size() == ITEM_INPUT_SLOT.length) {
            int amount = advancedMachineRecipe.getInput().get(i).getAmount();
            CustomItemStack icon = new CustomItemStack(advancedMachineRecipe.getInput().get(i).getItemStack());
            icon.setAmount(Math.min(amount, 64));
            ItemStackUtil.addLoreToLast(icon, "§8数量= " + amount);
            blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[i], icon);
            blockMenu.addMenuClickHandler(ITEM_INPUT_SLOT[i], ChestMenuUtils.getEmptyClickHandler());
        } else {
            blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[i], PARSE_EXTEND_ICON);
            blockMenu.addMenuClickHandler(ITEM_INPUT_SLOT[i], (player, i1, itemStack, clickAction) -> {
                ChestMenu chestMenu = new ChestMenu(ItemStackUtil.getItemName(advancedMachineRecipe.getOutput().get(0).getItemStack()));
                for (int slot = 0; slot < 54 && slot < advancedMachineRecipe.getInput().size(); slot++) {
                    if (advancedMachineRecipe.getInput().size() > slot) {
                        int amount = advancedMachineRecipe.getInput().get(slot).getAmount();
                        CustomItemStack icon = new CustomItemStack(advancedMachineRecipe.getInput().get(slot).getItemStack());
                        icon.setAmount(Math.min(amount, 64));
                        ItemStackUtil.addLoreToLast(icon, "§8数量= " + amount);
                        chestMenu.addItem(slot, icon);
                    } else {
                        chestMenu.addItem(slot, Icon.BORDER_ICON);
                    }
                    chestMenu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
                }
                chestMenu.open(player);
                return false;
            });
        }
        ItemStack icon = new ItemStack(advancedMachineRecipe.getOutput().get(0).getItemStack());
        ItemStackUtil.addLoreToLast(icon, "§8合成产物");
        blockMenu.replaceExistingItem(ITEM_OUTPUT_SLOT, icon);
    }

    private static List<MachineRecipe> getMachineRecipeList(SlimefunItemStack slimefunItemStack) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(slimefunItemStack);
        if (slimefunItem != null) {
            return MachineRecipeFactory.getInstance().getRecipe(slimefunItem.getClass());
        }
        return new ArrayList<>();
    }
}
