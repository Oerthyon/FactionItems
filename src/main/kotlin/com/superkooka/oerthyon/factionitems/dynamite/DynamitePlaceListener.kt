package com.superkooka.oerthyon.factionitems.dynamite

import com.gmail.filoghost.holographicdisplays.api.Hologram
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI
import com.gmail.filoghost.holographicdisplays.api.line.TextLine
import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.NBT
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.scheduler.BukkitRunnable

class DynamitePlaceListener : Listener {

    @EventHandler
    fun onRightClickwithDynamite(event: BlockPlaceEvent) {
        if (!Dynamite.isDynamite(event.itemInHand)) return

        event.isCancelled = true

        val message_variable = HashMap<String, String>()
        message_variable["player_name"] = event.player.displayName

        if (!event.player.hasPermission("oerthyon.factionitems.dynamite.use")) {
            event.player.sendMessage(
                StringUtils.parse(
                    Main.configuration.getString(
                        "dynamite.messages.not_enough_permission",
                        "Vous n'avez pas la permission d'utiliser cette dynamite"
                    ),
                    message_variable
                )
            )

            return
        }


        val location: Location = event.blockPlaced.location
        location.add(0.75, 0.0, 0.75) //TODO: Fix this ugly tweaks
        Dynamite.place(location, event.itemInHand)

        val hololocation: Location = location.clone()
        val hologram: Hologram = HologramsAPI.createHologram(Main.instance, hololocation.add(0.0, 2.0, 0.0))
        val holotext: TextLine = hologram.appendTextLine("undefined")

        val countdown: Int = NBT.get(event.itemInHand, "oerthyon.dynamite.countdown")?.toIntOrNull() ?: 4

        object : BukkitRunnable() {
            var tick_elapsed = 0
            override fun run() {
                tick_elapsed++

                message_variable["countdown"] = (countdown - tick_elapsed / 20).toString()

                holotext.text = StringUtils.parse(
                    Main.configuration.getString(
                        "dynamite.messages.placeholder",
                        "Explosion dans §l§4{{countdown}}§r secondes"
                    ), message_variable
                )

                if (tick_elapsed / 20 >= countdown) {
                    hologram.delete()
                    cancel()
                }
            }
        }.runTaskTimer(Main.instance, 1L, 1L)
    }
}