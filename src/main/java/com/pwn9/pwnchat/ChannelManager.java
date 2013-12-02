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
import com.pwn9.pwnchat.factions.FactionChannel;
import com.pwn9.pwnchat.utils.LogManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
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
    private HashMap<Character, Channel> shortcuts = new HashMap<Character,Channel>();
    private Channel local;
    private Channel defaultChannel;
    private PwnChat plugin;

    private ChannelManager() {}

    public static ChannelManager getInstance() {

        if (_instance == null) {
            _instance = new ChannelManager();
        }
        return _instance;
    }

    public void setupChannels(PwnChat p, PwnChatConfig config) {

        // Always make sure the local channel is set up.
        this.plugin = p;
        getLocal();

        FactionChannel.setupFactionsDefaults(p,config);

        p.getLogger().info("Configured Channel: " + local.getName());


        for ( Map.Entry<String, ConfigChannel> channelEntry : config.channels.entrySet()) {
            Channel chan = channels.get(channelEntry.getKey().toLowerCase());
            if (chan == null ) {
                chan = new Channel(channelEntry.getKey());
                p.getLogger().info("Updating Channel: " + chan.getName());
            }

            ConfigChannel configChannel = channelEntry.getValue();

            chan.setDescription(configChannel.description);
            chan.setPermission(configChannel.permission);
            chan.setPrefix(configChannel.prefix);
            chan.setPrivate(configChannel.privacy);
            chan.setShortcut(configChannel.shortcut.charAt(0));

            String format;
            if (configChannel.format != null && !configChannel.format.isEmpty()) {
                format = configChannel.format;
            } else {
                format = config.Settings_defaultFormat;
            }

            chan.setFormat(parseFormat(format));

            LogManager.getInstance().debugMedium("Channel: " + chan.getName() + " Format: " + chan.getFormat().toPattern());


            save(chan, false);
            chan.registerBridge(); // Register this channel with the bridge

            LogManager.logger.info("Channel Active: " + chan.getName());

        }

        String defaultName = config.Settings_defaultChannel.toLowerCase();

        if (ChannelManager.getInstance().exists(defaultName)) {
            LogManager.logger.info("Setting Default Channel to: " + defaultName);
            defaultChannel = channels.get(defaultName);
        }

    }

    public static MessageFormat parseFormat(String format) {
        format = format.replace("{DISPLAYNAME}", "%1$s")
                .replace("{MESSAGE}", "%2$s")
                .replace("{GROUP}", "{0}")
                .replace("{WORLDNAME}", "{1}")
                .replace("{TEAMPREFIX}", "{2}")
                .replace("{TEAMSUFFIX}", "{3}")
                .replace("{TEAMNAME}", "{4}")
                .replace("{CHANNELPREFIX}", "{5}")
                .replaceAll("\\{factions_roleprefix[^}]*}","{6}")
                .replaceAll("\\{factions_name[^}]*}", "{7}");
        format = "§r".concat(format);
        format = ChatColor.translateAlternateColorCodes('&',format);

        return new MessageFormat(format);
    }

    public Channel getDefaultChannel() {
        return defaultChannel;
    }

    public boolean exists(String cName) {
        return channels.containsKey(cName);
    }

    public synchronized boolean save(Channel c, boolean overwrite) {
        if (channels.get(c.getName().toLowerCase())!=null && !overwrite) return false;

        channels.put(c.getName().toLowerCase(),c);

        if (c.getShortcut()!= null) {
            Channel old = shortcuts.put(c.getShortcut(), c);
            if (old != null && old != c) {
                LogManager.logger.warning("Overriding Shortcut: '" + c.getPrefix() + "'. Old channel: " +
                old.getName() + ", new channel: " + c.getName());
            }
        }

        //Debugging
        StringBuilder sb = new StringBuilder();
        sb.append("Configuring Channel <" + c.getName() + ">");
        sb.append(" Description: " + c.getDescription());
        sb.append(" Permission: " + c.getPermission());
        sb.append(" Prefix: " + c.getPrefix());
        sb.append(" Privacy: " + c.isPrivateChannel());
        sb.append(" Shortcut: " + c.getShortcut());
        LogManager.getInstance().debugMedium(sb.toString());

        return true;

    }

    /* Should only be called by a Channel instance after it has cleaned itself up. */
    public synchronized void remove(Channel c) {
        if (c.hasChatters()) throw new IllegalStateException("Can't remove a channel with chatters! Channel: " + c.getName());
        shortcuts.remove(c.getShortcut());
        channels.remove(c.getName().toLowerCase());
    }

    public Channel getChannel(String name) {
        return channels.get(name.toLowerCase());
    }

    public Collection<Channel> getChannelList() {
        return channels.values();
    }

    public Channel shortcutLookup(String s, Chatter c) {
        if (s.charAt(0) == FactionChannel.getFactionsShortcut()) return FactionChannel.getForChatter(c);
        return shortcuts.get(s.charAt(0));
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
                Chatter chatter = ChatterManager.getInstance().getOrCreate((Player) sender);
                channelList = chatter.permittedChannels();
            } else {
                channelList = Collections.emptyList();
            }

            for ( Channel channel : channelList ) {
                if (channel.getName().startsWith(args[1].toLowerCase())) completions.add(channel.getName());
            }

        }

        if (completions.isEmpty()) return null;

        return completions;


    }

    public PwnChat getPlugin() {
        return plugin;
    }

}
