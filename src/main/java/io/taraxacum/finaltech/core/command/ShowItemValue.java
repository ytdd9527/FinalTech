package io.taraxacum.finaltech.core.command;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.libs.slimefun.dto.ItemValueTable;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A #{@link Command} that will show the value of an item.
 * #{@link ItemValueTable}
 * @author Final_ROOT
 * @since 2.0
 */
@Deprecated
public class ShowItemValue implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player player) {
            String id = null;
            ItemStack item = player.getItemInHand();
            SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
            if (slimefunItem != null) {
                id = slimefunItem.getId();
            }
            if (strings.length == 1) {
                id = strings[0];
            }
            if (id != null) {
                String inputValue = ItemValueTable.getInstance().getOrCalItemInputValue(id);
                String outputValue = ItemValueTable.getInstance().getOrCalItemOutputValue(id);
                player.sendRawMessage("Item id: " + id);
                player.sendRawMessage("Input value: " + inputValue);
                player.sendRawMessage("Output value: " + outputValue);
            }
        } else {
            String id = null;
            if (strings.length == 1) {
                id = strings[0];
            }
            if (id != null) {
                String inputValue = ItemValueTable.getInstance().getOrCalItemInputValue(id);
                String outputValue = ItemValueTable.getInstance().getOrCalItemOutputValue(id);
                Bukkit.getLogger().info("Item id: " + id);
                Bukkit.getLogger().info("Input value: " + inputValue);
                Bukkit.getLogger().info("Output value: " + outputValue);
            }
        }
        return true;
    }
}
