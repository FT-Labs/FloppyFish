/*
Author : Arda
Company : PhysTech
Date : 6/10/2020
*/

package com.mygdx.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.mygdx.game.screentransitions.ScreenTransition
import ktx.app.KtxGame
import ktx.app.KtxScreen
import kotlin.properties.Delegates

abstract class DirectedGame() : KtxGame<KtxScreen>() {
    private lateinit var currFbo : FrameBuffer
    private lateinit var nextFbo : FrameBuffer
    private lateinit var batch : SpriteBatch
    private var init = false
    private var t by Delegates.notNull<Float>()
    private var screenTransition : ScreenTransition? = null
    private var nextScreen : AbstractGameScreen? = null
    private var currScreen: AbstractGameScreen? = null


    override fun resize(width: Int, height: Int) {
        if (currScreen != null) currScreen!!.resize(width , height)
        if (nextScreen != null) nextScreen!!.resize(width ,height)
    }

    override fun pause() {
        if (currScreen != null) currScreen!!.resume()
    }

    override fun dispose() {
        if (currScreen != null) currScreen!!.hide()
        if (nextScreen != null) nextScreen!!.hide()
        if (init){
            currFbo.dispose()
            currScreen = null
            nextFbo.dispose()
            nextScreen = null
            batch.dispose()
            init = false
        }
    }


    override fun render() {
        //Get delta time and ensure upper limit of 1/60th of one second
        val deltaTime = Gdx.graphics.deltaTime.coerceAtLeast(1f/60f)
        if (nextScreen == null){
            //No transition
            if (currScreen != null) currScreen!!.render(deltaTime)
        }else{
            //Ongoing transition
            var duration = 0f
            if (screenTransition != null) duration = screenTransition!!.duration
            //Update progress of current transition
            t = Math.min(t+deltaTime , duration)
            if (screenTransition == null || t>= duration){

                //No current effect or transition finished
                if (currScreen != null) currScreen!!.hide()
                nextScreen!!.resume()
                //Enabling input for next screen
                Gdx.input.inputProcessor = nextScreen!!.getInputProcessor()

                //Switching screens
                currScreen = nextScreen
                nextScreen = null
                screenTransition = null
            }else{
                //Render screens to frame buffer
                currFbo.begin()
                if (currScreen != null) currScreen!!.render(deltaTime)
                currFbo.end()
                nextFbo.begin()
                nextScreen!!.render(deltaTime)
                nextFbo.end()
                //Render effect to screen
                val alpha = t/duration
                screenTransition!!.render(batch , currFbo.colorBufferTexture , nextFbo.colorBufferTexture , alpha)
            }

        }
    }



    fun setScreen (screen: AbstractGameScreen, screenTransition: ScreenTransition?) {
        val w = Gdx.graphics.width
        val h = Gdx.graphics.height

        if (!init){
            currFbo = FrameBuffer(Pixmap.Format.RGB888 , w , h , false)
            nextFbo = FrameBuffer(Pixmap.Format.RGB888 , w , h , false)
            batch = SpriteBatch()
            init = true
        }
        //Start transition
        nextScreen = screen
        nextScreen!!.show()
        nextScreen!!.resize(w , h)
        nextScreen!!.render(0f) //Let screen update once
        if(currScreen != null) currScreen!!.pause()
        nextScreen!!.pause()
        Gdx.input.inputProcessor = null //Disabling input
        this.screenTransition = screenTransition
        t = 0f

    }
}


















