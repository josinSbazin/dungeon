package com.rhanza.dungeon.screen.labyrinth.map

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Scaling
import com.rhanza.dungeon.screen.labyrinth.player.Player

interface MapElement {
    val moveAvailable: Boolean get() = true
    fun draw(batch: Batch, viewDirection: ViewDirection) = Unit
    fun action(player: Player): Boolean = false

    object Wall : MapElement {
        override val moveAvailable: Boolean = false

        private val sectionWidth = Gdx.graphics.width.toFloat() / 3
        private val sectionHeight = Gdx.graphics.height.toFloat()

        private val leftRect = Rectangle(0f, 0f, sectionWidth, sectionHeight)
        private val frontRect = Rectangle(sectionWidth, 0f, sectionWidth, sectionHeight)
        private val rightRect = Rectangle(sectionWidth * 2f, 0f, sectionWidth, sectionHeight)

        private val leftTexture = Texture("wall_left.png")
        private val frontTexture = Texture("wall_center.png")
        private val rightTexture = Texture("wall_right.png")

        override fun draw(batch: Batch, viewDirection: ViewDirection) {
            when (viewDirection) {
                ViewDirection.Left ->
                    batch.draw(leftTexture, leftRect.x, leftRect.y, leftRect.width, leftRect.height)
                ViewDirection.Front ->
                    batch.draw(frontTexture, frontRect.x, frontRect.y, frontRect.width, frontRect.height)
                ViewDirection.Right ->
                    batch.draw(rightTexture, rightRect.x, rightRect.y, rightRect.width, rightRect.height)
            }
        }
    }

    object Arc : MapElement {
        private val sectionWidth = Gdx.graphics.width.toFloat() / 3
        private val sectionHeight = Gdx.graphics.height.toFloat()

        private val leftRect = Rectangle(0f, 0f, sectionWidth, sectionHeight)
        private val frontRect = Rectangle(sectionWidth, 0f, sectionWidth, sectionHeight)
        private val rightRect = Rectangle(sectionWidth * 2f, 0f, sectionWidth, sectionHeight)

        private val leftTexture = Texture("arch_left.png")
        private val frontTexture = Texture("arch_center.png")
        private val rightTexture = Texture("arch_right.png")

        override fun draw(batch: Batch, viewDirection: ViewDirection) {
            when (viewDirection) {
                ViewDirection.Left ->
                    batch.draw(leftTexture, leftRect.x, leftRect.y, leftRect.width, leftRect.height)
                ViewDirection.Front ->
                    batch.draw(frontTexture, frontRect.x, frontRect.y, frontRect.width, frontRect.height)
                ViewDirection.Right ->
                    batch.draw(rightTexture, rightRect.x, rightRect.y, rightRect.width, rightRect.height)
            }
        }
    }

    object Start : MapElement {
        private val sectionWidth = Gdx.graphics.width.toFloat() / 3
        private val sectionHeight = Gdx.graphics.height.toFloat()

        private val leftRect = Rectangle(0f, 0f, sectionWidth, sectionHeight)
        private val frontRect = Rectangle(sectionWidth, 0f, sectionWidth, sectionHeight)
        private val rightRect = Rectangle(sectionWidth * 2f, 0f, sectionWidth, sectionHeight)

        private val leftTexture = Texture("arch_left.png")
        private val frontTexture = Texture("arch_center.png")
        private val rightTexture = Texture("arch_right.png")

        override fun draw(batch: Batch, viewDirection: ViewDirection) {
            when (viewDirection) {
                ViewDirection.Left ->
                    batch.draw(leftTexture, leftRect.x, leftRect.y, leftRect.width, leftRect.height)
                ViewDirection.Front ->
                    batch.draw(frontTexture, frontRect.x, frontRect.y, frontRect.width, frontRect.height)
                ViewDirection.Right ->
                    batch.draw(rightTexture, rightRect.x, rightRect.y, rightRect.width, rightRect.height)
            }
        }
    }

    object Weapon : MapElement {
        private val image = Image(Texture("weapon.png")).apply {
            width = Gdx.graphics.width * 0.7f
            setScaling(Scaling.fillX)
        }

        override fun draw(batch: Batch, viewDirection: ViewDirection) {
            Arc.draw(batch, viewDirection)
            if (viewDirection == ViewDirection.Front) {
                image.draw(batch, 1f)
            }
        }
    }

    object Enemy : MapElement {
        override val moveAvailable: Boolean = false

        private val image = Image(Texture("enemy.png")).apply {
            width = Gdx.graphics.width * 0.7f
            setScaling(Scaling.fillX)
        }

        override fun draw(batch: Batch, viewDirection: ViewDirection) {
            Arc.draw(batch, viewDirection)
            image.draw(batch, 1f)
        }
    }

    object Chest : MapElement {
        override val moveAvailable: Boolean = false

        private val image = Image(Texture("chest.png")).apply {
            val screenWidth = Gdx.graphics.width
            val screenHeight = Gdx.graphics.height

            width = screenWidth * 0.7f
            setScaling(Scaling.fit)
            setPosition(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2);
        }

        override fun draw(batch: Batch, viewDirection: ViewDirection) {
            Arc.draw(batch, viewDirection)
            image.draw(batch, 1f)
        }
    }

    enum class ViewDirection {
        Front, Left, Right
    }

    companion object {
        fun create(mark: String) = when (mark) {
            "0" -> Wall
            "+" -> Arc
            "S" -> Start
            "W" -> Weapon
            "E" -> Enemy
            "C" -> Chest
            else -> throw IllegalArgumentException("Can't parse MapElement with mark - $mark")
        }
    }
}

