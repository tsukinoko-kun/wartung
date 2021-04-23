package de.kuerbisskraft.wartung

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class Wartung : JavaPlugin(), Listener, CommandExecutor, TabCompleter {
    private val versionCheck = Regex("""1\.16\.[45]-R0\.1-SNAPSHOT""")
    private val wartungsManager = WartungsManager()
    private val cmdInterpreter = CmdInterpreter(wartungsManager)

    override fun onEnable() {
        val version = Bukkit.getServer().bukkitVersion
        if (versionCheck.matches(version)) {
            Bukkit.getPluginManager().registerEvents(this, this)
            super.onEnable()
        } else {
            logger.log(
                Level.WARNING,
                "Version \"${version}\" not comptible! The \"${this.name}\" plugin is deactivated for security reasons"
            )
            server.pluginManager.disablePlugin(this)
        }
    }

    override fun onDisable() {
        wartungsManager.onDisable()
        super.onDisable()
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        wartungsManager.playerWartungKickCheck(event.player)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return if (command.name == "wartung") {
            cmdInterpreter.onCommand(sender, args)
        } else {
            false
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        return if (command.name == "wartung") {
            cmdInterpreter.onTabComplete(sender, args)
        } else {
            null
        }
    }
}
