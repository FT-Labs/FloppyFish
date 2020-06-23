/*
Author : Arda
Company : PhysTech
Date : 4/20/2020
*/

package com.mygdx.game.objects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Ellipse
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

abstract class AbstractGameObject {
    val position : Vector2 = Vector2()
    val dimension : Vector2 = Vector2(1f , 1f)
    val origin : Vector2 = Vector2()
    val scale : Vector2 = Vector2(1f , 1f)
    var rotation : Float = 0f
    val velocity = Vector2()
    val gravity = Vector2()
    open val bounds = Circle()


    open fun updateMotionY(deltaTime: Float){
        velocity.y -= gravity.y * deltaTime
    }


    open fun update(deltaTime : Float){
        updateMotionY(deltaTime)
        //Move to new position
        position.x += velocity.x * deltaTime
        position.y += velocity.y * deltaTime
    }

    abstract fun render(batch: SpriteBatch)

}