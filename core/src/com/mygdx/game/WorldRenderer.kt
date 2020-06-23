/*
Author : Arda
Company : PhysTech
Date : 4/20/2020
*/

package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.mygdx.game.gamepreferences.GamePreferences
import com.mygdx.game.objects.Floppy
import com.mygdx.game.objects.NumberCalculator

class WorldRenderer(val worldController: WorldController) : Disposable {
    private var camera = OrthographicCamera(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT)
    private var batch = SpriteBatch()
    private val screenViewport = ScreenViewport(camera)
    private val background = TextureRegion(Assets.background.background)
    private var rotationMax = true
    var scoreBool = false
    private var score = 0
    var highScore = GamePreferences.highScore



    init {
        camera.position.set(0f , 0f , 0f)
        camera.update()
    }


    private fun renderGuiScore(batch: SpriteBatch){
        val numberTextures = NumberCalculator.calculateNumber(worldController.score)

        val size = numberTextures.size
        if (size == 1){
            batch.draw(numberTextures[0] , -1f , 12f , 2f, 2f)
        }else if(size == 2){
            batch.draw(numberTextures[0] , -2f , 12f , 2f , 2f)
            batch.draw(numberTextures[1] , -1f , 12f , 2f , 2f)
        }else if(size == 3){
            batch.draw(numberTextures[0] , -2.5f , 12f , 2f , 2f)
            batch.draw(numberTextures[1] , -1.5f , 12f , 2f , 2f)
            batch.draw(numberTextures[2] , -0.5f , 12f , 2f , 2f)
        }else if(size == 4){
            batch.draw(numberTextures[0] , -3f , 12f , 2f , 2f)
            batch.draw(numberTextures[1] , -2f , 12f , 2f , 2f)
            batch.draw(numberTextures[2] , -1f , 12f , 2f , 2f)
            batch.draw(numberTextures[3] , 0f , 12f , 2f , 2f)
        }
    }

    fun renderEndGameScore(){
        if (scoreBool){
            batch.begin()
            val numberTextures = NumberCalculator.calculateNumber(score)
            val size = numberTextures.size
            if (size == 1){
                batch.draw(numberTextures[0] , 4.4f , -0.9f , 2f, 2f)
            }else if(size == 2){
                batch.draw(numberTextures[0] , 3.2f , -0.9f , 2f , 2f)
                batch.draw(numberTextures[1] , 4.4f , -0.9f , 2f , 2f)
            }else if(size == 3){
                batch.draw(numberTextures[0] , 2f , -0.9f , 2f , 2f)
                batch.draw(numberTextures[1] , 3.2f, -0.9f , 2f , 2f)
                batch.draw(numberTextures[2] , 4.4f , -0.9f , 2f , 2f)
            }else if(size == 4){
                batch.draw(numberTextures[0] , 0.8f , -0.9f , 2f , 2f)
                batch.draw(numberTextures[1] , 2f , -0.9f , 2f , 2f)
                batch.draw(numberTextures[2] , 3.2f , -0.9f , 2f , 2f)
                batch.draw(numberTextures[3] , 4.4f , -0.9f , 2f , 2f)
            }
            if (score!=worldController.score){
                score += 1
                if(Gdx.input.isTouched){
                    score = worldController.score
                }
            }else{
                if (score>highScore){
                    GamePreferences.prefs.putInteger("highscore",score).flush()
                    highScore = score
                }
                val numberTextures2 = NumberCalculator.calculateNumber(highScore)
                val size2 = numberTextures2.size
                if (size2 == 1){
                    batch.draw(numberTextures2[0] , 4.4f , -4.7f , 2f, 2f)
                }else if(size2 == 2){
                    batch.draw(numberTextures2[0] , 3.2f , -4.7f , 2f , 2f)
                    batch.draw(numberTextures2[1] , 4.4f , -4.7f , 2f , 2f)
                }else if(size2 == 3){
                    batch.draw(numberTextures2[0] , 2f , -4.7f , 2f , 2f)
                    batch.draw(numberTextures2[1] , 3.2f, -4.7f , 2f , 2f)
                    batch.draw(numberTextures2[2] , 4.4f , -4.7f , 2f , 2f)
                }else if(size2 == 4){
                    batch.draw(numberTextures2[0] , 0.8f , -4.7f , 2f , 2f)
                    batch.draw(numberTextures2[1] , 2f , -4.7f , 2f , 2f)
                    batch.draw(numberTextures2[2] , 3.2f , -4.7f , 2f , 2f)
                    batch.draw(numberTextures2[3] , 4.4f , -4.7f , 2f , 2f)
                }
            }
            batch.end()
        }
    }

    private fun renderWorld(batch: SpriteBatch){
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        worldController.cameraHelper.applyTo(camera)
        batch.projectionMatrix = camera.combined
        worldController.mosses.shapeRendererFirst.projectionMatrix = camera.combined
        worldController.mosses.shapeRendererSecond.projectionMatrix = camera.combined

        batch.begin()
        batch.draw(background , -Constants.VIEWPORT_WIDTH/2f , -Constants.VIEWPORT_HEIGHT/2f , Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT)
        worldController.floppy.render(batch)
        worldController.mosses.render(batch)
        batch.end()
    }

    private fun renderGui(batch: SpriteBatch){
        batch.projectionMatrix = camera.combined
        batch.begin()
        renderGuiScore(batch)
        batch.end()
    }


    fun render(){

        if (!worldController.isGameOver){
            renderWorld(batch)
            renderGui(batch)
        }
        else{

            worldController.mosses.regMoss.forEach { it.MOSS_SPEED = 0f }
            worldController.floppy.fishState = Floppy.FISH_STATE.FALLING
            worldController.floppy.floppyWingState = 4
            worldController.floppy.GRAVITY = 0f
            worldController.floppy.velocity.y = 0f
            if(worldController.floppy.position.y < Constants.VIEWPORT_HEIGHT/2f - worldController.floppy.dimension.y/1.5f){
                worldController.floppy.position.y += 5f*Gdx.graphics.deltaTime
            }
            if (rotationMax){
                worldController.floppy.rotation += 7.5f
                if (worldController.floppy.rotation >= 180f){
                    rotationMax = false
                }
            }else{
                worldController.floppy.rotation = 180f
            }

            renderWorld(batch)
            renderGui(batch)
        }
    }


    fun resize(width : Int , height : Int){
//        camera.viewportWidth = Constants.VIEWPORT_HEIGHT / height * width
//        camera.viewportHeight = camera.viewportWidth * 2f
//        camera.update()

        screenViewport.setUnitsPerPixel(Math.min(1920.0f/ Gdx.graphics.getWidth(),1080.0f/Gdx.graphics.getHeight()))

//        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT
//        cameraGUI.viewportWidth = Constants.VIEWPORT_GUI_WIDTH / height * width
//        cameraGUI.position.set(cameraGUI.viewportWidth / 2f, cameraGUI.viewportHeight / 2f, 0f)
//        cameraGUI.update()
    }

    override fun dispose() {
        batch.dispose()
    }

}