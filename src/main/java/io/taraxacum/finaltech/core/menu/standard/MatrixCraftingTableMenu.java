//package io.taraxacum.finaltech.core.menu.standard;
//
//import io.taraxacum.finaltech.api.dto.ItemStackWithWrapper;
//import io.taraxacum.finaltech.core.factory.MachineRecipeFactory;
//import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
//import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
//import io.taraxacum.finaltech.core.menu.standard.AbstractStandardMachineMenu;
//import io.taraxacum.finaltech.util.ItemStackUtil;
//import io.taraxacum.finaltech.util.MachineUtil;
//import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
//import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
//import me.mrCookieSlime.Slimefun.api.BlockStorage;
//import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
//import org.bukkit.block.Block;
//import org.bukkit.inventory.ItemStack;
//
//import javax.annotation.Nonnull;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author Final_ROOT
// */
//public class MatrixCraftingTableMenu extends AbstractManualMachineMenu {
//    private static final int[] BORDER = new int[] {4, 5, 14, 22, 23};
//    private static final int[] INPUT_BORDER = new int[] {3, 12, 21};
//    private static final int[] OUTPUT_BORDER = new int[] {6, 7, 8, 15, 17, 24, 25, 26};
//    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 9, 10 ,11, 18, 19, 20};
//    private static final int[] OUTPUT_SLOT = new int[] {16};
//
//    private static final int CRAFT_SLOT = 13;
//
//    public MatrixCraftingTableMenu(@Nonnull AbstractMachine machine) {
//        super(machine);
//    }
//
//    @Override
//    protected int[] getBorder() {
//        return BORDER;
//    }
//
//    @Override
//    protected int[] getInputBorder() {
//        return INPUT_BORDER;
//    }
//
//    @Override
//    protected int[] getOutputBorder() {
//        return OUTPUT_BORDER;
//    }
//
//    @Override
//    public int[] getInputSlot() {
//        return INPUT_SLOT;
//    }
//
//    @Override
//    public int[] getOutputSlot() {
//        return OUTPUT_SLOT;
//    }
//
//    @Override
//    public void init() {
//        super.init();
//    }
//
//    @Override
//    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
//        super.newInstance(blockMenu, block);
//    }
//
//    @Override
//    public void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
//        List<MachineRecipe> recipe = MachineRecipeFactory.getRecipe(this.getMachine().getClass());
//        Map<Integer, ItemStackWithWrapper> itemWithWrapperMap = new HashMap<>(this.getInputSlot().length);
//        for (int i = 0; i < this.getInputSlot().length; i++) {
//            if (!ItemStackUtil.isItemNull(blockMenu.getItemInSlot(this.getInputSlot()[i]))) {
//                itemWithWrapperMap.put(i, new ItemStackWithWrapper(blockMenu.getItemInSlot(this.getInputSlot()[i])));
//            }
//        }
//        MachineRecipe matchRecipe = null;
//        for (MachineRecipe machineRecipe : recipe) {
//            ItemStack[] input = machineRecipe.getInput();
//            if (input.length > this.getInputSlot().length) {
//                continue;
//            }
//            boolean work = true;
//            for (int i = 0; i < this.getInputSlot().length; i++) {
//                if (ItemStackUtil.isItemNull(input[i])) {
//                    continue;
//                }
//                if (!itemWithWrapperMap.containsKey(i) || itemWithWrapperMap.get(i).getItemStack().getAmount() < input[i].getAmount() || !ItemStackUtil.isItemSimilar(input[i], itemWithWrapperMap.get(i))) {
//                    work = false;
//                    break;
//                }
//            }
//            if (work && MachineUtil.calMaxMatch(blockMenu, this.getOutputSlot(), MachineRecipeFactory.getAdvancedRecipe(this.getClass()).get(recipe.indexOf(machineRecipe)).getOutput()) >= 1) {
//                matchRecipe = machineRecipe;
//                break;
//            }
//        }
//
//        if (matchRecipe != null) {
//            for (int i = 0; i < this.getInputSlot().length; i++) {
//                int slot = this.getInputSlot()[i];
//                ItemStack item = blockMenu.getItemInSlot(slot);
//                item.setAmount(item.getAmount() - matchRecipe.getInput()[i].getAmount());
//                blockMenu.replaceExistingItem(slot, item);
//            }
//            blockMenu.pushItem(ItemStackUtil.cloneItem(matchRecipe.getOutput()[0]), this.getOutputSlot());
//        }
//    }
//}
