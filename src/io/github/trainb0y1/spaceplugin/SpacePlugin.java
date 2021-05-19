package io.github.trainb0y1.spaceplugin;

import io.github.trainb0y1.spaceplugin.commands.ItemCommands;
import io.github.trainb0y1.spaceplugin.events.DoubleJumpEvents;
import io.github.trainb0y1.spaceplugin.events.ItemEvents;
import io.github.trainb0y1.spaceplugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class SpacePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Send enable message
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpacePlugin]: Plugin Enabled");
        ItemManager.init();
        getServer().getPluginManager().registerEvents(new ItemEvents(), this);
        getServer().getPluginManager().registerEvents(new DoubleJumpEvents(), this);
        getCommand("givewand").setExecutor(new ItemCommands());
        getCommand("giveblaster").setExecutor(new ItemCommands());
        getCommand("givehandcannon").setExecutor(new ItemCommands());
        getCommand("givejetpack").setExecutor(new ItemCommands());
    }
    @Override
    public void onDisable() {
        // Send disable message
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpacePlugin]: Plugin Disabled");

    }

}
