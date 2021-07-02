package com.superkooka.oerthyon.factionitems.items.pickaxe

import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object EpicPickaxe {

    const val displayName = "§7Pioche §dEpique"

    @JvmStatic
    fun give(): ItemStack {
        var item = ItemStack(Material.DIAMOND_PICKAXE)

        val itemMeta = item.itemMeta
        itemMeta.displayName = displayName
        itemMeta.lore = arrayListOf(
            "§7§m»------------------------------«",
            "      §c● §7Enchantement: §cEfficiency III, Unbreaking III",
            "      §c● §7Cuit les minerais automatiquement",
            "      §c● §7Mine §8[§c3x3§8]",
            "§7§m»------------------------------«"
        )
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