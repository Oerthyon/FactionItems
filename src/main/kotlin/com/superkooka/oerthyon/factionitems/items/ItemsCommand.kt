package com.superkooka.oerthyon.factionitems.items

import com.superkooka.oerthyon.factionitems.ItemNotFoundException
import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.items.chunkbuster.Chunkbuster
import com.superkooka.oerthyon.factionitems.items.dynamite.Dynamite
import com.superkooka.oerthyon.factionitems.items.houe.EpicHoe
import com.superkooka.oerthyon.factionitems.items.houe.LegendaryHoe
import com.superkooka.oerthyon.factionitems.items.pickaxe.EpicPickaxe
import com.superkooka.oerthyon.factionitems.items.pickaxe.LegendaryPickaxe
import com.superkooka.oerthyon.factionitems.items.sword.EpicSword
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
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
            else -> {
                if (sender is Player && !hasPermission(sender, arguments[2])) {
                    sender.sendMessage(
                        StringUtils.parse(
                            Main.configuration.getString(
                                "global.messages.not_enough_permission_to_execute_it",
                                "Vous n'avez pas la permission d'executer cette commande"
                            ),
                            hashMapOf(Pair("player_name", sender.displayName))
                        )
                    )
                    return true
                }

                val player = Bukkit.getPlayer(arguments[1])
                if (null === player) {
                    if (sender is Player) sender.sendMessage(
                        StringUtils.parse(
                            Main.configuration.getString(
                                "global.messages.unable_to_find_player",
                                "Impossible de trouver ce joueur"
                            ),
                            hashMapOf(Pair("player_name", arguments[1]))
                        )
                    )
                    return false
                }

                try {
                    val items = getItem(arguments[2], if (arguments.size > 2) arguments[3].toInt() else 1, arguments.sliceArray(4 until arguments.size))
                    player.inventory.addItem(*items)
                } catch (e: ItemNotFoundException) {
                    if (sender is Player) sender.sendMessage(
                        StringUtils.parse(
                            Main.configuration.getString(
                                "global.messages.unable_to_find_item",
                                "Impossible de trouver cet item"
                            ),
                            hashMapOf(Pair("item_name", arguments[2]))
                        )
                    )
                }
            }
        }

        return true
    }

    private fun hasPermission(player: Player, itemName: String): Boolean {
        return when (itemName) {
            "chunkbuster" -> player.hasPermission("oerthyon.factionitems.chunkbuster.give")
            "dynamite" -> player.hasPermission("oerthyon.factionitems.dynamite.give")
            "sword:epic" -> player.hasPermission("oerthyon.factionitems.sword.epic.give")
            "hoe:epic" -> player.hasPermission("oerthyon.factionitems.hoe.epic.give")
            "hoe:legendary" -> player.hasPermission("oerthyon.factionitems.hoe.legendary.give")
            "pickaxe:epic" -> player.hasPermission("oerthyon.factionitems.pickaxe.epic.give")
            "pickaxe:legendary" -> player.hasPermission("oerthyon.factionitems.pickaxe.legendary.give")
            else -> throw ItemNotFoundException()
        }
    }

    private fun getItem(itemName: String, amount: Int = 1, options: Array<out String> = arrayOf()): Array<ItemStack> {
        return Array(amount) {
            when (itemName) {
                "chunkbuster" -> Chunkbuster.give()
                "dynamite" -> Dynamite.give(
                    (options.getOrNull(0)?.toFloat() ?: Main.configuration.getDouble("dynamite.power", 4.0).toFloat()),
                    (options.getOrNull(1)?.toBoolean() ?: Main.configuration.getBoolean("dynamite.fire", true)),
                )
                "sword:epic" -> EpicSword.give()
                "hoe:epic" -> EpicHoe.give()
                "hoe:legendary" -> LegendaryHoe.give()
                "pickaxe:epic" -> EpicPickaxe.give()
                "pickaxe:legendary" -> LegendaryPickaxe.give()
                else -> throw ItemNotFoundException()
            }
        }
    }
}