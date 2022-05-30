package io.taraxacum.finaltech.api.command;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.factory.ItemValueMap;
import io.taraxacum.finaltech.core.items.unusable.CopyCardItem;
import io.taraxacum.finaltech.core.items.unusable.ItemPhony;
import io.taraxacum.finaltech.core.items.unusable.Singularity;
import io.taraxacum.finaltech.core.items.unusable.Spirochete;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * A {@link Command} that will transfer item in player's hand to a {@link CopyCardItem}.
 * @author Final_ROOT
 * @since 2.0
 */
public class TransferToCopyCardItem implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player player) {
            ItemStack item = player.getItemInHand();
            if(!TransferToCopyCardItem.validItem(item)) {
                return false;
            }
            ItemStack copyCardItem = CopyCardItem.newItem(item, "1");
            player.setItemInHand(copyCardItem);
        } else {
            Bukkit.getLogger().info("Not support for console");
//            for (Map.Entry<String, List<String>> entry : ItemValueMap.VALUE_ITEM_LIST_OUTPUT_MAP.entrySet()) {
//                Bukkit.getLogger().info("&7value= " + entry.getKey());
//                for (String value : entry.getValue()) {
//                    Bukkit.getLogger().info("    " + SlimefunItem.getById(value).getItemName());
//                }
//            }
        }
        return true;
    }

    private static boolean validItem(@Nonnull ItemStack item) {
        return !ItemPhony.isValid(item) && !Singularity.isValid(item) && !Spirochete.isValid(item) && !CopyCardItem.isValid(item);
    }
}
