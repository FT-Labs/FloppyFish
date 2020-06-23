/*
Author : Arda
Company : PhysTech
Date : 5/2/2020
*/

package com.mygdx.game.objects

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2

fun Polygon.overlapsCircle(circle: Circle) : Boolean {

    val vertices = transformedVertices
    val center : Vector2 = Vector2(circle.x , circle.y)
    val squareRadius = circle.radius * circle.radius

    for (i in vertices.indices step 2){
        if (i == 0){
            if(Intersector.intersectSegmentCircle(Vector2(vertices[vertices.size - 2] , vertices[vertices.size - 1]) , Vector2(vertices[i] ,
                            vertices[i+1]) , center , squareRadius)){
                return true
            }
        }else{
            if(Intersector.intersectSegmentCircle(Vector2(vertices[i-2] , vertices[i-1]) , Vector2(vertices[i] , vertices[i+1]) ,
                            center , squareRadius)){
                return true
            }
        }
    }
    return this.contains(circle.x , circle.y)
}


fun FloatArray.multiplyMinus() : FloatArray{
    for (i in this.indices){
        if(i % 2 != 0 && i!=0){
            this[i] = -1 * this[i]
        }
    }
    return this
}

