package io.github.trainb0y1.spaceplugin.commands;

import io.github.trainb0y1.spaceplugin.items.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("givewand")) {
            player.getInventory().addItem(ItemManager.wand);
        }
        if (cmd.getName().equalsIgnoreCase("giveblaster")) {
            player.getInventory().addItem(ItemManager.blaster);
        }
        if (cmd.getName().equalsIgnoreCase("givehandcannon")) {
            player.getInventory().addItem(ItemManager.handcannon);
        }
        if (cmd.getName().equalsIgnoreCase("givejetpack")) {
            player.getInventory().addItem(ItemManager.jetpack);
        }
        return true;
    }

}
