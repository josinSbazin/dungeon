package com.rhanza.dungeon.control

import com.badlogic.gdx.input.GestureDetector
import kotlin.math.abs

class DungeonGestureDetector(gestureListener: GestureListener) :
    GestureDetector(DirectionGestureListener(gestureListener)) {
    interface GestureListener {
        fun onSwipeLeft()
        fun onSwipeRight()
        fun onTap()
    }

    private class DirectionGestureListener(var gestureListener: GestureListener) : GestureAdapter() {
        override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
            if (abs(velocityX) > abs(velocityY)) {
                if (velocityX > 0) {
                    gestureListener.onSwipeRight()
                } else {
                    gestureListener.onSwipeLeft()
                }
            }
            return super.fling(velocityX, velocityY, button)
        }

        override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
            gestureListener.onTap()
            return super.tap(x, y, count, button)
        }
    }
}