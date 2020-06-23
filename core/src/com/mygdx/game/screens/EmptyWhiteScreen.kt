/*
Author : Arda
Company : PhysTech
Date : 6/11/2020
*/

package com.mygdx.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.screentransitions.ScreenTransition
import com.mygdx.game.screentransitions.ScreenTransitionFade

class EmptyWhiteScreen(game : DirectedGame) : AbstractGameScreen(game) {


    private var changeScreen = true
    private val now = System.currentTimeMillis()

    private fun changeScreen() {
        val transition : ScreenTransition = ScreenTransitionFade.init(2f)
//        game.setScreen(GameScreen(game), null)
        game.setScreen(EntranceScreen(game) , transition)

    }

    override fun getInputProcessor(): InputProcessor {
        return InputAdapter()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f , 1f , 1f , 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        if(System.currentTimeMillis() - now >= 500 && changeScreen){
            changeScreen()
            changeScreen = false
        }
    }

}