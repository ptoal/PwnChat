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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * List Channels
 * User: ptoal
 * Date: 13-07-19
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class listen extends SubCommand {

    public listen(PwnChat instance) {
        super(instance,"listen");
        setUsage("listen <channel>");
        setDescription("Listen to a channel.");
        setPermission("pwnchat.listen");
    }

    public boolean execute(CommandSender sender, String commandName, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(PwnChat.PREFIX + " Only players can execute this command.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(getUsage());
            return true;
        }

        if (args[1].equalsIgnoreCase("local")) {
            sender.sendMessage(PwnChat.PREFIX+" You always listen to the local server channel.");
        }

        Chatter chatter = ChatterManager.getInstance().getOrCreateChatter((Player)sender);
        Channel channel = ChannelManager.getInstance().getChannel(args[1]);

        if (channel == null ) {
            sender.sendMessage(PwnChat.PREFIX + " Channel named: " + args[1] + " does not exist!");
            return true;
        }


        if (channel.hasChatter(chatter)) {
            sender.sendMessage(PwnChat.PREFIX + " You are already listening to that channel!");
            return true;
        }

        if (channel.addChatter(chatter)) {
            sender.sendMessage(PwnChat.PREFIX + " You will now hear chat from the '" + channel.getName() + "' channel.");
        } else {
            sender.sendMessage(PwnChat.PREFIX + " You aren't allowed to listen to the '" + channel.getName() + "' channel!");
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        return ChannelManager.getInstance().getCompletions(sender, args);
    }
}
