/*
Author : Arda
Company : PhysTech
Date : 5/2/2020
*/

package com.mygdx.game.objects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.*

enum class MossShape {
    MOSS_CLASSIC,
    MOSS_FIRST_TYPE,
    WOOD_CLASSIC,
    WOOD_FIRST_TYPE,
    WOOD_SECOND_TYPE,
    COLUMN_CLASSIC,
    COLUMN_FIRST_TYPE,
    COLUMN_SECOND_TYPE,
    COLUMN_THIRD_TYPE
}



abstract class AbstractMossObject(mossPair: Pair<TextureRegion, TextureRegion>, var MOSS_GAP: Float, open var dimX: Float = 2f, val dimY: Float = 28f, var positionX: Float,
                                  val flipBoolY: Boolean, val flipBoolX : Boolean,mossShape: MossShape) {

    val position : Vector2 = Vector2()
    val dimension : Vector2 = Vector2(1f , 1f)
    val origin : Vector2 = Vector2()
    val scale : Vector2 = Vector2(1f , 1f)
    val rotation : Float = 0f
    var MOSS_SPEED = 7f

    val randomHeightDownMoss = (MathUtils.random() - 0.25f ) / 2f * MathUtils.randomSign()
    val randomHeightUpMoss = 1f - randomHeightDownMoss

    val mossParameterDownMoss = MOSS_GAP + dimY * randomHeightDownMoss
    val mossParameterUpMoss = MOSS_GAP + dimY * randomHeightUpMoss



    open fun updateMotionX(deltaTime: Float){
        position.x -= MOSS_SPEED * deltaTime
    }


    open fun update(deltaTime : Float){
        updateMotionX(deltaTime)
        //Move to new position
    }

    abstract fun render(batch: SpriteBatch)

    abstract fun updateBounds(mossShape: MossShape)

}