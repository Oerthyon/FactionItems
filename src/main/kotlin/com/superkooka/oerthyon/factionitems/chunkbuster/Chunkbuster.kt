package com.superkooka.oerthyon.factionitems.chunkbuster

import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.NBT
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue

object Chunkbuster {
    @JvmStatic
    val placed_chunkbuster: HashMap<Location, Boolean> = HashMap()

    @JvmStatic
    fun giveOne(): ItemStack {
        val item = ItemStack(Material.ENDER_PORTAL_FRAME)

        val itemMeta = item.itemMeta
        itemMeta.displayName = "§r§4Chunkbuster"
        item.itemMeta = itemMeta

        return NBT.set(item, "oerthyon.item_type", "chunkbuster")
    }

    @JvmStatic
    fun place(location: Location) {
        val chunkbuster = location.world.getBlockAt(location)
        chunkbuster.type = Material.ENDER_PORTAL_FRAME
        chunkbuster.setMetadata("oerthyon.item_type", FixedMetadataValue(Main.instance, "chunkbuster"))
    }

    @JvmStatic
    fun place(block: Block) {
        block.type = Material.ENDER_PORTAL_FRAME
        block.setMetadata("oerthyon.item_type", FixedMetadataValue(Main.instance, "chunkbuster"))
    }

    @JvmStatic
    fun isChunkbuster(obj: Any?): Boolean {
        return when (obj) {
            is ItemStack -> "chunkbuster" == NBT.get(obj, "oerthyon.item_type")
            is Block -> "chunkbuster" == obj.getMetadata("oerthyon.item_type").firstOrNull()?.asString()
            else -> null
        } ?: return false
    }

    @JvmStatic
    fun burst(location: Location) {
        val xMin = location.chunk.x shl 4
        val xMax = (location.chunk.x shl 4) + 16

        val zMin = location.chunk.z shl 4
        val zMax = (location.chunk.z shl 4) + 16

        for (x in xMin..xMax) {
            for (y in 0..256) {
                for (z in zMin..zMax) {
                    val block = location.world.getBlockAt(x, y, z)

                    if (block.type == Material.BEDROCK) {
                        continue
                    }

                    block.type = Material.AIR
                }
            }
        }
    }
}