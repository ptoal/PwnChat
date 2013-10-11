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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chat Channel
 * User: ptoal
 * Date: 13-10-09
 * Time: 7:23 PM
 */
public class Channel {

    private String name;
    private String description;
    private String prefix;
    private String permission = "";
    private Set<Chatter> chatters = Collections.newSetFromMap(new ConcurrentHashMap<Chatter,Boolean>());
    private Set<Player> recipients = Collections.newSetFromMap(new ConcurrentHashMap<Player, Boolean>());

    public Channel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void registerBridge() {
        return; // stub
    }

    public boolean addChatter(Chatter c) {
        if ( c == null ) return false;
        if (hasPermission(c)) {
            this.chatters.add(c);
            this.recipients.add(c.getPlayer());
            c.addChannel(this);
            return true;
        } else return false;
    }

    public void removeChatter(Chatter c) {
        c.removeChannel(this);
        this.chatters.remove(c);
        this.recipients.remove(c.getPlayer());
    }

    public boolean hasPermission(Chatter c) {
        if (c == null || this.permission.isEmpty()) return true;

        Player p = Bukkit.getPlayer(c.getPlayerName());
        return p.hasPermission(permission);
    }

    public boolean hasChatter(Chatter c) {
        if (c == null) return false;
        return chatters.contains(c);
    }

    public Set<Player> getRecipients() {
        return recipients;
    }

}
