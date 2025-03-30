package com.kevin.balancebuddies

data class Ball(var x: Float, var y: Float, val radius: Float) {
    fun move(dx: Float, dy: Float) {
        x += dx
        y += dy
    }
}
