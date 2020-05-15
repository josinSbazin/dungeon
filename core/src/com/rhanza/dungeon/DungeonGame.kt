package com.rhanza.dungeon

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.rhanza.dungeon.screen.labyrinth.LabyrinthScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen

class DungeonGame : KtxGame<KtxScreen>() {
    val batch by lazy { SpriteBatch() }

    override fun create() {
        addScreen(LabyrinthScreen(this))
        setScreen<LabyrinthScreen>()
        super.create()
    }

    override fun dispose() {
        batch.dispose()
        super.dispose()
    }
}