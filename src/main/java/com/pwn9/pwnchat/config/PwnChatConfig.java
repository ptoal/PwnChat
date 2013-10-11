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

        HashMap<String, String> admin = new HashMap<String, String>();
        admin.put("description","Admin-only channel");
        admin.put("prefix","@");
        admin.put("permission","pwnchat.channel.admin");
        channels.put("admin",admin);

        HashMap<String, String> global = new HashMap<String, String>();
        global.put("description","Global Channel");
        global.put("prefix","G");
        global.put("permission","pwnchat.channel.global");
        channels.put("global",global);

	}
	
//	public boolean Settings_VaultSupport = true;
	public boolean Settings_FactionServer = false;
//	public boolean Settings_CancelChatEvents = false;
//	public boolean BroadcastToAllServers = false;

    public HashMap<String,HashMap<String,String>> channels = new HashMap<String, HashMap<String, String>>();



}
