# Better Ping Display - Fabric Edition

[![](https://img.shields.io/curseforge/dt/406343?style=for-the-badge&logo=curseforge&label=Downloads&color=rgb(241%2C%20100%2C%2054))](https://www.curseforge.com/minecraft/mc-mods/better-ping-display-fabric) [![](https://img.shields.io/modrinth/dt/better-ping-display-fabric?style=for-the-badge&logo=modrinth&logoColor=rgb(27%2C%20217%2C%20106)&label=Downloads&color=rgb(27%2C%20217%2C%20106))](https://modrinth.com/mod/better-ping-display-fabric)


A [Fabric](https://fabricmc.net/) mod for Minecraft to display each player's ping in the player list as a number.

Go [**here**](https://github.com/vladmarica/better-ping-display) for the Forge/NeoForge edition of this mod.

![](https://vladmarica.com/assets/minecraft/better-ping-display.png)

This is a client-side mod. The server doesn't need to have it installed. It works even when playing on vanilla servers.

## Configuration
This mod's config file is `betterpingdisplay.json`. It contains the following options:

| Option  | Default Value  | Description  |
|---|---|---|
| autoColorPingText  | `true` | Whether to color a player's ping based on their latency. |
| renderPingBars  | `false` | Whether to also draw the default Minecraft ping bars  |
| pingTextColor  | `#A0A0A0`  | The ping text color to use. Only works whens `autoColorPingText` is false |
| pingTextFormatString | `%dms` | The format string for ping text. Must include a `%d`, which will be replaced dynamically by the actual ping value.

> As of mod version **1.2.0**, a GUI configuration screen is available if you have
[Mod Menu](https://modrinth.com/mod/modmenu) and [YACL](https://modrinth.com/mod/yacl) installed. These are optional dependencies
> and this mod will continue to work normally without them installed. 

![](https://i.imgur.com/cX02gRg.png)

## Supported Minecraft Versions
* **1.15.x**
* **1.16.x**
* **1.17.x**
* **1.18.x**
* **1.19.x**
* **1.20.x**
* **1.21.x**

## Requirements
* [Fabric](https://fabricmc.net/)
