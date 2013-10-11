/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton for managing all server channels
 * User: ptoal
 * Date: 13-10-10
 * Time: 11:41 AM
 */
public class ChatterManager {

    private static ChatterManager _instance = null;
    private ConcurrentHashMap<String,Chatter> chatters = new ConcurrentHashMap<String, Chatter>();

    public static ChatterManager getInstance() {

        if (_instance == null) {
            _instance = new ChatterManager();
        }
        return _instance;
    }

    public Chatter getByName(String playerName) {
        return chatters.get(playerName);
    }

    public Chatter getOrCreateChatter(Player player) {

        if (player == null) return null;

        String playerName = player.getName();

        Chatter c = chatters.get(playerName);

        if (c == null) {
            c = new Chatter(playerName, player);
            chatters.put(playerName,c);
        }

        return c;

    }

    public void removeChatter(Chatter c) {
        for (Channel channel : c.getChannels()) {
            channel.removeChatter(c);
        }
        chatters.remove(c);
    }

    public Collection<Chatter> getAllChatters() {
        return chatters.values();
    }

}
