/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.config;

import java.io.File;
import java.util.HashMap;

public class PwnChatConfig extends Config {
	
	public PwnChatConfig(com.pwn9.pwnchat.PwnChat plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "PwnChat Configuration";

        ConfigChannel admin = new ConfigChannel();
        admin.description = "Admin-only channel";
        admin.prefix = "A";
        admin.shortcut = "@";
        admin.permission = "pwnchat.channel.admin";
        admin.privacy = true;
        channels.put("admin",admin);


        ConfigChannel global = new ConfigChannel();
        global.description = "Global Channel";
        global.prefix = "G";
        global.shortcut = "*";
        global.permission = "pwnchat.channel.global";
        global.privacy = false;
        channels.put("global",global);
	}

    public boolean Settings_BungeeCord = true;
    public boolean Settings_Factions = true;
    public String Settings_debug = "off";
    public String Settings_defaultChannel = "local";
    public String Settings_defaultFormat = "&7[{GROUP}]&r {DISPLAYNAME}&7:&r {MESSAGE}";
    public String Settings_defaultFactionFormat = "&7[{CHANNELPREFIX}]&r {factions_roleprefix}&r&7{factions_name|rp}&r {DISPLAYNAME}&7:&r {MESSAGE}";
    public String Settings_factionShortcut = ".";

//	public boolean Settings_VaultSupport = true;
//	public boolean Settings_CancelChatEvents = false;
//	public boolean BroadcastToAllServers = false;

    public HashMap<String,ConfigChannel> channels = new HashMap<String, ConfigChannel>();


}
