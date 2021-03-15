package me.interuptings.inventoryshrink.commands;

import com.google.common.collect.ImmutableList;
import me.interuptings.inventoryshrink.InventoryShrink;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/15/2021
 *
 * @author Dylan
 */
public class ShrinkCommand implements CommandExecutor, TabCompleter {

    private final InventoryShrink plugin;

    public ShrinkCommand(InventoryShrink plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            this.sendHelp(sender);
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "on":
            case "enable":
                plugin.enable();
                sender.sendMessage(ChatColor.GRAY + "The plugin has been " + ChatColor.GREEN + "enabled");
                break;
            case "off":
            case "disable":
                plugin.disable();
                sender.sendMessage(ChatColor.GRAY + "The plugin has been " + ChatColor.RED + "disabled");
                break;
            case "toggle":
                plugin.toggle();
                sender.sendMessage(ChatColor.GRAY + "The plugin has been " + (plugin.isItEnabled() ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));
                break;
            case "developer":
            default:
                this.sendHelp(sender);
        }

        return false;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8»&m-------------------------------------------------&r&8«"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7This plugin was made by &6Interuptings/Dylan&7."));
        sender.sendMessage("");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &b@Interuptings"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &7Dylan#6678"));
        sender.sendMessage("");
        sender.sendMessage(ChatColor.RED + "  Usage: /invshrink <on/off/toggle>");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8»&m-------------------------------------------------&r&8«"));
    }

    private static final List<String> COMMANDS = ImmutableList.of("on", "enable", "off", "disable", "toggle", "developer");
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return (args.length == 1) ? StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>()) : null;
    }
}
