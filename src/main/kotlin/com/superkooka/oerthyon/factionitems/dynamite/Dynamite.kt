package com.superkooka.oerthyon.factionitems.dynamite

import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue

object Dynamite {

    @JvmStatic
    val placed_dynamite: ArrayList<Location> = ArrayList()

    @JvmStatic
    fun giveOne(power: Float, fire: Boolean): ItemStack {
        var item = ItemStack(Material.TNT)

        val itemMeta = item.itemMeta
        itemMeta.displayName = "§r§4Dynamite"
        item.itemMeta = itemMeta

        item = NBT.set(item, "oerthyon.item_type", "dynamite")
        item = NBT.set(item, "oerthyon.dynamite.power", power.toString())
        return NBT.set(item, "oerthyon.dynamite.fire", fire.toString())
    }

    fun giveMany(amount: Int, power: Float, fire: Boolean): ArrayList<ItemStack> {
        val items = ArrayList<ItemStack>()

        for (i in 1..amount) {
            items.add(giveOne(power, fire))
        }

        return items
    }

    fun place(location: Location) {
        location.block.setMetadata("oerthyon.item_type", FixedMetadataValue(Main.instance, "dynamite"))
        placed_dynamite.add(location)
    }

    @JvmStatic
    fun explode(location: Location, item: ItemStack) {
        if (!isDynamite(item)) return

        val power = NBT.get(item, "oerthyon.dynamite.power")?.toFloat() ?: return
        val fire = NBT.get(item, "oerthyon.dynamite.fire")?.toBoolean() ?: return

        location.world.getBlockAt(location).type = Material.AIR
        placed_dynamite.remove(location)

        location.world.createExplosion(location, power, fire)
    }

    @JvmStatic
    fun isDynamite(obj: Any?): Boolean {
        return when (obj) {
            is ItemStack -> "dynamite" == NBT.get(obj, "oerthyon.item_type")
            is Block -> "dynamite" == obj.getMetadata("oerthyon.item_type").firstOrNull()?.asString()
            else -> null
        } ?: return false
    }
}