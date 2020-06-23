/*
Author : Arda
Company : PhysTech
Date : 6/13/2020
*/

package com.mygdx.game.screentransitions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import kotlin.properties.Delegates

object ScreenTransitionSlide : ScreenTransition{

    const val LEFT = 1
    const val RIGHT = 2
    const val UP = 3
    const val DOWN = 4

    override var duration: Float by Delegates.notNull<Float>()
    private var direction by Delegates.notNull<Int>()
    private var slideOut by Delegates.notNull<Boolean>()
    private var eas : Interpolation? = null

    fun init (duration : Float , direction : Int , slideOut : Boolean , eas : Interpolation) : ScreenTransitionSlide{
        this.duration = duration
        this.direction = direction
        this.slideOut = slideOut
        this.eas = eas
        return this
    }

    override fun render(batch: SpriteBatch, currScreen: Texture, nextScreen: Texture, alpha: Float) {
        val w = currScreen.width.toFloat()
        val h = currScreen.height.toFloat()
        var x = 0f
        var y = 0f
        var alpha1 : Float? = null

        if(eas!= null) {
            alpha1 = eas!!.apply(alpha)
        }

        //Calculate position offset

        when (direction) {
            LEFT -> {
                x = -w * alpha1!!
                if (!slideOut) x += w
            }
            RIGHT -> {
                x = w * alpha1!!
                if (!slideOut) x -= w
            }
            UP -> {
                y = h*alpha1!!
                if (!slideOut) y -= h
            }
            DOWN -> {
                y = -h * alpha1!!
                if (!slideOut) y += h
            }
        }

        //Drawing order, depens on in or out
        val texBottom : Texture = if (slideOut) nextScreen else currScreen
        val texTop : Texture = if (slideOut) currScreen else nextScreen

        //Draw both screens
        Gdx.gl20.glClearColor(0f , 0f , 0f , 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        batch.draw(texBottom , 0f , 0f , 0f , 0f , w , h , 1f , 1f ,  0f , 0 , 0, currScreen.width , currScreen.height , false , true)
        batch.draw(texTop , x, y , 0f , 0f , w , h , 1f , 1f ,  0f , 0 , 0, currScreen.width , currScreen.height , false , true)
        batch.end()

    }
}