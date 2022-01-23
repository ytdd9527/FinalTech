package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.util.CargoUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class PipeMenu extends BlockMenuPreset {
    private static final int[] BORDER = new int[] {
             0,      2,              6,      8,
                                14,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29,             33, 34, 35,
            36, 37, 38,             42, 43, 44,
            45, 46, 47,             51, 52, 53
    };
    public static final int[] ITEM_MATCH = new int[]{30, 31, 32, 39, 40, 41, 48, 49, 50};

    private static final int ITEM_COUNT_SUB_SLOT = 3;
    private static final int ITEM_COUNT_SLOT = 4;
    private static final int ITEM_COUNT_ADD_SLOT = 5;

    private static final int INPUT_SIZE_SLOT = 9;
    private static final int INPUT_ORDER_SLOT = 10;

    private static final int OUTPUT_SIZE_SLOT = 15;
    private static final int OUTPUT_ORDER_SLOT = 16;

    private static final int INPUT_BLOCK_SEARCH_MODE_SLOT = 11;
    private static final int OUTPUT_BLOCK_SEARCH_MODE_SLOT = 17;

    private static final int FILTER_MODE_SLOT = 12;

    private static final int CARGO_MODE_SLOT = 13;

    private static final int ITEM_COUNT_LIMIT = 3456;

    public PipeMenu(@Nonnull String id, @Nonnull String title, boolean universal) {
        super(id, title, universal);
    }

    @Override
    public void init() {
        Material border = Material.LIGHT_GRAY_STAINED_GLASS_PANE;
        for (int slot : BORDER) {
            this.addItem(slot, new CustomItemStack(border, " "), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : new int[]{14}) {
            this.addItem(slot, new CustomItemStack(Material.GRAY_STAINED_GLASS, "&7暂未实现的功能", "&7尽情期待"), ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(1, new CustomItemStack(Material.WATER_BUCKET, "&9输入侧设置"), ChestMenuUtils.getEmptyClickHandler());
        this.addItem(7, new CustomItemStack(Material.LAVA_BUCKET, "&6输出侧设置"), ChestMenuUtils.getEmptyClickHandler());

        this.addItem(ITEM_COUNT_SUB_SLOT, new ItemStack(CargoUtil.ITEM_SUB_ICON));
        this.addItem(ITEM_COUNT_SLOT, new CustomItemStack(CargoUtil.ITEM_COUNT_ICON, 64), ChestMenuUtils.getEmptyClickHandler());
        this.addItem(ITEM_COUNT_ADD_SLOT, new ItemStack(CargoUtil.ITEM_ADD_ICON));

        this.addItem(INPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(CargoUtil.SIZE_OUTPUTS_ONLY));
        this.addItem(INPUT_ORDER_SLOT, CargoUtil.getOrderIcon(CargoUtil.ORDER_ASCENT));
        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, CargoUtil.BLOCK_SEARCH_MODE_INHERIT_ICON);

        this.addItem(OUTPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(CargoUtil.SIZE_INPUTS_ONLY));
        this.addItem(OUTPUT_ORDER_SLOT, CargoUtil.getOrderIcon(CargoUtil.ORDER_ASCENT));
        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, CargoUtil.BLOCK_SEARCH_MODE_INHERIT_ICON);

        this.addItem(FILTER_MODE_SLOT, CargoUtil.getFilterModeIcon(CargoUtil.FILTER_MODE_BLACK_LIST));

        this.addItem(CARGO_MODE_SLOT, CargoUtil.getCargoModeIcon(CargoUtil.CARGO_MODE_INPUT_MAIN));

    }

    @Override
    public void newInstance(BlockMenu blockMenu, Block block) {
        updateMenu(blockMenu, block);
        blockMenu.addMenuClickHandler(ITEM_COUNT_SUB_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.ITEM_COUNT));
            itemCount = (itemCount - 2 + ITEM_COUNT_LIMIT) % ITEM_COUNT_LIMIT + 1;
            CargoUtil.setIconAmount(itemCount, blockMenu.getItemInSlot(ITEM_COUNT_SLOT));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.ITEM_COUNT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(ITEM_COUNT_ADD_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.ITEM_COUNT));
            itemCount = itemCount % ITEM_COUNT_LIMIT + 1;
            CargoUtil.setIconAmount(itemCount, blockMenu.getItemInSlot(ITEM_COUNT_SLOT));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.ITEM_COUNT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_SIZE_SLOT, (player, i, itemStack, clickAction) -> {
            String inputSize = CargoUtil.nextSize(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_SIZE));
            blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(inputSize));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.INPUT_SIZE, inputSize);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ORDER_SLOT, (player, i, itemStack, clickAction) -> {
            String inputOrder = CargoUtil.nextOrder(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ORDER));
            blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, CargoUtil.getOrderIcon(inputOrder));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.INPUT_ORDER, inputOrder);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String inputSearch = CargoUtil.nextBlockSearchMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_BLOCK_SEARCH_MODE));
            blockMenu.replaceExistingItem(INPUT_BLOCK_SEARCH_MODE_SLOT, CargoUtil.getBlockSearchModeIcon(inputSearch));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.INPUT_BLOCK_SEARCH_MODE, inputSearch);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_SIZE_SLOT, (player, i, itemStack, clickAction) -> {
            String outputSize = CargoUtil.nextSize(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_SIZE));
            blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(outputSize));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.OUTPUT_SIZE, outputSize);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ORDER_SLOT, (player, i, itemStack, clickAction) -> {
            String outputOrder = CargoUtil.nextOrder(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ORDER));
            blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, CargoUtil.getOrderIcon(outputOrder));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.OUTPUT_ORDER, outputOrder);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String outputSearch = CargoUtil.nextBlockSearchMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_BLOCK_SEARCH_MODE));
            blockMenu.replaceExistingItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, CargoUtil.getBlockSearchModeIcon(outputSearch));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.OUTPUT_BLOCK_SEARCH_MODE, outputSearch);
            return false;
        });
        blockMenu.addMenuClickHandler(FILTER_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String filterMode = CargoUtil.nextFilterMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.FILTER_MODE));
            blockMenu.replaceExistingItem(FILTER_MODE_SLOT, CargoUtil.getFilterModeIcon(filterMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.FILTER_MODE, filterMode);
            return false;
        });
        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String cargoMode = CargoUtil.nextCargoMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.CARGO_MODE));
            blockMenu.replaceExistingItem(CARGO_MODE_SLOT, CargoUtil.getCargoModeIcon(cargoMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.CARGO_MODE, cargoMode);
            return false;
        });
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        return ITEM_MATCH;
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return this.getSlotsAccessedByItemTransport(flow);
    }

    public void updateMenu(BlockMenu blockMenu, Block block) {
        CargoUtil.setIconAmount(Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.ITEM_COUNT)), blockMenu.getItemInSlot(ITEM_COUNT_SLOT));
        blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_SIZE)));
        blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, CargoUtil.getOrderIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ORDER)));
        blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_SIZE)));
        blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, CargoUtil.getOrderIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ORDER)));
        blockMenu.replaceExistingItem(FILTER_MODE_SLOT, CargoUtil.getFilterModeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.FILTER_MODE)));
        blockMenu.replaceExistingItem(CARGO_MODE_SLOT, CargoUtil.getCargoModeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.CARGO_MODE)));
        blockMenu.replaceExistingItem(INPUT_BLOCK_SEARCH_MODE_SLOT, CargoUtil.getBlockSearchModeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_BLOCK_SEARCH_MODE)));
        blockMenu.replaceExistingItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, CargoUtil.getBlockSearchModeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_BLOCK_SEARCH_MODE)));
    }
}
