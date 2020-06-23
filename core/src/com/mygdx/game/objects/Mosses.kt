/*
Author : Arda
Company : PhysTech
Date : 4/25/2020
*/

package com.mygdx.game.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.mygdx.game.Assets

import com.mygdx.game.Constants

enum class MossType {
    MOSS,
    WOOD,
    COLUMN
}


class Mosses(var mossType: MossType = MossType.MOSS) : AbstractGameObject() {

    private val TAG = "Moss"
    private val MOSS_SPACE = 8f

    //DEBUG, DELETE THIS
    val shapeRendererFirst = ShapeRenderer()
    val shapeRendererSecond = ShapeRenderer()


    private fun createMosses() {

        //Random Moss Generator
        val rand = MathUtils.random(1, 7)
        val newRegMoss: Moss

        if (rand <= 3) {
            when (mossType) {
                MossType.MOSS -> {
                    newRegMoss = Moss(Pair(Assets.moss.moss1_normal, Assets.moss.moss1_flipped), dimX = 6f, MOSS_GAP = 1.6f,
                            positionX = regMoss[regMoss.lastIndex].position.x + MOSS_SPACE + regMoss[regMoss.lastIndex].dimX, flipBoolY = MathUtils.randomBoolean(), flipBoolX = MathUtils.randomBoolean(),
                            mossShape = MossShape.MOSS_FIRST_TYPE)
                }
                MossType.WOOD -> {
                    val randWood = MathUtils.random(1, 5)
                    newRegMoss = if (randWood <= 3) {
                        Moss(Pair(Assets.wood.wood2_normal, Assets.wood.wood2_flipped), dimX = 4f, MOSS_GAP = 1.6f,
                                positionX = regMoss[regMoss.lastIndex].position.x + MOSS_SPACE + regMoss[regMoss.lastIndex].dimX, flipBoolX = MathUtils.randomBoolean(),
                                mossShape = MossShape.WOOD_FIRST_TYPE)
                    } else {
                        Moss(Pair(Assets.wood.wood3_normal, Assets.wood.wood3_flipped), dimX = 6f, MOSS_GAP = 1.6f,
                                positionX = regMoss[regMoss.lastIndex].position.x + MOSS_SPACE + regMoss[regMoss.lastIndex].dimX, flipBoolY = MathUtils.randomBoolean(),
                                mossShape = MossShape.WOOD_SECOND_TYPE)
                    }
                }
                else -> {
                    val randCol = MathUtils.random(1, 3)
                    newRegMoss = when (randCol) {
                        1 -> {
                            Moss(Pair(Assets.column.column2_normal, Assets.column.column2_flipped), dimX = 4f, MOSS_GAP = 1.6f,
                                    positionX = regMoss[regMoss.lastIndex].position.x + MOSS_SPACE + regMoss[regMoss.lastIndex].dimX, flipBoolY = MathUtils.randomBoolean(), flipBoolX = MathUtils.randomBoolean(),
                                    mossShape = MossShape.COLUMN_FIRST_TYPE)
                        }
                        2 -> {
                            Moss(Pair(Assets.column.column3_normal, Assets.column.column3_flipped), dimX = 6f, MOSS_GAP = 1.6f,
                                    positionX = regMoss[regMoss.lastIndex].position.x + MOSS_SPACE + regMoss[regMoss.lastIndex].dimX, flipBoolY = MathUtils.randomBoolean(), flipBoolX = MathUtils.randomBoolean(),
                                    mossShape = MossShape.COLUMN_SECOND_TYPE)
                        }
                        else -> {
                            Moss(Pair(Assets.column.column4_normal, Assets.column.column4_flipped), dimX = 8f, MOSS_GAP = 1.6f,
                                    positionX = regMoss[regMoss.lastIndex].position.x + MOSS_SPACE + regMoss[regMoss.lastIndex].dimX, flipBoolY = MathUtils.randomBoolean(),
                                    mossShape = MossShape.COLUMN_THIRD_TYPE)
                        }
                    }

                }
            }

        } else {
            newRegMoss = when (mossType) {
                MossType.MOSS -> {
                    Moss(Pair(Assets.moss.moss_normal, Assets.moss.moss_normal), positionX = regMoss[regMoss.lastIndex].position.x + MOSS_SPACE + regMoss[regMoss.lastIndex].dimX
                            ,
                            mossShape = MossShape.MOSS_CLASSIC)
                }
                MossType.WOOD -> {
                    Moss(Pair(Assets.wood.wood_normal, Assets.wood.wood_normal), positionX = regMoss[regMoss.lastIndex].position.x + MOSS_SPACE + regMoss[regMoss.lastIndex].dimX
                            ,
                            mossShape = MossShape.WOOD_CLASSIC)
                }
                else -> {
                    Moss(Pair(Assets.column.column_normal, Assets.column.column_normal), positionX = regMoss[regMoss.lastIndex].position.x + MOSS_SPACE + regMoss[regMoss.lastIndex].dimX
                            ,
                            mossShape = MossShape.COLUMN_CLASSIC)
                }
            }
        }

        regMoss = regMoss.subList(1, 4)
        regMoss.add(newRegMoss)
    }

    var regMoss: MutableList<Moss> = mutableListOf()


    private var mossClassic = Moss(Pair(Assets.moss.moss_normal, Assets.moss.moss_normal), dimX = 2f,  mossShape = MossShape.MOSS_CLASSIC, positionX = Constants.VIEWPORT_WIDTH / 2f + 6f)
    private var mossClassicSecond = Moss(Pair(Assets.moss.moss_normal, Assets.moss.moss_normal), dimX = 2f,  mossShape = MossShape.MOSS_CLASSIC, positionX = mossClassic.position.x + MOSS_SPACE + mossClassic.dimX, flipBoolY = true)
    private var mossClassicThird = Moss(Pair(Assets.moss.moss_normal, Assets.moss.moss_normal), dimX = 2f, mossShape = MossShape.MOSS_CLASSIC, positionX = mossClassicSecond.position.x + MOSS_SPACE + mossClassicSecond.dimX)
    var mossTypeFirstFourth = Moss(Pair(Assets.moss.moss1_normal, Assets.moss.moss1_flipped), dimX = 6f, MOSS_GAP = 1.6f, mossShape = MossShape.MOSS_FIRST_TYPE, positionX = mossClassicThird.position.x + MOSS_SPACE + mossClassicThird.dimX)


    init {
        regMoss.add(mossClassic)
        regMoss.add(mossClassicSecond)
        regMoss.add(mossClassicThird)
        regMoss.add(mossTypeFirstFourth)
    }


    override fun update(deltaTime: Float) {

        if (regMoss[0].position.x <= -Constants.VIEWPORT_WIDTH / 2f - regMoss[0].dimX) {
            createMosses()
        }

        super.update(deltaTime)
        regMoss.forEach { it.update(deltaTime) }
        regMoss.forEach { it.updateBounds(it.mossShape) }
    }

    override fun render(batch: SpriteBatch) {
        regMoss.forEach { it.render(batch) }
    }

    fun shapeRender(batch: SpriteBatch) {
//MOSSES SHAPES
//        val floatArrayDownMoss = floatArrayOf(regMoss[0].position.x, regMoss[0].mossParameterDownMoss - 2f - 1.6f * 2,
//                regMoss[0].position.x + 2f, regMoss[0].mossParameterDownMoss - 1.6f * 2,
//                regMoss[0].position.x + 6f, regMoss[0].mossParameterDownMoss - 1.6f * 2,
//                regMoss[0].position.x + 6f, regMoss[0].mossParameterDownMoss - 28f - 1.6f * 2,
//                regMoss[0].position.x, regMoss[0].mossParameterDownMoss - 28f - 1.6f * 2)
//
//        shapeRendererFirst.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererFirst.setColor(1f, 0f, 0f, 0f)
//        shapeRendererFirst.polygon(floatArrayDownMoss)
//        shapeRendererFirst.end()
//
//        val floatArrayUpMoss = floatArrayOf(regMoss[0].position.x, -regMoss[0].mossParameterUpMoss + 56f + 3.2f,
//                regMoss[0].position.x + 6f, -regMoss[0].mossParameterUpMoss + 56f + 3.2f,
//                regMoss[0].position.x + 6f, -regMoss[0].mossParameterUpMoss + 28f + 3.2f + 1.9f,
//                regMoss[0].position.x + 2f, -regMoss[0].mossParameterUpMoss + 28f + 3.2f + 1.9f,
//                regMoss[0].position.x, -regMoss[0].mossParameterUpMoss + 28f + 3.2f)
//
//        shapeRendererSecond.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererSecond.setColor(1f, 0f, 0f, 0f)
//        shapeRendererSecond.polygon(floatArrayUpMoss)
//        shapeRendererSecond.end()
//
//        val floatArrayUpMoss = floatArrayOf(regMoss[0].position.x, regMoss[0].mossParameterUpMoss - 2f - 1.6f * 2 - 28f,
//                regMoss[0].position.x + 2f, regMoss[0].mossParameterUpMoss - 1.6f * 2 - 28f,
//                regMoss[0].position.x + 8f, regMoss[0].mossParameterUpMoss - 1.6f * 2 - 28f,
//                regMoss[0].position.x + 8f, regMoss[0].mossParameterUpMoss - 56f - 1.6f * 2,
//                regMoss[0].position.x, regMoss[0].mossParameterUpMoss - 56f - 1.6f * 2).multiplyMinus()
//
//        shapeRendererFirst.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererFirst.setColor(1f, 0f, 0f, 0f)
//        shapeRendererFirst.polygon(floatArrayUpMoss)
//        shapeRendererFirst.end()
//
//        val floatArrayDownMoss = floatArrayOf(regMoss[0].position.x, -regMoss[0].mossParameterDownMoss + 28f + 3.2f,
//                regMoss[0].position.x + 8f, -regMoss[0].mossParameterDownMoss + 28f + 3.2f,
//                regMoss[0].position.x + 8f, -regMoss[0].mossParameterDownMoss  + 3.2f + 1.9f,
//                regMoss[0].position.x + 2f, -regMoss[0].mossParameterDownMoss  + 3.2f + 1.9f,
//                regMoss[0].position.x, -regMoss[0].mossParameterDownMoss  + 3.2f).multiplyMinus()
//
//
//        val poly = Polygon(floatArrayDownMoss)
//        poly.setOrigin(regMoss[0].position.x + regMoss[0].dimX / 2f , regMoss[0].position.y + regMoss[0].dimension.y / 2f)
//        poly.setScale(-1f , 1f)
//
//        shapeRendererSecond.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererSecond.setColor(1f, 0f, 0f, 0f)
//        shapeRendererSecond.polygon(poly.transformedVertices)
//        shapeRendererSecond.end()

        //WOOD SHAPES
//        val floatArrayDownMoss = floatArrayOf(regMoss[0].position.x, regMoss[0].mossParameterDownMoss - 3f - 1.6f,
//                regMoss[0].position.x + 4f, regMoss[0].mossParameterDownMoss - 1.6f,
//                regMoss[0].position.x + 4f, regMoss[0].mossParameterDownMoss - 28f - 1.6f,
//                regMoss[0].position.x, regMoss[0].mossParameterDownMoss - 28f - 1.6f)
//
//        shapeRendererFirst.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererFirst.setColor(1f, 0f, 0f, 0f)
//        shapeRendererFirst.polygon(floatArrayDownMoss)
//        shapeRendererFirst.end()
//
//        val floatArrayUpMoss = floatArrayOf(regMoss[0].position.x, -regMoss[0].mossParameterUpMoss + 56f + 1.6f,
//                regMoss[0].position.x + 4f , -regMoss[0].mossParameterUpMoss + 56f + 1.6f ,
//                regMoss[0].position.x + 4f, -regMoss[0].mossParameterUpMoss + 28f + 1.6f + 3f ,
//                regMoss[0].position.x , -regMoss[0].mossParameterUpMoss + 28f + 1.6f)
//
//        shapeRendererSecond.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererSecond.setColor(1f, 0f, 0f, 0f)
//        shapeRendererSecond.polygon(floatArrayUpMoss)
//        shapeRendererSecond.end()
        //WOOD 2 END, WOOD 3 BEGIN
//        val floatArrayDownMoss = floatArrayOf(regMoss[0].position.x, regMoss[0].mossParameterDownMoss -1.4f - 1.6f,
//                regMoss[0].position.x + 2f, regMoss[0].mossParameterDownMoss - 1.6f,
//                regMoss[0].position.x + 4f, regMoss[0].mossParameterDownMoss  - 1.6f,
//                regMoss[0].position.x + 6f, regMoss[0].mossParameterDownMoss - 1.4f - 1.6f,
//                regMoss[0].position.x + 6f, regMoss[0].mossParameterDownMoss  - 1.6f -28f,
//                regMoss[0].position.x , regMoss[0].mossParameterDownMoss  - 1.6f -28f)
//
//        shapeRendererFirst.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererFirst.setColor(1f, 0f, 0f, 0f)
//        shapeRendererFirst.polygon(floatArrayDownMoss)
//        shapeRendererFirst.end()
//
//        val floatArrayUpMoss = floatArrayOf(regMoss[0].position.x, -regMoss[0].mossParameterUpMoss + 56f + 1.6f,
//                regMoss[0].position.x + 6f, -regMoss[0].mossParameterUpMoss + 56f + 1.6f,
//                regMoss[0].position.x + 6f, -regMoss[0].mossParameterUpMoss +28f + 1.6f,
//                regMoss[0].position.x + 4f, -regMoss[0].mossParameterUpMoss +28f + 1.6f + 1.4f,
//                regMoss[0].position.x + 2f, -regMoss[0].mossParameterUpMoss + 1.4f + 1.6f +28f,
//                regMoss[0].position.x , -regMoss[0].mossParameterUpMoss + 1.6f +28f)
//
//        shapeRendererSecond.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererSecond.setColor(1f, 0f, 0f, 0f)
//        shapeRendererSecond.polygon(floatArrayUpMoss)
//        shapeRendererSecond.end()
        //WOOD 3 FLIP Y
//        val floatArrayDownMoss = floatArrayOf(regMoss[0].position.x , -regMoss[0].mossParameterDownMoss  + 3.2f +28f,
//                regMoss[0].position.x + 6f, -regMoss[0].mossParameterDownMoss  + 3.2f +28f,
//                regMoss[0].position.x + 6f, -regMoss[0].mossParameterDownMoss + 3.2f ,
//                regMoss[0].position.x + 4f, -regMoss[0].mossParameterDownMoss  + 3.2f +1.4f ,
//                regMoss[0].position.x + 2f, -regMoss[0].mossParameterDownMoss + 3.2f+1.4f,
//                regMoss[0].position.x, -regMoss[0].mossParameterDownMoss +3.2f ).multiplyMinus()
//
//        shapeRendererFirst.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererFirst.setColor(1f, 0f, 0f, 0f)
//        shapeRendererFirst.polygon(floatArrayDownMoss)
//        shapeRendererFirst.end()
//
//        val floatArrayUpMoss = floatArrayOf(regMoss[0].position.x , regMoss[0].mossParameterUpMoss -28f - 3.2f -1.4f  ,
//                regMoss[0].position.x + 2f, regMoss[0].mossParameterUpMoss -28f  - 3.2f,
//                regMoss[0].position.x + 4f, regMoss[0].mossParameterUpMoss -28f - 3.2f ,
//                regMoss[0].position.x + 6f, regMoss[0].mossParameterUpMoss -28f - 3.2f-1.4f,
//                regMoss[0].position.x + 6f, regMoss[0].mossParameterUpMoss -56f - 3.2f ,
//                regMoss[0].position.x, regMoss[0].mossParameterUpMoss -56f - 3.2f ).multiplyMinus()
//
//        shapeRendererSecond.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererSecond.setColor(1f, 0f, 0f, 0f)
//        shapeRendererSecond.polygon(floatArrayUpMoss)
//        shapeRendererSecond.end()
        //COLUMN SHAPES
        //COLUMN 2 START
//        val floatArrayDownMoss = floatArrayOf(regMoss[0].position.x +0.5f , regMoss[0].mossParameterDownMoss  - 3.2f -1.5f,
//                regMoss[0].position.x +2f , regMoss[0].mossParameterDownMoss  - 3.2f -1.5f ,
//                regMoss[0].position.x + 4f, regMoss[0].mossParameterDownMoss - 3.2f + 1.5f -1.5f,
//                regMoss[0].position.x + 4f, regMoss[0].mossParameterDownMoss - 3.2f -28f -1.5f,
//                regMoss[0].position.x +0.5f, regMoss[0].mossParameterDownMoss -3.2f -28f -1.5f)
//
//        shapeRendererFirst.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererFirst.setColor(1f, 0f, 0f, 0f)
//        shapeRendererFirst.polygon(floatArrayDownMoss)
//        shapeRendererFirst.end()
//
//        val floatArrayUpMoss = floatArrayOf(regMoss[0].position.x +0.5f, -regMoss[0].mossParameterUpMoss +3.2f +28f,
//                regMoss[0].position.x + 2f, -regMoss[0].mossParameterUpMoss + 3.2f +28f,
//                regMoss[0].position.x + 4f, -regMoss[0].mossParameterUpMoss + 3.2f +28f +1.5f,
//                regMoss[0].position.x + 4f, -regMoss[0].mossParameterUpMoss +56f + 3.2f ,
//                regMoss[0].position.x +2f , -regMoss[0].mossParameterUpMoss +56f + 3.2f ,
//                regMoss[0].position.x +0.5f , -regMoss[0].mossParameterUpMoss +56f  + 3.2f )
//
//        shapeRendererSecond.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererSecond.setColor(1f, 0f, 0f, 0f)
//        shapeRendererSecond.polygon(floatArrayUpMoss)
//        shapeRendererSecond.end()
        //COLUMN 2 FLIPPED Y
//        val floatArrayDownMoss = floatArrayOf(regMoss[0].position.x + 0.5f, -regMoss[0].mossParameterDownMoss + 3.2f + 28f + 1.5f,
//                regMoss[0].position.x + 8f, -regMoss[0].mossParameterDownMoss + 3.2f + 28f + 1.5f,
//                regMoss[0].position.x + 8f, -regMoss[0].mossParameterDownMoss + 3.2f - 1.5f + 1.5f,
//                regMoss[0].position.x + 6f, -regMoss[0].mossParameterDownMoss + 3.2f + 1.5f,
//                regMoss[0].position.x + 2f, -regMoss[0].mossParameterDownMoss + 3.2f + 1.5f,
//                regMoss[0].position.x + 0.5f, -regMoss[0].mossParameterDownMoss + 3.2f - 1f + 1.5f).multiplyMinus()
//
//        shapeRendererFirst.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererFirst.setColor(1f, 0f, 0f, 0f)
//        shapeRendererFirst.polygon(floatArrayDownMoss)
//        shapeRendererFirst.end()
//
//        val floatArrayUpMoss = floatArrayOf(regMoss[0].position.x + 0.5f, regMoss[0].mossParameterUpMoss - 56f - 3.2f - 1.5f,
//                regMoss[0].position.x + 2f, regMoss[0].mossParameterUpMoss - 56f - 3.2f - 1.5f,
//                regMoss[0].position.x + 8f, regMoss[0].mossParameterUpMoss - 56f - 3.2f - 1.5f,
//                regMoss[0].position.x + 8f, regMoss[0].mossParameterUpMoss - 3.2f - 28f - 1.5f,
//                regMoss[0].position.x + 6f, regMoss[0].mossParameterUpMoss - 3.2f - 28f - 1.5f + 1.5f,
//                regMoss[0].position.x + 2f, regMoss[0].mossParameterUpMoss - 3.2f - 28f - 1.5f + 1.5f,
//                regMoss[0].position.x + 0.5f, regMoss[0].mossParameterUpMoss - 3.2f - 28f - 1.5f).multiplyMinus()
//
//        shapeRendererSecond.begin(ShapeRenderer.ShapeType.Line)
//        shapeRendererSecond.setColor(1f, 0f, 0f, 0f)
//        shapeRendererSecond.polygon(floatArrayUpMoss)
//        shapeRendererSecond.end()
//
//

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            println("ParamDown : ${regMoss[0].mossParameterDownMoss}")
            println("ParamUp : ${regMoss[0].mossParameterUpMoss}")
        }

    }

}