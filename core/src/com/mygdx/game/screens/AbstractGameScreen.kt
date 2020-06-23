/*
Author : Arda
Company : PhysTech
Date : 4/20/2020
*/

package com.mygdx.game.screens

import com.badlogic.gdx.InputProcessor
import com.mygdx.game.Assets
import ktx.app.KtxGame
import ktx.app.KtxScreen

abstract class AbstractGameScreen(val game : DirectedGame) : KtxScreen {

    override fun resume() {
        Assets
    }

    override fun dispose() {
        Assets.dispose()
    }

    abstract fun getInputProcessor() : InputProcessor
}