package com.pwn9.pwnchat.commands.subcommands;

import com.pwn9.pwnchat.PwnChat;
import com.pwn9.pwnchat.commands.SubCommand;
import org.bukkit.command.CommandSender;

/**
 * Reload configs
 * User: ptoal
 * Date: 13-07-19
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class reload extends SubCommand {

    public reload(PwnChat instance) {
        super(instance,"reload");
        setUsage("reload");
        setDescription("Reload config.");
        setPermission("pwnchat.reload");
    }

    public boolean execute(CommandSender sender, String commandName, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage(PwnChat.PREFIX + " Reloaded configuration.");
        return true;
    }

}
