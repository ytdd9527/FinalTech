package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.menu.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class TransferPipeMenu extends AbstractMachineMenu {
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

    private static final int ITEM_MODE_SLOT = 14;

    private static final int ITEM_COUNT_LIMIT = 3456;

    public TransferPipeMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
        super(id, title, machine);
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
        return ITEM_MATCH;
    }

    @Override
    public int[] getOutputSlots() {
        return ITEM_MATCH;
    }

    @Override
    public void init() {
        super.init();
        this.addItem(ITEM_MODE_SLOT, CargoItemMode.ALL_ICON);
        this.addItem(1, new CustomItemStack(Material.WATER_BUCKET, "&9输入侧设置"), ChestMenuUtils.getEmptyClickHandler());
        this.addItem(7, new CustomItemStack(Material.LAVA_BUCKET, "&6输出侧设置"), ChestMenuUtils.getEmptyClickHandler());

        this.addItem(ITEM_COUNT_SUB_SLOT, new ItemStack(CargoNumber.CARGO_NUMBER_SUB_ICON));
        this.addItem(ITEM_COUNT_SLOT, new CustomItemStack(CargoNumber.CARGO_NUMBER_ICON, 64), ChestMenuUtils.getEmptyClickHandler());
        this.addItem(ITEM_COUNT_ADD_SLOT, new ItemStack(CargoNumber.CARGO_NUMBER_ADD_ICON));

        this.addItem(INPUT_SIZE_SLOT, SlotSearchSize.getIcon(SlotSearchSize.VALUE_OUTPUTS_ONLY));
        this.addItem(INPUT_ORDER_SLOT, SlotSearchOrder.getIcon(SlotSearchOrder.VALUE_ASCENT));
        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.ZERO_ICON);

        this.addItem(OUTPUT_SIZE_SLOT, SlotSearchSize.getIcon(SlotSearchSize.VALUE_INPUTS_ONLY));
        this.addItem(OUTPUT_ORDER_SLOT, SlotSearchOrder.getIcon(SlotSearchOrder.VALUE_ASCENT));
        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.ZERO_ICON);

        this.addItem(FILTER_MODE_SLOT, FilterMode.getIcon(FilterMode.VALUE_BLACK));

        this.addItem(CARGO_MODE_SLOT, CargoMode.getIcon(CargoMode.VALUE_SYMMETRY));
    }

    @Override
    public void newInstance(BlockMenu blockMenu, Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(ITEM_COUNT_SUB_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY));
            itemCount = (itemCount - 2 + ITEM_COUNT_LIMIT) % ITEM_COUNT_LIMIT + 1;
            CargoNumber.setIcon(blockMenu.getItemInSlot(ITEM_COUNT_SLOT), itemCount);
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(ITEM_COUNT_ADD_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY));
            itemCount = itemCount % ITEM_COUNT_LIMIT + 1;
            CargoNumber.setIcon(blockMenu.getItemInSlot(ITEM_COUNT_SLOT), itemCount);
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_SIZE_SLOT, (player, i, itemStack, clickAction) -> {
            String inputSize = SlotSearchSize.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT));
            blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, SlotSearchSize.getIcon(inputSize));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_INPUT, inputSize);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ORDER_SLOT, (player, i, itemStack, clickAction) -> {
            String inputOrder = SlotSearchOrder.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT));
            blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, SlotSearchOrder.getIcon(inputOrder));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT, inputOrder);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String inputSearch = BlockSearchMode.next(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_INPUT), FinalTechItems.TRANSFER_PIPE.getItemId());
            blockMenu.replaceExistingItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(inputSearch));
            BlockStorage.addBlockInfo(block.getLocation(), BlockSearchMode.KEY_INPUT, inputSearch);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_SIZE_SLOT, (player, i, itemStack, clickAction) -> {
            String outputSize = SlotSearchSize.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT));
            blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, SlotSearchSize.getIcon(outputSize));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT, outputSize);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ORDER_SLOT, (player, i, itemStack, clickAction) -> {
            String outputOrder = SlotSearchOrder.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT));
            blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, SlotSearchOrder.getIcon(outputOrder));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT, outputOrder);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String outputSearch = BlockSearchMode.next(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_OUTPUT), FinalTechItems.TRANSFER_PIPE.getItemId());
            blockMenu.replaceExistingItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(outputSearch));
            BlockStorage.addBlockInfo(block.getLocation(), BlockSearchMode.KEY_OUTPUT, outputSearch);
            return false;
        });
        blockMenu.addMenuClickHandler(FILTER_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String filterMode = FilterMode.next(BlockStorage.getLocationInfo(block.getLocation(), FilterMode.KEY));
            blockMenu.replaceExistingItem(FILTER_MODE_SLOT, FilterMode.getIcon(filterMode));
            BlockStorage.addBlockInfo(block.getLocation(), FilterMode.KEY, filterMode);
            return false;
        });
        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String cargoMode = CargoMode.next(BlockStorage.getLocationInfo(block.getLocation(), CargoMode.KEY));
            blockMenu.replaceExistingItem(CARGO_MODE_SLOT, CargoMode.getIcon(cargoMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoMode.KEY, cargoMode);
            return false;
        });
        blockMenu.addMenuClickHandler(ITEM_MODE_SLOT, ((player, i, itemStack, clickAction) -> {
            String cargoItemMode = CargoItemMode.next(BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY));
            blockMenu.replaceExistingItem(ITEM_MODE_SLOT, CargoItemMode.getIcon(cargoItemMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoItemMode.KEY, cargoItemMode);
            return false;
        }));
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        return ITEM_MATCH;
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return this.getSlotsAccessedByItemTransport(flow);
    }

    @Override
    public void updateMenu(BlockMenu blockMenu, Block block) {
        if (BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY) == null) {
            BlockStorage.addBlockInfo(block, CargoNumber.KEY, "64");
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_INPUT, SlotSearchSize.VALUE_OUTPUTS_ONLY);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT, SlotSearchOrder.VALUE_ASCENT);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT, SlotSearchSize.VALUE_INPUTS_ONLY);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT, SlotSearchOrder.VALUE_ASCENT);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), FilterMode.KEY) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), FilterMode.KEY, FilterMode.VALUE_BLACK);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), CargoMode.KEY) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), CargoMode.KEY, CargoMode.VALUE_INPUT_MAIN);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), BlockSearchMode.KEY_INPUT, BlockSearchMode.VALUE_ZERO);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_OUTPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), BlockSearchMode.KEY_OUTPUT, BlockSearchMode.VALUE_ZERO);
        }
        CargoNumber.setIcon(blockMenu.getItemInSlot(ITEM_COUNT_SLOT), Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY)));
        blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, SlotSearchSize.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT)));
        blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, SlotSearchOrder.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT)));
        blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, SlotSearchSize.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT)));
        blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, SlotSearchOrder.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT)));
        blockMenu.replaceExistingItem(FILTER_MODE_SLOT, FilterMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), FilterMode.KEY)));
        blockMenu.replaceExistingItem(CARGO_MODE_SLOT, CargoMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoMode.KEY)));
        blockMenu.replaceExistingItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_INPUT)));
        blockMenu.replaceExistingItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_OUTPUT)));
        blockMenu.replaceExistingItem(ITEM_MODE_SLOT, CargoItemMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY)));
    }
}
