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

import com.pwn9.pwnchat.factions.FactionChannel;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Information about a Player's Chat channels.
 * User: ptoal
 * Date: 13-10-09
 * Time: 7:17 PM
 */
public class Chatter {

    private String playerName; // Player name
    private Set<Channel> channels; //All channels this player is listening to.
    private Channel focus; // Channel this player is talking in. (default = local = local server)
    private Player player;

    public Chatter(String playerName, Player p) {
        if ( playerName == null ) throw new IllegalArgumentException("PlayerName can not be null");
        this.playerName = playerName;
        this.player = p;
        focus = ChannelManager.getInstance().getLocal();
        channels = Collections.newSetFromMap(new ConcurrentHashMap<Channel,Boolean>());
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    public boolean addChannel(Channel c) {
        if (!c.hasPermission(this)) return false;
        if (c.addChatter(this)) {
            channels.add(c);
            return true;
        }
        return false;
    }

    public boolean removeChannel(Channel c) {
        c.removeChatter(this);
        channels.remove(c);
        return true;
    }

    public void removeChannels() {
        for (Channel c : channels) {
            c.removeChatter(this);
            channels.remove(c);
        }
    }

    public void remove() {
        removeChannels();
        ChatterManager.getInstance().remove(this);
    }

    public Channel getFocus() {
        return focus;
    }

    public void setFocus(Channel focus) {
        this.focus = focus;
    }

    public boolean setFocusFaction() {
        Channel fChannel = FactionChannel.getForChatter(this);
        if (fChannel != null) {
            setFocus(fChannel);
            return true;
        }
        return false;
    }

    public boolean isFocused(Channel c) {
        return getFocus() == c;
    }

    public List<Channel> permittedChannels() {
        List<Channel> retval = new ArrayList<Channel>();
        for (Channel channel : ChannelManager.getInstance().getChannelList()) {
            if (channel.hasPermission(this)) {
                retval.add(channel);
            }
        }
        return retval;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
