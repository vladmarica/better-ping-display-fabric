# Better Ping Display - Fabric Edition

[![](http://cf.way2muchnoise.eu/full_406343_downloads.svg)](https://curseforge.com/minecraft/mc-mods/better-ping-display-fabric) 

A [Fabric](https://fabricmc.net/) mod for Minecraft to display each player's ping in the player list as a number.

Go [**here**](https://github.com/vladmarica/better-ping-display) for the Minecraft Forge edition of this mod.

![](https://i.imgur.com/HTrH0i2.png)

This is a client-side mod. The server doesn't need to have it installed. It works even when playing on vanilla servers.

## Configuration

__If you have a [ModMenu](https://modrinth.com/mod/modmenu) installed, you can edit mod's config using it.__

This mod's config file is `betterpingdisplay.json`. It contains the following options:

| Option  | Default Value  | Description  |
|---|---|---|
| autoColorPingText  | `true` | Whether to color a player's ping based on their latency. E.g, low latency = green, high latency = red |
| renderPingBars  | `false` | Whether to also draw the default Minecraft ping bars  |
| pingTextColor  | `#A0A0A0`  | The ping text color to use. Only works whens `autoColorPingText` is false |
| pingTextFormatString | `%dms` | The format string for ping text. Must include a `%d`, which will be replaced dynamically by the actual ping value.

## Supported Minecraft Versions
* **1.15.x**
* **1.16.x**
* **1.17.x**
* **1.18.x**
* **1.19.x**

## Requirements
* [Fabric](https://fabricmc.net/)
