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
    public Character shortcut = null;
    public String permission = "";
    public boolean privacy = true;

}
