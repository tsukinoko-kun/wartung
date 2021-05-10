# wartung

| :information_source: This is a commissioned work |
|--------------------------------------------------|

Minecraft Plugin

Version: Bukkit 1.16.4

[Download](https://github.com/Frank-Mayer/wartung/releases/latest)

## Config

`./plugins/wartung/config.json`

```json
{
  "motdFormat": "§4Serverwartung!\n§r%s"
}
```

%s ist ein Platzhalter für die aktuelle motd

Diese motd wird verwendet, wenn der Wartungsmodus aktiv ist.

## Commands

| Command                                           | Beschreibung                                     | Permission                            |
|---------------------------------------------------|--------------------------------------------------|---------------------------------------|
| `/wartung on`                                     | Aktiviert den Wartungsmodus                      | wartung.on                            |
| `/wartung off`                                    | Deaktiviert den Wartungsmodus                    | wartung.off                           |

## Permission wartungs.join

Die Permission wartung.join erlaubt das Betreten des Servers wärend der Wartungsmodus aktiv ist. 
