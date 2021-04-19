package com.superkooka.oerthyon.factionitems

import com.superkooka.oerthyon.factionitems.randomtp.RandomTPCommand
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        println("Hello Spigot !")

        this.getCommand("rtp").executor = RandomTPCommand()
    }
}
