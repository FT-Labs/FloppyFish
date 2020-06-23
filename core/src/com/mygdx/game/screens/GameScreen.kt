/*
Author : Arda
Company : PhysTech
Date : 4/20/2020
*/

package com.mygdx.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.mygdx.game.Constants
import com.mygdx.game.WorldController
import com.mygdx.game.WorldRenderer
import com.mygdx.game.objects.NumberCalculator
import com.mygdx.game.screentransitions.ScreenTransitionFade
import com.mygdx.game.screentransitions.ScreenTransitionSlide
import ktx.app.KtxGame
import ktx.app.KtxScreen

class GameScreen(game : DirectedGame) : AbstractGameScreen(game) {


    private var paused = false
    private lateinit var worldController : WorldController
    private lateinit var worldRenderer : WorldRenderer

    private lateinit var stage : Stage
    lateinit var btnReplay : Button
    private lateinit var btnMenu : Button
    private lateinit var imgGameOver : Image
    private lateinit var skin : Skin


    private var btnMenuX = Constants.VIEWPORT_WIDTH + 4f
    private var btnReplayX = -8f
    private var gameOverY = Constants.VIEWPORT_HEIGHT
    private var pausedGame = true
    private var inputBool = true

    private fun rebuildStage(){

        skin = Skin(Gdx.files.internal(Constants.SKIN_OVER_UI_JSON) , TextureAtlas(Constants.SKIN_OVER_UI))
        skin.atlas.textures.forEach { it.setFilter(Texture.TextureFilter.Linear , Texture.TextureFilter.Linear) }
        //Layers
        val layerBackground = buildBackgroundLayer()
        val layerMenuButton = buildMenuButton()
        val layerReplayButton = buildReplayButton()
        stage.clear()
        val stack = Stack()
        stage.addActor(stack)
        stack.setSize(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT)
        stack.add(layerBackground)
        stack.add(layerMenuButton)
        stack.add(layerReplayButton)
    }

    private fun buildBackgroundLayer() : Table {
        val layer = Table()
        //add bg
        imgGameOver = Image(skin , "gameover")
        imgGameOver.setSize(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT / 1.45f)
        layer.addActor(imgGameOver)
        return layer
    }

    private fun buildMenuButton() : Table {
        val layer = Table()
        //add menu button
        btnMenu = Button(skin , "menu")
        layer.addActor(btnMenu)
        btnMenu.setSize(4f , 3f)
        btnMenu.addListener(object : ChangeListener(){
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                onMenuClicked()
            }
        })
        return layer
    }

    private fun buildReplayButton() : Table{
        val layer = Table()
        btnReplay = Button(skin , "replay")
        layer.addActor(btnReplay)
        btnReplay.setSize(4f , 3f)
        btnReplay.addListener(object : ChangeListener(){
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                onReplayClicked()
            }
        })
        return layer
    }

    private fun onReplayClicked(){
        val transition = ScreenTransitionSlide.init(0.75f , 1 , true , Interpolation.bounceOut)
        game.setScreen(GameScreen(game) , transition)

    }

    private fun onMenuClicked(){
        val transition = ScreenTransitionFade.init(0.75f)
        game.setScreen(MenuScreen(game) , transition)
    }

    private fun moveMenuButton(button: Button){
        if(btnMenuX > 8f){
            btnMenuX -= 10f*Gdx.graphics.deltaTime
            button.setPosition(btnMenuX , 3f)
        }
    }

    private fun moveReplayButton(button: Button){
        if(btnReplayX < 3f){
            btnReplayX += 9.3f*Gdx.graphics.deltaTime
            button.setPosition(btnReplayX , 3f)
        }
    }

    private fun moveGameOver(image: Image){
        if(gameOverY > 10f){
            gameOverY -= 17f*Gdx.graphics.deltaTime
            image.setPosition(0f, gameOverY)
            image.toBack()
        }else{
            worldRenderer.scoreBool = true
        }
    }

    override fun getInputProcessor() : InputProcessor {
        return worldController
    }


    override fun render(delta: Float) {

        if(!paused){
            //Update game
            if(pausedGame){
                if(Gdx.input.isTouched){
                    pausedGame = false
                }
                worldController.update(0f)
            }else{
                worldController.update(delta)
            }
        }
//        Sets the clear screen color to : Cornflower blue
//        Gdx.gl.glClearColor(0x64 / 255f , 0x95 / 255f , 0xed/255f , 0xff / 255f)
//        //Clears screen
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        //Render game world to screen

        worldRenderer.render()
        if(worldController.isGameOver){
            if(inputBool){
                Gdx.input.inputProcessor = stage
                inputBool = false
            }
            moveMenuButton(btnMenu)
            moveReplayButton(btnReplay)
            moveGameOver(imgGameOver)
            stage.act()
            stage.draw()
            worldRenderer.renderEndGameScore()
        }
    }

    override fun show() {
        stage = Stage(StretchViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT))
        rebuildStage()
    }

    override fun hide() {
        btnMenuX = Constants.VIEWPORT_WIDTH + 4f
        btnReplayX = -8f
        gameOverY = Constants.VIEWPORT_HEIGHT
        stage.dispose()
        skin.dispose()
        worldRenderer.dispose()
    }

    override fun pause() {
        paused = true
    }

    override fun resume() {
        super.resume()
        //Android
        paused = false
    }

    override fun resize(width: Int, height: Int) {
        worldController = WorldController(game)
        worldRenderer = WorldRenderer(worldController)
        worldRenderer.resize(width , height)
    }

}