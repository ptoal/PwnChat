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

import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.commands.SubCommand;
import org.bukkit.command.CommandSender;

/**
 * Reload configs
 * User: ptoal
 * Date: 13-07-19
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class reload extends SubCommand {

    public reload(PwnChat instance) {
        super(instance,"reload");
        setUsage("reload");
        setDescription("Reload config.");
        setPermission("pwnchat.reload");
    }

    public boolean execute(CommandSender sender, String commandName, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage(PwnChat.PREFIX + " Reloaded configuration.");
        return true;
    }

}
