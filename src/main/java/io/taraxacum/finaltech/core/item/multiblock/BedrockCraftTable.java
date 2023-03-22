package io.taraxacum.finaltech.core.item.multiblock;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechRecipeTypes;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.slimefun.dto.AdvancedCraft;
import io.taraxacum.libs.slimefun.dto.MachineRecipeFactory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class BedrockCraftTable extends AbstractMultiBlockItem implements RecipeItem {
    public BedrockCraftTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipe, BlockFace.SELF);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerRecipeByRecipeType(this, FinalTechRecipeTypes.BEDROCK_CRAFT_TABLE);
    }

    @Override
    public void onInteract(Player player, Block block) {
        Block inventoryBlock = block.getRelative(BlockFace.UP);
        BlockState blockState = inventoryBlock.getState();
        if (Material.TRAPPED_CHEST.equals(inventoryBlock.getType()) && blockState instanceof InventoryHolder inventoryHolder) {
            Inventory inventory = inventoryHolder.getInventory();
            List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getId());
            int[] ints = JavaUtil.generateRandomInts(27);
            AdvancedCraft advancedCraft = AdvancedCraft.craftAsc(inventory, ints, advancedMachineRecipeList, 1, 0);
            if (advancedCraft != null && advancedCraft.getMatchCount() > 0) {
                int availableCount = MachineUtil.calMaxMatch(inventory, ints, advancedCraft.getOutputItemList());
                if (availableCount > 0) {
                    advancedCraft.consumeItem(inventory);
                    for (ItemStack itemStack : advancedCraft.calMachineRecipe(0).getOutput()) {
                        inventory.addItem(itemStack);
                    }
                    // TODO sound
                }
            }
        }
        // TODO sound
    }

    @Override
    public int getRegisterRecipeDelay() {
        return 1;
    }
}
