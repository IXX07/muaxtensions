package com.entertainment.mua.muaxtensions.commands;

import com.entertainment.mua.muaxtensions.main.CommandBase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CopyCommand extends CommandBase {

    public CopyCommand() {
        super("copy");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            var pos = 0;
            var blockLoc = player.getTargetBlock(null, 100).getLocation();

            if (args.length == 1 || args.length == 4 || args.length == 7) pos = blockLoc.getBlockX();
            else if (args.length == 2 || args.length == 5 || args.length == 8) pos = blockLoc.getBlockY();
            else if (args.length == 3 || args.length == 6 || args.length == 9) pos = blockLoc.getBlockZ();

            return List.of(String.valueOf(pos));
        }
        else return List.of();
    }

    @Override
    protected void onExecute(CommandSender sender, String[] args) {

    }
}