package com.entertainment.mua.muaxtensions.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class CommandBase implements CommandExecutor, TabCompleter {

    private static class CommandInputException extends Exception {
        public CommandInputException() {
            super("wrong parameter count");
        }
    }

    @Retention(value = RetentionPolicy.RUNTIME)
    @Target(value = ElementType.METHOD)
    protected @interface SubCommand {
        String name();
    }

    public final String name;
    private final int minArgs, maxArgs;
    private final boolean infiniteArgs;
    private final HashMap<SubCommand, Method> subCommands = new HashMap<>();
    private final List<String> tabCompletions;

    public CommandBase(String name, String... tabCompletions) {
        this(name, 0, 0, true, tabCompletions);
    }

    public CommandBase(String name, int minArgs, int maxArgs, String... tabCompletions) {
        this(name, minArgs, maxArgs, false, tabCompletions);
    }

    private CommandBase(String name, int minArgs, int maxArgs, boolean infiniteArgs, String[] tabCompletions) {
        this.name = name;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
        this.infiniteArgs = infiniteArgs;
        this.tabCompletions = Arrays.asList(tabCompletions);

        for (Method m : getClass().getMethods()) {
            SubCommand data = m.getAnnotation(SubCommand.class);
            if (data != null) {
                subCommands.put(data, m);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (infiniteArgs || (args.length >= minArgs && args.length <= maxArgs)) {
                boolean useMainCommand = true;

                if (args.length > 0) {
                    for (Map.Entry<SubCommand, Method> c : subCommands.entrySet()) {
                        if (args[0].equals(c.getKey().name())) {
                            ArrayList<String> _args = new ArrayList<>(Arrays.asList(args));
                            _args.remove(0);
                            c.getValue().invoke(this, sender, _args.toArray(new String[0]));
                            useMainCommand = false;
                        }
                    }
                }

                if (useMainCommand) {
                    onExecute(sender, args);
                    try {
                        Method method = getClass().getMethod("onExecute", CommandSender.class, String[].class);
                    } catch (Exception ignored) { }
                }
            } else {
                throw new CommandInputException();
            }

            return true;
        } catch (CommandInputException ex) {
            sender.sendMessage(ChatColor.RED + ex.getMessage());
            return false;
        } catch (IllegalAccessException | InvocationTargetException ex) {
            System.out.println("[" + ex.getClass().getName() + "]: " + ex.getMessage());
            return false;
        }
    }

    protected void onExecute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.RED + "This Command doesn't exists. Use one of the following commands instead.");
        subCommands.forEach(((subCommand, method) -> {
            sender.sendMessage(ChatColor.GOLD + "/" + name + " " + subCommand.name());
        }));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return tabCompletions;
    }

    protected void executeAsPlayer(CommandSender sender, Consumer<Player> action) {
        if (sender instanceof Player player) {
            action.accept(player);
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
        }
    }
}