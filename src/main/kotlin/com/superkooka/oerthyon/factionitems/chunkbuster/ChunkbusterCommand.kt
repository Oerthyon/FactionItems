package com.superkooka.oerthyon.factionitems.chunkbuster

import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ChunkbusterCommand : CommandExecutor {
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

        when (args?.get(0)) {
            "give" -> {
                if (!player.hasPermission("oerthyon.factionitems.chunkbuster.give")) {
                    player.sendMessage(
                        StringUtils.parse(
                            Main.configuration.getString(
                                "not_enough_permission",
                                "Vous n'avez pas la permission d'executer cette commande"
                            ), messageVariable
                        )
                    )
                    return false
                }
                val drop_items = player.inventory.addItem(Chunkbuster.giveOne())
                for (drop_item in drop_items.values) {
                    player.world.dropItem(player.location, drop_item)
                }
            }
            "burst" -> {
                if (args.size < 3) {
                    return false
                }

                if (!player.hasPermission("oerthyon.factionitems.chunknbuster.bust_command")) {
                    player.sendMessage(
                        StringUtils.parse(
                            Main.configuration.getString(
                                "not_enough_permission",
                                "Vous n'avez pas la permission d'executer cette commande"
                            ), messageVariable
                        )
                    )
                }

                Chunkbuster.burst(Location(player.world, args[1].toDouble(), 0.0, args[2].toDouble()))
            }
            else -> return false
        }
        return true
    }
}