package com.superkooka.oerthyon.factionitems.randomtp

import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.TooManyIterationException
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

class RandomTPCommand : CommandExecutor {

    companion object {
        @JvmStatic
        var cooldown: HashMap<UUID, Long> = HashMap()
    }

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
        messageVariable["dimension"] = player.world.environment.name

        if (!player.hasPermission("oerthyon.factionitems.rtp")) {
            player.sendMessage(
                StringUtils.parse(
                    Main.configuration.getString(
                        "random_tp.messages.not_enough_permission",
                        "Vous n'avez pas la permission d'executer cette commande"
                    ), messageVariable
                )
            )
            return true
        }

        if (!player.hasPermission("oerthyon.factionitems.no_cooldown")) {
            val cooldown = RandomTPCommand.cooldown[player.uniqueId] ?: 0

            if (cooldown > System.currentTimeMillis()) {
                messageVariable["cooldown_remaining"] = ((cooldown - System.currentTimeMillis()) / 1000).toString()
                player.sendMessage(
                    StringUtils.parse(
                        Main.configuration.getString(
                            "random_tp.messages.cooldown_unfinished",
                            "Veuillez attendre {{cooldown_remaining}} secondes"
                        ), messageVariable
                    )
                )
                return true
            }

            RandomTPCommand.cooldown[player.uniqueId] =
                System.currentTimeMillis() + (Main.configuration.getLong("random_tp.cooldown", 10) * 1000)
        }

        val currentWorld: World = player.world

        when (currentWorld.environment) {
            World.Environment.NORMAL -> {
                try {
                    player.teleport(this.findValidLocationInOverworld(currentWorld))
                } catch (e: TooManyIterationException) {
                    player.sendMessage(
                        StringUtils.parse(
                            Main.configuration.getString(
                                "random_tp.messages.unable_to_find_location",
                                "Impossible de trouver un endroit où vous téléporter"
                            ), messageVariable
                        )
                    )
                }
            }
            World.Environment.NETHER -> {
                try {
                    player.teleport(this.findValidLocationInNether(currentWorld))
                } catch (e: TooManyIterationException) {
                    player.sendMessage(
                        StringUtils.parse(
                            Main.configuration.getString(
                                "random_tp.messages.unable_to_find_location",
                                "Impossible de trouver un endroit où vous téléporter"
                            ), messageVariable
                        )
                    )
                }
            }
            else -> {
                player.sendMessage(
                    StringUtils.parse(
                        Main.configuration.getString(
                            "random_tp.messages.bad_dimension",
                            "Vous devez être dans l'overworld ou le nether pour utiliser cette commande"
                        ), messageVariable
                    )
                )
            }
        }

        return true
    }

    private fun findValidLocationInOverworld(world: World): Location {
        val location = Location(world, 0.0, 0.0, 0.0)
        val maxIteration = Main.configuration.getInt("random_tp.max_iteration_before_finding_position", 100000)

        var i = 0
        while (maxIteration >= i) {
            location.x = Random.nextDouble(
                Main.configuration.getDouble("random_tp.overworld.min_x", -1000.0),
                Main.configuration.getDouble("random_tp.overworld.max_x", 1000.0)
            )
            location.z = Random.nextDouble(
                Main.configuration.getDouble("random_tp.overworld.min_z", -1000.0),
                Main.configuration.getDouble("random_tp.overworld.max_z", -1000.0)
            )
            location.y = world.getHighestBlockYAt(location.x.toInt(), location.z.toInt()) + 1.0

            if (location.block.type.isSolid) {
                return location
            }

            i++
        }

        throw TooManyIterationException()
    }

    private fun findValidLocationInNether(world: World): Location {
        val location = Location(world, 0.0, 0.0, 0.0)
        val maxIteration = Main.configuration.getInt("random_tp.max_iteration_before_finding_position", 100000)

        var i = 0
        while (maxIteration >= i) {
            location.x = Random.nextDouble(
                Main.configuration.getDouble("random_tp.nether.min_x", -1000.0),
                Main.configuration.getDouble("random_tp.nether.max_x", 1000.0)
            )
            location.z = Random.nextDouble(
                Main.configuration.getDouble("random_tp.nether.min_z", -1000.0),
                Main.configuration.getDouble("random_tp.nether.max_z", -1000.0)
            )

            var j = 0
            while (126 > j) {
                location.y = j + 1.0
                val lowerBlock =
                    location.world.getBlockAt(location.x.toInt(), location.y.toInt() - 1, location.z.toInt())
                val currentBlock = location.block
                val upperBlock =
                    location.world.getBlockAt(location.x.toInt(), location.y.toInt() + 1, location.z.toInt())

                if (lowerBlock.type.isSolid && currentBlock.isEmpty && upperBlock.isEmpty) {
                    return location
                }

                j++
            }

            i++
        }

        throw TooManyIterationException()
    }
}