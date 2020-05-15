package com.rhanza.dungeon.screen.labyrinth.map

data class Position(val x: Int, val y: Int) {
    private val positionsByDirection = mutableMapOf<Direction, Position>()

    val allNearestPositions: List<Position> by lazy { Direction.values().map { getNearestPositionByDirection(it) } }

    fun getNearestPositionByDirection(direction: Direction) = positionsByDirection.getOrPut(direction) {
        when (direction) {
            Direction.North -> this.copy(y = this.y - 1)
            Direction.East -> this.copy(x = this.x + 1)
            Direction.South -> this.copy(y = this.y + 1)
            Direction.West -> this.copy(x = this.x - 1)
        }
    }
}