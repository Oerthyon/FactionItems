package com.superkooka.oerthyon.factionitems.items.chunkbuster

import com.gmail.filoghost.holographicdisplays.api.Hologram
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI
import com.gmail.filoghost.holographicdisplays.api.line.TextLine
import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable


class ChunkbusterActivationListener : Listener {

    @EventHandler
    fun onRightClickonChunkbuster(event: PlayerInteractEvent) {
        if (event.isBlockInHand || Action.RIGHT_CLICK_BLOCK != event.action) return
        if (!Chunkbuster.isChunkbuster(event.clickedBlock)) return

        val messageVariable = hashMapOf(Pair("player_name", event.player.displayName), Pair("block_name", "Chunkbuster"))

        if (!event.player.hasPermission("oerthyon.factionitems.chunkbuster.use")) {
            event.player.sendMessage(
                StringUtils.parse(
                    Main.configuration.getString(
                        "global.messages.not_enough_permission_to_use_it",
                        "Vous n'avez pas la permission d'utiliser ce chunkbuster"
                    ), messageVariable
                )
            )
            return
        }

        event.isCancelled = true
        val location: Location = event.clickedBlock.location

        Chunkbuster.placed_chunkbuster[location] = !(Chunkbuster.placed_chunkbuster[location] ?: true)

        val hololocation: Location = location.clone()
        val hologram: Hologram = HologramsAPI.createHologram(Main.instance, hololocation.add(0.0, 2.0, 0.0))
        val holotext: TextLine = hologram.appendTextLine("undefined")

        val countdown: Int = Main.configuration.getInt("chunkbuster.countdown", 4)

        object : BukkitRunnable() {
            var tick_elapsed = 0
            override fun run() {
                tick_elapsed++

                messageVariable["countdown"] = (countdown - tick_elapsed / 20).toString()

                holotext.text = StringUtils.parse(
                    Main.configuration.getString(
                        "chunkbuster.messages.placeholder",
                        "Explosion dans §l§4{{countdown}}§r secondes"
                    ), messageVariable
                )

                if (Chunkbuster.placed_chunkbuster[location] == false) {
                    hologram.delete()
                    cancel()
                }

                if (tick_elapsed / 20 >= countdown) {
                    hologram.delete()
                    Chunkbuster.burst(location)
                    Chunkbuster.placed_chunkbuster.remove(location)
                    cancel()
                }
            }
        }.runTaskTimer(Main.instance, 1L, 1L)
    }
}