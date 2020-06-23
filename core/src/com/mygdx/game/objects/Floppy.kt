/*
Author : Arda
Company : PhysTech
Date : 4/20/2020
*/

package com.mygdx.game.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.mygdx.game.Assets
import com.mygdx.game.Constants

class Floppy : AbstractGameObject() {

    private val TAG = "Floppy"
    var floppyArray : ArrayList<TextureAtlas.AtlasRegion> = arrayListOf(Assets.fish.upward , Assets.fish.downward , Assets.fish.diveFirst,Assets.fish.diveSecond , Assets.fish.dead)
    var floppyWingState = 0
    var flapStateTimer = 0f
    var diveBool = false
    var GRAVITY = 35f
    private val FISHSPEED = 85f
    private val FISH_TERMINAL_VELOCITY = 16f
    private val MINIMUM_HEIGHT = -Constants.VIEWPORT_HEIGHT / 2f
    private val MAX_UP_ROTATION = 40f
    private val MAX_DOWN_ROTATION = -80f
    private val GRAVITY_ROTATION = 50f
    private val SWIM_UP_ROTATION = 150f
    private val DIVE_ROTATION = 300f

    enum class FISH_STATE{DIVE , FALLING , SWIM_UP}
    var fishState = FISH_STATE.FALLING
    val bubbleEffect : ParticleEffect = ParticleEffect()

    init {
        bubbleEffect.load(Gdx.files.internal("particles/bubbles.pfx") , Gdx.files.internal("particles"))


        dimension.set(2f , 1.44f)
        //Center image on obj
        origin.set(dimension.x / 2f , dimension.y / 2f)
        position.set(-5f , 0f)
        //Bounding box
        bounds.set(-5f , 0f , dimension.y / 2f )
        //Physics values
        gravity.set(0f , GRAVITY)
    }


    override fun render(batch: SpriteBatch) {
        //Draw
        if(fishState == FISH_STATE.DIVE){
            floppyWingState = FlapWings(Gdx.graphics.deltaTime) + 2
            bubbleEffect.draw(batch)
        }

        batch.draw(floppyArray[floppyWingState].texture , position.x , position.y , origin.x , origin.y , dimension.x , dimension.y , scale.x , scale.y,
        rotation , floppyArray[floppyWingState].regionX , floppyArray[floppyWingState].regionY , floppyArray[floppyWingState].regionWidth , floppyArray[floppyWingState].regionHeight , false ,false)

    }

    fun FlapWings(deltaTime : Float) : Int{
        flapStateTimer += deltaTime
        return if(flapStateTimer < 0.2f){
            1
        }else if(flapStateTimer < 0.4f && flapStateTimer > 0.2f){
            0
        }else{
            flapStateTimer = 0f
            1
        }
    }


    override fun update(deltaTime: Float) {
        if(fishState == FISH_STATE.DIVE){
            bubbleEffect.update(deltaTime)
        }
        super.update(deltaTime)
        updateMotionY(deltaTime)
    }

    override fun updateMotionY(deltaTime: Float) {

        super.updateMotionY(deltaTime)
        bubbleEffect.setPosition(position.x, position.y + dimension.y / 2f)

        when (fishState) {
            FISH_STATE.FALLING -> {
                gravity.set(0f , GRAVITY)
                gravityRotation(deltaTime)
            }
            FISH_STATE.SWIM_UP -> {
                floppyWingState = FlapWings(deltaTime)
                swimUpRotation(deltaTime)
                gravity.set(0f , 0f)
                velocity.y += FISHSPEED * deltaTime
                if(velocity.y >= FISH_TERMINAL_VELOCITY){
                    velocity.y = FISH_TERMINAL_VELOCITY
                }
                fishState = FISH_STATE.FALLING
            }
            FISH_STATE.DIVE -> {

                bubbleEffect.start()
                floppyWingState = FlapWings(deltaTime) + 2
                diveRotation(deltaTime)
                gravity.set(0f , 0f)
                velocity.set(0f , 0f)
                if(diveBool){
                    fishState = FISH_STATE.DIVE
                }else{
                    fishState = FISH_STATE.FALLING
                }
            }
        }
        if(!diveBool){
            fishState = FISH_STATE.FALLING
        }

        if(position.y <= MINIMUM_HEIGHT){
            position.y = MINIMUM_HEIGHT
        }
        if(fishState != FISH_STATE.DIVE){
            bubbleEffect.allowCompletion()
            bubbleEffect.reset()
        }
    }

    private fun gravityRotation(deltaTime: Float){
        rotation -= GRAVITY_ROTATION * deltaTime
        if(rotation <= MAX_DOWN_ROTATION){
            rotation = MAX_DOWN_ROTATION
        }
    }

    private fun swimUpRotation(deltaTime: Float){
        rotation += SWIM_UP_ROTATION * deltaTime

        if(rotation >= MAX_UP_ROTATION){
            rotation = MAX_UP_ROTATION
        }

    }

    private fun diveRotation(deltaTime: Float){
        val rotationNow = rotation

        if(rotationNow < 0f){
            if(rotation > -3f){
                rotation = 0f
            }else{
                rotation += deltaTime * DIVE_ROTATION
            }
        }else if(rotationNow > 0f){
            if(rotation < 3f){
                rotation = 0f
            }else{
                rotation -= deltaTime * DIVE_ROTATION

            }
        }else{
            rotation = 0f
        }

    }


}