package com.superkooka.oerthyon.factionitems.items.dynamite

import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ExplosionPrimeEvent

class DynamiteExplosionListener : Listener {

    @EventHandler
    fun onDynamiteExpload(event: ExplosionPrimeEvent) {
        if (!Dynamite.isDynamite(event.entity)) return
        val tnt = event.entity as TNTPrimed

        event.fire = tnt.getMetadata("oerthyon.dynamite.fire").firstOrNull()?.asBoolean() ?: true
        event.radius = tnt.getMetadata("oerthyon.dynamite.radius").firstOrNull()?.asFloat() ?: 40f
    }
}
