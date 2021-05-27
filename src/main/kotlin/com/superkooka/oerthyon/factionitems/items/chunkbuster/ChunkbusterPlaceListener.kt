package com.superkooka.oerthyon.factionitems.items.chunkbuster

import com.massivecraft.factions.*
import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class ChunkbusterPlaceListener : Listener {

    @EventHandler
    fun onChunkbusterPlaced(event: BlockPlaceEvent) {
        if (!Chunkbuster.isChunkbuster(event.itemInHand)) {
            return
        }

        val fplayer: FPlayer = FPlayers.getInstance().getByPlayer(event.player) ?: return
        val fchunk = FLocation(event.blockPlaced.world.name, event.blockPlaced.chunk.x, event.blockPlaced.chunk.z)
        val faction: Faction = Factions.getInstance().getFactionById(fplayer.factionId)

        val message_variable: HashMap<String, String> = HashMap()
        message_variable["player"] = event.player.displayName

        if (
            !event.player.hasPermission("oerthyon.factionitems.chunkbuster.use_everywhere") &&
            !fplayer.isAdminBypassing &&
            !faction.allClaims.contains(fchunk)
        ) {
            event.player.sendMessage(
                StringUtils.parse(
                    Main.configuration.getString(
                        "chunkbuster.messages.not_enough_permission_to_place_it",
                        "Vous n'avez pas la permission de placer un chunkbuster ici"
                    ), message_variable
                )
            )

            event.isCancelled = true
            return
        }

        val block = event.block
        Chunkbuster.place(block)
        Chunkbuster.placed_chunkbuster[block.location] = false
    }
}