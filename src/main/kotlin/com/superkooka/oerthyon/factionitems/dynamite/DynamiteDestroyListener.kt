package com.superkooka.oerthyon.factionitems.dynamite

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class DynamiteDestroyListener : Listener {

    @EventHandler
    fun onDynamiteDestroy(event: BlockBreakEvent) {
        if (!Dynamite.isDynamite(event.block)) return

        Dynamite.placed_dynamite.remove(event.block.location)
    }
}