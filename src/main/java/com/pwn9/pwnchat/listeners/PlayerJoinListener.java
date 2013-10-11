package com.pwn9.pwnchat.listeners;

import com.pwn9.pwnchat.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Listen for Player join events and set up their default channels.
 * User: ptoal
 * Date: 13-07-15
 * Time: 10:14 PM
 */
public class PlayerJoinListener implements Listener {
    private final PwnChat plugin;

    public PlayerJoinListener(PwnChat instance) {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();
        Chatter chatter = ChatterManager.getInstance().getOrCreateChatter(p);

        StringBuilder channelMessage = new StringBuilder();

        for (Channel c: ChannelManager.getInstance().getChannelList()) {
            if(c.addChatter(chatter)) {
                channelMessage.append(c.getName()).append(",");
            }
        }
        channelMessage.deleteCharAt(channelMessage.length()-1);
        event.getPlayer().sendMessage(PwnChat.PREFIX + " Listening to: " + channelMessage.toString());

    }
}
