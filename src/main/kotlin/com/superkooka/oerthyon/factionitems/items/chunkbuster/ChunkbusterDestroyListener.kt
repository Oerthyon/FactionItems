package com.superkooka.oerthyon.factionitems.items.chunkbuster

import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class ChunkbusterDestroyListener : Listener {

    @EventHandler
    fun onChunkbusterDestroyed(event: PlayerInteractEvent) {
        if (!Chunkbuster.isChunkbuster(event.clickedBlock)) return
        if (Action.LEFT_CLICK_BLOCK != event.action || null == event.clickedBlock) return

        event.isCancelled = true

        if (null == event.item) return
        if (Material.IRON_PICKAXE != event.item.type && Material.DIAMOND_PICKAXE != event.item.type) return

        val location: Location = event.clickedBlock.location

        if (Chunkbuster.placed_chunkbuster[location] == true) {
            event.player.sendMessage(
                StringUtils.parse(
                    Main.configuration.getString(
                        "chunkbuster.messages.already_active",
                        "§l§4Attention...§r Ce chunkbuster est déjà prêt à exploser"
                    ),
                    hashMapOf(Pair("player_name", event.player.displayName))
                )
            )

            return
        }

        Chunkbuster.placed_chunkbuster.remove(location)
        location.world.getBlockAt(location).type = Material.AIR
        location.world.dropItem(location, Chunkbuster.give())
    }
}