/*
Author : Arda
Company : PhysTech
Date : 6/11/2020
*/

package com.mygdx.game.screentransitions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import kotlin.properties.Delegates

object ScreenTransitionFade : ScreenTransition {

    override var duration: Float by Delegates.notNull<Float>()

    fun init(duration : Float) : ScreenTransitionFade{
        this.duration = duration
        return this
    }

    override fun render(batch: SpriteBatch, currScreen: Texture, nextScreen: Texture, alpha: Float) {
        val w = currScreen.width.toFloat()
        val h = currScreen.height.toFloat()
        val alpha1 = Interpolation.fade.apply(alpha)

        Gdx.gl.glClearColor(0f , 0f , 0f , 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        batch.setColor(1f , 1f , 1f , 1f)
        batch.draw(currScreen , 0f , 0f , 0f , 0f , w , h , 1f , 1f , 0f , 0 , 0 , currScreen.width , currScreen.height , false , true)
        batch.setColor(1f , 1f , 1f , alpha1)
        batch.draw(nextScreen , 0f , 0f , 0f , 0f , w , h , 1f , 1f , 0f , 0 , 0 , nextScreen.width , nextScreen.height , false , true)
        batch.end()
    }


}