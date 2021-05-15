package com.superkooka.oerthyon.factionitems.dynamite

import com.superkooka.oerthyon.factionitems.Main
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DynamiteCommand : CommandExecutor {

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) {
            return false
        }

        val player: Player = sender
        val messageVariable = HashMap<String, String>()
        messageVariable["player_name"] = player.displayName

        val power = Main.configuration.getDouble("dynamite.power", 4.0).toFloat()
        val fire = Main.configuration.getBoolean("dynamite.fire", true)

        when (args?.get(0)) {
            "give" -> when (args.count()) {
                1 -> sender.inventory.addItem(Dynamite.giveOne(power, fire))
                2 -> Bukkit.getPlayer(args[1]).inventory.addItem(Dynamite.giveOne(power, fire))
                3 -> Dynamite.giveMany(args[2].toIntOrNull() ?: 1, power, fire)
                    .forEach { item -> Bukkit.getPlayer(args[1]).inventory.addItem(item) }
                4 -> Dynamite.giveMany(args[2].toIntOrNull() ?: 1, args[3].toFloatOrNull() ?: 4f, fire)
                    .forEach { item -> Bukkit.getPlayer(args[1]).inventory.addItem(item) }
            }
        }

        return true
    }
}