package com.zahirmeddour.calculatrice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                Calculator()
            }
        }
    }
}

@Composable
fun Calculator() {
    var currentNumber by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }

    fun calculate() {
        val expression = currentNumber.split("\\d+".toRegex())
        val numbers = currentNumber.split("\\D+".toRegex()).mapNotNull { it.toDoubleOrNull() }

        if (numbers.size >= 2 && expression.isNotEmpty()) {
            val num1 = numbers[0]
            val num2 = numbers[1]
            val op = expression[0]

            val result = when (op) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
                else -> Double.NaN
            }

            currentNumber = if (result.isNaN()) {
                "Error"
            } else {
                result.toString()
            }
        } else {
            currentNumber = "Error"
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentNumber,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        // Number Buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0..9) {
                Button(onClick = { currentNumber += i.toString() }) { Text(i.toString()) }
            }
        }

        // Operator Buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { operator = "+"; currentNumber += "+" }) { Text("+") }
            Button(onClick = { operator = "-"; currentNumber += "-" }) { Text("-") }
            Button(onClick = { operator = "*"; currentNumber += "*" }) { Text("x") }
            Button(onClick = { operator = "/"; currentNumber += "/" }) { Text("/") }
        }

        // Calculate and Clear Buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { calculate() }) { Text("=") }
            Button(onClick = { currentNumber = ""; operator = "" }) { Text("C") }
        }
    }
}