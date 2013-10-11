/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.tasks;

import com.pwn9.pwnchat.PwnChat;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;

public class PluginMessageTask extends BukkitRunnable {
	
	private final PwnChat plugin;
	private final Player player;
	private ByteArrayOutputStream bytes;
	
	public PluginMessageTask(PwnChat plugin, Player player, ByteArrayOutputStream bytes) {
		this.plugin = plugin;
		this.player = player;
		this.bytes = bytes;
	}

	public void run() {
		player.sendPluginMessage(this.plugin, "BungeeCord", this.bytes.toByteArray());
	}

}
