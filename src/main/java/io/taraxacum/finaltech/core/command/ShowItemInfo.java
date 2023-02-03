package io.taraxacum.finaltech.core.command;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.slimefun.dto.ItemValueTable;
import io.taraxacum.finaltech.core.group.RecipeItemGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * A #{@link Command} that will show the value of an item.
 * #{@link ItemValueTable}
 * @author Final_ROOT
 * @since 2.0
 */
public class ShowItemInfo implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player player) {
            ItemStack item = player.getItemInHand();
            SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
            if (slimefunItem == null || slimefunItem.isDisabled() || !slimefunItem.canUse(player, false) || slimefunItem.isHidden()) {
                player.sendRawMessage(FinalTech.getLanguageString("message", "no-permission", "item"));
                return true;
            }
            Optional<PlayerProfile> playerProfile = PlayerProfile.find(player);
            if (playerProfile.isPresent()) {
                RecipeItemGroup recipeItemGroup = RecipeItemGroup.getByItemStack(player, playerProfile.get(), SlimefunGuideMode.SURVIVAL_MODE, item);
                if (recipeItemGroup != null) {
                    GuideHistory guideHistory = playerProfile.get().getGuideHistory();
                    guideHistory.clear();
                    recipeItemGroup.open(player, playerProfile.get(), SlimefunGuideMode.SURVIVAL_MODE);
                } else {
                    GuideHistory guideHistory = playerProfile.get().getGuideHistory();
                    SlimefunGuide.openMainMenu(playerProfile.get(), SlimefunGuideMode.SURVIVAL_MODE, guideHistory.getMainMenuPage());
                }
            }
        } else if(commandSender instanceof ConsoleCommandSender) {

        }
        return true;
    }
}
