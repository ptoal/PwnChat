package com.pwn9.pwnchat.utils;

import com.pwn9.pwnchat.Channel;
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

        String worldName = p.getWorld().getName();
        Team team = p.getScoreboard().getPlayerTeam(p);
        Object[] objects = {  groupName,worldName,
                team == null ? "" : team.getPrefix(),
                team == null ? "" : team.getSuffix(),
                team == null ? "" : team.getDisplayName(),
                c.getPrefix()
        };

        return mFormat.format(objects);
    }
}
