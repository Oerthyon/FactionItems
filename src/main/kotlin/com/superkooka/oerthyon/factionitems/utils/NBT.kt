package com.superkooka.oerthyon.factionitems.utils

import net.minecraft.server.v1_8_R3.NBTTagCompound
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

object NBT {

    fun set(item: ItemStack, key: String, value: String): ItemStack {
        val stack: net.minecraft.server.v1_8_R3.ItemStack = CraftItemStack.asNMSCopy(item)
        val tag: NBTTagCompound = if (stack.tag != null) stack.tag else NBTTagCompound()
        tag.setString(key, value)
        stack.tag = tag

        return CraftItemStack.asCraftMirror(stack)
    }

    fun get(item: ItemStack, key: String): String? {
        val stack: net.minecraft.server.v1_8_R3.ItemStack? = CraftItemStack.asNMSCopy(item)
        if (stack === null) return null

        return stack.tag?.getString(key)
    }
}