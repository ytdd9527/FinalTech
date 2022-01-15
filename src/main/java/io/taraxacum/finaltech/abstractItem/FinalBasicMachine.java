package io.taraxacum.finaltech.abstractItem;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.*;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * 模板
 * 不耗电
 * 4输入4输出
 */
public abstract class FinalBasicMachine extends SlimefunItem implements RecipeDisplayItem, InventoryBlock, MachineProcessHolder<CraftingOperation> {
    private static final int[] BORDER = new int[]{3, 4, 5, 12, 13, 14, 21, 23, 30, 32, 39, 40, 41, 48, 49, 50}; // 机器容器边框
    private static final int[] BORDER_IN = new int[]{2, 11, 20, 29, 38, 47}; // 机器输入槽边框
    private static final int[] BORDER_OUT = new int[]{6, 15, 24, 33, 42, 51}; // 机器输出槽边框
    private static final int[] INPUT = new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46}; // 输入槽
    private static final int[] OUTPUT = new int[]{7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53}; // 输出槽
    private static final int[] CONFIGURATION = new int[]{31}; // 控制槽：放入物品以改变或控制机器逻辑
    private static final int[] INFO = new int[]{22}; // 信息显示菜单
    protected final List<MachineRecipe> recipes; // 工作配方
    private final MachineProcessor<CraftingOperation> processor; // 工作计时器
    private final ItemStack progressBar = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "&f非可升级模块", "&8这个机器无法被升级","&8无需在下方放入组件"); // 机器工作图标

    @ParametersAreNonnullByDefault
    protected FinalBasicMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.recipes = new ArrayList();
        this.processor = new MachineProcessor(this);
        this.processor.setProgressBar(this.getProgressBar());
        this.createPreset(this, this.getInventoryTitle(), this::constructMenu);
        this.addItemHandler(this.onBlockBreak());
    }

    @ParametersAreNonnullByDefault
    protected FinalBasicMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
        this(itemGroup, item, recipeType, recipe);
        this.recipeOutput = recipeOutput;
    }

    public MachineProcessor<CraftingOperation> getMachineProcessor() {
        return this.processor;
    }

    @Nonnull
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {
            public void onBlockBreak(Block b) {
                BlockMenu inv = BlockStorage.getInventory(b);
                if (inv != null) {
                    inv.dropItems(b.getLocation(), FinalBasicMachine.this.getInputSlots());
                    inv.dropItems(b.getLocation(), FinalBasicMachine.this.getOutputSlots());
                    inv.dropItems(b.getLocation(), CONFIGURATION);
                }

                FinalBasicMachine.this.processor.endOperation(b);
            }
        };
    }

    /**
     * 生成机器容器菜单
     * @param preset
     */
    protected void constructMenu(BlockMenuPreset preset) {
        Material border = Material.LIGHT_GRAY_STAINED_GLASS_PANE;
        Material input = Material.ORANGE_STAINED_GLASS_PANE;
        Material output = Material.BLUE_STAINED_GLASS_PANE;
        for(Integer slot:BORDER) {
            preset.addItem(slot, new ItemStack(border), ChestMenuUtils.getEmptyClickHandler());
        }
        for(Integer slot:BORDER_IN) {
            preset.addItem(slot, new ItemStack(input), ChestMenuUtils.getEmptyClickHandler());
        }
        for(Integer slot:BORDER_OUT) {
            preset.addItem(slot, new ItemStack(output), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(22, progressBar, ChestMenuUtils.getEmptyClickHandler());
    }

    /**
     * 获取机器标题
     * @return
     */
    @Nonnull
    public String getInventoryTitle() {
        return this.getItemName();
    }

    /**
     * 返回机器工作图标
     * @return
     */
    public ItemStack getProgressBar() {
        return this.progressBar;
    }

    public int[] getInfoSlot() {
        return INFO;
    }

    /**
     * 注册该机器
     * @param addon
     */
    public void register(@Nonnull SlimefunAddon addon) {
        this.addon = addon;
//        this.registerDefaultRecipes();
        super.register(addon);
    }

    /**
     * 获取机器的粘液科技唯一标识id
     * @return
     */
    @Nonnull
    public abstract String getMachineIdentifier();

    /**
     * 注册该机器的合成配方
     */
    protected void registerDefaultRecipes() {
    }

    /**
     * 获取机器的工作配方
     * @return
     */
    public List<MachineRecipe> getMachineRecipes() {
        return this.recipes;
    }

    /**
     * 计算并获取机器展示的工作配方
     * @return
     */
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList(this.recipes.size() * 2);
        Iterator iterator = this.recipes.iterator();

        while(iterator.hasNext()) {
            MachineRecipe recipe = (MachineRecipe)iterator.next();
            Integer inputLength = recipe.getInput().length;
            Integer outputLength = recipe.getOutput().length;
            for(int i = 0; i < inputLength; i++) {
                displayRecipes.add(recipe.getInput()[i]);
                if(i < inputLength-1) {
                    displayRecipes.add(new ItemStack(Material.AIR));
                }
            }
            for(int i = 0; i < outputLength; i++) {
                if(i != 0) {
                    displayRecipes.add(new ItemStack(Material.AIR));
                }
                displayRecipes.add(recipe.getOutput()[i]);
            }
        }

        return displayRecipes;
    }

    /**
     * 获取输入槽位置
     * @return
     */
    public int[] getInputSlots() {
        return INPUT;
    }

    /**
     * 获取输出槽位置
     * @return
     */
    public int[] getOutputSlots() {
        return OUTPUT;
    }

    /**
     * 注册机器的工作配方
     * @param recipe 配方类
     */
    public void registerRecipe(MachineRecipe recipe) {
        this.recipes.add(recipe);
    }

    /**
     * 注册机器的工作配方
     * @param seconds 消耗的时间
     * @param input 输入
     * @param output 输出
     */
    public void registerRecipe(int seconds, ItemStack[] input, ItemStack[] output) {
        this.registerRecipe(new MachineRecipe(seconds, input, output));
    }

    /**
     * 注册机器的工作配方
     * @param seconds 消耗的时间
     * @param input 输入
     * @param output 输出
     */
    public void registerRecipe(int seconds, ItemStack input, ItemStack output) {
        this.registerRecipe(new MachineRecipe(seconds, new ItemStack[]{input}, new ItemStack[]{output}));
    }

    /**
     * 预注册
     */
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                FinalBasicMachine.this.tick(b);
            }

            public boolean isSynchronized() {
                return false;
            }
        });
    }

    /**
     * 每粘液刻的工作逻辑
     * @param b
     */
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        CraftingOperation currentOperation = this.processor.getOperation(b);

        tickBefore(b);

        if(currentOperation == null) {
            stock(inv, INPUT);
            MachineRecipe next = this.findNextRecipe(inv);
            if (next != null) {
                if(next.getTicks() == 0) {
                    ItemStack[] outputItems = next.getOutput();
                    int outputItemLength = outputItems.length;
                    for(int i = 0; i < outputItemLength; ++i) {
                        ItemStack output = outputItems[i];
                        inv.pushItem(output.clone(), this.getOutputSlots());
                    }
                    stock(inv, OUTPUT);
                } else {
                    this.processor.startOperation(b, new CraftingOperation(next));
                }
            }
        } else {
            if (currentOperation.isFinished()) {
                ItemStack[] outputItems = currentOperation.getResults();
                int outputItemLength = outputItems.length;

                for(int i = 0; i < outputItemLength; ++i) {
                    ItemStack output = outputItems[i];
                    inv.pushItem(output.clone(), this.getOutputSlots());
                }

                this.processor.endOperation(b);
            } else {
                currentOperation.addProgress(1);
            }
        }

        tickAfter(b);
    }

    abstract protected void tickBefore(Block block);

    abstract protected void tickAfter(Block block);

    /**
     *  寻找匹配的配方
     * @param inv
     * @return
     */
    protected MachineRecipe findNextRecipe(BlockMenu inv) {

        Map<Integer, ItemStack> inventory = new HashMap();
        int[] inputSlots = this.getInputSlots();
        int inputSlotLength = inputSlots.length;

        for(int i = 0; i < inputSlotLength; ++i) {
            int slot = inputSlots[i];
            ItemStack item = inv.getItemInSlot(slot);
            if (item != null) {
                inventory.put(slot, ItemStackWrapper.wrap(item));
            }
        }

        Map<Integer, Integer> found = new HashMap();
        Iterator iterator = this.recipes.iterator();

        while(iterator.hasNext()) {
            MachineRecipe recipe = (MachineRecipe)iterator.next();
            ItemStack[] inputItem = recipe.getInput();
            int inputLength = inputItem.length;

            for(int i = 0; i < inputLength; ++i) {
                ItemStack input = inputItem[i];

                for(int j = 0; j < inputSlotLength; ++j) {
                    int slot = inputSlots[j];
                    if (SlimefunUtils.isItemSimilar(inventory.get(slot), input, true)) {
                        found.put(slot, input.getAmount());
                        break;
                    }
                }
            }

            if (found.size() == recipe.getInput().length) {
                if (!InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    return null;
                }

                Iterator iterator2 = found.entrySet().iterator();

                while(iterator2.hasNext()) {
                    Map.Entry<Integer, Integer> entry = (Map.Entry)iterator2.next();
                    inv.consumeItem(entry.getKey(), entry.getValue());
                }

                return recipe;
            }

            found.clear();
        }

        return null;
    }

    /**
     * 使指定范围内的格子的物品进行合并
     * 避免因物品分散至多个格子导致物品因数量不足无法被识别为工作配方
     * @param slots 堆叠的位置
     */
    protected void stock(BlockMenu inv, int[] slots) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for(int slot : slots) {
            ItemStack i = inv.getItemInSlot(slot);
            if(i == null || i.getType() == Material.AIR) {
                continue;
            }
            for(int j = 0; j < itemStacks.size(); j++) {
                ItemStack item = itemStacks.get(j);
                if(item != null && item.isSimilar(i)) {
                    int consume = 0;
                    if(i.getAmount() + item.getAmount() <= item.getMaxStackSize()) {
                        consume = i.getAmount();
                        item.setAmount(item.getAmount() + i.getAmount());
                    } else {
                        int itemAmount = item.getAmount();
                        consume = item.getMaxStackSize() - itemAmount;
                        item.setAmount(item.getMaxStackSize());
                        itemStacks.set(j, null);
                    }
                    i.setAmount(i.getAmount() - consume);
                }
            }
            if(i.getAmount() > 0) {
                itemStacks.add(i);
            }
        }
    }
}
