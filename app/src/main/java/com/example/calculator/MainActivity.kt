package com.example.calculator

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : Activity() {
    private lateinit var display: TextView
    private var current = "0"
    private var stored: Double? = null
    private var pending: String? = null
    private var startNew = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(16, 20, 30)
        window.navigationBarColor = Color.rgb(16, 20, 30)
        window.decorView.systemUiVisibility = 0

        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 28, 18, 18)
            setBackgroundColor(Color.rgb(16, 20, 30))
        }

        val heading = TextView(this).apply {
            text = "เครื่องคิดเลข"
            textSize = 18f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER_VERTICAL
        }
        page.addView(heading, LinearLayout.LayoutParams(-1, 48))

        display = TextView(this).apply {
            text = current
            textSize = 48f
            typeface = Typeface.create("sans-serif", Typeface.NORMAL)
            setTextColor(Color.WHITE)
            gravity = Gravity.END or Gravity.CENTER_VERTICAL
            maxLines = 1
            setPadding(8, 8, 8, 8)
        }
        page.addView(display, LinearLayout.LayoutParams(-1, 0, 1f))

        val grid = GridLayout(this).apply {
            columnCount = 4
            rowCount = 5
            useDefaultMargins = true
        }
        val keys = arrayOf(
            "AC", "±", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "−",
            "1", "2", "3", "+",
            "⌫", "0", ".", "="
        )
        keys.forEach { key ->
            val button = Button(this).apply {
                text = key
                textSize = 21f
                isAllCaps = false
                setTextColor(Color.WHITE)
                setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    when (key) {
                        "=" -> Color.rgb(36, 145, 112)
                        in listOf("÷", "×", "−", "+", "%") -> Color.rgb(56, 75, 105)
                        "AC", "±", "⌫" -> Color.rgb(67, 75, 91)
                        else -> Color.rgb(35, 43, 59)
                    }
                ))
                setOnClickListener { press(key) }
            }
            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = 0
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(5, 5, 5, 5)
            }
            grid.addView(button, params)
        }
        page.addView(grid, LinearLayout.LayoutParams(-1, 0, 3.2f))
        setContentView(page)
    }

    private fun refresh() { display.text = current }

    private fun press(key: String) {
        when (key) {
            "AC" -> { current = "0"; stored = null; pending = null; startNew = false }
            "⌫" -> {
                if (!startNew) current = if (current.length <= 1 || (current.length == 2 && current.startsWith("-"))) "0" else current.dropLast(1)
            }
            "±" -> if (current != "0") current = if (current.startsWith("-")) current.drop(1) else "-$current"
            "%" -> current = format(current.toDoubleOrNull()?.div(100.0) ?: 0.0)
            "." -> {
                if (startNew) { current = "0."; startNew = false }
                else if (!current.contains(".")) current += "."
            }
            "+", "−", "×", "÷" -> {
                stored = current.toDoubleOrNull()
                pending = key
                startNew = true
            }
            "=" -> {
                val a = stored
                val b = current.toDoubleOrNull()
                if (a != null && b != null && pending != null) {
                    val result = when (pending) {
                        "+" -> a + b
                        "−" -> a - b
                        "×" -> a * b
                        "÷" -> if (b == 0.0) null else a / b
                        else -> null
                    }
                    current = result?.let { format(it) } ?: "หารด้วยศูนย์ไม่ได้"
                    stored = null
                    pending = null
                    startNew = true
                }
            }
            else -> {
                if (current == "หารด้วยศูนย์ไม่ได้" || startNew) {
                    current = key
                    startNew = false
                } else if (current == "0") current = key
                else if (current.length < 14) current += key
            }
        }
        refresh()
    }

    private fun format(value: Double): String {
        if (!value.isFinite()) return "Error"
        return BigDecimal.valueOf(value).setScale(8, RoundingMode.HALF_UP)
            .stripTrailingZeros().toPlainString()
    }
}
