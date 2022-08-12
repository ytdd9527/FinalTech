package io.taraxacum.finaltech.core.menu.manual;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.AdvancedCraft;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.api.dto.ItemAmountWrapper;
import io.taraxacum.finaltech.api.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.items.machine.manual.craft.AbstractManualCraftMachine;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ManualCraftMachineMenu extends AbstractManualMachineMenu {
    private static final int[] BORDER = new int[] {48, 50};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
    private static final int[] OUTPUT_SLOT = new int[] {27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    private static final int STATUS_SLOT = 40;
    private static final int[] STATUS_L_SLOT = new int[] {38, 37, 36};
    private static final int[] STATUS_R_SLOT = new int[] {42, 43, 44};
    private static final int PREVIOUS_SLOT = 39;
    private static final int NEXT_SLOT = 41;
    private static final int CRAFT_SLOT = 49;
    private static final int[] CRAFT_L_SLOT = new int[] {47, 46, 45};
    private static final int[] CRAFT_R_SLOT = new int[] {51, 52, 53};

    private static final ItemStack CRAFT_ICON = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, FinalTech.getLanguageString("items", "ManualCraftMachine", "craft-icon", "name"), FinalTech.getLanguageStringArray("items", "ManualCraftMachine", "craft-icon", "lore"));
    private static final ItemStack STATUS_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTech.getLanguageString("items", "ManualCraftMachine", "status-icon", "name"), FinalTech.getLanguageStringArray("items", "ManualCraftMachine", "status-icon", "lore"));

    public static final String KEY = "offset";
    public static final String[] KEY_L = new String[] {"offset-l1", "offset-l2", "offset-l3"};
    public static final String[] KEY_R = new String[] {"offset-r1", "offset-r2", "offset-r3"};
    private static final String KEY_ORDER = "order";
    private static final String ORDER_VALUE_DESC = "desc";
    private static final String ORDER_VALUE_ASC = "asc";

    public ManualCraftMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
    }

    @Override
    public void init() {
        super.init();
        this.addItem(STATUS_SLOT, STATUS_ICON);
        for (int slot : STATUS_L_SLOT) {
            this.addItem(slot, STATUS_ICON);
        }
        for (int slot : STATUS_R_SLOT) {
            this.addItem(slot, STATUS_ICON);
        }
        this.addItem(PREVIOUS_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, FinalTech.getLanguageString("items", "ManualCraftMachine", "previous-icon", "name"), FinalTech.getLanguageStringArray("items", "ManualCraftMachine", "previous-icon", "lore")));
        this.addItem(NEXT_SLOT, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, FinalTech.getLanguageString("items", "ManualCraftMachine", "next-icon", "name"), FinalTech.getLanguageStringArray("items", "ManualCraftMachine", "next-icon", "lore")));
        this.addItem(CRAFT_SLOT, CRAFT_ICON);
        for(int slot : CRAFT_L_SLOT) {
            this.addItem(slot, CRAFT_ICON);
        }
        for(int slot : CRAFT_R_SLOT) {
            this.addItem(slot, CRAFT_ICON);
        }
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location location = blockMenu.getLocation();
        Config config = BlockStorage.getLocationInfo(location);
        blockMenu.addMenuOpeningHandler((player -> {
            config.setValue(AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT)));
            ManualCraftMachineMenu.this.updateInventory(inventory, location);
        }));
        blockMenu.addMenuClickHandler(STATUS_SLOT, (((player, i, itemStack, clickAction) -> {
            config.setValue(AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT)));
            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
            MachineUtil.stockSlots(inventory, INPUT_SLOT);
            return false;
        })));
        for(int slotP = 0; slotP < STATUS_L_SLOT.length; slotP++) {
            final int finalSlotP = slotP;
            blockMenu.addMenuClickHandler(STATUS_L_SLOT[slotP], (player, i, itemStack, clickAction) -> {
                config.setValue(AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT)));
                ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);

                config.setValue(KEY, config.getString(KEY_L[finalSlotP]));
                ManualCraftMachineMenu.this.updateInventory(inventory, location);
                return false;
            });
        }
        for(int slotP = 0; slotP < STATUS_R_SLOT.length; slotP++) {
            final int finalSlotP = slotP;
            blockMenu.addMenuClickHandler(STATUS_R_SLOT[slotP], (player, i, itemStack, clickAction) -> {
                config.setValue(AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT)));
                ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);

                config.setValue(KEY, config.getString(KEY_R[finalSlotP]));
                ManualCraftMachineMenu.this.updateInventory(inventory, location);
                return false;
            });
        }
        blockMenu.addMenuClickHandler(PREVIOUS_SLOT, ((player, i, itemStack, clickAction) -> {
            config.setValue(AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT)));
            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);

            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            int length = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()).size();
            offset = (offset + length - 1) % length;
            config.setValue(KEY, String.valueOf(offset));
            config.setValue(KEY_ORDER, ORDER_VALUE_DESC);
            ManualCraftMachineMenu.this.updateInventory(inventory, location);
            return false;
        }));
        blockMenu.addMenuClickHandler(NEXT_SLOT, ((player, i, itemStack, clickAction) -> {
            config.setValue(AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT)));
            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);

            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            int length = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()).size();
            offset = (offset + 1) % length;
            config.setValue(KEY, String.valueOf(offset));
            config.setValue(KEY_ORDER, ORDER_VALUE_ASC);
            ManualCraftMachineMenu.this.updateInventory(inventory, location);
            return false;
        }));
        blockMenu.addMenuClickHandler(CRAFT_SLOT, ((player, i, itemStack, clickAction) -> {
            config.setValue(AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT)));
            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);

            int offset = config.contains(KEY) ? Integer.parseInt(config.getValue(KEY).toString()) : 0;
            ManualCraftMachineMenu.this.doFunction(blockMenu, clickAction, player, offset);
            return false;
        }));
        for(int slotP = 0; slotP < CRAFT_L_SLOT.length; slotP++) {
            final int fSlotP = slotP;
            blockMenu.addMenuClickHandler(CRAFT_L_SLOT[slotP], (player, i, itemStack, clickAction) -> {
                config.setValue(AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT)));
                ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
                int offset = config.contains(KEY_L[fSlotP]) ? Integer.parseInt(config.getValue(KEY_L[fSlotP]).toString()) : 0;
                ManualCraftMachineMenu.this.doFunction(blockMenu, clickAction, player, offset);
                return false;
            });
        }
        for(int slotP = 0; slotP < CRAFT_R_SLOT.length; slotP++) {
            final int fSlotP = slotP;
            blockMenu.addMenuClickHandler(CRAFT_R_SLOT[slotP], (player, i, itemStack, clickAction) -> {
                config.setValue(AbstractManualCraftMachine.KEY_COUNT, StringNumberUtil.add(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT)));
                ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);

                int offset = config.contains(KEY_R[fSlotP]) ? Integer.parseInt(config.getValue(KEY_R[fSlotP]).toString()) : 0;
                ManualCraftMachineMenu.this.doFunction(blockMenu, clickAction, player, offset);
                return false;
            });
        }
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
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        Config config = BlockStorage.getLocationInfo(location);

        AdvancedCraft craft = null;
        String order = config.getString(KEY_ORDER);
        int offset = config.contains(KEY) ? Integer.parseInt(config.getValue(KEY).toString()) : 0;
        if (order == null || ORDER_VALUE_ASC.equals(order)) {
            craft = AdvancedCraft.craftAsc(inventory, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()), Integer.MAX_VALUE, offset);
        } else if (ORDER_VALUE_DESC.equals(order)) {
            craft = AdvancedCraft.craftDesc(inventory, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass()), Integer.MAX_VALUE, offset);
        }

        if (craft != null) {
            config.setValue(KEY, String.valueOf(craft.getOffset()));
            ItemStack item = ItemStackUtil.cloneItem(craft.getOutputItemList().get(0).getItemStack());
            ItemStackUtil.addLoreToLast(item, FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("items", "ManualCraftMachine", "match-item", "lore"), String.valueOf(craft.getMatchCount())));
            inventory.setItem(STATUS_SLOT, item);
            int offsetR = offset + 1;
            for(int i = 0; i < STATUS_R_SLOT.length; i++) {
                craft = AdvancedCraft.craftAsc(inventory, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getSlimefunItem().getClass()), Integer.MAX_VALUE, offsetR);
                if(craft != null) {
                    config.setValue(KEY_R[i], String.valueOf(craft.getOffset()));
                    offsetR = craft.getOffset() + 1;
                    item = ItemStackUtil.cloneItem(craft.getOutputItemList().get(0).getItemStack());
                    ItemStackUtil.addLoreToLast(item, FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("items", "ManualCraftMachine", "match-item", "lore"), String.valueOf(craft.getMatchCount())));
                    inventory.setItem(STATUS_R_SLOT[i], item);
                } else {
                    config.setValue(KEY_R[i], null);
                    inventory.setItem(STATUS_R_SLOT[i], STATUS_ICON);
                }
            }
            int offsetL = offset - 1;
            for(int i = 0; i < STATUS_L_SLOT.length; i++) {
                craft = AdvancedCraft.craftDesc(inventory, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getSlimefunItem().getClass()), Integer.MAX_VALUE, offsetL);
                if(craft != null) {
                    config.setValue(KEY_L[i], String.valueOf(craft.getOffset()));
                    offsetL = craft.getOffset() - 1;
                    item = ItemStackUtil.cloneItem(craft.getOutputItemList().get(0).getItemStack());
                    ItemStackUtil.addLoreToLast(item, FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("items", "ManualCraftMachine", "match-item", "lore"), String.valueOf(craft.getMatchCount())));
                    inventory.setItem(STATUS_L_SLOT[i], item);
                } else {
                    config.setValue(KEY_L[i], null);
                    inventory.setItem(STATUS_L_SLOT[i], STATUS_ICON);
                }
            }
        } else {
            config.setValue(KEY, "0");
            inventory.setItem(STATUS_SLOT, STATUS_ICON);
            for (int slot : STATUS_R_SLOT) {
                inventory.setItem(slot, STATUS_ICON);
            }
            for (int slot : STATUS_L_SLOT) {
                inventory.setItem(slot, STATUS_ICON);
            }
        }

        config.setValue(KEY_ORDER, null);
    }

    public void doFunction(@Nonnull BlockMenu blockMenu, @Nonnull ClickAction clickAction, @Nonnull Player player, int offset) {
        Inventory inventory = blockMenu.toInventory();
        int quantity = 1;
        if(clickAction.isShiftClicked()) {
            quantity = 3456;
        } else if(clickAction.isRightClicked()) {
            quantity = 64;
        }

        List<AdvancedMachineRecipe> advancedRecipe = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getMachine().getClass());
        List<AdvancedMachineRecipe> targetAdvancedRecipe = new ArrayList<>(List.of(advancedRecipe.get(offset % advancedRecipe.size())));

        AdvancedCraft craft;
        craft = AdvancedCraft.craftAsc(blockMenu.toInventory(), INPUT_SLOT, targetAdvancedRecipe, quantity, 0);

        if (craft == null) {
            return;
        }

        List<ItemAmountWrapper> outputItemList = craft.getOutputItemList();
        for(ItemAmountWrapper itemAmountWrapper : outputItemList) {
            SlimefunItem slimefunItem = SlimefunItem.getByItem(itemAmountWrapper.getItemStack());
            if(slimefunItem != null && !slimefunItem.canUse(player, true)) {
                return;
            }
        }

        craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(inventory, OUTPUT_SLOT, craft.getOutputItemList())));
        if (craft.getMatchCount() == 0) {
            return;
        }

        AdvancedMachineRecipe advancedMachineRecipe = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getSlimefunItem().getClass()).get(craft.getOffset());
        if(advancedMachineRecipe.getOutputList().size() > 1) {
            craft.setMatchCount(Math.min(64, craft.getMatchCount()));
        }

        craft.consumeItem(blockMenu.toInventory());
        for (ItemStack item : craft.calMachineRecipe(0).getOutput()) {
            blockMenu.pushItem(item, OUTPUT_SLOT);
        }

        this.updateInventory(inventory, blockMenu.getLocation());
    }
}
