package com.entertainment.mua.muaxtensions.commands;

import com.entertainment.mua.muaxtensions.main.CommandBase;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PingCommand extends CommandBase {

    public PingCommand() {
        super("ping");
    }

    @Override
    protected void onExecute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.AQUA + "pong");
    }
}