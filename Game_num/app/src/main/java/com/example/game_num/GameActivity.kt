package com.example.game_num

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var tvQuestion: TextView
    private lateinit var tvAnswer: TextView
    private lateinit var yesButton: Button
    private lateinit var noButton: Button
    private var begin: Int = 0
    private var end: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        tvQuestion = findViewById(R.id.question)
        tvAnswer = findViewById(R.id.answer)
        yesButton = findViewById(R.id.yes)
        noButton = findViewById(R.id.no)

        // Получаем информацию о границах из Intent
        begin = intent.getIntExtra("begin", 0)
        end = intent.getIntExtra("end", 0)

        // Проверка на валидность диапазона
        if (begin == 0 && end == 0) {
            Toast.makeText(this, "Неверный диапазон", Toast.LENGTH_SHORT).show()
            finish() // Закрываем Activity
            return
        }

        Log.d("mytag", "begin = " + begin)
        Log.d("mytag", "end = " + end)

        startGuessing()
    }


    private fun startGuessing() {
        if (begin > end) {
            Toast.makeText(this, "Проблемы с диапазоном!", Toast.LENGTH_SHORT).show()
            return
        }

        if (begin == end) {
            tvAnswer.text = "Ответ: Ваше число - $begin"
            tvQuestion.visibility = View.GONE
            return
        }

        val middle = (begin + end) / 2
        tvQuestion.text = "Ваше число больше $middle?"
        // Устанавливаем слушатели кликов для кнопок
        yesButton.setOnClickListener(this::onYesNoClick)
        noButton.setOnClickListener(this::onYesNoClick)
    }

    fun onYesNoClick(view: View) {
        when (view.id) {
            R.id.yes -> {
                begin = (begin + end) / 2 + 1
            }
            R.id.no -> {
                end = (begin + end) / 2 - 1
            }
        }

        if (end - begin > 1) {
            startGuessing()
        } else {
            // If only two numbers left, ask the final question
            tvQuestion.text = "Ваше число $begin?"
            // Change the listeners to handle the final answer
            yesButton.setOnClickListener(this::onFinalClick)
            noButton.setOnClickListener(this::onFinalClick)
        }
    }

    fun onFinalClick(view: View) {
        when (view.id) {
            R.id.yes -> {
                tvAnswer.text = "Ответ: Ваше число - $begin"
            }
            R.id.no -> {
                tvAnswer.text = "Ответ: Ваше число - $end"
            }
        }
        tvQuestion.visibility = View.GONE
    }
}