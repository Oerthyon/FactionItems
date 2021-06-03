package com.superkooka.oerthyon.factionitems

import com.superkooka.oerthyon.factionitems.items.ItemsCommand
import com.superkooka.oerthyon.factionitems.items.chunkbuster.*
import com.superkooka.oerthyon.factionitems.items.dynamite.DynamitePlaceListener
import com.superkooka.oerthyon.factionitems.items.dynamite.DynamiteExplosionListener
import com.superkooka.oerthyon.factionitems.items.houe.HoeInteractEvent
import com.superkooka.oerthyon.factionitems.items.pickaxe.PickaxeBreakBlockListener
import com.superkooka.oerthyon.factionitems.randomtp.RandomTPCommand
import com.superkooka.oerthyon.factionitems.utils.Utf8YamlConfiguration
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream

class Main : JavaPlugin() {

    companion object {
        @JvmStatic
        var configuration: Utf8YamlConfiguration = Utf8YamlConfiguration()

        @JvmStatic
        var instance: JavaPlugin? = null
    }

    override fun onEnable() {
        instance = this
        configuration.load(FileInputStream(File(this.dataFolder, "config.yml")))

        val file = File(this.dataFolder, "chunkbuster_position.dat")
        if (!file.exists()) file.createNewFile()

        for (line in file.readText().split("\r\n")) {
            if (line == "") continue
            val (world, x, y, z) = line.split(';')
            val location = Location(Bukkit.getWorld(world), x.toDouble(), y.toDouble(), z.toDouble())
            Chunkbuster.place(location)
        }
        file.delete()

        this.getCommand("rtp").executor = RandomTPCommand()
        this.getCommand("factionitems").executor = ItemsCommand()

        Bukkit.getPluginManager().registerEvents(ChunkbusterPlaceListener(), this)
        Bukkit.getPluginManager().registerEvents(ChunkbusterActivationListener(), this)
        Bukkit.getPluginManager().registerEvents(ChunkbusterDestroyListener(), this)

        Bukkit.getPluginManager().registerEvents(DynamitePlaceListener(), this)
        Bukkit.getPluginManager().registerEvents(DynamiteExplosionListener(), this)

        Bukkit.getPluginManager().registerEvents(PickaxeBreakBlockListener(), this)

        Bukkit.getPluginManager().registerEvents(HoeInteractEvent(), this)
    }

    override fun onDisable() {
        val file = File(this.dataFolder, "chunkbuster_position.dat")
        if (!file.exists()) file.createNewFile()

        for (location in Chunkbuster.placed_chunkbuster.keys) {
            file.appendText(listOf(location.world.name, location.x, location.y, location.z).joinToString(";") + "\r\n")
        }
    }
}
