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
import com.pwn9.pwnchat.utils.ChannelFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ChatListener implements Listener {
	
	private PwnChat plugin;
//    private boolean active;
	
	public ChatListener(PwnChat plugin) {
		this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

//    @Override
    public String getShortName() { return "PWNCHAT"; }

    @EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChatLowest(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) return;

        Player p = event.getPlayer();
        String message = event.getMessage();

        if (p.hasPermission("essentials.chat.color")) {
            message = ChatColor.translateAlternateColorCodes('&',message);
        }

        if (!p.hasPermission("essentials.chat.magic")) {
            final String MAGICCODE = "" + ChatColor.COLOR_CHAR + ChatColor.MAGIC;
            message = message.replaceAll(MAGICCODE,"");
        }

        Chatter chatter = ChatterManager.getInstance().getOrCreate(p);

        // If using a shortcut, override the current channel focus
        Channel c = ChannelManager.getInstance().shortcutLookup(message, chatter);

        // If not using shortcut, try channel focus.
        if (c == null) {
            c = chatter.getFocus();
            if (c == null) { // If still null, set local }
                c = ChannelManager.getInstance().getLocal();
            }
        } else {
            message = message.substring(1);
        }

        if (!c.hasPermission(chatter)) {
            // They don't have permission for this channel anymore.
            // Remove them from the channel, and dump them back in
            // Local chat
            chatter.removeChannel(c);
            Channel defaultChannel = chatter.setFocus(ChannelManager.getInstance().getDefaultChannel());
            p.sendMessage("You don't have permission to be in the '"+c.getName()+"' channel anymore.");
            p.sendMessage("Returning you to the default channel: "+ defaultChannel.getName());
            return;
        }

        String format = ChannelFormat.getFormat(p, c, plugin);
        event.setFormat(format);

        if (c.isPrivateChannel()) {
            event.setCancelled(true);
            c.sendMessage(plugin,p.getDisplayName(),format,message, p.getName());
        } else {
            event.setMessage(message);
            Set<Player> recipientList = event.getRecipients();

            try  {
                recipientList.clear();
                Set channelRecipients = c.getPermittedRecipients(p);
                if (channelRecipients == null) {
                    event.setCancelled(true);
                } else {
                    recipientList.addAll(channelRecipients);
                }

            } catch (UnsupportedOperationException ex) {
                plugin.getLogger().warning("Caught an exception while trying to manipulate recipients list: "+ex.getMessage());
                event.setCancelled(true);
                return;
            }
        }

        plugin.sendToChannel(p,c,format, message);

	}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChatHighest(AsyncPlayerChatEvent event) {
        // Clean up any stray tags.

        String format = event.getFormat();
        event.setFormat(format.replaceAll("\\{\\S*\\}",""));

    }

//    /**
//     * @return The primary rulechain for this filter
//     */
//    @Override
//    public RuleChain getRuleChain() {
//        return chatRuleChain;
//    }
//
//    @Override
//    public boolean isActive() {
//        return active;
//    }

    /**
     * Activate this listener.  This method can be called either by the owning plugin
     * or by PwnFilter.  PwnFilter will call the shutdown / activate methods when PwnFilter
     * is enabled / disabled and whenever it is reloading its config / rules.
     * <p/>
     * These methods could either register / deregister the listener with Bukkit, or
     * they could just enable / disable the use of the filter.
     *
     * @param config PwnFilter Configuration object, which the plugin can read for configuration
     *               information. (eg: config.getString("ruledir")
     */
//    @Override
//    public void activate(Configuration config) {
//        if (isActive()) return;
//        chatRuleChain = RuleManager.getInstance().getRuleChain("pwnchat.txt");
//        tagRuleChain = RuleManager.getInstance().getRuleChain("pwnchat_tag.txt");
//
//        Bukkit.getPluginManager().registerEvents(this,plugin);
//    }
//
//    @Override
//    public void shutdown() {
//        if (active) {
//            HandlerList.unregisterAll(this);
//            active = false;
//        }
//    }
}
