package com.example.air_tickets

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cities = resources.getStringArray(R.array.cities)
        // Получение массива городов из файла strings.xml
        val spinnerDeparture = findViewById<Spinner>(R.id.spinnerDepartureCity)
        // Получение ссылки на Spinner для города вылета
        val spinnerArrival = findViewById<Spinner>(R.id.spinnerArrivalCity)
        // Получение ссылки на Spinner для города прилета
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        // Адаптер выступает в роли посредника между источником данных (массив cities) и Spinner.
        // Он извлекает данные из источника и предоставляет их Spinner для отображения.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Установка стиля выпадающего списка - стандартный
        spinnerDeparture.adapter = adapter // Установка адаптера для Spinner города вылета
        spinnerArrival.adapter = adapter // Установка адаптера для Spinner города прилета

        val departureDateEditText = findViewById<EditText>(R.id.editTextDepartureDate)
        // Получение ссылки на EditText для даты вылета
        val arrivalDateEditText = findViewById<EditText>(R.id.editTextArrivalDate)
        // Получение ссылки на EditText для даты прилета

        val calendar = Calendar.getInstance() // Создание объекта Calendar для получения текущей даты
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        departureDateEditText.setOnClickListener { // Обработчик клика по полю даты вылета
            DatePickerDialog(this, { _, year, month, dayOfMonth -> // Создание DatePickerDialog
                departureDateEditText.setText("${year}-${month + 1}-${dayOfMonth}") // Установка выбранной даты в поле
            }, year, month, day).show() // Отображение DatePickerDialog
        }

        arrivalDateEditText.setOnClickListener { // Аналогичный обработчик для даты прилета
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                arrivalDateEditText.setText("${year}-${month + 1}-${dayOfMonth}")
            }, year, month, day).show()
        }

        findViewById<Button>(R.id.buttonSearch).setOnClickListener { // Обработчик клика по кнопке "Найти"
            Toast.makeText(this, "Поиск производится...", Toast.LENGTH_SHORT).show() // Отображение сообщения Toast
        }
    }
}