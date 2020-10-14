package com.rhanza.dungeon.screen.labyrinth.player

import com.badlogic.gdx.utils.Disposable
import com.rhanza.dungeon.screen.labyrinth.map.Direction
import com.rhanza.dungeon.screen.labyrinth.map.DungeonMap
import com.rhanza.dungeon.screen.labyrinth.map.MapElement
import com.rhanza.dungeon.screen.labyrinth.map.Position

class TravelController(private val map: DungeonMap) : Disposable {
    private val restrictedForMovePositions: MutableList<Position> = mutableListOf()
    private val currentPositionListeners: MutableList<(
        oldPosition: Position,
        newPosition: Position,
        oldDirection: Direction,
        newDirection: Direction
    ) -> Unit> = mutableListOf()

    private val nextMovePosition
        get() = currentPosition.getNearestPositionByDirection(currentDirection)

    var currentPosition: Position = checkNotNull(map.startPosition)
        private set(value) {
            currentPositionListeners.forEach {
                it(field, value, currentDirection, currentDirection)
                field = value
            }
        }

    var currentDirection: Direction = Direction.North
        private set(value) {
            currentPositionListeners.forEach {
                it(currentPosition, currentPosition, field, value)
                field = value
            }
        }

    override fun dispose() {
        currentPositionListeners.clear()
    }

    fun tryMoveForward() = if (canMoveTo(nextMovePosition)) {
        moveTo(nextMovePosition)
        true
    } else {
        false
    }

    fun tryRotate(isClockwise: Boolean) = if (canRotate(isClockwise)) {
        rotate(isClockwise)
        true
    } else {
        false
    }

    fun addCurrentPositionListener(
        listener: (
            oldPosition: Position,
            newPosition: Position,
            oldDirection: Direction,
            newDirection: Direction
        ) -> Unit
    ) {
        currentPositionListeners.add(listener)
    }

    fun removeCurrentPositionListener(
        listener: (
            oldPosition: Position,
            newPosition: Position,
            oldDirection: Direction,
            newDirection: Direction
        ) -> Unit
    ) {
        currentPositionListeners.remove(listener)
    }

    private fun canMoveTo(position: Position): Boolean {
        return position.x >= 0
            && position.x < map.columnCount
            && position.y >= 0
            && position.y < map.rowCount
            && map[position] != MapElement.Wall
            && restrictedForMovePositions.none { it == position }
    }

    private fun moveTo(position: Position) {
        reapplyRestrivtedPosition(position)
        currentPosition = position
    }

    private fun reapplyRestrivtedPosition(position: Position) {
        restrictedForMovePositions.clear()
        if (map[position] == MapElement.Enemy) {
            addAllNearPositionInsteadCurrent(position)
        }
    }

    private fun canRotate(isClockwise: Boolean): Boolean {
        return true
    }

    private fun rotate(isClockwise: Boolean) {
        currentDirection = if (isClockwise) {
            currentDirection.clockwise
        } else {
            currentDirection.counterclockwise
        }
    }

    private fun addAllNearPositionInsteadCurrent(position: Position) {
        restrictedForMovePositions.addAll(position.allNearestPositions)
        restrictedForMovePositions.remove(currentPosition)
    }
}