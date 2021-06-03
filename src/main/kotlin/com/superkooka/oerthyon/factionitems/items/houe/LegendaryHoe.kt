package com.superkooka.oerthyon.factionitems.items.houe

import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object LegendaryHoe {

    @JvmStatic
    fun give(): ItemStack {
        var item = ItemStack(Material.DIAMOND_HOE)

        val itemMeta = item.itemMeta
        itemMeta.displayName = "§r§4Houe Legendaire"
        item.itemMeta = itemMeta

        item = NBT.set(item, "oerthyon.item_type", "hoe:legendary")
        return item
    }

    @JvmStatic
    fun isLegendaryHoe(item: Any?): Boolean {
        if (null === item) return false
        if (item !is ItemStack) return false
        if ("hoe:legendary" != NBT.get(item, "oerthyon.item_type")) return false

        return true
    }
}