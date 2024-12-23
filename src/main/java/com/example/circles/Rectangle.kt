package com.example.circles

class Rectangle(val left: Float, val top: Float, val right: Float, val bottom: Float, var color: Int) {
    fun contains(x: Float, y: Float): Boolean {
        return x >= left && x <= right && y >= top && y <= bottom
    }
}
