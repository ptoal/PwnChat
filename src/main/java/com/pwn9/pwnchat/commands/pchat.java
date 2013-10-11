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

import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.commands.subcommands.*;
import org.bukkit.command.CommandSender;

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
    public boolean sendHelpMsg(CommandSender sender, String alias) {
        sender.sendMessage(GOLD + "PwnChat Commands:");
        return super.sendHelpMsg(sender, alias);
    }
}