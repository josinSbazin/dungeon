package com.rhanza.dungeon.screen.labyrinth.player

import com.rhanza.dungeon.screen.labyrinth.map.DungeonMap

class Player(val maxHitPoints: Int) {
    var currentHitPoints: Int = maxHitPoints
        private set
}
