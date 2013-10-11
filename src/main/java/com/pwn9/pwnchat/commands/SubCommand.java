package com.pwn9.pwnchat.commands;

import com.pwn9.pwnchat.PwnChat;
import org.bukkit.command.Command;

/**
 * SubCommand adds the plugin to the instance of Command.
 * User: ptoal
 * Date: 13-08-16
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class SubCommand extends Command {

    protected final PwnChat plugin;

    public SubCommand(PwnChat plugin, String name) {
        super(name);
        this.plugin = plugin;
    }

    public String getHelpMessage() {
        String message = "";
        if (!getUsage().isEmpty()) message = getUsage();
        if (!getDescription().isEmpty()) {
            if (!message.isEmpty()) {
                message = message + " -- " + getDescription();
            } else {
                message = getDescription();
            }
        }
        return message;
    }
}
