package com.rhanza.dungeon.screen.labyrinth.room

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.rhanza.dungeon.DungeonGame
import com.rhanza.dungeon.screen.labyrinth.map.Direction
import com.rhanza.dungeon.screen.labyrinth.map.DungeonMap
import com.rhanza.dungeon.screen.labyrinth.map.Position

class RoomDrawer(
    private val game: DungeonGame,
    private val map: DungeonMap,
    initialPosition: Position,
    initialDirection: Direction
) {

    private val sectionWidth = Gdx.graphics.width.toFloat() / 3
    private val sectionHeight = Gdx.graphics.height.toFloat()

    private val leftRect = Rectangle(0f, 0f, sectionWidth, sectionHeight)
    private val centerRect = Rectangle(sectionWidth, 0f, sectionWidth, sectionHeight)
    private val rightRect = Rectangle(sectionWidth * 2f, 0f, sectionWidth, sectionHeight)

    private val roomTypes: Map<Int, RoomBackgroundSet>

    private lateinit var currentBackgroundSet: RoomBackgroundSet

    init {
        val frontArch = Texture("arch_center.png")
        val leftArch = Texture("arch_left.png")
        val rightArch = Texture("arch_right.png")
        val frontWall = Texture("wall_center.png")
        val leftWall = Texture("wall_left.png")
        val rightWall = Texture("wall_right.png")


        roomTypes = mapOf(
            calculateRoomCode(left = false, front = false, right = false) to RoomBackgroundSet(
                leftWall,
                frontWall,
                rightWall
            ),
            calculateRoomCode(left = false, front = true, right = false) to RoomBackgroundSet(
                leftWall,
                frontArch,
                rightWall
            ),
            calculateRoomCode(left = true, front = false, right = false) to RoomBackgroundSet(
                leftArch,
                frontWall,
                rightWall
            ),
            calculateRoomCode(left = false, front = false, right = true) to RoomBackgroundSet(
                leftWall,
                frontWall,
                rightArch
            ),
            calculateRoomCode(left = true, front = true, right = false) to RoomBackgroundSet(
                leftArch,
                frontArch,
                rightWall
            ),
            calculateRoomCode(left = false, front = true, right = true) to RoomBackgroundSet(
                leftWall,
                frontArch,
                rightArch
            ),
            calculateRoomCode(left = true, front = false, right = true) to RoomBackgroundSet(
                leftArch,
                frontWall,
                rightArch
            ),
            calculateRoomCode(left = true, front = true, right = true) to RoomBackgroundSet(
                leftArch,
                frontArch,
                rightArch
            )
        )

        chooseRoom(initialPosition, initialDirection)
    }

    fun chooseRoom(position: Position, direction: Direction) {
        currentBackgroundSet = getCurrentRoomBackgroundSet(position, direction)
    }

    fun draw() {
        game.batch.draw(currentBackgroundSet.left, leftRect.x, leftRect.y, leftRect.width, leftRect.height)
        game.batch.draw(currentBackgroundSet.front, centerRect.x, centerRect.y, centerRect.width, centerRect.height)
        game.batch.draw(currentBackgroundSet.right, rightRect.x, rightRect.y, centerRect.width, centerRect.height)
    }

    private fun getCurrentRoomBackgroundSet(position: Position, direction: Direction): RoomBackgroundSet {
        val left = position.getNearestPositionByDirection(direction.counterclockwise)
        val front = position.getNearestPositionByDirection(direction)
        val right = position.getNearestPositionByDirection(direction.clockwise)

        return checkNotNull(
            roomTypes[calculateRoomCode(
                map.isPositionAvailable(left),
                map.isPositionAvailable(front),
                map.isPositionAvailable(right)
            )]
        ) {
            "Can't find room type"
        }
    }

    private fun calculateRoomCode(left: Boolean, front: Boolean, right: Boolean): Int {
        var result = 0
        if (left) result = result or 0x001
        if (front) result = result or 0x010
        if (right) result = result or 0x100
        return result
    }
}