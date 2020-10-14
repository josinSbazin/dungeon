package com.rhanza.dungeon.screen.labyrinth.map

import ktx.assets.file

class DungeonMap(filePath: String) {
    private val map: List<MutableList<MapElement>>

    val rowCount: Int get() = map.size
    val columnCount: Int get() = map[0].size
    val startPosition: Position

    init {
        val rawMap = file(filePath).readString()
        val rows = rawMap.split("\n")
        map = rows.map { row ->
            row
                .split(" ")
                .asSequence()
                .map { MapElement.create(it) }
                .toMutableList()
        }

        val rowSize = map.first().size
        check(map.all { it.size == rowSize })
        startPosition = getStart()
    }

    operator fun set(position: Position, newElement: MapElement) {
        map[position.x][position.y] = newElement
    }

    operator fun get(position: Position) = map[position.y][position.x]

    private fun getStart(): Position {
        map.forEachIndexed { y, list ->
            val x = list.indexOfFirst { it == MapElement.Start }
            if (x != -1) {
                return Position(x, y)
            }
        }

        throw IllegalStateException("Map must contains Start element")
    }

    fun moveToAvailable(position: Position): Boolean {
        return this[position].moveAvailable
    }
}