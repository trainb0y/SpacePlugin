package com.trainb0y.spaceplugin;

import com.trainb0y.spaceplugin.commands.ItemCommands;
import com.trainb0y.spaceplugin.events.DoubleJumpEvents;
import com.trainb0y.spaceplugin.events.ItemEvents;
import com.trainb0y.spaceplugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class SpacePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Send enable message
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpaceCustomItems]: Plugin Enabled");
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
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpaceCustomItems]: Plugin Disabled");

    }

}
