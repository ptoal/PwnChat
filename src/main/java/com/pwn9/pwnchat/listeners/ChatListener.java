package com.pwn9.pwnchat.listeners;

import com.pwn9.pwnchat.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ChatListener implements Listener {
	
	private PwnChat plugin;
    private boolean active;
	
	public ChatListener(PwnChat plugin) {
		this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

//    @Override
    public String getShortName() { return "PWNCHAT"; }

    @EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) return;

        Player p = event.getPlayer();
        Chatter chatter = ChatterManager.getInstance().getOrCreateChatter(p);

        Channel c = chatter.getFocus();

        if (c == null || c.equals(ChannelManager.getInstance().getLocal())) return;
        // Player is in local server channel.

        if (!p.hasPermission(c.getPermission())) {
            // They don't have permission for this channel anymore.
            // Remove them from the channel, and dump them back in
            // Local chat
            chatter.removeChannel(c);
            chatter.setFocus(null);
            return;
        }

        // Now, for basic testing, just prepend the channel in the tag
        event.setFormat("[" + c.getPrefix() + "]<%s> %s");

        Set<Player> recipientList = event.getRecipients();

        try  {
            recipientList.clear();
            recipientList.addAll(c.getRecipients());
        } catch (UnsupportedOperationException ex) {
            plugin.getLogger().warning("Caught an exception while trying to manipulate recipients list: "+ex.getMessage());
            event.setCancelled(true);
            return;
        }

        plugin.sendToChannel(p,c, event.getMessage());
	}

    /**
     * @return The primary rulechain for this filter
     */
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
