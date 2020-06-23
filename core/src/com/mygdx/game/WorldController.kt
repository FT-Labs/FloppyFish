/*
Author : Arda
Company : PhysTech
Date : 4/20/2020
*/

package com.mygdx.game

import com.mygdx.game.slidegesture.SimpleDirectionGestureDetector
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.*
import com.badlogic.gdx.math.Interpolation.circle
import com.mygdx.game.objects.Floppy
import com.mygdx.game.objects.MossType
import com.mygdx.game.objects.Mosses
import com.mygdx.game.objects.overlapsCircle
import com.mygdx.game.screens.DirectedGame
import com.mygdx.game.screens.MenuScreen
import ktx.app.KtxGame
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import kotlin.math.pow
import kotlin.properties.Delegates



class WorldController(val game: KtxGame<KtxScreen>) : KtxInputAdapter {

    private val TAG = "WorldController"
    val cameraHelper = CameraHelper()
    val floppy = Floppy()
    var score by Delegates.notNull<Int>()
    var scoreBool = true
    var divePosBool = true
    var diveSwipeTouch = true
    var isGameOver = false
    val mosses = Mosses()
    var regHash = mosses.regMoss[0]

    init {
        SimpleDirectionGestureDetector
        Gdx.input.inputProcessor = this
        score = 0
    }

    private fun countScore() {

        if (mosses.regMoss[0].position.x + mosses.regMoss[0].dimX < floppy.position.x + floppy.dimension.x && scoreBool) {
            score += 1
            regHash = mosses.regMoss[0]
            divePosBool = true
            Gdx.app.debug(TAG, "Score is : $score")
            scoreBool = false

            if(score in 3..5){
                mosses.mossType = MossType.WOOD
            }else if(score>=6) {
                mosses.mossType = MossType.COLUMN
            }
        }

        if (mosses.regMoss[0].position.x <= -Constants.VIEWPORT_WIDTH / 2f - mosses.regMoss[0].dimX) {
            scoreBool = true
            regHash = mosses.regMoss[1]
        }
    }

    private fun diveStop() {

        if (mosses.regMoss[0].position.x + mosses.regMoss[0].dimX < floppy.position.x && floppy.fishState == Floppy.FISH_STATE.DIVE) {
            if (divePosBool) {
                floppy.diveBool = false
            }
        }
    }

    private fun testCollision() {
        val fishCircle = Circle()
        fishCircle.set(0.2f + floppy.position.x + floppy.dimension.x / 2f, floppy.position.y + floppy.dimension.y / 2f, floppy.bounds.radius)

        for (moss in mosses.regMoss) {

            if (Intersector.overlaps(fishCircle, moss.firstMossRect)) {
                Gdx.app.debug(TAG, "Collision Rectangle")
                isGameOver = true
            }
            if (Intersector.overlaps(fishCircle, moss.secondMossRect)) {
                Gdx.app.debug(TAG, "Collision Rectangle")
                isGameOver = true
            }

            if(moss.firstMossPoly.overlapsCircle(fishCircle) || moss.secondMossPoly.overlapsCircle(fishCircle)){
                Gdx.app.debug(TAG , "Collision Polygon")
                isGameOver = true
            }
        }
    }


    fun update(deltaTime: Float) {
        countScore()
        floppy.update(deltaTime)
        mosses.update(deltaTime)
        diveStop()
        handleDebugInput(deltaTime)
        handleInputGame(deltaTime)
        testCollision()

    }


    private fun handleInputGame(deltaTime: Float) {

        //Bug solving
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || SimpleDirectionGestureDetector.isSwipeRight())  && divePosBool) {

            diveSwipeTouch = false
            floppy.diveBool = true
            divePosBool = false
            floppy.fishState = Floppy.FISH_STATE.DIVE
            floppy.floppyWingState = 2
        }
        if (Gdx.input.isTouched && !SimpleDirectionGestureDetector.isSwipeRight() && diveSwipeTouch) {
            floppy.fishState = Floppy.FISH_STATE.SWIM_UP
        } else {
            floppy.floppyWingState = 1
        }
    }


    fun handleDebugInput(deltaTime: Float) {
        if (Gdx.app.type != Application.ApplicationType.Desktop) return

        //Camera Controls
        var camMoveSpeed = 1f
        val camMoveAccelerationFactor = 1f


        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveAccelerationFactor
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCamera(-camMoveSpeed, 0f)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCamera(camMoveSpeed, 0f)
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCamera(0f, camMoveSpeed)
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCamera(0f, -camMoveSpeed)
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) cameraHelper.position.set(0f, 0f)


        //Camera zoom controls
        var camZoomSpeed = 1 * deltaTime
        val camZoomSpeedAccelerationFactor = 5f
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed)
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed)
        if (Gdx.input.isKeyPressed(Input.Keys.P)) cameraHelper.setZoom(1f)

    }


    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        diveSwipeTouch = true
        return super.touchDown(screenX, screenY, pointer, button)
    }




    fun backToMenu() {
        game.setScreen<MenuScreen>()
    }

    private fun moveCamera(x: Float, y: Float) {
        cameraHelper.position.set(cameraHelper.position.x + x, cameraHelper.position.y + y)
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACKSPACE) {
            backToMenu()
        }
        return false
    }




    override fun keyDown(keycode: Int): Boolean {


        if (keycode == Input.Keys.L) {
            print(Gdx.app.debug(TAG, "${mosses.regMoss[0].position.x}"))
        }

        return false
    }

}