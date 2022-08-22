package io.taraxacum.finaltech.core.menu.manual;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.api.dto.AdvancedCraft;
import io.taraxacum.finaltech.api.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.items.machine.manual.craft.AbstractManualCraftMachine;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ManualCraftMachineMenu extends AbstractManualMachineMenu {
    private static final int[] BORDER = new int[] {39, 41, 48, 50};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53};
    private static final int[] OUTPUT_SLOT = new int[] {27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    private static final int STATUS_SLOT = 31;
    private static final int PREVIOUS_SLOT = 30;
    private static final int NEXT_SLOT = 32;
    private static final int STOCK_SLOT = 40;
    private static final int CRAFT_SLOT = 49;

    private static final ItemStack INFO_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, TextUtil.COLOR_NEGATIVE + "无匹配的目标物品");

    public static final String KEY = "offset";
    private static final String KEY_ORDER = "order";
    private static final String ORDER_VALUE_DESC = "desc";
    private static final String ORDER_VALUE_ASC = "asc";

    private static final long UPDATE_TIME_LIMIT = 50;

    public ManualCraftMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
    }

    @Override
    public void init() {
        super.init();
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(PREVIOUS_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, TextUtil.colorRandomString("上一个物品")));
        this.addItem(NEXT_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, TextUtil.colorRandomString("下一个物品")));
        this.addItem(STOCK_SLOT, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, TextUtil.colorRandomString("点击堆叠")));
        this.addItem(CRAFT_SLOT, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, TextUtil.colorRandomString("点击合成"),
                TextUtil.COLOR_ACTION + "左键" + TextUtil.COLOR_NORMAL + "合成 " + TextUtil.COLOR_NUMBER + "64次" + TextUtil.COLOR_NORMAL + "  只会合成一种物品",
                TextUtil.COLOR_ACTION + "右键" + TextUtil.COLOR_NORMAL + "合成 " + TextUtil.COLOR_NUMBER + "3456次" + TextUtil.COLOR_NORMAL + "  只会合成一种物品",
                TextUtil.COLOR_ACTION + "shift+左键" + TextUtil.COLOR_NORMAL + "合成 " + TextUtil.COLOR_NUMBER + "3456次" + TextUtil.COLOR_NORMAL + "  可能合成多种物品（顺序配方搜索）",
                TextUtil.COLOR_ACTION + "shift+右键" + TextUtil.COLOR_NORMAL + "合成 " + TextUtil.COLOR_NUMBER + "3456次" + TextUtil.COLOR_NORMAL + "  可能合成多种物品（逆序配方搜索）"));
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuOpeningHandler((player -> {
            ManualCraftMachineMenu.this.updateMenu(blockMenu, block);
        }));
        blockMenu.addMenuClickHandler(STATUS_SLOT, (((player, i, itemStack, clickAction) -> {

            return false;
        })));
        blockMenu.addMenuClickHandler(PREVIOUS_SLOT, ((player, i, itemStack, clickAction) -> {
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(block.getLocation(), AbstractManualCraftMachine.KEY_COUNT)));

            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
            BlockStorage.addBlockInfo(block.getLocation(), KEY_ORDER, ORDER_VALUE_DESC);
            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            int length = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()).size();
            offset = (offset + length - 1) % length;
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(offset));
            ManualCraftMachineMenu.this.updateMenu(blockMenu, block);
            return false;
        }));
        blockMenu.addMenuClickHandler(NEXT_SLOT, ((player, i, itemStack, clickAction) -> {
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(block.getLocation(), AbstractManualCraftMachine.KEY_COUNT)));

            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
            BlockStorage.addBlockInfo(block.getLocation(), KEY_ORDER, ORDER_VALUE_ASC);
            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            int length = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()).size();
            offset = (offset + 1) % length;
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(offset));
            ManualCraftMachineMenu.this.updateMenu(blockMenu, block);
            return false;
        }));
        blockMenu.addMenuClickHandler(STOCK_SLOT, ((player, i, itemStack, clickAction) -> {
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(block.getLocation(), AbstractManualCraftMachine.KEY_COUNT)));

            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
            MachineUtil.stockSlots(blockMenu, INPUT_SLOT);
            return false;
        }));
        blockMenu.addMenuClickHandler(CRAFT_SLOT, ((player, i, itemStack, clickAction) -> {
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(block.getLocation(), AbstractManualCraftMachine.KEY_COUNT)));

            if(MachineUtil.itemCount(blockMenu, OUTPUT_SLOT) == OUTPUT_SLOT.length) {
                return false;
            }

            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
            Config config = BlockStorage.getLocationInfo(block.getLocation());

            int quantity = clickAction.isShiftClicked() || clickAction.isRightClicked() ? 3456 : 64;
            int offset = config.contains(KEY) ? Integer.parseInt(config.getValue(KEY).toString()) : 0;
            String order = null;
            if (clickAction.isShiftClicked()) {
                if (clickAction.isRightClicked()) {
                    order = ORDER_VALUE_DESC;
                } else {
                    order = ORDER_VALUE_ASC;
                }
            }

            AdvancedCraft craft;
            if (ORDER_VALUE_DESC.equals(order)) {
                craft = AdvancedCraft.craftDesc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()), quantity, offset);
            } else {
                craft = AdvancedCraft.craftAsc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()), quantity, offset);
            }

            if (craft == null) {
                return false;
            }
            craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(blockMenu, OUTPUT_SLOT, craft.getOutputItemList())));
            if (craft.getMatchCount() == 0 && order == null) {
                return false;
            }

            craft.consumeItem(blockMenu);
            for (ItemStack item : craft.calMachineRecipe(0).getOutput()) {
                blockMenu.pushItem(item, OUTPUT_SLOT);
            }

            if (order == null) {
                updateMenu(blockMenu, block);
                return false;
            }

            quantity -= craft.getMatchCount();
            while (quantity > 0) {
                offset = craft.getOffset();
                if (ORDER_VALUE_DESC.equals(order)) {
                    craft = AdvancedCraft.craftDesc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()), quantity, offset);
                } else {
                    craft = AdvancedCraft.craftAsc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()), quantity, offset);
                }

                if (craft == null) {
                    break;
                }
                craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(blockMenu, OUTPUT_SLOT, craft.getOutputItemList())));
                if (craft.getMatchCount() == 0) {
                    break;
                }

                craft.consumeItem(blockMenu);
                for (ItemStack item : craft.calMachineRecipe(0).getOutput()) {
                    blockMenu.pushItem(item, OUTPUT_SLOT);
                }

                quantity -= craft.getMatchCount();
            }

            updateMenu(blockMenu, block);
            return false;

        }));
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
    public void updateMenu(@Nonnull BlockMenu blockMenu, Block block) {
        Config config = BlockStorage.getLocationInfo(block.getLocation());

        AdvancedCraft craft = null;
        String order = config.getString(KEY_ORDER);
        int offset = config.contains(KEY) ? Integer.parseInt(config.getValue(KEY).toString()) : 0;
        if (order == null || ORDER_VALUE_ASC.equals(order)) {
            craft = AdvancedCraft.craftAsc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()), 1, offset);
        } else if (ORDER_VALUE_DESC.equals(order)) {
            craft = AdvancedCraft.craftDesc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()), 1, offset);
        }

        if (craft != null) {
            config.setValue(KEY, String.valueOf(craft.getOffset()));
            ItemStack item = ItemStackUtil.cloneItem(craft.getOutputItemList().get(0).getItemStack());
            ItemStackUtil.addLoreToLast(item, "§8匹配的产物");
            blockMenu.replaceExistingItem(STATUS_SLOT, item);
        } else {
            config.setValue(KEY, "0");
            blockMenu.replaceExistingItem(STATUS_SLOT, INFO_ICON);
        }

        BlockStorage.addBlockInfo(block.getLocation(), KEY_ORDER, null);
    }
}
