package com.superkooka.oerthyon.factionitems.randomtp

import com.superkooka.oerthyon.factionitems.TooManyIterationException
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.random.Random

class RandomTPCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            return false
        }

        val player: Player = sender

        if (!player.hasPermission("oerthyon.factionitems.rtp")) {
            player.sendMessage("Vous n'avez pas la permission d'executer cette commande")
            return true
        }

        val currentWorld: World = player.world

        when(currentWorld.environment) {
            World.Environment.NORMAL -> {
                try {
                    player.teleport(this.findValidLocationInOverworld(currentWorld))
                } catch (e: TooManyIterationException) {
                    player.sendMessage("Impossible de trouver un endroit où vous téléporter")
                }
            }
            World.Environment.NETHER -> {
                try {
                    player.teleport(this.findValidLocationInNether(currentWorld))
                } catch (e: TooManyIterationException) {
                    player.sendMessage("Impossible de trouver un endroit où vous téléporter")
                }
            }
            else -> {
                player.sendMessage("Vous devez être dans l'overworld ou le nether pour utiliser cette commande")
            }
        }

        return true
    }

    private fun findValidLocationInOverworld(world: World): Location {
        val location = Location(world, 0.0, 0.0, 0.0)
        val maxIteration = 100000

        var i = 0
        while (maxIteration >= i) {
            location.x = Random.nextDouble(-100.0,100.0)
            location.z = Random.nextDouble(-100.0, 100.0)
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
        val maxIteration = 100000

        var i = 0
        while (maxIteration >= i) {
            location.x = Random.nextDouble(-100.0,100.0)
            location.z = Random.nextDouble(-100.0, 100.0)

            var j = 0
            while (126 > j) {
                location.y = j + 1.0
                val lowerBlock = location.world.getBlockAt(location.x.toInt(), location.y.toInt() - 1, location.z.toInt())
                val currentBlock = location.block
                val upperBlock = location.world.getBlockAt(location.x.toInt(), location.y.toInt() + 1, location.z.toInt())

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