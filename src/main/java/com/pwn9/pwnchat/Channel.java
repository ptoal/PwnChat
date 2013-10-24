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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
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
   // TODO: Add flag for private channels, so they can be cancelled, to avoid showing in IRC
    private String name;
    private String description;
    private String prefix;
    private Character shortcut;
    private String permission = "";
    private boolean privateChannel = true;
    private Set<Chatter> chatters = Collections.newSetFromMap(new ConcurrentHashMap<Chatter,Boolean>());
    private Set<Player> recipients = Collections.newSetFromMap(new ConcurrentHashMap<Player, Boolean>());

    public Channel(String name) {
        this.name = name.toLowerCase();
    }

    public Collection<Chatter> getChatters() {
        return chatters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
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
            LogManager.getInstance().debugMedium("Added " + c.getPlayerName() + " to [" + this.getName() + "]");
            return true;
        } else return false;
    }

    /* Remove this channel. */
    public void remove() {
        removeAllChatters();
        ChannelManager.getInstance().remove(this);
    }

    public boolean removeChatter(Chatter c) {
        this.chatters.remove(c);
        this.recipients.remove(c.getPlayer());
        LogManager.getInstance().debugMedium("Removed " + c.getPlayerName() + " from [" + this.getName() + "]");
        return true;
    }

    public boolean hasChatters() {
        return !chatters.isEmpty();
    }

    public void removeAllChatters() {
        for (Chatter c : chatters ) {
            c.removeChannel(this);
        }
        if (!chatters.isEmpty()) throw new IllegalStateException("Unable to remove all chatters from channel: " + this.name);
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

    public boolean isPrivateChannel() {
        return privateChannel;
    }

    public void setPrivate(boolean privateChannel) {
        this.privateChannel = privateChannel;
    }

    public void sendMessage(final Plugin p, final String playerName, final String format, final String chatMessage) {
        StringBuilder recipients = new StringBuilder();
        for (Player r : getRecipients()) { recipients.append(r.getName()).append(" "); }
        LogManager.getInstance().debugMedium("Sending message: " + chatMessage + " to [" + recipients.toString().trim() + "]");

        Bukkit.getScheduler().scheduleSyncDelayedTask(p, new BukkitRunnable() {
            @Override
            public void run() {

                // For now, just send the message to players directly.
                for (Player p : getRecipients() ) {
                    p.sendMessage(String.format(format,
                            playerName,chatMessage));
                }
            }
        });

    }

    public Character getShortcut() {
        return shortcut;
    }

    public void setShortcut(Character shortcut) {
        this.shortcut = shortcut;
    }
}
