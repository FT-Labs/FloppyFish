/*
Author : Arda
Company : PhysTech
Date : 6/10/2020
*/

package com.mygdx.game.screentransitions

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface ScreenTransition {

    var duration : Float

    fun render(batch : SpriteBatch , currScreen : Texture , nextScreen : Texture, alpha : Float)
}