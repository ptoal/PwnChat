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
