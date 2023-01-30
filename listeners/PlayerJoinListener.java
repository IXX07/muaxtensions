package com.entertainment.mua.muaxtensions.listeners;

import com.entertainment.mua.muaxtensions.main.ListenerBase;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;

public class PlayerJoinListener extends ListenerBase {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var greetings = new String[] {
            "Nice to see you", "Hi", "Welcome"
        };
        var ran = new Random();

        var grt = greetings[ran.nextInt(greetings.length)];
        var name = e.getPlayer().getName();

        e.setJoinMessage(ChatColor.GREEN + grt + ", " + name + "!");
    }
}