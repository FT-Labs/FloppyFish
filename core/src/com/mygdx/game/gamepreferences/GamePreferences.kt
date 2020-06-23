/*
Author : Arda
Company : PhysTech
Date : 6/11/2020
*/

package com.mygdx.game.gamepreferences

import com.badlogic.gdx.Gdx
import com.mygdx.game.Constants

object GamePreferences {
    private val TAG = "GamePref"
    val prefs = Gdx.app.getPreferences(Constants.PREFERENCES)
    val highScore = prefs.getInteger("highscore")


}