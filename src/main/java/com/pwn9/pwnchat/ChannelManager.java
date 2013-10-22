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

import com.pwn9.pwnchat.config.ConfigChannel;
import com.pwn9.pwnchat.config.PwnChatConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton for managing all server channels
 * User: ptoal
 * Date: 13-10-10
 * Time: 11:41 AM
 */
public class ChannelManager {

    private static ChannelManager _instance = null;
    private ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<String, Channel>();
    private Channel local;

    private ChannelManager() {}

    public static ChannelManager getInstance() {

        if (_instance == null) {
            _instance = new ChannelManager();
        }
        return _instance;
    }

    public void setupChannels(PwnChat p, PwnChatConfig config) {
        // Always make sure the local channel is set up.
        getLocal();
        p.getLogger().info("Configured Channel: " + local.getName());

        LogManager lm = LogManager.getInstance();

        for ( Map.Entry<String, ConfigChannel> channelEntry : config.channels.entrySet()) {
            StringBuilder sb = new StringBuilder();
            Channel chan = channels.get(channelEntry.getKey());
            if (chan == null ) {
                chan = new Channel(channelEntry.getKey());
            }
            ConfigChannel configChannel = channelEntry.getValue();
            sb.append("Configuring Channel <" + channelEntry.getKey() + ">");
            chan.setDescription(configChannel.description);
            sb.append(" Description: " + configChannel.description);
            chan.setPermission(configChannel.permission);
            sb.append(" Permission: " + configChannel.permission);
            chan.setPrefix(configChannel.prefix);
            sb.append(" Prefix: " + configChannel.prefix);
            chan.setPrivate(configChannel.privacy);
            sb.append(" Privacy: " + configChannel.privacy);
            chan.setShortcut(configChannel.shortcut);
            sb.append(" Shortcut: " + configChannel.shortcut);
            lm.debugMedium(sb.toString());
            channels.put(chan.getName(), chan);
            chan.registerBridge(); // Register this channel with the bridge
            p.getLogger().info("Configured Channel: " + chan.getName());
        }

    }

    public Channel getChannel(String name) {
        return channels.get(name);
    }

    public Collection<Channel> getChannelList() {
        return channels.values();
    }

    public Channel getLocal() {
        local = channels.get("local");

        // Set up the local server channel if it doesn't exist.
        if (local == null) {
            local = new Channel("local");
            local.setDescription("Local Server (default)");
            local.setPrefix("L");
            local.setPrivate(false);
            channels.put("local",local);
        }
        return local;

    }

    public List<String> getCompletions(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<String>();

        Collection<Channel> channelList;

        if (args.length == 2 ) {

            if (sender instanceof ConsoleCommandSender) {
                channelList = ChannelManager.getInstance().getChannelList();
            } else if (sender instanceof Player){
                Chatter chatter = ChatterManager.getInstance().getOrCreateChatter((Player)sender);
                channelList = chatter.permittedChannels();
            } else {
                channelList = Collections.emptyList();
            }

            for ( Channel channel : channelList ) {
                if (channel.getName().startsWith(args[1])) completions.add(channel.getName());
            }

        }

        if (completions.isEmpty()) return null;

        return completions;

    }

}
