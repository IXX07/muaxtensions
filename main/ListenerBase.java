package com.entertainment.mua.muaxtensions.main;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.Arrays;
import java.util.function.Consumer;

public abstract class ListenerBase implements Listener {

    protected boolean isInWorld(Player player, String... worldNames) {
        for (String wn : worldNames) {
            if (player.getWorld().getName().equals(wn)) {
                return true;
            }
        }

        return false;
    }

    protected boolean isLocated(Location loc, double x, double y, double z) {
        return loc.getX() == x && loc.getY() == y && loc.getZ() == z;
    }

    protected <T> boolean matches(T obj, T... check) {
        return Arrays.asList(check).contains(obj);
    }

    protected void doInWorlds(Player player, Consumer<World> action, Runnable alternativeAction, World... worlds) {
        boolean executed = false;

        for (World w : worlds) {
            World world = player.getWorld();
            if (world.getSeed() == world.getSeed()) {
                action.accept(world);
                executed = true;
            }
        }

        if (!executed) {
            alternativeAction.run();
        }
    }

    protected void doInWorlds(Player player, Consumer<World> action, World... worlds) {
        doInWorlds(player, action, () -> { }, worlds);
    }
}