package com.laurabautista.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Números
        val numbers = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in numbers) {
            findViewById<MaterialButton>(id).setOnClickListener {
                currentInput += (it as MaterialButton).text
                display.text = currentInput
            }
        }

        // Operadores
        val operators = listOf(
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide
        )

        for (id in operators) {
            findViewById<MaterialButton>(id).setOnClickListener {
                val operator = (it as MaterialButton).text
                if (currentInput.isNotEmpty() &&
                    currentInput.last() !in setOf('+', '-', '×', '÷', '.', '(')) {
                    currentInput += operator
                    display.text = currentInput
                }
            }
        }

        // Paréntesis
        findViewById<MaterialButton>(R.id.btnOpenParenthesis).setOnClickListener {
            currentInput += "("
            display.text = currentInput
        }

        findViewById<MaterialButton>(R.id.btnCloseParenthesis).setOnClickListener {
            currentInput += ")"
            display.text = currentInput
        }

        // Punto decimal
        findViewById<MaterialButton>(R.id.btnDot).setOnClickListener {
            if (currentInput.isEmpty() ||
                currentInput.last() !in setOf('+', '-', '×', '÷', '.', '(')) {
                currentInput += "."
                display.text = currentInput
            }
        }

        // Porcentaje
        findViewById<MaterialButton>(R.id.btnPercentage).setOnClickListener {
            currentInput += "%"
            display.text = currentInput
        }

        // AC (Borrar todo)
        findViewById<MaterialButton>(R.id.btnAC).setOnClickListener {
            currentInput = ""
            display.text = "0"
        }

        // DELETE (Borrar último)
        findViewById<MaterialButton>(R.id.btnDelete).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                display.text = currentInput.ifEmpty { "0" }
            }
        }

        // Igual
        findViewById<MaterialButton>(R.id.btnEqual).setOnClickListener {
            try {
                val result = evaluateExpression(currentInput)
                display.text = result.toString()
                currentInput = result.toString()
            } catch (e: Exception) {
                display.text = "Error"
                currentInput = ""
            }
        }
    }

    private fun evaluateExpression(expr: String): Double {
        // Reemplazar símbolos visuales por operadores matemáticos
        var expression = expr.replace("×", "*").replace("÷", "/")

        // Por ahora, una implementación simple
        // En un proyecto real, usarías una librería como exp4j o rhino
        return try {
            // Evaluación simple (solo para demostración)
            if (expression.contains("+")) {
                val parts = expression.split("+")
                parts[0].toDouble() + parts[1].toDouble()
            } else if (expression.contains("-")) {
                val parts = expression.split("-")
                parts[0].toDouble() - parts[1].toDouble()
            } else if (expression.contains("*")) {
                val parts = expression.split("*")
                parts[0].toDouble() * parts[1].toDouble()
            } else if (expression.contains("/")) {
                val parts = expression.split("/")
                parts[0].toDouble() / parts[1].toDouble()
            } else {
                expression.toDouble()
            }
        } catch (e: Exception) {
            0.0
        }
    }
}