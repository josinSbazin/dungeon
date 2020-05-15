package com.rhanza.dungeon.screen.labyrinth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.rhanza.dungeon.DungeonGame
import com.rhanza.dungeon.control.DungeonGestureDetector
import com.rhanza.dungeon.screen.labyrinth.map.Direction
import com.rhanza.dungeon.screen.labyrinth.map.DungeonMap
import com.rhanza.dungeon.screen.labyrinth.player.Player
import com.rhanza.dungeon.screen.labyrinth.player.TravelController
import com.rhanza.dungeon.screen.labyrinth.room.RoomDrawer
import ktx.app.KtxScreen
import ktx.graphics.use

class LabyrinthScreen(private val game: DungeonGame) : KtxScreen {
    private val camera = OrthographicCamera().apply { setToOrtho(false) }

    private val map = DungeonMap("test.dmap")
    private val roomController = RoomDrawer(game, map, checkNotNull(map.getPosition(DungeonMap.MapElement.Start)) {
        "Can't find initial position"
    }, Direction.North)

    private val travelController = TravelController(map).apply {
        addCurrentPositionListener { _, newPosition, _, newDirection ->
            roomController.chooseRoom(newPosition, newDirection)
        }
    }

    private val player = Player(100)

    init {
        Gdx.input.inputProcessor = DungeonGestureDetector(object : DungeonGestureDetector.GestureListener {
            override fun onSwipeLeft() {
                travelController.tryRotate(true)
            }

            override fun onSwipeRight() {
                travelController.tryRotate(false)
            }

            override fun onTap() {
                travelController.tryMoveForward()
            }
        })
    }

    override fun render(delta: Float) {
        game.batch.use(camera) {
            roomController.draw()
        }
    }
}