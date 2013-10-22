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
public class talk extends SubCommand {

    public talk(PwnChat instance) {
        super(instance,"talk");
        setUsage("talk <channel>");
        setDescription("Change your default channel.");
        setPermission("pwnchat.talk");
    }

    public boolean execute(CommandSender sender, String commandName, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(PwnChat.PREFIX + " Only players can execute this command.");
            return true;
        }
        Chatter chatter = ChatterManager.getInstance().getOrCreateChatter((Player)sender);

        if (args.length < 2) {
            sender.sendMessage(getUsage());
            return true;
        }

        if (args[1].equalsIgnoreCase("local")) {
            chatter.setFocus(null);
            sender.sendMessage(PwnChat.PREFIX + " You are now talking in the local server channel.");
            return true;
        }

        Channel channel = ChannelManager.getInstance().getChannel(args[1].toLowerCase());

        if (channel == null ) {
            sender.sendMessage(PwnChat.PREFIX + " Channel named: " + args[1] + " does not exist!");
            return true;
        }

        if (channel.hasChatter(chatter)) {
            chatter.setFocus(channel);
            sender.sendMessage(PwnChat.PREFIX+" You are now talking in the "+ channel.getName() + " channel.");
            return true;
        } else {
            sender.sendMessage(PwnChat.PREFIX + " You must be listening to the channel before you can talk in it!");
            return true;
        }

    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        return ChannelManager.getInstance().getCompletions(sender, args);
    }

}
