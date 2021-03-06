package com.superkooka.oerthyon.factionitems.items.houe

import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object EpicHoe {

    @JvmStatic
    fun give(): ItemStack {
        var item = ItemStack(Material.DIAMOND_HOE)

        val itemMeta = item.itemMeta
        itemMeta.displayName = "§7Houe §dEpique"
        itemMeta.lore = arrayListOf(
            "§7§m»------------------------------«",
            "      §c● §7Accélaration sur l'âmes du nether",
            "      §c● §7Replantation auto. §8[§c3x3§8]",
            "      §c● §7Enchantement: §cUnbreaking III",
            "§7§m»------------------------------«",
        )
        item.itemMeta = itemMeta

        item.addEnchantment(Enchantment.DURABILITY, 3)

        item = NBT.set(item, "oerthyon.item_type", "hoe:epic")
        return item
    }

    @JvmStatic
    fun isEpicHoe(item: Any?): Boolean {
        if (null === item) return false
        if (item !is ItemStack) return false
        if ("hoe:epic" != NBT.get(item, "oerthyon.item_type")) return false

        return true
    }
}