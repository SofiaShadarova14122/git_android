package com.example.game_num

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var begin: EditText
    private lateinit var end: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Инициализация EditText
        begin = findViewById(R.id.begin)
        end = findViewById(R.id.end)
    }
    fun onGuessClick(view: View) {

        // Получение значений из EditText и их преобразование в числа
        val beginValueString = begin.text.toString()
        val endValueString = end.text.toString()

        val beginValue = beginValueString.toInt()
        val endValue = endValueString.toInt()


        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra("begin", beginValue)
            putExtra("end", endValue)
        }
        startActivity(intent)
    }

}