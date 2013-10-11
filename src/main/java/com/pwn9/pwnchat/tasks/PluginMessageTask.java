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
