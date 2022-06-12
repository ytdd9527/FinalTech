package io.taraxacum.finaltech.core.menu.manual;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.api.factory.ItemValueTable;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EquivalentExchangeTableMenu extends AbstractManualMachineMenu {
    private static final int[] BORDER = new int[] {30, 31, 32, 39, 41, 48, 49, 50};
    private static final int[] INPUT_BORDER = new int[] {2, 11, 20, 29, 38, 47};
    private static final int[] OUTPUT_BORDER = new int[] {6, 15, 24, 33, 42, 51};
    private static final int[] INPUT_SLOT = new int[] {0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46};
    private static final int[] OUTPUT_SLOT = new int[] {7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53};

    private static final int[] PARSE_BORDER = new int[] {3, 4, 5, 12, 14, 21, 23};
    public static final int PARSE_ITEM_SLOT = 13;
    private static final int PARSE_STATUS_SLOT = 22;

    private static final int CRAFT_SLOT = 40;

    private static final ItemStack PARSE_BORDER_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, TextUtil.COLOR_NORMAL + "解析槽");
    private static final ItemStack PARSE_STATUS_ICON = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, TextUtil.COLOR_STRESS + "解析结果");
    private static final ItemStack CRAFT_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, TextUtil.colorRandomString("价值"));

    public EquivalentExchangeTableMenu(@Nonnull AbstractMachine machine) {
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
        for (int slot : PARSE_BORDER) {
            this.addItem(slot, PARSE_BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(PARSE_STATUS_SLOT, PARSE_STATUS_ICON);
        this.addMenuClickHandler(PARSE_STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(CRAFT_SLOT, CRAFT_ICON);
        this.addMenuClickHandler(CRAFT_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        ItemStack item = blockMenu.getItemInSlot(PARSE_ITEM_SLOT);
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        List<String> lore = new ArrayList<>();
        lore.add("§f" + ItemStackUtil.getItemName(item));
        if (slimefunItem == null) {
            lore.add(TextUtil.COLOR_NEGATIVE + "无法作为材料或目标产物");
        } else {
            lore.add(TextUtil.COLOR_NORMAL + "作为材料的价值= " + TextUtil.COLOR_NUMBER + ItemValueTable.getInstance().getOrCalItemInputValue(item));
            lore.add(TextUtil.COLOR_NORMAL + "作为产物所需要的价值= " + TextUtil.COLOR_NUMBER + ItemValueTable.getInstance().getOrCalItemOutputValue(item));
        }
        ItemStack iconItem = blockMenu.getItemInSlot(PARSE_STATUS_SLOT);
        ItemStackUtil.setLore(iconItem, lore);

        iconItem = blockMenu.getItemInSlot(CRAFT_SLOT);
        ItemStackUtil.setLore(iconItem, TextUtil.COLOR_NORMAL + "当前存储价值= " + TextUtil.COLOR_NUMBER + BlockStorage.getLocationInfo(block.getLocation(), "value"));
    }
}
