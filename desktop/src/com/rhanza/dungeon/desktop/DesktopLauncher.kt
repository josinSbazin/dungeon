package com.rhanza.dungeon.desktop

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.rhanza.dungeon.DungeonGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            title = "Dungeon"
            height = 640
            width = 360
        }
        LwjglApplication(DungeonGame(), config).logLevel = Application.LOG_DEBUG
    }
}