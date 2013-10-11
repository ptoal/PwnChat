package com.pwn9.pwnchat.commands;

import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.commands.subcommands.*;
import org.bukkit.command.CommandSender;

import static org.bukkit.ChatColor.GOLD;

/**
 * Main command handler for all /pr commands.
 */
public class pchat extends BaseCommandExecutor {

    public pchat(PwnChat instance) {
        super(instance);
        addSubCommand(new reload(instance));
        addSubCommand(new list(instance));
        addSubCommand(new listen(instance));
        addSubCommand(new silence(instance));
        addSubCommand(new talk(instance));
    }

    @Override
    public boolean sendHelpMsg(CommandSender sender, String alias) {
        sender.sendMessage(GOLD + "PwnChat Commands:");
        return super.sendHelpMsg(sender, alias);
    }
}