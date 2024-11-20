package com.example.films

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Random


class MainActivity : AppCompatActivity() {

    lateinit var movies : MutableList<String>
    val r = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movies = resources.getStringArray(R.array.movies).toMutableList()
        }

    fun onNextFilmClick(view: View) {
        val tvTitle = findViewById<TextView>(R.id.title)
        if (movies.isNotEmpty()) {
            val r = Random().nextInt(movies.size)
            tvTitle.text = movies[r]
            movies.removeAt(r) // Удаляем показанный фильм из списка
        } else {
            tvTitle.text = "Все фильмы просмотрены!"
        }
    }

    fun onNextResetClick(view: View) {
        movies.clear() // Сброс списка оставшихся фильмов
        movies.addAll(resources.getStringArray(R.array.movies).toList()) //возвращение списка вильмов
        val tvTitle = findViewById<TextView>(R.id.title)
        tvTitle.text = "Список фильмов сброшен!"
    }

}