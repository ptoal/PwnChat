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

import com.pwn9.pwnchat.Chatter;
import com.pwn9.pwnchat.ChatterManager;
import com.pwn9.pwnchat.PwnChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Listen for Player join events and set up their default channels.
 * User: ptoal
 * Date: 13-07-15
 * Time: 10:14 PM
 */
public class PlayerQuitListener implements Listener {
    private final PwnChat plugin;

    public PlayerQuitListener(PwnChat instance) {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        /*
        Every time someone leaves, check to see how many players are online,
        and if there are more than 20 chatters over that number, clean out
        logged off players.

        Why do this?  It's kind-of a quick hack to prevent memory leaks, while
        at the same time caching the players joined channels for a little while.

        TODO: Synchronize player channels / settings across servers.

        */

        List<Player> onlinePlayers = new ArrayList<Player>(Arrays.asList(plugin.getServer().getOnlinePlayers()));

        if (ChatterManager.getInstance().getAllChatters().size() -
                onlinePlayers.size() > 20 ) {

            for (Chatter chatter : ChatterManager.getInstance().getAllChatters()) {
               if (!onlinePlayers.contains(chatter.getPlayer())) {
                    ChatterManager.getInstance().removeChatter(chatter);
                }
            }
        }
    }
}
