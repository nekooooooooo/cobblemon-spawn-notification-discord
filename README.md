![Modrinth Version](https://img.shields.io/modrinth/v/GjyKHwb6?style=for-the-badge&labelColor=444444&color=00af5c)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/GjyKHwb6?style=for-the-badge&logo=modrinth&color=00af5c&label=%20&labelColor=444444)](https://modrinth.com/mod/cobblemon-spawn-notification-for-discord)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/1239095?style=for-the-badge&logo=curseforge&label=%20&labelColor=444444&color=e04e14&link=https%3A%2F%2Fwww.curseforge.com%2Fminecraft%2Fmc-mods%2Fcobblemon-spawn-notification-discord-integration)](https://www.curseforge.com/minecraft/mc-mods/cobblemon-spawn-notification-discord-integration)

# Cobblemon Spawn Notification Discord Integration

Cobblemon Spawn Notification Discord Integration is an addon/extension for the original Cobblemon Spawn Notification Mod by tmetcalfe89, adding Discord integration to various Discord Integration Mods.

![DCIntegration example: in-game](https://cdn.modrinth.com/data/GjyKHwb6/images/830beb76740f4c18d1fa18d940bbbfddaac53549.png)

![DCIntegration example: discord](https://cdn.modrinth.com/data/GjyKHwb6/images/4444a57c4463ce77cc165de3a528767d3fd3928b.png)

## Currently Supported Discord Integration Mods

- [Discord Integration](https://modrinth.com/plugin/dcintegration) by ErdbeerbaerLP
- [MC2Discord](https://modrinth.com/mod/mc2discord) by DenisD3D

## How to Enable

### For versions 0.3 and above

**No need to change anything in the configs now. It should work by default but if you want to it to respect ``announceCrossDimensions`` config in ``spawn_notification.json``, check the config section.**

### For versions 0.2 and below

**Currently only works when ``announceCrossDimensions`` is set to true inside ``spawn_notification.json`` as I thought that would make the most sense. I will update it in the future to include a config file instead**

Open ``spawn_notification.json`` inside ``config/``

Set ``"announceCrossDimensions"`` to ``true``

### For MC2Discord

Open ``mc2discord.toml`` inside ``config/``

Add ```"cobblemon_spawn_notification_discord"``` to ``subscriptions = [...]``

For example:

```toml
[Channels]
 [[Channels.Channel]]
  id = 0
  subscriptions = ["info", "chat", "command", "cobblemon_spawn_notification_discord"]
  mode = "WEBHOOK"
```

## Config

Config file is located at ``config/csn-discord-integration.json`` 

- ``bypassCrossDimension``: bypasses ``announceCrossDimension`` config ``spawn_notification.json``. Set to ``false`` to respect the setting. Setting it to false would essentially disable the mod and you would need to set ``announceCrossDimension`` to ``true``. Default value: ``true``
- ``botName``: Sets the webhook name for MC2Discord. Default value: ``"Cobblemon Spawn Notification"``
- ``botAvatarUrl``: Sets the webhook avatar for MC2Discord.

## Commands

- ``/csn-discord reload``: Reloads the config

## Dependencies

[Cobblemon](https://modrinth.com/mod/cobblemon)

[Cobblemon Spawn Notification](https://modrinth.com/mod/cobblemon-spawn-notification)

One of the various supported Discord Integration

- [Discord Integration](https://modrinth.com/plugin/dcintegration)
- [MC2Discord](https://modrinth.com/mod/mc2discord)

## Credits

This mod is based on the Cobblemon Spawn Notification mod by tmetcalfe89. Full credit to them for the base implementation. Additionally, this mod utilizes various Discord Integration APIs and functions, not limited to; ErdbeerbaerLP, DenisD3D. Thanks to the developers for their work in making this functionality possible.

Special thanks to tmetcalfe89 for their valuable input towards the mod and allowing me to use their original mod's name for this mod.

------

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/L3L11CY3WS)
