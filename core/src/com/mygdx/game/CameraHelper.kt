/*
Author : Arda
Company : PhysTech
Date : 4/20/2020
*/

package com.mygdx.game

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

class CameraHelper {
    private val TAG = "CameraHelper"
    private val MAX_ZOOM_IN = 0.25f
    private val MAX_ZOOM_OUT = 10f
    private var zoom = 1f

    var position = Vector2()

    fun addZoom(amount : Float) = setZoom(zoom + amount)

    fun setZoom(Zoom : Float){
        zoom = MathUtils.clamp(Zoom , MAX_ZOOM_IN , MAX_ZOOM_OUT)
    }

    fun applyTo(camera: OrthographicCamera){
        camera.position.x = position.x
        camera.position.y = position.y
        camera.zoom = zoom
        camera.update()
    }
}