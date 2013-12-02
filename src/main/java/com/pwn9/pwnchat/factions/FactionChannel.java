package com.pwn9.pwnchat.factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import com.pwn9.pwnchat.Channel;
import com.pwn9.pwnchat.ChannelManager;
import com.pwn9.pwnchat.Chatter;
import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.config.PwnChatConfig;

import java.text.MessageFormat;

/**
 * A Dynamic channel for Factions
 * User: ptoal
 * Date: 13-10-29
 * Time: 5:41 PM
 */
public class FactionChannel extends Channel {

    private Faction faction = null;
    private static MessageFormat defaultFactionsFormat;
    private static char factionsShortcut = 0;


    public FactionChannel(String name, Faction faction) {
        super(name);
        this.faction = faction;
    }

    @Override
    public boolean hasPermission(Chatter c) {
        // Check to see if this channel is our faction channel
        return this == getForChatter(c);

    }

    public static MessageFormat getDefaultFactionsFormat() {
        return defaultFactionsFormat;
    }

    public static void setDefaultFactionsFormat(MessageFormat format) {
        defaultFactionsFormat = format;
    }

    public static char getFactionsShortcut() {
        return factionsShortcut;
    }

    public static void setupFactionsDefaults(PwnChat p, PwnChatConfig config) {
        if (p.factionsEnabled()) {
            if (config.Settings_defaultFactionFormat != null) {
                defaultFactionsFormat = ChannelManager.parseFormat(config.Settings_defaultFactionFormat);
            } else {
                p.getLogger().warning("No Default Factions Channel format found!");
            }
            factionsShortcut = config.Settings_factionShortcut.charAt(0);
        }

    }

    public static Channel getForChatter(Chatter chatter) {
        PwnChat plugin = ChannelManager.getInstance().getPlugin();
        if (plugin == null) return null; // Might happen, but shouldn't.

        if (plugin.factionsEnabled()) {
            UPlayer factionsPlayer = UPlayer.get(chatter.getPlayer());
            Faction faction = factionsPlayer.getFaction();
            if (faction.getName() != null && !faction.getName().isEmpty()) {
                return ChannelManager.getInstance().getChannel(faction.getName());
            }
        }
        return null;
    }

    public static Channel getOrCreateFactionsChannel(Chatter chatter) {
        PwnChat plugin = ChannelManager.getInstance().getPlugin();
        if (plugin == null) return null; // Might happen, but shouldn't.

        if (plugin.factionsEnabled()) {
            UPlayer factionsPlayer = UPlayer.get(chatter.getPlayer());
            Faction faction = factionsPlayer.getFaction();

            if (faction.getName() != null && !faction.getName().isEmpty()) {
                Channel fChannel = ChannelManager.getInstance().getChannel(faction.getName());

                if (fChannel == null) {
                    fChannel = new FactionChannel(faction.getName().toLowerCase(),faction);
                    fChannel.setFormat(FactionChannel.getDefaultFactionsFormat());
                    fChannel.setDescription(String.format("Private Factions Channel for: %s", faction.getName()));
                    fChannel.setPrivate(true);
                    fChannel.setPrefix(faction.getName());
                    fChannel.setShortcut(null); // No shortcut for factions channels.
                    fChannel.save(); // Now add this to the channel Manager.
                    chatter.addChannel(fChannel);
                    return fChannel;
                } else if (!(fChannel instanceof FactionChannel)) {
                    plugin.getLogger().warning("Could not create Factions Channel, as a non-faction channel with the name '"+fChannel.getName()+"' already exists.");
                } else return fChannel;
            }
        }
        return null;
    }

    public static void removeEmptyFactionsChannels() {
        for (Channel c: ChannelManager.getInstance().getChannelList()) {
            if (c instanceof FactionChannel ) {
                if (c.getChatters() == null || c.getChatters().isEmpty() ) {
                    c.remove();
                }
            }
        }
    }


}
