package com.superkooka.oerthyon.factionitems.items

import com.superkooka.oerthyon.factionitems.ItemNotFoundException
import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.items.chunkbuster.Chunkbuster
import com.superkooka.oerthyon.factionitems.items.dynamite.Dynamite
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack

class ItemsCommand : CommandExecutor {

    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, arguments: Array<out String>?
    ): Boolean {
        if ("give" != arguments?.get(0)) {
            return false
        }

        when (arguments.count()) {
            0, 1, 2 -> {
                return false
            }
            3 -> {
                val player = Bukkit.getPlayer(arguments[1])
                if (null === player) {
                    sender?.sendMessage("Impossible de trouver ce joueur")
                    return false
                }

                try {
                    val items = getItem(arguments[2])
                    player.inventory.addItem(*items)
                } catch (e: ItemNotFoundException) {
                    player.sendMessage("Cet item n'existe pas :/")
                }
            }
            4 -> {
                val player = Bukkit.getPlayer(arguments[1])
                if (null === player) {
                    sender?.sendMessage("Impossible de trouver ce joueur")
                    return false
                }

                try {
                    val items = getItem(arguments[2], arguments[3].toInt())
                    player.inventory.addItem(*items)
                } catch (e: ItemNotFoundException) {
                    player.sendMessage("Cet item n'existe pas :/")
                }
            }
            else -> {
                val player = Bukkit.getPlayer(arguments[1])
                if (null === player) {
                    sender?.sendMessage("Impossible de trouver ce joueur")
                    return false
                }

                try {
                    val items =
                        getItem(arguments[2], arguments[3].toInt(), arguments.sliceArray(4 until arguments.size))
                    player.inventory.addItem(*items)
                } catch (e: ItemNotFoundException) {
                    player.sendMessage("Cet item n'existe pas :/")
                }
            }
        }

        return true
    }

    private fun getItem(itemName: String, amount: Int = 1, options: Array<out String> = arrayOf()): Array<ItemStack> {
        return Array(amount) {
            when (itemName) {
                "chunkbuster" -> Chunkbuster.give()
                "dynamite" -> Dynamite.give(
                    (options.getOrNull(0)?.toFloat() ?: Main.configuration.getDouble("", 4.0).toFloat()),
                    (options.getOrNull(1)?.toBoolean() ?: Main.configuration.getBoolean("", true)),
                )
                else -> throw ItemNotFoundException()
            }
        }
    }
}