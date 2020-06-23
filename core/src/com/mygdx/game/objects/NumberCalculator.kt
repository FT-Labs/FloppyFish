/*
Author : Arda
Company : PhysTech
Date : 5/19/2020
*/

package com.mygdx.game.objects

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.Assets

object NumberCalculator {

    fun calculateNumber(score : Int) : MutableList<TextureRegion> {
        val numberTextures : MutableList<TextureRegion> = arrayListOf()
        for (char in score.toString()){
            when (char) {
                '0' -> numberTextures.add(Assets.gameobjects.numberZero)
                '1' -> numberTextures.add(Assets.gameobjects.numberOne)
                '2' -> numberTextures.add(Assets.gameobjects.numberTwo)
                '3' -> numberTextures.add(Assets.gameobjects.numberThree)
                '4' -> numberTextures.add(Assets.gameobjects.numberFour)
                '5' -> numberTextures.add(Assets.gameobjects.numberFive)
                '6' -> numberTextures.add(Assets.gameobjects.numberSix)
                '7' -> numberTextures.add(Assets.gameobjects.numberSeven)
                '8' -> numberTextures.add(Assets.gameobjects.numberEight)
                '9' -> numberTextures.add(Assets.gameobjects.numberNine)
            }
        }
        return numberTextures
    }
}