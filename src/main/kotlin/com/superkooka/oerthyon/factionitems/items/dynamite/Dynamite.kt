package com.superkooka.oerthyon.factionitems.items.dynamite

import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.TNTPrimed
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue

object Dynamite {

    @JvmStatic
    fun give(radius: Float, fire: Boolean): ItemStack {
        var item = ItemStack(Material.TNT)

        val itemMeta = item.itemMeta
        itemMeta.displayName = "§r§4Dynamite"
        item.itemMeta = itemMeta

        item = NBT.set(item, "oerthyon.item_type", "dynamite")
        item = NBT.set(item, "oerthyon.dynamite.countdown", Main.configuration.getString("dynamite.countdown", "4"))
        item = NBT.set(item, "oerthyon.dynamite.radius", radius.toString())
        return NBT.set(item, "oerthyon.dynamite.fire", fire.toString())
    }

    fun place(location: Location, item: ItemStack) {
        val tnt: TNTPrimed = location.world.spawnEntity(location, EntityType.PRIMED_TNT) as TNTPrimed
        tnt.fuseTicks = (NBT.get(item, "oerthyon.dynamite.countdown")?.toIntOrNull() ?: 4) * 20

        tnt.setMetadata("oerthyon.item_type", FixedMetadataValue(Main.instance, "dynamite"))
        tnt.setMetadata("oerthyon.dynamite.fire", FixedMetadataValue(Main.instance, NBT.get(item, "oerthyon.dynamite.fire")))
        tnt.setMetadata("oerthyon.dynamite.radius", FixedMetadataValue(Main.instance, NBT.get(item, "oerthyon.dynamite.radius")))
    }

    @JvmStatic
    fun isDynamite(obj: Any?): Boolean {
        return when (obj) {
            is ItemStack -> "dynamite" == NBT.get(obj, "oerthyon.item_type")
            is TNTPrimed -> "dynamite" == obj.getMetadata("oerthyon.item_type").firstOrNull()?.asString()
            else -> null
        } ?: return false
    }
}