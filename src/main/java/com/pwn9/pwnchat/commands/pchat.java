/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.commands;

import com.pwn9.pwnchat.Channel;
import com.pwn9.pwnchat.ChannelManager;
import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.GOLD;

/**
 * Main command handler for all /pr commands.
 */
public class pchat extends BaseCommandExecutor {

    public pchat(PwnChat instance) {
        super(instance);
        addSubCommand(new reload(instance));
        addSubCommand(new list(instance));
        addSubCommand(new listen(instance));
        addSubCommand(new silence(instance));
        addSubCommand(new talk(instance));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        // If this is a channel prefix, then send the message to that channel.

        if (args.length > 1) {
            for (Channel c : ChannelManager.getInstance().getChannelList()) {
                if (c.getPrefix().equalsIgnoreCase(args[0]) && sender.hasPermission(c.getPermission())) {
                    int i;
                    StringBuilder message = new StringBuilder();
                    for ( i = 1 ; i < args.length ; i++ ) {
                        message.append(args[i]).append(" ");
                    }
                    String format = plugin.getFormat(sender, c);
                    c.sendMessage(plugin,sender.getName(),format,message.toString().trim());
                    if (sender instanceof Player) {
                        plugin.sendToChannel((Player)sender, c, format, message.toString().trim());
                    }
                    return true;
                }
            }
        }

        return super.onCommand(sender, command, alias, args);
    }

    @Override
    public boolean sendHelpMsg(CommandSender sender, String alias) {
        sender.sendMessage(GOLD + "PwnChat Commands:");
        return super.sendHelpMsg(sender, alias);
    }
}