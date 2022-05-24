package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapperAmount;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageLoreHelper;
import io.taraxacum.finaltech.core.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public final class RecipeLock {
    public static final String KEY = "rl";

    public static final String VALUE_UNLOCK = "-1";
    public static final String VALUE_LOCK_OFF = "-2";

    public static final ItemStack ICON = new CustomItemStack(Material.TRIPWIRE_HOOK, "&7配方锁", "&8禁用锁定");

    private static final Map<String, List<String>> LORE_MAP = new LinkedHashMap<>(2) {
        {
            this.put("-2", List.of("§8禁用锁定"));
            this.put("-1", List.of("§8未锁定"));
        }
    };

    public static final BlockStorageLoreHelper HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, LORE_MAP) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY;
        }

        @Nonnull
        @Override
        public String nextOrDefaultValue(@Nullable String value) {
            return this.defaultValue();
        }

        @Nonnull
        @Override
        public String previousOrDefaultValue(@Nullable String value) {
            return this.defaultValue();
        }

        @Override
        public boolean setIcon(@Nonnull ItemStack item, @Nullable String value, @Nonnull AbstractMachine abstractMachine) {
            if(this.validValue(value)) {
                super.setIcon(item, value);
                return true;
            } else {
                int recipeLock = value == null ? Integer.parseInt(this.defaultValue()) :  Integer.parseInt(value);
                List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getAdvancedRecipe(abstractMachine.getClass());
                if(recipeLock < advancedMachineRecipeList.size() && recipeLock >= 0) {
                    AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get(recipeLock);
                    List<String> loreList;
                    if(advancedMachineRecipe.getOutputList().size() == 1) {
                        loreList = new ArrayList<>(advancedMachineRecipe.getInput().size() + advancedMachineRecipe.getOutputList().get(0).getOutputItem().size() + 3);
                        loreList.add("§9输入:");
                        for(ItemStackWithWrapperAmount inputItem : advancedMachineRecipe.getInput()) {
                            loreList.add("    §7" + ItemStackUtil.getItemName(inputItem.getItemStack()) + " x" + inputItem.getAmount());
                        }
                        loreList.add("");
                        loreList.add("§6输出:");
                        for(ItemStackWithWrapperAmount outputItem : advancedMachineRecipe.getOutputList().get(0).getOutputItem()) {
                            loreList.add("    §7" + ItemStackUtil.getItemName(outputItem.getItemStack()) + " x" + outputItem.getAmount());
                        }
                    } else {
                        int outputLength = 0;
                        for(AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputList()) {
                            outputLength += advancedRandomOutput.getOutputItem().size() + 1;
                        }
                        loreList = new ArrayList<>(advancedMachineRecipe.getInput().size() + outputLength + 3);
                        loreList.add("§9输入:");
                        for(ItemStackWithWrapperAmount inputItem : advancedMachineRecipe.getInput()) {
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
                            for(ItemStackWithWrapperAmount outputItem : advancedRandomOutput.getOutputItem()) {
                                loreList.add("    §7" + ItemStackUtil.getItemName(outputItem.getItemStack()) + " x" + outputItem.getAmount());
                            }
                        }
                    }
                    ItemStackUtil.setLore(item, loreList);
                }
                return false;
            }
        }
    };

    public static ChestMenu.MenuClickHandler getHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            HELPER.checkOrSetBlockStorage(block.getLocation());
            String value = clickAction.isRightClicked() ? VALUE_LOCK_OFF : VALUE_UNLOCK;
            HELPER.setIcon(blockMenu.getItemInSlot(slot), value);
            BlockStorage.addBlockInfo(block.getLocation(), KEY, value);
            return false;
        };
    }
}
