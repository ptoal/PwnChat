/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.listeners;

////public class FactionChatListener implements Listener {
////
////	PwnChat plugin;
////
////	public FactionChatListener(PwnChat plugin) {
////		this.plugin = plugin;
////	}
////
////	@EventHandler(priority = EventPriority.HIGHEST)
////    public void onPlayerChat(AsyncPlayerChatEvent event) {
////    	if (plugin.getPwnChatConfig().Settings_CancelChatEvents) {
////    		event.setCancelled(true);
////    	}
////    	if (!event.isCancelled()) {
////			Player player = event.getPlayer();
////			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
////			DataOutputStream output = new DataOutputStream(bStream);
////
////			String message = event.getMessage();
////
////			try {
////				output.writeUTF("FactionChat");
////				output.writeUTF(player.getName());
////				output.writeUTF(message);
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////
////			event.getPlayer().sendPluginMessage(this.plugin, "PwnChat", bStream.toByteArray());
////		}
////	}
//
//}
