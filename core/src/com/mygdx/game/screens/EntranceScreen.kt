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
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.Assets
import com.mygdx.game.Constants
import com.mygdx.game.screentransitions.ScreenTransition
import com.mygdx.game.screentransitions.ScreenTransitionFade

class EntranceScreen(game : DirectedGame) : AbstractGameScreen(game)  {



    private val logo : TextureRegion = Assets.logo.logo
    private val name : TextureRegion = Assets.logo.name

    private val batch : SpriteBatch = SpriteBatch()
    private val now = System.currentTimeMillis()
    private val camera = OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT)

    init {
        camera.position.set(0f , 0f , 0f)
        camera.update()
    }


    private fun changeScreen() {
        val transition : ScreenTransition = ScreenTransitionFade.init(2f)
        game.setScreen(MenuScreen(game) , transition)
    }

    override fun getInputProcessor(): InputProcessor {
        return InputAdapter()
    }

    override fun render(delta: Float) {
        batch.projectionMatrix = camera.combined

        batch.begin()
        if (System.currentTimeMillis() - now >=50){
            batch.draw(logo , -4.4f  , -2.5f , 8.8f , 8f)
            batch.draw(name , -3f , -6f , 6f , 2f)
        }
        if (System.currentTimeMillis() - now >=1800){
            changeScreen()
        }

        batch.end()
    }

}