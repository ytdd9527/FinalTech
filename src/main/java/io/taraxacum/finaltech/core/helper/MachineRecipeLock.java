package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.api.dto.ItemAmountWrapper;
import io.taraxacum.finaltech.api.factory.BlockStorageHelper;
import io.taraxacum.finaltech.api.factory.BlockStorageLoreHelper;
import io.taraxacum.finaltech.api.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Final_ROOT
 */
public final class MachineRecipeLock {
    public static final String KEY = "rl";

    public static final String VALUE_UNLOCK = "-1";
    public static final String VALUE_LOCK_OFF = "-2";

    public static final ItemStack ICON = new CustomItemStack(Material.TRIPWIRE_HOOK, FinalTech.getLanguageString("helper", "machine-recipe-lock", "icon", "name"), FinalTech.getLanguageStringArray("helper", "machine-recipe-lock", "icon", "lore"));

    public static final BlockStorageLoreHelper HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, new LinkedHashMap<>(2) {{
        this.put("-2", FinalTech.getLanguageStringList("helper", "machine-recipe-lock", "unlock", "lore"));
        this.put("-1", FinalTech.getLanguageStringList("helper", "machine-recipe-lock", "lock-off", "lore"));
    }}) {
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

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (player, i, itemStack, clickAction) -> {
                HELPER.checkOrSetBlockStorage(location);
                String value = clickAction.isRightClicked() ? VALUE_LOCK_OFF : VALUE_UNLOCK;
                HELPER.setIcon(inventory.getItem(slot), value);
                BlockStorage.addBlockInfo(location, KEY, value);
                return false;
            };
        }

        @Override
        public boolean setIcon(@Nonnull ItemStack item, @Nullable String value, @Nonnull SlimefunItem slimefunItem) {
            if (this.validValue(value)) {
                super.setIcon(item, value);
                return true;
            } else {
                int recipeLock = value == null ? Integer.parseInt(this.defaultValue()) :  Integer.parseInt(value);
                List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(slimefunItem.getClass());
                if (recipeLock < advancedMachineRecipeList.size() && recipeLock >= 0) {
                    AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get(recipeLock);
                    List<String> loreList;
                    if (advancedMachineRecipe.getOutputList().size() == 1) {
                        loreList = new ArrayList<>(advancedMachineRecipe.getInput().size() + advancedMachineRecipe.getOutputList().get(0).getOutputItem().size() + 3);
                        loreList.addAll(FinalTech.getLanguageStringList("helper", "machine-recipe-lock", "lock", "input", "title"));
                        for (ItemAmountWrapper inputItem : advancedMachineRecipe.getInput()) {
                            loreList.add(FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("helper", "machine-recipe-lock", "input", "items"), ItemStackUtil.getItemName(inputItem.getItemStack()), String.valueOf(inputItem.getAmount())));
                        }
                        loreList.addAll(FinalTech.getLanguageStringList("helper", "machine-recipe-lock", "lock", "output", "title"));
                        for (ItemAmountWrapper outputItem : advancedMachineRecipe.getOutputList().get(0).getOutputItem()) {
                            loreList.add(FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("helper", "machine-recipe-lock", "output", "items"), ItemStackUtil.getItemName(outputItem.getItemStack()), String.valueOf(outputItem.getAmount())));
                        }
                    } else {
                        int outputLength = 0;
                        for (AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputList()) {
                            outputLength += advancedRandomOutput.getOutputItem().size() + 1;
                        }
                        loreList = new ArrayList<>(advancedMachineRecipe.getInput().size() + outputLength + 3);
                        loreList.addAll(FinalTech.getLanguageStringList("helper", "machine-recipe-lock", "lock", "input", "title"));
                        for (ItemAmountWrapper inputItem : advancedMachineRecipe.getInput()) {
                            loreList.add(FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("helper", "machine-recipe-lock", "input", "items"), ItemStackUtil.getItemName(inputItem.getItemStack()), String.valueOf(inputItem.getAmount())));
                        }
                        loreList.addAll(FinalTech.getLanguageStringList("helper", "machine-recipe-lock", "lock", "output", "title"));
                        for (AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputList()) {
                            String random = String.valueOf(((double) advancedRandomOutput.getWeight()) / advancedMachineRecipe.getWeightSum() * 100.0);
                            if (random.contains(".")) {
                                random = random.substring(0, Math.min(random.indexOf(".") + 3, random.length()));
                            }
                            loreList.addAll(FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("helper", "machine-recipe-lock", "lock", "output-random", "title"), random));
                            for (ItemAmountWrapper outputItem : advancedRandomOutput.getOutputItem()) {
                                loreList.add(FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("helper", "machine-recipe-lock", "input", "items"), ItemStackUtil.getItemName(outputItem.getItemStack()), String.valueOf(outputItem.getAmount())));
                            }
                        }
                    }
                    ItemStackUtil.setLore(item, loreList);
                }
                return false;
            }
        }
    };
}
