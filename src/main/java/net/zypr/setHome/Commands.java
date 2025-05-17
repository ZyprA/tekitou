package net.zypr.setHome;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (command.getName().equalsIgnoreCase("sthome")) {
                SetHome.getInstance().getHomeMapManager().addPlayerLocation(player.getUniqueId(), player.getLocation());
                player.sendMessage("更新したにょ");
                return true;
            } else if (command.getName().equalsIgnoreCase("home")) {
                Location location = SetHome.getInstance().getHomeMapManager().getPlayerLocation(player.getUniqueId());
                if (location != null) {
                    player.teleport(location);
                }
                return true;
            }

        }
        return false;
    }

}
