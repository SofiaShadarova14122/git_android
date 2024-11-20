package com.example.snow

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.AsyncTask
import android.view.View
import kotlin.math.sin
import kotlin.random.Random


data class Snowflake(
    var x: Float,
    var y: Float,
    var velocity: Float,
    val horizontalDrift: Float, // Горизонтальное смещение снежинки
    val radius: Float,
    val color: Int
)

class Snow(ctx: Context) : View(ctx) {
    private val snow: MutableList<Snowflake> = mutableListOf() // Список снежинок
    private val paint = Paint()
    private var h = 1000
    private var w = 1000
    private var animationRunning = true // Контроль анимации, при запуске она запустится
    private lateinit var moveTask: MoveTask // Задача AsyncTask для анимации

    // Инициализация задачи AsyncTask в блоке init
    init {
        moveTask = MoveTask(this)
        moveTask.execute()
    }

    @SuppressLint("DrawAllocation") // Подавление предупреждения Lint
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        h = bottom - top
        w = right - left
        snow.clear() // Очистка списка снежинок


        val r = Random(System.currentTimeMillis()) // Генератор случайных чисел
        for (i in 0 until 200) {
            val red = r.nextInt(31) + 215
            val green = r.nextInt(31) + 215
            val blue = r.nextInt(31) + 215
            val color = Color.rgb(red, green, blue) // Создание цвета
            val horizontalDrift = (r.nextFloat() - 0.5f) * 2// Случайное горизонтальное смещение (-1..1)

            snow.add(
                Snowflake(
                    x = r.nextFloat() * w,
                    y = r.nextFloat() * h,
                    velocity = 5f + 20f * r.nextFloat(),// Случайная скорость (5-25)
                    horizontalDrift = horizontalDrift,
                    radius = 10f + 20f * r.nextFloat(),
                    color = color
                )
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.rgb(0, 0, 41))
        for (s in snow) {
            paint.color = s.color
            canvas.drawCircle(s.x, s.y, s.radius, paint)
        }
    }

    // Функция для обновления позиций снежинок
    fun moveSnowflakes() {
        for (s in snow) {
            if (s.y > h - 10) { // Если снежинка достигла нижней границы
                s.y -= h // Перемещаем ее наверх
                s.x = Random.nextFloat() * w // Случайная позиция по X
            }
            val adjustedVelocity = s.velocity * (1 - s.y / h) // Уменьшение скорости падения к низу
            s.y += adjustedVelocity
            s.x += s.horizontalDrift * sin(s.y / 100f) // Горизонтальное смещение
        }
        invalidate() // Запрос перерисовки View
    }

    // Внутренний класс AsyncTask для выполнения анимации в фоновом потоке
    class MoveTask(private val snowView: Snow) : AsyncTask<Void, Void, Void>() {
        //объявляет внутренний класс MoveTask, вложенный внутри класса Snow. Он расширяет AsyncTask
        //AsyncTask<Void, Void, Void> :
        //не принимает входных параметров
        //не публикует никаких промежуточных обновлений
        //не возвращает никаких результатов
        override fun doInBackground(vararg params: Void?): Void? {
            while (snowView.animationRunning) { // Цикл работает пока animationRunning == true
                try {
                    Thread.sleep(20) // Задержка 20 мс
                    snowView.post { snowView.moveSnowflakes() } // Обновление UI в главном потоке
                } catch (e: InterruptedException) {
                //обрабатывает исключение InterruptedException,
                // которое может возникнуть, если поток прерывается во время ожидания (sleep)
                    e.printStackTrace()
                }
            }
            return null
            //AsyncTask требует возвращаемого значения от doInBackground(),
            // даже если оно не используется, потому возвращаем null
        }
    }
}