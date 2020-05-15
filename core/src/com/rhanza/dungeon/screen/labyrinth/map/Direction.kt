package com.rhanza.dungeon.screen.labyrinth.map

enum class Direction {
    North, East, South, West;

    operator fun unaryMinus(): Direction {
        return when (this) {
            North -> South
            East -> West
            South -> North
            West -> East
        }
    }

    val clockwise: Direction
        get() = when (this) {
            North -> East
            East -> South
            South -> West
            West -> North
        }

    val counterclockwise: Direction
        get() = when(this) {
            North -> West
            West -> South
            South -> East
            East -> North
        }
}