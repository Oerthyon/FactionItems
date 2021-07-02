package com.superkooka.oerthyon.factionitems.items.sword

import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object EpicSword {

    @JvmStatic
    fun give(): ItemStack {
        var item = ItemStack(Material.DIAMOND_SWORD)

        val itemMeta = item.itemMeta
        itemMeta.displayName = "§7Epée §dEpique"
        itemMeta.lore = arrayListOf(
            "§7§m»------------------------------«",
            "      §c● §7Enchantement: ",
            "      §c● §cChatiment V",
            "      §c● §cUnbreaking III",
            "      §c● §cLooting V",
            "§7§m»------------------------------«"
        )
        item.itemMeta = itemMeta

        item.addEnchantment(Enchantment.DAMAGE_UNDEAD, 5)
        item.addEnchantment(Enchantment.DURABILITY, 3)
        item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 5)

        item = NBT.set(item, "oerthyon.item_type", "sword:epic")
        return item
    }
}