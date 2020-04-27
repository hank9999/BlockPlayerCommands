package com.github.hank9999.BlockPlayerCommands.Command;

import com.github.hank9999.BlockPlayerCommands.BlockPlayerCommands;
import com.github.hank9999.BlockPlayerCommands.Libs.Lib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class bpc implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("blockplayercommands") || command.getName().equalsIgnoreCase("bpc")) {
            if (strings.length == 0) {
                commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rUse &7/bpc help to get help"));
                return true;
            }
            if (strings[0].equalsIgnoreCase("help")) {
                commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rUse &7/bpc help &rto get help"));
                commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rUse &7/bpc reload &rto reload"));
                commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rUse &7/bpc list &rto get list"));
                commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rUse &7/bpc add <playername> &rto add"));
                commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rUse &7/bpc del <playername> &rto del"));
                return true;
            }
            if (strings[0].equalsIgnoreCase("reload")) {
                BlockPlayerCommands.plugin.reloadConfig();
                commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rReload Config"));
                return true;
            }
            if (strings[0].equalsIgnoreCase("list")) {
                commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rPlayer Black List:"));
                if (BlockPlayerCommands.plugin.getConfig().getStringList("blocklist").size() == 0) {
                    commandSender.sendMessage(Lib.color_translate(" - &3Null"));
                    return true;
                }
                for (String playername : BlockPlayerCommands.plugin.getConfig().getStringList("blocklist")) {
                    if (playername.equalsIgnoreCase("")) {
                        continue;
                    }
                    commandSender.sendMessage(Lib.color_translate(" - &3" + playername));
                }
                return true;
            }
            if (strings[0].equalsIgnoreCase("add")) {
                if (strings.length == 1) {
                    commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rPlease add with playername"));
                    return true;
                }
                List<String> temp = BlockPlayerCommands.plugin.getConfig().getStringList("blocklist");
                boolean containsSearchStr = temp.stream().anyMatch(strings[1]::equalsIgnoreCase);
                if (containsSearchStr) {
                    commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rThis black player already exists"));
                } else {
                    temp.add(strings[1]);
                    BlockPlayerCommands.plugin.getConfig().set("blocklist", temp);
                    BlockPlayerCommands.plugin.saveConfig();
                    BlockPlayerCommands.plugin.reloadConfig();
                    String message = Lib.color_translate("&2[&eBPC&2] &9" + commandSender.getName() + " &radd a black player &3" + strings[1]);
                    BlockPlayerCommands.plugin.getServer().broadcast(message, "BlockPlayerCommands.controllist");
                }

                return true;
            }
            if (strings[0].equalsIgnoreCase("del")) {
                if (strings.length == 1) {
                    commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rPlease add with playername"));
                    return true;
                }
                List<String> temp = BlockPlayerCommands.plugin.getConfig().getStringList("blocklist");
                if (temp.contains(strings[1])) {
                    temp.remove(strings[1]);
                    BlockPlayerCommands.plugin.getConfig().set("blocklist", temp);
                    BlockPlayerCommands.plugin.saveConfig();
                    BlockPlayerCommands.plugin.reloadConfig();
                    String message = Lib.color_translate("&2[&eBPC&2] &9" + commandSender.getName() + " &rdel a black player &3" + strings[1]);
                    BlockPlayerCommands.plugin.getServer().broadcast(message, "BlockPlayerCommands.controllist");

                } else {
                    commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &rThis black player is not exist"));
                }
                return true;
            }
            commandSender.sendMessage(Lib.color_translate("&2[&eBPC&2] &cUnknown Command"));
            return true;
        }
        return true;
    }
    private String[] Commands = {"help", "reload", "list", "add", "del"};
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) return Arrays.asList(Commands);
        if (args[0].equalsIgnoreCase("add")) {
            return null;
        }
        if (args[0].equalsIgnoreCase("del")) {
            return BlockPlayerCommands.plugin.getConfig().getStringList("blocklist");
        }
        if (args.length > 1) return new ArrayList<>();
        return Arrays.stream(Commands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }
}
