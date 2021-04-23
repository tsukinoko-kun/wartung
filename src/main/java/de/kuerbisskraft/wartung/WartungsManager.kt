package de.kuerbisskraft.wartung

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import de.kuerbisskraft.wartung.data.Config
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getServer
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_16_R3.CraftServer
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File


class WartungsManager {
    private val configPath = "plugins/wartung/"
    private val configFilePath = configPath + "config.json"
    private val configFile = File(configFilePath)
    private var status = false
    private val originalMotd = getServer().motd
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val motdFormat: String

    init {
        val p = File(configPath)
        if (!p.exists()) {
            p.mkdirs()
        }

        if (configFile.exists() && !configFile.isDirectory) {
            val json = configFile.readText(Charsets.UTF_8)
            val type = object : TypeToken<Config>() {}.type
            val import: Config = gson.fromJson(json, type)
            motdFormat = import.motdFormat
        } else {
            motdFormat = "${ChatColor.DARK_RED}Serverwartung!\n${ChatColor.RESET}%s"
            configFile.writeText(gson.toJson(Config(motdFormat)), Charsets.UTF_8)
        }
    }

    private fun setMotd(motd: String) {
        (getServer() as CraftServer).server.motd = motd
    }

    fun set(value: Boolean, commandSender: CommandSender) {
        status = value
        commandSender.sendMessage(
            if (status) {
                setMotd(String.format(motdFormat, originalMotd))
                for (player in Bukkit.getOnlinePlayers()) {
                    playerWartungKickCheck(player)
                }
                "${ChatColor.GOLD}Wartungsmodus ist jetzt aktiv! Spieler können deinen Server nicht betreten!"
            } else {
                setMotd(originalMotd)
                "${ChatColor.GREEN}Wartungsmodus ist jetzt inaktiv"
            }
        )
    }

    fun status(commandSender: CommandSender) {
        commandSender.sendMessage(
            if (status) {
                "${ChatColor.GOLD}Wartungsmodus ist aktiv! Spieler können deinen Server nicht betreten!"
            } else {
                "${ChatColor.GREEN}Wartungsmodus ist inaktiv"
            }
        )
    }

    fun playerWartungKickCheck(player: Player) {
        if (status && !player.hasPermission("wartung.join")) {
            player.kickPlayer("${ChatColor.RED}Aufgrund aktueller Wartungsarbeiten ist der Server vorübergehend nicht erreichbar!\nVersuche es später erneut")
        }
    }

    fun onDisable() {
        setMotd(originalMotd)
        status = false
    }
}
