package com.superkooka.oerthyon.factionitems.items.houe

import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object LegendaryHoe {

    @JvmStatic
    fun give(): ItemStack {
        var item = ItemStack(Material.DIAMOND_HOE)

        val itemMeta = item.itemMeta
        itemMeta.displayName = "§7Houe §bLégendaire"
        itemMeta.lore = arrayListOf(
            "§7§m»------------------------------«",
            "      §c● §7Accélaration sur l''âmes du nether",
            "      §c● §7Replantation auto. §8[§c5x5§8]",
            "      §c● §7Enchantement: §cUnbreaking III",
            "§7§m»------------------------------«",
        )
        item.itemMeta = itemMeta

        item.addEnchantment(Enchantment.DURABILITY, 3)

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