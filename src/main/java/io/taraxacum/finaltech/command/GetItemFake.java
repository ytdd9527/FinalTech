package io.taraxacum.finaltech.command;

import io.taraxacum.finaltech.item.unusable.CopyCardItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Final_ROOT
 */
public class GetItemFake implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Bukkit.getLogger().info("command active!");
        Bukkit.getLogger().info(s);
        Bukkit.getLogger().info(Arrays.toString(strings));
        Bukkit.getLogger().info(strings.length + "");
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ItemStack item = player.getItemInHand();
            ItemStack copyCardItem = CopyCardItem.newCopyCardItem(item, "1");
            player.setItemInHand(copyCardItem);
            Bukkit.getLogger().info("ok");
        }
        return true;
    }
}
