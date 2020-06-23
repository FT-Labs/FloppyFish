/*
Author : Arda
Company : PhysTech
Date : 5/12/2020
*/

package com.mygdx.game.objects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle

class Moss(var mossPair: Pair<TextureRegion, TextureRegion>, MOSS_GAP: Float = 2.6f, dimX: Float = 2f, dimY: Float = 28f,
           positionX: Float, flipBoolY: Boolean = false, flipBoolX: Boolean = false,
           val mossShape: MossShape) : AbstractMossObject(mossPair, MOSS_GAP, dimX, dimY, positionX, flipBoolY, flipBoolX, mossShape) {

    //For simple types
    val firstMossRect = Rectangle()
    val secondMossRect = Rectangle()

    //For complex types
    val firstMossPoly = Polygon()
    val secondMossPoly = Polygon()

    init {
        dimension.set(dimX, dimY)
        origin.set(dimX / 2f, dimY / 2f)
        position.set(positionX, 0f)

    }

    override fun render(batch: SpriteBatch) {

        if (flipBoolY) {
            batch.draw(mossPair.first.texture, position.x, mossParameterDownMoss, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                    mossPair.first.regionX, mossPair.first.regionY, mossPair.first.regionWidth, mossPair.first.regionHeight, flipBoolX, true)
            batch.draw(mossPair.second.texture, position.x, -mossParameterUpMoss, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                    mossPair.second.regionX, mossPair.second.regionY, mossPair.second.regionWidth, mossPair.second.regionHeight, flipBoolX, true)
        } else {
            batch.draw(mossPair.first.texture, position.x, -mossParameterUpMoss, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                    mossPair.first.regionX, mossPair.first.regionY, mossPair.first.regionWidth, mossPair.first.regionHeight, flipBoolX, false)
            batch.draw(mossPair.second.texture, position.x, mossParameterDownMoss, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                    mossPair.second.regionX, mossPair.second.regionY, mossPair.second.regionWidth, mossPair.second.regionHeight, flipBoolX, false)
        }
    }


    override fun update(deltaTime: Float) {
        super.update(deltaTime)
    }

    override fun updateBounds(mossShape: MossShape) {

        when {
            mossShape == MossShape.MOSS_CLASSIC -> {
                firstMossRect.set(position.x, mossParameterDownMoss, dimX, dimY)
                secondMossRect.set(position.x, -mossParameterUpMoss, dimX, dimY)
            }
            mossShape == MossShape.MOSS_FIRST_TYPE && !flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x, mossParameterDownMoss - 1.9f - MOSS_GAP * 2,
                        position.x + 2f, mossParameterDownMoss - MOSS_GAP * 2,
                        position.x + dimX, mossParameterDownMoss - MOSS_GAP * 2,
                        position.x + dimX, mossParameterDownMoss - MOSS_GAP * 2 - dimY,
                        position.x, mossParameterDownMoss - MOSS_GAP * 2 - dimY)

                val floatArrayUpMoss = floatArrayOf(position.x, -mossParameterUpMoss + 2 * dimY + 2 * MOSS_GAP,
                        position.x + dimX, -mossParameterUpMoss + 2 * dimY + 2 * MOSS_GAP,
                        position.x + dimX, -mossParameterUpMoss + dimY + 2 * MOSS_GAP + 1.9f,
                        position.x + 2f, -mossParameterUpMoss + dimY + 2 * MOSS_GAP + 1.9f,
                        position.x, -mossParameterUpMoss + dimY + 2 * MOSS_GAP)

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss

                if (flipBoolX) {
                    firstMossPoly.setOrigin(position.x + dimension.x / 2f, position.y + dimension.y / 2f)
                    secondMossPoly.setOrigin(position.x + dimension.x / 2f, position.y + dimension.y / 2f)
                    firstMossPoly.setScale(-1f, 1f)
                    secondMossPoly.setScale(-1f, 1f)
                }
            }
            mossShape == MossShape.MOSS_FIRST_TYPE && flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x, -mossParameterDownMoss + dimY + 2 * MOSS_GAP,
                        position.x + dimX, -mossParameterDownMoss + dimY + 2 * MOSS_GAP,
                        position.x + dimX, -mossParameterDownMoss + 2 * MOSS_GAP + 1.9f,
                        position.x + 2f, -mossParameterDownMoss + 2 * MOSS_GAP + 1.9f,
                        position.x, -mossParameterDownMoss + 2 * MOSS_GAP).multiplyMinus()

                val floatArrayUpMoss = floatArrayOf(position.x, mossParameterUpMoss - 2f - MOSS_GAP * 2 - dimY,
                        position.x + 2f, mossParameterUpMoss - MOSS_GAP * 2 - dimY,
                        position.x + dimX, mossParameterUpMoss - MOSS_GAP * 2 - dimY,
                        position.x + dimX, mossParameterUpMoss - MOSS_GAP * 2 - dimY * 2,
                        position.x, mossParameterUpMoss - MOSS_GAP * 2 - dimY * 2).multiplyMinus()

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss

                if (flipBoolX) {
                    firstMossPoly.setOrigin(position.x + dimension.x / 2f, position.y + dimension.y / 2f)
                    secondMossPoly.setOrigin(position.x + dimension.x / 2f, position.y + dimension.y / 2f)
                    firstMossPoly.setScale(-1f, 1f)
                    secondMossPoly.setScale(-1f, 1f)
                }
            }
            mossShape == MossShape.WOOD_CLASSIC -> {
                firstMossRect.set(position.x, mossParameterDownMoss, dimX, dimY)
                secondMossRect.set(position.x, -mossParameterUpMoss, dimX, dimY)
            }
            mossShape == MossShape.WOOD_FIRST_TYPE -> {
                //THERE IS NO FLIPBOOLY ON WOOD FIRST TYPE!!
                val floatArrayDownMoss = floatArrayOf(position.x, mossParameterDownMoss - 3f - 2 * MOSS_GAP,
                        position.x + dimX, mossParameterDownMoss - 2 * MOSS_GAP,
                        position.x + dimX, mossParameterDownMoss - dimY - 2 * MOSS_GAP,
                        position.x, mossParameterDownMoss - dimY - 2 * MOSS_GAP)

                val floatArrayUpMoss = floatArrayOf(position.x, -mossParameterUpMoss + dimY * 2f + 2 * MOSS_GAP,
                        position.x + 4f, -mossParameterUpMoss + dimY * 2f + 2 * MOSS_GAP,
                        position.x + 4f, -mossParameterUpMoss + dimY + 2 * MOSS_GAP + 3f,
                        position.x, -mossParameterUpMoss + dimY + 2 * MOSS_GAP)

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss

                if (flipBoolX) {
                    firstMossPoly.setOrigin(position.x + dimension.x / 2f, position.y + dimension.y / 2f)
                    secondMossPoly.setOrigin(position.x + dimension.x / 2f, position.y + dimension.y / 2f)
                    firstMossPoly.setScale(-1f, 1f)
                    secondMossPoly.setScale(-1f, 1f)
                }

            }
            mossShape == MossShape.WOOD_SECOND_TYPE && !flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x, mossParameterDownMoss - 1.4f - MOSS_GAP * 2f,
                        position.x + 2f, mossParameterDownMoss - MOSS_GAP * 2f,
                        position.x + 4f, mossParameterDownMoss - MOSS_GAP * 2f,
                        position.x + 6f, mossParameterDownMoss - 1.4f - MOSS_GAP * 2f,
                        position.x + 6f, mossParameterDownMoss - 1.4f - MOSS_GAP * 2f - dimY,
                        position.x, mossParameterDownMoss - 1.4f - MOSS_GAP * 2f - dimY)

                val floatArrayUpMoss = floatArrayOf(position.x, -mossParameterUpMoss + 2 * dimY + MOSS_GAP * 2f,
                        position.x + 6f, -mossParameterUpMoss + 2 * dimY + MOSS_GAP * 2f,
                        position.x + 6f, -mossParameterUpMoss + dimY + MOSS_GAP * 2f,
                        position.x + 4f, -mossParameterUpMoss + dimY + MOSS_GAP * 2f + 1.4f,
                        position.x + 2f, -mossParameterUpMoss + dimY + MOSS_GAP * 2f + 1.4f,
                        position.x, -mossParameterUpMoss + dimY + MOSS_GAP * 2f)

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss

            }
            mossShape == MossShape.WOOD_SECOND_TYPE && flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x, -mossParameterDownMoss + 2 * MOSS_GAP + dimY,
                        position.x + 6f, -mossParameterDownMoss + 2 * MOSS_GAP + dimY,
                        position.x + 6f, -mossParameterDownMoss + 2 * MOSS_GAP,
                        position.x + 4f, -mossParameterDownMoss + 2 * MOSS_GAP + 1.4f,
                        position.x + 2f, -mossParameterDownMoss + 2 * MOSS_GAP + 1.4f,
                        position.x, -mossParameterDownMoss + 2 * MOSS_GAP).multiplyMinus()

                val floatArrayUpMoss = floatArrayOf(position.x, mossParameterUpMoss - dimY - 2 * MOSS_GAP - 1.4f,
                        position.x + 2f, mossParameterUpMoss - dimY - 2 * MOSS_GAP,
                        position.x + 4f, mossParameterUpMoss - dimY - 2 * MOSS_GAP,
                        position.x + 6f, mossParameterUpMoss - dimY - 2 * MOSS_GAP - 1.4f,
                        position.x + 6f, mossParameterUpMoss - 2 * dimY - 2 * MOSS_GAP,
                        position.x, mossParameterUpMoss - 2 * dimY - 2 * MOSS_GAP).multiplyMinus()

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss
            }
            mossShape == MossShape.COLUMN_CLASSIC -> {
                firstMossRect.set(position.x+0.5f, mossParameterDownMoss, dimX - 0.3f, dimY)
                secondMossRect.set(position.x+0.5f, -mossParameterUpMoss, dimX - 0.3f, dimY)
            }
            mossShape == MossShape.COLUMN_FIRST_TYPE && !flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x + 0.5f, mossParameterDownMoss - MOSS_GAP * 2 - 1.5f,
                        position.x + 2f, mossParameterDownMoss - MOSS_GAP * 2 - 1.5f,
                        position.x + 4f, mossParameterDownMoss - MOSS_GAP * 2,
                        position.x + 4f, mossParameterDownMoss - MOSS_GAP * 2 - 1.5f - dimY,
                        position.x + 0.5f, mossParameterDownMoss - MOSS_GAP * 2 - 1.5f - dimY)

                val floatArrayUpMoss = floatArrayOf(position.x + 0.5f, -mossParameterUpMoss + MOSS_GAP * 2 + dimY,
                        position.x + 2f, -mossParameterUpMoss + MOSS_GAP * 2 + dimY,
                        position.x + 4f, -mossParameterUpMoss + MOSS_GAP * 2 + dimY + 1.5f,
                        position.x + 4f, -mossParameterUpMoss + MOSS_GAP * 2 + 2 * dimY,
                        position.x + 0.5f, -mossParameterUpMoss + MOSS_GAP * 2 + 2 * dimY)

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss

                if (flipBoolX) {
                    firstMossPoly.setOrigin(position.x + 0.5f + dimension.x / 2f, position.y + dimension.y / 2f)
                    secondMossPoly.setOrigin(position.x + 0.5f + dimension.x / 2f, position.y + dimension.y / 2f)
                    firstMossPoly.setScale(-1f, 1f)
                    secondMossPoly.setScale(-1f, 1f)
                }

            }
            mossShape == MossShape.COLUMN_FIRST_TYPE && flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x + 0.5f, mossParameterDownMoss - MOSS_GAP * 2f,
                        position.x + 2f, mossParameterDownMoss - MOSS_GAP * 2f,
                        position.x + 4f, mossParameterDownMoss - MOSS_GAP * 2f - 1.5f,
                        position.x + 4f, mossParameterDownMoss - MOSS_GAP * 2f - dimY,
                        position.x + 0.5f, mossParameterDownMoss - MOSS_GAP * 2f - dimY)

                val floatArrayUpMoss = floatArrayOf(position.x + 0.5f, -mossParameterUpMoss + MOSS_GAP * 2f + dimY + 1.5f,
                        position.x + 2f, -mossParameterUpMoss + MOSS_GAP * 2f + 1.5f + dimY,
                        position.x + 4f, -mossParameterUpMoss + MOSS_GAP * 2f + dimY,
                        position.x + 4f, -mossParameterUpMoss + MOSS_GAP * 2f + 2 * dimY + 1.5f,
                        position.x + 2f, -mossParameterUpMoss + MOSS_GAP * 2f + 2 * dimY + 1.5f,
                        position.x + 0.5f, -mossParameterUpMoss + MOSS_GAP * 2f + 2 * dimY + 1.5f)

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss

                if (flipBoolX) {
                    firstMossPoly.setOrigin(position.x + 0.5f + dimension.x / 2f, position.y + dimension.y / 2f)
                    secondMossPoly.setOrigin(position.x + 0.5f + dimension.x / 2f, position.y + dimension.y / 2f)
                    firstMossPoly.setScale(-1f, 1f)
                    secondMossPoly.setScale(-1f, 1f)
                }
            }
            mossShape == MossShape.COLUMN_SECOND_TYPE && !flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x + 0.5f, mossParameterDownMoss - MOSS_GAP * 2f,
                        position.x + 4f, mossParameterDownMoss - MOSS_GAP * 2f,
                        position.x + 6f, mossParameterDownMoss - MOSS_GAP * 2f - 1.5f,
                        position.x + 6f, mossParameterDownMoss - MOSS_GAP * 2f - dimY,
                        position.x + 0.5f, mossParameterDownMoss - MOSS_GAP * 2f - dimY)

                val floatArrayUpMoss = floatArrayOf(position.x + 0.5f, -mossParameterUpMoss + MOSS_GAP * 2f + dimY + 1.5f,
                        position.x + 4f, -mossParameterUpMoss + MOSS_GAP * 2f + 1.5f + dimY,
                        position.x + 6f, -mossParameterUpMoss + MOSS_GAP * 2f + dimY,
                        position.x + 6f, -mossParameterUpMoss + MOSS_GAP * 2f + 2 * dimY + 1.5f,
                        position.x + 4f, -mossParameterUpMoss + MOSS_GAP * 2f + 2 * dimY + 1.5f,
                        position.x + 0.5f, -mossParameterUpMoss + MOSS_GAP * 2f + 2 * dimY + 1.5f)

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss

                if (flipBoolX) {
                    firstMossPoly.setOrigin(position.x + 0.5f + dimension.x / 2f, position.y + dimension.y / 2f)
                    secondMossPoly.setOrigin(position.x + 0.5f + dimension.x / 2f, position.y + dimension.y / 2f)
                    firstMossPoly.setScale(-1f, 1f)
                    secondMossPoly.setScale(-1f, 1f)
                }
            }

            mossShape == MossShape.COLUMN_SECOND_TYPE && flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x + 0.5f, mossParameterDownMoss - MOSS_GAP * 2 - 1.5f,
                        position.x + 4f, mossParameterDownMoss - MOSS_GAP * 2 - 1.5f,
                        position.x + 6f, mossParameterDownMoss - MOSS_GAP * 2,
                        position.x + 6f, mossParameterDownMoss - MOSS_GAP * 2 - 1.5f - dimY,
                        position.x + 0.5f, mossParameterDownMoss - MOSS_GAP * 2 - 1.5f - dimY)

                val floatArrayUpMoss = floatArrayOf(position.x + 0.5f, -mossParameterUpMoss + MOSS_GAP * 2 + dimY,
                        position.x + 4f, -mossParameterUpMoss + MOSS_GAP * 2 + dimY,
                        position.x + 6f, -mossParameterUpMoss + MOSS_GAP * 2 + dimY + 1.5f,
                        position.x + 6f, -mossParameterUpMoss + MOSS_GAP * 2 + 2 * dimY,
                        position.x + 0.5f, -mossParameterUpMoss + MOSS_GAP * 2 + 2 * dimY)

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss

                if (flipBoolX) {
                    firstMossPoly.setOrigin(position.x + 0.5f + dimension.x / 2f, position.y + dimension.y / 2f)
                    secondMossPoly.setOrigin(position.x + 0.5f + dimension.x / 2f, position.y + dimension.y / 2f)
                    firstMossPoly.setScale(-1f, 1f)
                    secondMossPoly.setScale(-1f, 1f)
                }
            }
            mossShape == MossShape.COLUMN_THIRD_TYPE && !flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x + 0.5f , mossParameterDownMoss -MOSS_GAP*2f -1.5f,
                                                      position.x + 2f , mossParameterDownMoss -MOSS_GAP*2f,
                                                      position.x + 6f , mossParameterDownMoss -MOSS_GAP*2f,
                                                      position.x + 8f , mossParameterDownMoss -MOSS_GAP*2f -1.5f,
                                                      position.x + 8f , mossParameterDownMoss -MOSS_GAP*2f -dimY,
                                                      position.x + 0.5f , mossParameterDownMoss -MOSS_GAP*2f -dimY)

                val floatArrayUpMoss = floatArrayOf(position.x +0.5f, -mossParameterUpMoss + MOSS_GAP*2f + dimY + 0.5f,
                                                    position.x + 2f , -mossParameterUpMoss + MOSS_GAP*2f + dimY + 1.5f,
                                                    position.x + 6f , -mossParameterUpMoss + MOSS_GAP*2f + dimY + 1.5f,
                                                    position.x + 8f , -mossParameterUpMoss + MOSS_GAP*2f + dimY ,
                                                    position.x + 8f , -mossParameterUpMoss + MOSS_GAP*2f + 2*dimY ,
                                                    position.x + 2f , -mossParameterUpMoss + MOSS_GAP*2f + 2*dimY ,
                                                    position.x + 0.5f , -mossParameterUpMoss + MOSS_GAP*2f + 2*dimY)

                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss


            }
            mossShape == MossShape.COLUMN_THIRD_TYPE && flipBoolY -> {
                val floatArrayDownMoss = floatArrayOf(position.x +0.5f, -mossParameterDownMoss +2*MOSS_GAP+dimY +1.5f,
                        position.x + 8f, -mossParameterDownMoss + 2*MOSS_GAP +dimY +1.5f,
                        position.x + 8f, -mossParameterDownMoss + 2*MOSS_GAP ,
                        position.x + 6f, -mossParameterDownMoss + 2*MOSS_GAP +1.5f,
                        position.x +2f , -mossParameterDownMoss  + 3.2f  +1.5f,
                        position.x +0.5f , -mossParameterDownMoss  + 3.2f -1f +1.5f).multiplyMinus()

                val floatArrayUpMoss = floatArrayOf(position.x +0.5f , mossParameterUpMoss -2*dimY  -MOSS_GAP*2f-1.5f,
                        position.x +2f , mossParameterUpMoss -2*dimY -2*MOSS_GAP -1.5f,
                        position.x + 8f, mossParameterUpMoss -2*dimY - 3.2f -1.5f,
                        position.x + 8f, mossParameterUpMoss - 2*MOSS_GAP -dimY -1.5f,
                        position.x + 6f, mossParameterUpMoss - 2*MOSS_GAP -dimY ,
                        position.x + 2f, mossParameterUpMoss - 2*MOSS_GAP -dimY ,
                        position.x +0.5f, mossParameterUpMoss -2*MOSS_GAP -dimY -1.5f ).multiplyMinus()
                firstMossPoly.vertices = floatArrayDownMoss
                secondMossPoly.vertices = floatArrayUpMoss
            }
        }
    }
}