package com.pwn9.pwnchat.utils;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import com.pwn9.pwnchat.Channel;
import com.pwn9.pwnchat.Chatter;
import com.pwn9.pwnchat.ChatterManager;
import com.pwn9.pwnchat.PwnChat;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.text.MessageFormat;

/**
 * Created with IntelliJ IDEA.
 * User: ptoal
 * Date: 13-10-27
 * Time: 12:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelFormat {
    public static String getFormat(Player p, Channel c, PwnChat plugin) {
        MessageFormat mFormat = c.getFormat();
        String groupName = "";
        if (plugin.getPerms() != null) {
            groupName = plugin.getPerms().getPrimaryGroup(p);
        }

        Chatter chatter = ChatterManager.getInstance().getOrCreate(p);
        String factionRolePrefix = "";
        String factionName = "";
        if (plugin.factionsEnabled()) {
            UPlayer factionsPlayer = UPlayer.get(chatter.getPlayer());
            Faction faction = factionsPlayer.getFaction();
            if (faction != null) {
                factionName = faction.getName();
                factionRolePrefix = factionsPlayer.getRole().getPrefix();
            }
        }

        String worldName = p.getWorld().getName();
        Team team = p.getScoreboard().getPlayerTeam(p);
        Object[] objects = {  groupName,worldName,
                team == null ? "" : team.getPrefix(),
                team == null ? "" : team.getSuffix(),
                team == null ? "" : team.getDisplayName(),
                c.getPrefix(),
                    factionRolePrefix,
                    "Test"
        };

        return mFormat.format(objects);
    }
}
