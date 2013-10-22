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
public class silence extends SubCommand {

    public silence(PwnChat instance) {
        super(instance,"silence");
        setUsage("silence <channel>");
        setDescription("Silence a channel.");
        setPermission("pwnchat.silence");
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

        Channel channel = ChannelManager.getInstance().getChannel(args[1].toLowerCase());

        if (channel == null ) {
            sender.sendMessage(PwnChat.PREFIX + " Channel named: " + args[1] + " does not exist!");
            return true;
        }

        if (channel == ChannelManager.getInstance().getLocal()) {
            sender.sendMessage(PwnChat.PREFIX + " You can't silence the local server channel!");
            return true;
        }

        Chatter chatter = ChatterManager.getInstance().getOrCreateChatter((Player)sender);

        if (channel.hasChatter(chatter)) {
            if (chatter.isFocused(channel)) {
                chatter.setFocus(ChannelManager.getInstance().getLocal());
                sender.sendMessage(PwnChat.PREFIX + " Changing talk channel to local server channel.");
            }
            channel.removeChatter(chatter);
            sender.sendMessage(PwnChat.PREFIX + " You will no longer hear chat from the '" + channel.getName() + "' channel.");
        } else {
            sender.sendMessage(PwnChat.PREFIX + " You weren't listening to the channel named: " + channel.getName() + "!  Nothing done.");
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        return ChannelManager.getInstance().getCompletions(sender, args);
    }

}
