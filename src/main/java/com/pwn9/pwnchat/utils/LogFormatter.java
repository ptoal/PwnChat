/*
 * PwnChat -- A Bukkit/Spigot plugin for multi-channel cross-server (via bungeecord) chat.
 * Copyright (c) 2013 Pwn9.com. Sage905 <ptoal@takeflight.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.pwnchat.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFormatter extends SimpleFormatter {

    @Override
    public synchronized String format(LogRecord record) {

        String dateStr;
        Format formatter;
        Date date = new Date(record.getMillis());
        formatter = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]");
        dateStr = formatter.format(date);
        return dateStr + " " + record.getMessage() + "\n";
    }
}
