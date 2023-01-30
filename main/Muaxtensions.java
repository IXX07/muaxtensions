package com.entertainment.mua.muaxtensions.main;

import com.entertainment.mua.muaxtensions.commands.CopyCommand;
import com.entertainment.mua.muaxtensions.commands.PingCommand;
import com.entertainment.mua.muaxtensions.listeners.PlayerJoinListener;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Muaxtensions extends JavaPlugin {

    private final ListenerBase[] listeners = new ListenerBase[] {
        new PlayerJoinListener()
    };

    private final CommandBase[] commands = new CommandBase[] {
        new PingCommand(),
        new CopyCommand()
    };

    @Override
    public void onEnable() {
        var pluginManager = getServer().getPluginManager();

        for (var l : listeners) {
            pluginManager.registerEvents(l, this);
        }

        for (var c : commands) {
            getCommand(c.name).setExecutor(c);
        }

        System.out.println("uaxtensions plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        System.out.println("Muaxtensions plugin has been disabled.");
    }
}