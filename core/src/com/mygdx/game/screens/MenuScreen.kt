/*
Author : Arda
Company : PhysTech
Date : 4/26/2020
*/

package com.mygdx.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.mygdx.game.Assets
import com.mygdx.game.Constants
import com.mygdx.game.screentransitions.ScreenTransition
import com.mygdx.game.screentransitions.ScreenTransitionFade
import com.mygdx.game.screentransitions.ScreenTransitionSlice
import ktx.app.KtxGame
import ktx.app.KtxScreen
import kotlin.properties.Delegates

class MenuScreen(game : DirectedGame) : AbstractGameScreen(game)  {

    private val TAG = "MenuScreen"
    private lateinit var stage : Stage
    private lateinit var skin : Skin
    private lateinit var btnPlay : Button
    private lateinit var btnRate : Button
    private lateinit var btnScores : Button
    private lateinit var imgFish : Image
    private lateinit var imgBackground : Image

    //debug
    private val DEBUG_REBUILD_INTERVAL = 5f
    private var debugEnabled = false
    private var debugRebuildStage by Delegates.notNull<Float>()

    private fun rebuildStage(){
        skin = Skin(Gdx.files.internal(Constants.SKIN_UI_JSON) , TextureAtlas(Constants.SKIN_UI))
        skin.atlas.textures.forEach { it.setFilter(Texture.TextureFilter.Linear , Texture.TextureFilter.Linear) }
        //Layers
        val layerBackground = buildBackgroundLayer()
        val layerObjects = buildObjectsLayer()
        val layerFish = buildFishLayer()
        stage.clear()
        val stack = Stack()
        stage.addActor(stack)
        stack.setSize(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT)
        stack.add(layerBackground)
        stack.add(layerObjects)
        stack.add(layerFish)
    }

    private fun onPlayClicked(){
        val transition : ScreenTransition = ScreenTransitionSlice.init(2f, ScreenTransitionSlice.UP_DOWN , 10 , Interpolation.pow5Out)
        game.setScreen(GameScreen(game) , transition)
    }

    override fun getInputProcessor(): InputProcessor {
        return stage
    }

    private fun buildObjectsLayer() : Table{
        val layer = Table()
        //TODO (Add changelisteners)
        //Play button
        btnPlay = Button(skin , "play")
        layer.addActor(btnPlay)
        btnPlay.setSize(4f, 3f)
        btnPlay.setPosition(3f , 12f)
        layer.padRight(5f)
        btnPlay.addListener(object : ChangeListener(){
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                onPlayClicked()
            }
        })
        //Rate button
        btnRate = Button(skin , "rate")
        layer.addActor(btnRate)
        btnRate.setSize(5f, 3f)
        btnRate.setPosition(5.6f , 5f)
        //Score button
        btnScores = Button(skin , "score")
        layer.addActor(btnScores)
        btnScores.setSize(4f, 3f)
        btnScores.setPosition(9f , 12f)

        return layer
    }

    private fun buildFishLayer() : Table{
        val layer = Table()
        layer.setSize(5f,5f)
        imgFish = Image(skin , "fish")
        layer.addActor(imgFish)
        imgFish.setSize(5f , 5f)
        imgFish.rotateBy(40f)
        imgFish.setPosition(Constants.VIEWPORT_WIDTH/2f -0.5f , 18f)
        return layer
    }

    private fun buildBackgroundLayer() : Table{
        val layer = Table()
        //add bg
        imgBackground = Image(skin , "background")
        layer.add(imgBackground)
        imgBackground.setSize(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT)
        return layer
    }

    override fun show() {
        stage = Stage(StretchViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT))
        Gdx.input.inputProcessor = stage
        rebuildStage()
    }

    override fun hide() {
        stage.dispose()
        skin.dispose()
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0x64 / 255f , 0x95 / 255f , 0xed/255f , 0xff / 255f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()

    }
}