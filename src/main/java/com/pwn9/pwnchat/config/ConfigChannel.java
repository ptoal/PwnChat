/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.config;

/**
 * Configuration for a Channel
 * User: ptoal
 * Date: 13-10-20
 * Time: 10:56 AM
 */
public class ConfigChannel extends ConfigObject {

    public ConfigChannel() {
    };

    public String description = "";
    public String prefix = null;
    public String shortcut = null;
    public String permission = "";
    public String format = null;
    public Boolean privacy = true;

}
