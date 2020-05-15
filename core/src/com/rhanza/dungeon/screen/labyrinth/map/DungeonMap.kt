package com.rhanza.dungeon.screen.labyrinth.map

import ktx.assets.file

class DungeonMap(filePath: String) {
    private val map: List<MutableList<MapElement>>

    val rowCount: Int get() = map.size
    val columnCount: Int get() = map[0].size
    val size: Int get() = rowCount * columnCount

    init {
        val rawMap = file(filePath).readString()
        val rows = rawMap.split("\n")
        map = rows.map { row ->
            row
                .split(" ")
                .asSequence()
                .map { MapElement.parse(it) }
                .toMutableList()
        }

        val rowSize = map.first().size
        check(map.all { it.size == rowSize })
    }

    operator fun set(position: Position, newElement: MapElement) {
        map[position.x][position.y] = newElement
    }

    operator fun get(position: Position) = map[position.y][position.x]

    fun getPosition(element: MapElement): Position? {
        map.forEachIndexed { y, list ->
            val x = list.indexOfFirst { it == DungeonMap.MapElement.Start }
            if (x != -1) {
                return Position(x, y)
            }
        }
        return null
    }

    fun isPositionAvailable(position: Position): Boolean {
        return when (this[position]) {
            MapElement.Wall -> false
            MapElement.Weapon,
            MapElement.Start,
            MapElement.Chest,
            MapElement.Enemy,
            MapElement.Way -> true
        }
    }

    enum class MapElement(val mark: String) {
        Wall("0"),
        Way("+"),
        Start("S"),
        Weapon("W"),
        Enemy("E"),
        Chest("C");

        companion object {
            fun parse(mark: String) = values().first { it.mark == mark }
        }
    }
}