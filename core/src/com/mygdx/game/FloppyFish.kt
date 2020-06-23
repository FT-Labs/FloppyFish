package com.mygdx.game

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.screens.DirectedGame
import com.mygdx.game.screens.EmptyWhiteScreen
import com.mygdx.game.screens.EntranceScreen
import com.mygdx.game.screens.MenuScreen
import com.mygdx.game.screentransitions.ScreenTransition
import com.mygdx.game.screentransitions.ScreenTransitionFade

class FloppyFish : DirectedGame() {

    private val TAG = "FloppyMain"


    override fun create() {

//        Gdx.app.logLevel = Application.LOG_DEBUG
        //Load assets
        Assets
        addScreen(MenuScreen(this))
        setScreen(EmptyWhiteScreen(this) , null)

    }




    override fun dispose() {
        Assets.dispose()
    }
}