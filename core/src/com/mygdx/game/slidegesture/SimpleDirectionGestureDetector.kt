/*
Author : Arda
Company : PhysTech
Date : 5/4/2020
*/

package com.mygdx.game.slidegesture

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.input.GestureDetector

object SimpleDirectionGestureDetector{

    fun isSwipeRight() : Boolean{
        if(Gdx.input.isTouched && Gdx.input.deltaX>Gdx.graphics.width / 20f){
            return true
        }
        return false
    }
}