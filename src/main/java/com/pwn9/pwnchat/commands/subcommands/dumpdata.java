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

import java.util.logging.Logger;

/**
 * Reload configs
 * User: ptoal
 * Date: 13-07-19
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class dumpdata extends SubCommand {

    public dumpdata(PwnChat instance) {
        super(instance,"dumpdata");
        setUsage("dumpdata");
        setDescription("Dump internal data to logfile.");
        setPermission("pwnchat.debug");
    }

    public boolean execute(CommandSender sender, String commandName, String[] args) {
        Logger l = LogManager.logger;
        for (Channel c : ChannelManager.getInstance().getChannelList()) {
            l.info("Channel: " + c.getName() + " Listeners: " + c.getRecipients().size());
        }
        for (Chatter chatter : ChatterManager.getInstance().getAll()) {
            StringBuilder sb = new StringBuilder();
            for (Channel chan : chatter.getChannels()) {
                sb.append(" " + chan.getName());
            }
            l.info("  Chatter: " + chatter.getPlayerName() + " Talking: " + chatter.getFocus().getName());
            l.info("   Other Channels:" + sb.toString());
        }

        sender.sendMessage(PwnChat.PREFIX + " Check pwnchat.log for data.");
        return true;
    }

}
