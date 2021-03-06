/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.commands.subcommands;

import com.pwn9.pwnchat.*;
import com.pwn9.pwnchat.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * List Channels
 * User: ptoal
 * Date: 13-07-19
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class list extends SubCommand {

    public list(PwnChat instance) {
        super(instance,"list");
        setUsage("list");
        setDescription("List available channels.");
        setPermission("pwnchat.list");
    }

    public boolean execute(CommandSender sender, String commandName, String[] args) {

        sender.sendMessage(ChatColor.GOLD + "Available Chat Channels "
                +ChatColor.RED + "[*Talk]"
                +ChatColor.GREEN+"[Listening]"
                +ChatColor.BLUE+"[Silenced]");
        sender.sendMessage(ChatColor.WHITE + "+-------------------------------------------+");

        Chatter chatter = null;

        if (sender instanceof Player) {
            chatter = ChatterManager.getInstance().getOrCreate((Player) sender);
        }

        String prefix;

        for (Channel channel : ChannelManager.getInstance().getChannelList()) {
            if (channel.hasPermission(chatter)) {
                prefix = ChatColor.BLUE + "[S] ";
                if (channel.hasChatter(chatter)) {
                    prefix = ChatColor.GREEN + "[L] ";
                    if (chatter != null && chatter.isFocused(channel)) {
                        prefix = ChatColor.RED + "[*] ";
                    }
                }
                prefix = prefix + "("+channel.getPrefix()+") ";
                sender.sendMessage(" " + prefix + channel.getName() + " - " + ChatColor.WHITE + channel.getDescription());
            }
        }
        return true;
    }

}
