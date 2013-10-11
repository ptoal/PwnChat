package com.pwn9.pwnchat;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Information about a Player's Chat channels.
 * User: ptoal
 * Date: 13-10-09
 * Time: 7:17 PM
 */
public class Chatter {

    private String playerName; // Player name
    private Set<Channel> channels; //All channels this player is permitted to join
    private Channel focus; // Channel this player is talking in. (default = local = local server)
    private Player player;

    public Chatter(String playerName, Player p) {
        if ( playerName == null ) throw new IllegalArgumentException("PlayerName can not be null");
        this.playerName = playerName;
        this.player = p;
        focus = ChannelManager.getInstance().getLocal();
        channels = new HashSet<Channel>();
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

    public void addChannel(Channel c) {
        channels.add(c);
    }

    public void removeChannel(Channel c) {
        channels.remove(c);
    }

    public Channel getFocus() {
        return focus;
    }

    public void setFocus(Channel focus) {
        this.focus = focus;
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
