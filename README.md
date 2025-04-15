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
- [Discord-MC-Chat](https://modrinth.com/mod/discord-mc-chat) by Xujiayao

## How to Enable

### For versions 0.3 and above

No need to change anything in ``spawn_notification.json`` now. It should work by default but if you want to it to **respect** ``announceCrossDimensions`` setting from ``spawn_notification.json``, refer to the config section.

### For versions 0.2 and below

Currently, the mod only works when `announceCrossDimensions` is set to `true` in `spawn_notification.json` as I thought that would make the most sense.

To enable it:

1. Open `spawn_notification.json` inside the `config/` folder.
2. Set `"announceCrossDimensions"` to `true`.

### For all versions and using MC2Discord

1. Open `mc2discord.toml` inside the `config/` folder.
2. Add `"cobblemon_spawn_notification_discord"` to the `subscriptions = [...]` section.

For example:

```toml
[Channels]
 [[Channels.Channel]]
  id = 0
  subscriptions = ["info", "chat", "command", "cobblemon_spawn_notification_discord"]
  mode = "WEBHOOK"
```

## Config

The config file for this mod can be found at:  
`config/csn-discord-integration.json`

- **`bypassCrossDimension`**  
  If set to `true`, this will ignore the `announceCrossDimension` setting from `spawn_notification.json` will always broadcast announcements to discord.  
  If set to `false`, it will follow whatever is set in `announceCrossDimension`.  
  **Note:** Setting this to `false` will effectively disable the mod unless `announceCrossDimension` is also set to `true`.  
  **Default:** `true`

- **`botName`**  
  Sets the display name of the webhook.  
  **Default:** `"Cobblemon Spawn Notification"`

- **`botAvatarUrl`**  
  Sets the avatar image URL of the webhook.

## Commands

- ``/csn-discord reload``: Reloads the config

## Dependencies

[Cobblemon](https://modrinth.com/mod/cobblemon)

[Cobblemon Spawn Notification](https://modrinth.com/mod/cobblemon-spawn-notification)

One of the various supported Discord Integration Mods

- [Discord Integration](https://modrinth.com/plugin/dcintegration)
- [MC2Discord](https://modrinth.com/mod/mc2discord)
- [Discord-MC-Chat](https://modrinth.com/mod/discord-mc-chat)

## Credits

This mod is based on the Cobblemon Spawn Notification mod by tmetcalfe89. Full credit to them for the base implementation. Additionally, this mod utilizes various Discord Integration APIs and functions, not limited to; ErdbeerbaerLP, DenisD3D, Xujiayao. Thanks to the developers for their work in making this functionality possible.

Special thanks to tmetcalfe89 for their valuable input towards the mod and allowing me to use their original mod's name for this mod.

------

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/L3L11CY3WS)
