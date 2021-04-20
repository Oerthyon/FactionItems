package com.superkooka.oerthyon.factionitems

import com.superkooka.oerthyon.factionitems.randomtp.RandomTPCommand
import com.superkooka.oerthyon.factionitems.utils.Utf8YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream

class Main : JavaPlugin() {

    companion object {
        @JvmStatic
        var configuration: Utf8YamlConfiguration = Utf8YamlConfiguration()
    }

    override fun onEnable() {
        configuration.load(FileInputStream(File( this.dataFolder, "config.yml")))
        this.getCommand("rtp").executor = RandomTPCommand()
    }
}
