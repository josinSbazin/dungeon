package com.rhanza.dungeon.screen.labyrinth.room

import com.badlogic.gdx.graphics.g2d.Batch
import com.rhanza.dungeon.screen.labyrinth.map.Direction
import com.rhanza.dungeon.screen.labyrinth.map.DungeonMap
import com.rhanza.dungeon.screen.labyrinth.map.MapElement
import com.rhanza.dungeon.screen.labyrinth.map.Position

class MapElementDrawer(
    private val batch: Batch,
    private val map: DungeonMap,
    initialPosition: Position,
    initialDirection: Direction
) {
    private val elements: MutableList<MapElementWithViewDirection> = mutableListOf()

    init {
        choose(initialPosition, initialDirection)
    }

    fun choose(position: Position, direction: Direction) {
        elements.add(position.left(direction))
        elements.add(position.front(direction))
        elements.add(position.right(direction))
    }

    fun draw() {
        elements.forEach { it.mapElement.draw(batch, it.viewDirection) }
    }

    private fun Position.front(direction: Direction): MapElementWithViewDirection =
        MapElementWithViewDirection(map[this.getNearestPositionByDirection(direction)],
            MapElement.ViewDirection.Front)

    private fun Position.left(direction: Direction): MapElementWithViewDirection =
        MapElementWithViewDirection(map[this.getNearestPositionByDirection(direction.counterclockwise)],
            MapElement.ViewDirection.Left)

    private fun Position.right(direction: Direction): MapElementWithViewDirection =
        MapElementWithViewDirection(map[this.getNearestPositionByDirection(direction.clockwise)],
            MapElement.ViewDirection.Right)

    private class MapElementWithViewDirection(val mapElement: MapElement, val viewDirection: MapElement.ViewDirection)
}