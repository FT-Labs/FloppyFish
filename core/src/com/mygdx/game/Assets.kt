/*
Author : Arda
Company : PhysTech
Date : 4/20/2020
*/

package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable

object Assets : Disposable, AssetErrorListener {
    val TAG = "Assets"
    val assetManager = AssetManager()

    init {
        //Set asset manager error handler
        assetManager.setErrorListener(this)
        //Load atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS , TextureAtlas::class.java)
        //Start loading assets and wait
        assetManager.finishLoading()
        Gdx.app.debug(TAG , "Asset loading finished.")
        assetManager.assetNames.forEach { Gdx.app.debug(TAG , "Asset : $it")}
        assetManager.update()

    }

    val atlas : TextureAtlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS)


    //Creating game objects

    val fish = AssetFloppy(atlas)
    val moss = AssetMoss(atlas)
    val wood = AssetWood(atlas)
    val column = AssetColumn(atlas)
    val background = AssetBackground(atlas)
    val gameobjects = AssetNumbers(atlas)
    val logo = AssetLogo(atlas)


    class AssetLogo(atlas: TextureAtlas){
        val logo = atlas.findRegion("logo")
        val name = atlas.findRegion("compname")
    }

    class AssetBackground(atlas: TextureAtlas){
        val background = atlas.findRegion("seabackground")
    }

    class AssetWood(atlas: TextureAtlas){
        val wood_normal = atlas.findRegion("odun1")
        val wood2_normal = atlas.findRegion("odun2_duz(70x560)")
        val wood2_flipped = atlas.findRegion("odun2_ters(70x560)")
        val wood3_normal = atlas.findRegion("odun3_duz(105x560)")
        val wood3_flipped = atlas.findRegion("odun3_ters(105x560)")
    }

    class AssetNumbers(atlas : TextureAtlas){
        val numberZero = atlas.findRegion("sayiSifir")
        val numberOne = atlas.findRegion("sayiBir")
        val numberTwo = atlas.findRegion("sayiIki")
        val numberThree = atlas.findRegion("sayiUc")
        val numberFour = atlas.findRegion("sayiDort")
        val numberFive = atlas.findRegion("sayiBes")
        val numberSix = atlas.findRegion("sayiAlti")
        val numberSeven = atlas.findRegion("sayiYedi")
        val numberEight = atlas.findRegion("sayiSekiz")
        val numberNine = atlas.findRegion("sayiDokuz")
    }

    class AssetColumn(atlas: TextureAtlas){
        val column_normal = atlas.findRegion("sutun1")
        val column2_normal = atlas.findRegion("sutun2_duz(70x560)")
        val column2_flipped = atlas.findRegion("sutun2_ters(70x560)")
        val column3_normal = atlas.findRegion("sutun3_duz(105x560)")
        val column3_flipped = atlas.findRegion("sutun3_ters(105x560)")
        val column4_normal = atlas.findRegion("sutun4_duz(140x560)")
        val column4_flipped = atlas.findRegion("sutun4_ters(140x560)")
    }

    class AssetFloppy(atlas: TextureAtlas){
        val diveFirst = atlas.findRegion("fish_diving")
        val diveSecond = atlas.findRegion("fish_diving2")
        val upward = atlas.findRegion("fish_up")
        val downward = atlas.findRegion("fish_down")
        val dead = atlas.findRegion("fish_gg")
    }

    class AssetMoss(atlas: TextureAtlas){
        val moss_normal = atlas.findRegion("yosun1")
        val moss1_normal = atlas.findRegion("yosun2_duz")
        val moss1_flipped = atlas.findRegion("yosun2_ters")
    }

    init {
        //Smooth textures
        atlas.textures.forEach { it.setFilter(Texture.TextureFilter.Linear , Texture.TextureFilter.Linear) }
    }



    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        Gdx.app.error(TAG , "Asset load error -> ${asset?.fileName}  -> Exception : $throwable")
    }

    override fun dispose() {
        assetManager.dispose()
    }

}