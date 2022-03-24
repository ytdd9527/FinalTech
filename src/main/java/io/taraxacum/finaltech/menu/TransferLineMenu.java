package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.cargo.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class TransferLineMenu extends AbstractMachineMenu {
    private static final int[] SPECIAL_BORDER = new int[] {27, 28, 29, 30, 31, 32, 33, 34, 35};
    private static final int[] BORDER = new int[] {5, 14, 23};

    public static final int[] ITEM_MATCH = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26};

    public static final int[] INPUT_SLOTS = new int[] {36, 37, 38, 39, 40, 41, 42, 43, 44};

    public static final int[] OUTPUT_SLOTS = new int[] {45, 46, 47, 48, 49, 50, 51, 52, 53};

    private static final int BLOCK_CARGO_ORDER_SLOT = 2;
    private static final int BLOCK_SEARCH_CYCLE_SLOT = 3;
    private static final int BLOCK_SEARCH_MODE_SLOT = 0;
    private static final int BLOCK_SEARCH_ORDER_SLOT = 1;
    private static final int BLOCK_SEARCH_SELF_SLOT = 4;

    private static final int ITEM_COUNT_SUB_SLOT = 9;
    private static final int ITEM_COUNT_SLOT = 10;
    private static final int ITEM_COUNT_ADD_SLOT = 11;

    private static final int CARGO_MODE_SLOT = 12;

    private static final int FILTER_MODE_SLOT = 13;

    private static final int ITEM_MODE_SLOT = 20;

    private static final int INPUT_SIZE_SLOT = 18;
    private static final int INPUT_ORDER_SLOT = 19;

    private static final int OUTPUT_SIZE_SLOT = 21;
    private static final int OUTPUT_ORDER_SLOT = 22;

    private static final int ITEM_COUNT_LIMIT = 3456;

    public TransferLineMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
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
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public void init() {
        super.init();
        setSize(54);
        for(int slot : SPECIAL_BORDER) {
            this.addItem(slot, new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, " "));
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }

        this.addItem(BLOCK_CARGO_ORDER_SLOT, BlockCargoOrder.POSITIVE_ICON);

        this.addItem(BLOCK_SEARCH_CYCLE_SLOT, BlockSearchCycle.FALSE_ICON);

        this.addItem(BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.ZERO_ICON);

        this.addItem(BLOCK_SEARCH_ORDER_SLOT, BlockSearchOrder.POSITIVE_ICON);

        this.addItem(BLOCK_SEARCH_SELF_SLOT, BlockSearchSelf.FALSE_ICON);

        this.addItem(ITEM_COUNT_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addItem(ITEM_COUNT_SLOT, new CustomItemStack(CargoNumber.CARGO_NUMBER_ICON, 64));
        this.addMenuClickHandler(ITEM_COUNT_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(ITEM_COUNT_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);

        this.addItem(CARGO_MODE_SLOT, CargoMode.SYMMETRY_ICON);

        this.addItem(FILTER_MODE_SLOT, FilterMode.FILTER_MODE_BLACK_ICON);

        this.addItem(ITEM_MODE_SLOT, CargoItemMode.ALL_ICON);

        this.addItem(INPUT_SIZE_SLOT, SlotSearchSize.OUTPUTS_ONLY_ICON);
        this.addItem(INPUT_ORDER_SLOT, SlotSearchOrder.ASCENT_ICON);

        this.addItem(OUTPUT_SIZE_SLOT, SlotSearchSize.INPUTS_ONLY_ICON);
        this.addItem(OUTPUT_ORDER_SLOT, SlotSearchOrder.ASCENT_ICON);
    }

    @Override
    public void newInstance(BlockMenu blockMenu, Block block) {
        super.newInstance(blockMenu, block);
        Location location = block.getLocation();
        blockMenu.addMenuClickHandler(BLOCK_CARGO_ORDER_SLOT, ((player, i, itemStack, clickAction) -> {
            String blockCargoOrder = BlockCargoOrder.next(BlockStorage.getLocationInfo(location, BlockCargoOrder.KEY));
            blockMenu.replaceExistingItem(BLOCK_CARGO_ORDER_SLOT, BlockCargoOrder.getIcon(blockCargoOrder));
            BlockStorage.addBlockInfo(location, BlockCargoOrder.KEY, blockCargoOrder);
            return false;
        }));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_CYCLE_SLOT, ((player, i, itemStack, clickAction) -> {
            String blockSearchCycle = BlockSearchCycle.next(BlockStorage.getLocationInfo(location, BlockSearchCycle.KEY));
            blockMenu.replaceExistingItem(BLOCK_SEARCH_CYCLE_SLOT, BlockSearchCycle.getIcon(blockSearchCycle));
            BlockStorage.addBlockInfo(location, BlockSearchCycle.KEY, blockSearchCycle);
            return false;
        }));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_MODE_SLOT, ((player, i, itemStack, clickAction) -> {
            String blockSearchMode = BlockSearchMode.next(BlockStorage.getLocationInfo(location, BlockSearchMode.KEY), FinalTechItems.TRANSFER_LINE.getItemId());
            blockMenu.replaceExistingItem(BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(blockSearchMode));
            BlockStorage.addBlockInfo(location, BlockSearchMode.KEY, blockSearchMode);
            return false;
        }));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_ORDER_SLOT, ((player, i, itemStack, clickAction) -> {
            String blockSearchOrder = BlockSearchOrder.next(BlockStorage.getLocationInfo(location, BlockSearchOrder.KEY));
            blockMenu.replaceExistingItem(BLOCK_SEARCH_ORDER_SLOT, BlockSearchOrder.getIcon(blockSearchOrder));
            BlockStorage.addBlockInfo(location, BlockSearchOrder.KEY, blockSearchOrder);
            return false;
        }));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_SELF_SLOT, ((player, i, itemStack, clickAction) -> {
            String blockSearchSelf = BlockSearchSelf.next(BlockStorage.getLocationInfo(location, BlockSearchSelf.KEY));
            blockMenu.replaceExistingItem(BLOCK_SEARCH_SELF_SLOT, BlockSearchSelf.getIcon(blockSearchSelf));
            BlockStorage.addBlockInfo(location, BlockSearchSelf.KEY, blockSearchSelf);
            return false;
        }));
        blockMenu.addMenuClickHandler(ITEM_COUNT_SUB_SLOT, ((player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY));
            itemCount = (itemCount - 2 + ITEM_COUNT_LIMIT) % ITEM_COUNT_LIMIT + 1;
            CargoNumber.setIcon(blockMenu.getItemInSlot(ITEM_COUNT_SLOT), itemCount);
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY, String.valueOf(itemCount));
            return false;
        }));
        blockMenu.addMenuClickHandler(ITEM_COUNT_ADD_SLOT, ((player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY));
            itemCount = itemCount % ITEM_COUNT_LIMIT + 1;
            CargoNumber.setIcon(blockMenu.getItemInSlot(ITEM_COUNT_SLOT), itemCount);
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY, String.valueOf(itemCount));
            return false;
        }));
        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, ((player, i, itemStack, clickAction) -> {
            String cargoMode = CargoMode.next(BlockStorage.getLocationInfo(location, CargoMode.KEY));
            blockMenu.replaceExistingItem(CARGO_MODE_SLOT, CargoMode.getIcon(cargoMode));
            BlockStorage.addBlockInfo(location, CargoMode.KEY, cargoMode);
            return false;
        }));
        blockMenu.addMenuClickHandler(FILTER_MODE_SLOT, ((player, i, itemStack, clickAction) -> {
            String filterMode = FilterMode.next(BlockStorage.getLocationInfo(location, FilterMode.KEY));
            blockMenu.replaceExistingItem(FILTER_MODE_SLOT, FilterMode.getIcon(filterMode));
            BlockStorage.addBlockInfo(location, FilterMode.KEY, filterMode);
            return false;
        }));
        blockMenu.addMenuClickHandler(INPUT_SIZE_SLOT, ((player, i, itemStack, clickAction) -> {
            String inputSize = SlotSearchSize.next(BlockStorage.getLocationInfo(location, SlotSearchSize.KEY_INPUT));
            blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, SlotSearchSize.getIcon(inputSize));
            BlockStorage.addBlockInfo(location, SlotSearchSize.KEY_INPUT, inputSize);
            return false;
        }));
        blockMenu.addMenuClickHandler(INPUT_ORDER_SLOT, ((player, i, itemStack, clickAction) -> {
            String inputOrder = SlotSearchOrder.next(BlockStorage.getLocationInfo(location, SlotSearchOrder.KEY_INPUT));
            blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, SlotSearchOrder.getIcon(inputOrder));
            BlockStorage.addBlockInfo(location, SlotSearchOrder.KEY_INPUT, inputOrder);
            return false;
        }));
        blockMenu.addMenuClickHandler(OUTPUT_SIZE_SLOT, ((player, i, itemStack, clickAction) -> {
            String outputSize = SlotSearchSize.next(BlockStorage.getLocationInfo(location, SlotSearchSize.KEY_OUTPUT));
            blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, SlotSearchSize.getIcon(outputSize));
            BlockStorage.addBlockInfo(location, SlotSearchSize.KEY_OUTPUT, outputSize);
            return false;
        }));
        blockMenu.addMenuClickHandler(OUTPUT_ORDER_SLOT, ((player, i, itemStack, clickAction) -> {
            String outputOrder = SlotSearchOrder.next(BlockStorage.getLocationInfo(location, SlotSearchOrder.KEY_OUTPUT));
            blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, SlotSearchOrder.getIcon(outputOrder));
            BlockStorage.addBlockInfo(location, SlotSearchOrder.KEY_OUTPUT, outputOrder);
            return false;
        }));
        blockMenu.addMenuClickHandler(ITEM_MODE_SLOT, ((player, i, itemStack, clickAction) -> {
            String cargoItemMode = CargoItemMode.next(BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY));
            blockMenu.replaceExistingItem(ITEM_MODE_SLOT, CargoItemMode.getIcon(cargoItemMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoItemMode.KEY, cargoItemMode);
            return false;
        }));
    }

    @Override
    protected void updateMenu(BlockMenu blockMenu, Block block) {
        blockMenu.replaceExistingItem(BLOCK_CARGO_ORDER_SLOT, BlockCargoOrder.getIcon(BlockStorage.getLocationInfo(block.getLocation(), BlockCargoOrder.KEY)));
        blockMenu.replaceExistingItem(BLOCK_SEARCH_CYCLE_SLOT, BlockSearchCycle.getIcon(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchCycle.KEY)));
        blockMenu.replaceExistingItem(BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY)));
        blockMenu.replaceExistingItem(BLOCK_SEARCH_ORDER_SLOT, BlockSearchOrder.getIcon(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchOrder.KEY)));
        blockMenu.replaceExistingItem(BLOCK_SEARCH_SELF_SLOT, BlockSearchSelf.getIcon(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchSelf.KEY)));
        blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, SlotSearchSize.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT)));
        blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, SlotSearchOrder.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT)));
        blockMenu.replaceExistingItem(CARGO_MODE_SLOT, CargoMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoMode.KEY)));
        blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, SlotSearchSize.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT)));
        blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, SlotSearchOrder.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT)));
        blockMenu.replaceExistingItem(FILTER_MODE_SLOT, FilterMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), FilterMode.KEY)));
        blockMenu.replaceExistingItem(ITEM_MODE_SLOT, CargoItemMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY)));
        CargoNumber.setIcon(blockMenu.getItemInSlot(ITEM_COUNT_SLOT), Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY)));
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        switch (itemTransportFlow) {
            case WITHDRAW:
                return OUTPUT_SLOTS;
            case INSERT:
                return INPUT_SLOTS;
            default:
                return ITEM_MATCH;
        }
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return this.getSlotsAccessedByItemTransport(flow);
    }
}
