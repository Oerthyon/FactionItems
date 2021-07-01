package com.superkooka.oerthyon.factionitems.items.pickaxe

import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object EpicPickaxe {

    const val displayName = "§r§4Pioche Epic"

    @JvmStatic
    fun give(): ItemStack {
        var item = ItemStack(Material.DIAMOND_PICKAXE)

        val itemMeta = item.itemMeta
        itemMeta.displayName = displayName
        item.itemMeta = itemMeta

        item.addEnchantment(Enchantment.DIG_SPEED, 5)
        item.addEnchantment(Enchantment.DURABILITY, 3)

        item = NBT.set(item, "oerthyon.item_type", "pickaxe:epic")
        return item
    }

    @JvmStatic
    fun isEpicPickaxe(item: Any?): Boolean {
        if (null === item) return false
        if (item !is ItemStack) return false
        if ("pickaxe:epic" != NBT.get(item, "oerthyon.item_type")) return false

        return true
    }
}