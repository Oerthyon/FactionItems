package com.superkooka.oerthyon.factionitems.items.pickaxe

import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object LegendaryPickaxe {

    @JvmStatic
    val LUCK_LEVEL = 2

    @JvmStatic
    fun give(): ItemStack {
        var item = ItemStack(Material.DIAMOND_PICKAXE)

        val itemMeta = item.itemMeta
        itemMeta.displayName = "§r§4Pioche Légendaire"
        item.itemMeta = itemMeta

        item.addEnchantment(Enchantment.DIG_SPEED, 3)
        item.addEnchantment(Enchantment.DURABILITY, 3)
        item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, LUCK_LEVEL)

        item = NBT.set(item, "oerthyon.item_type", "pickaxe:legendary")
        return item
    }

    @JvmStatic
    fun isLegendaryPickaxe(item: Any?): Boolean {
        if (null === item) return false
        if (item !is ItemStack) return false
        if ("pickaxe:legendary" != NBT.get(item, "oerthyon.item_type")) return false

        return true
    }
}