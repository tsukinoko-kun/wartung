package de.kuerbisskraft.wartung

import org.bukkit.command.CommandSender

class CmdInterpreter(private val wartungsManager: WartungsManager) {
    fun onCommand(commandSender: CommandSender, args: Array<out String>): Boolean {
        return if (args.isEmpty()) {
            wartungsManager.status(commandSender)
            true
        } else {
            when (args[0].toLowerCase()) {
                "on" -> {
                    if (commandSender.hasPermission("wartung.on")) {
                        wartungsManager.set(true, commandSender)
                        true
                    } else {
                        false
                    }
                }
                "off" -> {
                    if (commandSender.hasPermission("wartung.off")) {
                        wartungsManager.set(false, commandSender)
                        true
                    } else {
                        false
                    }
                }
                else -> false
            }
        }
    }

    fun onTabComplete(commandSender: CommandSender, args: Array<out String>): MutableList<String> {
        if (args.count() == 1) {
            val params = mutableListOf<String>()
            if (commandSender.hasPermission("wartung.on")) {
                params.add("on")
            }
            if (commandSender.hasPermission("wartung.off")) {
                params.add("off")
            }
            return params
        }
        return mutableListOf()
    }

}
