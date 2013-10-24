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

import com.pwn9.pwnchat.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Listen for Player join events and set up their default channels.
 * User: ptoal
 * Date: 13-07-15
 * Time: 10:14 PM
 */
public class PlayerJoinListener implements Listener {
    private final PwnChat plugin;

    public PlayerJoinListener(PwnChat instance) {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();
        Chatter chatter = ChatterManager.getInstance().getOrCreate(p);

        StringBuilder channelMessage = new StringBuilder();

        for (Channel c: ChannelManager.getInstance().getChannelList()) {
            if(chatter.addChannel(c)) {
                channelMessage.append(c.getName()).append(",");
            }
        }
        Channel defaultChannel = ChannelManager.getInstance().getDefaultChannel();
        if (defaultChannel != null) {
            chatter.setFocus(defaultChannel);
        }
        channelMessage.deleteCharAt(channelMessage.length()-1);
        event.getPlayer().sendMessage(PwnChat.PREFIX + " Listening to: " + channelMessage.toString());

    }
}
