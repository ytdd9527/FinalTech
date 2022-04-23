package io.taraxacum.finaltech.menu.manual;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.dto.AdvancedCraftV2;
import io.taraxacum.finaltech.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.machine.manual.craft.AbstractManualCraftMachine;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
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

    //todo 说明优化
    private static final ItemStack INFO_ICON = new CustomItemStack(Material.TARGET, "§f介绍",
            "",
            "§f点击合成即可进行合成",
            "",
            "§f目标合成产物的显示可能略有延迟",
            "§f这不会影响实际合成效果",
            "",
            "§f每隔约" + Slimefun.getTickerTask().getTickRate() / 20.0 + "秒会刷新一次目标合成产物");

    public static final String KEY = "offset";
    private static final String KEY_ORDER = "order";
    private static final String ORDER_VALUE_DESC = "desc";
    private static final String ORDER_VALUE_ASC = "asc";

    private static final long UPDATE_TIME_LIMIT = 50;

    private AbstractMachine abstractMachine;

    public ManualCraftMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
        this.abstractMachine = abstractMachine;
    }

    @Override
    public void init() {
        super.init();
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(PREVIOUS_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "§7上一个物品"));
        this.addItem(NEXT_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "§7下一个物品"));
        this.addItem(STOCK_SLOT, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "§7点击堆叠",
                "§e点击 尝试堆叠物品"));
        this.addItem(CRAFT_SLOT, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "§7点击合成",
                "§e左键 §b合成64次，但只会合成一种物品",
                "§e右键 §b合成3456次，但只会合成一种物品",
                "§eshift+左键 §b合成3456次，可能合成多种物品（配方顺序搜索）",
                "§eshift+右键 §b合成3456次，可能合成多种物品（配方逆序搜索）"));
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuOpeningHandler((player -> {
            ManualCraftMachineMenu.this.updateMenu(blockMenu, block);
        }));
        blockMenu.addMenuClickHandler(PREVIOUS_SLOT, ((player, i, itemStack, clickAction) -> {
            BlockStorage.addBlockInfo(block.getLocation(), KEY_ORDER, ORDER_VALUE_DESC);
            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            int length = MachineRecipeFactory.getAdvancedRecipe(this.abstractMachine.getClass()).size();
            offset = (offset + length - 1) % length;
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(offset));
            ManualCraftMachineMenu.this.updateMenu(blockMenu, block);
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_CURRENT, String.valueOf(System.currentTimeMillis()));
            return false;
        }));
        blockMenu.addMenuClickHandler(NEXT_SLOT, ((player, i, itemStack, clickAction) -> {
            BlockStorage.addBlockInfo(block.getLocation(), KEY_ORDER, ORDER_VALUE_ASC);
            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            int length = MachineRecipeFactory.getAdvancedRecipe(this.abstractMachine.getClass()).size();
            offset = (offset + 1) % length;
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(offset));
            ManualCraftMachineMenu.this.updateMenu(blockMenu, block);
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_CURRENT, String.valueOf(System.currentTimeMillis()));
            return false;
        }));
        blockMenu.addMenuClickHandler(STOCK_SLOT, ((player, i, itemStack, clickAction) -> {
            MachineUtil.stockSlots(blockMenu, INPUT_SLOT);
            return false;
        }));
        blockMenu.addMenuClickHandler(CRAFT_SLOT, ((player, i, itemStack, clickAction) -> {
            Config config = BlockStorage.getLocationInfo(block.getLocation());

            long lastTimeMillis = config.contains(AbstractManualCraftMachine.KEY_CURRENT) ? Long.parseLong(config.getString(AbstractManualCraftMachine.KEY_CURRENT)) : 0;
            long currentTimeMillis = System.currentTimeMillis();
            if(currentTimeMillis - lastTimeMillis < UPDATE_TIME_LIMIT) {
                Location location = blockMenu.getLocation();
                Bukkit.getLogger().warning("[" + JavaPlugin.getPlugin(FinalTech.class).getName() + "]§c位于" + location.getWorld().getName() + "(" + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + ")处的粘液科技机器" + this.getTitle() + "正在被玩家高速点击，相关的玩家包括：");
                for(HumanEntity humanEntity : blockMenu.toInventory().getViewers()) {
                    Bukkit.getLogger().warning("    " + humanEntity.getName());
                }
                Bukkit.getLogger().warning("请确定这是否是由玩家网络卡顿造成，如果不是，可能是其正尝试恶意卡服");
            }

            int quantity = clickAction.isShiftClicked() || clickAction.isRightClicked() ? 3456 : 64;
            int offset = config.contains(KEY) ? Integer.parseInt(config.getValue(KEY).toString()) : 0;
            String order = null;
            if(clickAction.isShiftClicked()) {
                if(clickAction.isRightClicked()) {
                    order = ORDER_VALUE_DESC;
                } else {
                    order = ORDER_VALUE_ASC;
                }
            }

            AdvancedCraftV2 craft;
            if(ORDER_VALUE_DESC.equals(order)) {
                craft = AdvancedCraftV2.craftDesc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getAdvancedRecipe(this.abstractMachine.getClass()), quantity, offset);
            } else {
                craft = AdvancedCraftV2.craftAsc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getAdvancedRecipe(this.abstractMachine.getClass()), quantity, offset);
            }

            if(craft == null) {
                BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_CURRENT, String.valueOf(System.currentTimeMillis()));
                return false;
            }
            craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(blockMenu, OUTPUT_SLOT, craft.getOutputItemList())));
            if(craft.getMatchCount() == 0 && order == null) {
                BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_CURRENT, String.valueOf(System.currentTimeMillis()));
                return false;
            }

            craft.consumeItem(blockMenu);
            for(ItemStack item : craft.calMachineRecipe(0).getOutput()) {
                blockMenu.pushItem(item, OUTPUT_SLOT);
            }

            if(order == null) {
                updateMenu(blockMenu, block);
                BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_CURRENT, String.valueOf(System.currentTimeMillis()));
                return false;
            }

            quantity -= craft.getMatchCount();
            while (quantity > 0) {
                offset = craft.getOffset();
                if(ORDER_VALUE_DESC.equals(order)) {
                    craft = AdvancedCraftV2.craftDesc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getAdvancedRecipe(this.abstractMachine.getClass()), quantity, offset);
                } else {
                    craft = AdvancedCraftV2.craftAsc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getAdvancedRecipe(this.abstractMachine.getClass()), quantity, offset);
                }

                if(craft == null) {
                    break;
                }
                craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(blockMenu, OUTPUT_SLOT, craft.getOutputItemList())));
                if(craft.getMatchCount() == 0) {
                    break;
                }

                craft.consumeItem(blockMenu);
                for(ItemStack item : craft.calMachineRecipe(0).getOutput()) {
                    blockMenu.pushItem(item, OUTPUT_SLOT);
                }

                quantity -= craft.getMatchCount();
            }

            updateMenu(blockMenu, block);
            BlockStorage.addBlockInfo(block.getLocation(), AbstractManualCraftMachine.KEY_CURRENT, String.valueOf(System.currentTimeMillis()));
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

        long lastTimeMillis = config.contains(AbstractManualCraftMachine.KEY_CURRENT) ? Long.parseLong(config.getString(AbstractManualCraftMachine.KEY_CURRENT)) : 0;
        long currentTimeMillis = System.currentTimeMillis();
        if(currentTimeMillis - lastTimeMillis < UPDATE_TIME_LIMIT) {
            Location location = blockMenu.getLocation();
            Bukkit.getLogger().warning("[" + JavaPlugin.getPlugin(FinalTech.class).getName() + "]§c位于" + location.getWorld().getName() + "(" + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + ")处的粘液科技机器" + this.getTitle() + "正在被玩家高速点击，相关的玩家包括：");
            for(HumanEntity humanEntity : blockMenu.toInventory().getViewers()) {
                Bukkit.getLogger().warning("    " + humanEntity.getName());
            }
            Bukkit.getLogger().warning("请确定这是否是由玩家网络卡顿造成，如果不是，可能是其正尝试恶意卡服");
        }

        AdvancedCraftV2 craft = null;
        String order = config.getString(KEY_ORDER);
        int offset = config.contains(KEY) ? Integer.parseInt(config.getValue(KEY).toString()) : 0;
        if(order == null || ORDER_VALUE_ASC.equals(order)) {
            craft = AdvancedCraftV2.craftAsc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getAdvancedRecipe(this.abstractMachine.getClass()), 1, offset);
        } else if (ORDER_VALUE_DESC.equals(order)) {
            craft = AdvancedCraftV2.craftDesc(blockMenu, INPUT_SLOT, MachineRecipeFactory.getAdvancedRecipe(this.abstractMachine.getClass()), 1, offset);
        }

        if(craft != null) {
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
