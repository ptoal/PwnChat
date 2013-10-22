# PwnChat


PwnChat started from a fork of BungeeChat(bukkit)+, but has been almost completely re-written.  There were many plugins that did some of what PwnChat does, but they were either not maintained, closed-source, or didn't meet enough requirements.  PwnChat borrows from some of these (esp. BungeeChat+, which itself borrowed from others).  The current features include:

* A channel-based chat system which includes, by default, Local, Global and Admin channels.
* Any channel which is configured on all servers in a bungeecord cluster will receive/send messages cross-server. (Eg: if "global" is configured on all servers, any chat sent to "global" on any server will be sent to any player "listening" to the global channel on any server.
* Chat formatting similar to (borrowed from) EssentialsChat.

Upcoming features:
* Integration with PwnFilter API to filter cursing / advertising of chat messages locally and from remote servers.
* Cross-server (via BungeeCord) PM support.

Note: PwnChat does *not* require Bungeecord.  Also, unlike most Bungeecord chat systems, there is no requirement for a bungeecord plugin to enable cross-server chat.

## Installation


Download the appropriate .jar file from the selections at the right, and drop into the bukkit plugins folder on each server you want to run it on.

## Configuration


Run the server once, to allow the plugin to auto-generate its config.yml file.

Example config file:

    settings:
      bungeecord: true # To enable / disable bungeecord cross-server chat
      factions: true # FUTURE: This will enable dynamic creation of Faction chat channels.
      pwnfilter: true # FUTURE: This will hook chat into the PwnFilter API, to allow swear / advertising filtering

    channels:
      admin: #This is the name of the channel that will show in lists.
        # Friendly description of the channel to be shown in /pchat list
        description: Admin-only channel
        # The prefix can be used in the chat display, eg:
        # [A]<Sage905> Hey admins!
        prefix: A
        # If a shortcut is specified, all player chat that starts with this character
        # will be sent to the channel.  Eg: !Hi Admins.
        # [A]<Sage905> Hi admins.
        shortcut: '!'
        # Players require this permission to listen / talk on this channel.
        permission: pwnchat.channel.admin
        # This flag changes the behaviour of how messages are sent to players.
        # Setting this flag to true will set the message cancelled, which stops
        # it from appearing in IRC if using CraftIRC.
        privacy: true
      global:
        description: Global Channel
        prefix: G
        privacy: false
        permission: pwnchat.channel.global
      staff:
        description: Staff-only channel
        prefix: '@'
        shortcut: '@'
        privacy: true
        permission: pwnchat.channel.staff


In order for a player to be able to listen or talk on a channel, they must have the matching permission.

NOTE: Any servers that are connected via bungeecord that have the same channels configured will receive and foward received messages to players who are listening to that channel.  Thus, if the "admin" channel is configured on server A and B, players who have the admin privilege on either server will be able to send/receive messages sent to that channel.

## Commands

### Player Commands

/pchat with no arguments will display help

/pchat list - List all channels available to this player (will not show ones they don't have permission for)

/pchat silence <channel>  - The player will no longer receive messages sent on this channel.

/pchat listen <channel> - The opposite of silence.  Player will receive messages sent to this channel.

/pchat talk <channel> - This will "switch" the player into the channel so that any chat they type is sent into the channel.

/pchat <prefix|channel> <message> - This will send a message directly to a channel without needing to switch into it with the "talk" command.

Also, as mentioned in the config section above, for players with the appropriate permission, any chat that begins with a registered shortcut will have that message sent to the channel, eg:

    !Hi Admins....

Would send to the Admin channel:

    [A]<Sage905> Hi Admins...
 
### Admin Commands


/pchat reload - Reload the pchat configuration file.

## Upcoming Features

* More chat formatting options
* Private Message cross bungee-cord servers.
* Direct integration with [PwnFilter](http://dev.bukkit.org/bukkit-plugins/pwnfilter "PwnFilter").
* Dynamic creation of channels for Factions.