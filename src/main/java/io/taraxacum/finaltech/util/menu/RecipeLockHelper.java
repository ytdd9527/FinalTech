package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class RecipeLockHelper {
    public static final String KEY = "recipe-lock";
    public static final String VALUE_UNLOCK = "-1";
    public static final String VALUE_LOCK_OFF = "-2";
    public static final int INT_VALUE_UNLOCK = -1;
    public static final int INT_VALUE_LOCK_OFF = -2;

    public static final ItemStack ICON = new CustomItemStack(Material.TRIPWIRE_HOOK, "&7配方锁", "&8禁用锁定");
    public static final String UNLOCK_LORE = "§8未锁定";
    public static final String LOCK_OFF_LORE = "§8禁用锁定";

    public static void setIcon(@Nonnull ItemStack item, int recipeLock, @Nonnull AbstractMachine abstractMachine) {
        if(recipeLock == INT_VALUE_LOCK_OFF) {
            ItemStackUtil.setLore(item, LOCK_OFF_LORE);
        } else if(recipeLock == INT_VALUE_UNLOCK) {
            ItemStackUtil.setLore(item, UNLOCK_LORE);
        } else {
            List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getAdvancedRecipe(abstractMachine.getClass());
            if(recipeLock < advancedMachineRecipeList.size() && recipeLock >= 0) {
                AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get(recipeLock);
                List<String> loreList;
                if(advancedMachineRecipe.getOutputList().size() == 1) {
                    loreList = new ArrayList<>(advancedMachineRecipe.getInput().size() + advancedMachineRecipe.getOutputList().get(0).getOutputItem().size() + 3);
                    loreList.add("§9输入:");
                    for(ItemStackWithWrapper inputItem : advancedMachineRecipe.getInput()) {
                        loreList.add("    §7" + ItemStackUtil.getItemName(inputItem.getItemStack()) + " x" + inputItem.getAmount());
                    }
                    loreList.add("");
                    loreList.add("§6输出:");
                    for(ItemStackWithWrapper outputItem : advancedMachineRecipe.getOutputList().get(0).getOutputItem()) {
                        loreList.add("    §7" + ItemStackUtil.getItemName(outputItem.getItemStack()) + " x" + outputItem.getAmount());
                    }
                } else {
                    int outputLength = 0;
                    for(AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputList()) {
                        outputLength += advancedRandomOutput.getOutputItem().size() + 1;
                    }
                    loreList = new ArrayList<>(advancedMachineRecipe.getInput().size() + outputLength + 3);
                    loreList.add("§9输入:");
                    for(ItemStackWithWrapper inputItem : advancedMachineRecipe.getInput()) {
                        loreList.add("    §7" + ItemStackUtil.getItemName(inputItem.getItemStack()) + " x" + inputItem.getAmount());
                    }
                    loreList.add("");
                    loreList.add("§6输出:");
                    for(AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputList()) {
                        String random = String.valueOf(((double) advancedRandomOutput.getWeight()) / advancedMachineRecipe.getWeightSum() * 100.0);
                        if(random.contains(".")) {
                            random = random.substring(0, Math.min(random.indexOf(".") + 3, random.length()));
                        }
                        loreList.add("  §a" + random + "%");
                        for(ItemStackWithWrapper outputItem : advancedRandomOutput.getOutputItem()) {
                            loreList.add("    §7" + ItemStackUtil.getItemName(outputItem.getItemStack()) + " x" + outputItem.getAmount());
                        }
                    }
                }
                ItemStackUtil.setLore(item, loreList);
            }
        }
    }
}
