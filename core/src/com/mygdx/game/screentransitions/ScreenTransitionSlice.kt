/*
Author : Arda
Company : PhysTech
Date : 6/13/2020
*/

package com.mygdx.game.screentransitions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import kotlin.properties.Delegates

object ScreenTransitionSlice : ScreenTransition {

    const val UP = 1
    const val DOWN = 2
    const val UP_DOWN = 3

    override var duration: Float by Delegates.notNull<Float>()
    private var direction by Delegates.notNull<Int>()
    private var slideOut by Delegates.notNull<Boolean>()
    private var eas : Interpolation? = null
    private var sliceIndex : ArrayList<Int> = arrayListOf()
    
    fun init(duration : Float , direction : Int , numSlices : Int , eas : Interpolation) : ScreenTransitionSlice{
        this.duration = duration
        this.direction = direction
        this.eas = eas
        //Creating shuffled list of slice to determine the order of animation
        for (i in 0 until numSlices){
            this.sliceIndex.add(i)
        }
        this.sliceIndex.shuffle()
        return this
    }

    override fun render(batch: SpriteBatch, currScreen: Texture, nextScreen: Texture, alpha: Float) {
        val w = currScreen.width.toFloat()
        val h = currScreen.height.toFloat()
        var x = 0f
        var y = 0f
        val sliceWidth = (w/ sliceIndex.size).toInt()
        var alpha1 : Float? = null
        
        Gdx.gl.glClearColor(0f , 0f , 0f , 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(currScreen , 0f , 0f , 0f , 0f , w , h , 1f , 1f , 0f , 0 , 0 , currScreen.width , currScreen.height , false , true)
        if(eas!= null) alpha1 = eas!!.apply(alpha)
        
        for (i in 0 until sliceIndex.size){
            //Curr slice/column
            x = (i*sliceWidth).toFloat()
            //Vertical displacement using sliced indices
            val offsetY = h * (1 + sliceIndex[i] / sliceIndex.size.toFloat())
            
            when (direction){
                UP -> y = -offsetY + offsetY * alpha1!!
                DOWN -> y = offsetY - offsetY *alpha1!!
                UP_DOWN -> {
                    y = if (i%2 == 0){
                        -offsetY + offsetY * alpha1!!
                    }else{
                        offsetY - offsetY * alpha1!!
                    }
                }
            }
            batch.draw(nextScreen , x , y , 0f , 0f , sliceWidth.toFloat() , h , 1f , 1f , 0f , i*sliceWidth , 0 , sliceWidth , nextScreen.height , false , true)
        }
        batch.end()
    }
}










