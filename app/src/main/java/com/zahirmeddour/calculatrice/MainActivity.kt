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

    fun calculate() {
        // Match any number and operator in the input string
        val regex = """(\d+\.?\d*)([+\-*/])(\d+\.?\d*)""".toRegex()
        val matchResult = regex.matchEntire(currentNumber)

        if (matchResult != null) {
            val (num1, op, num2) = matchResult.destructured
            val number1 = num1.toDouble()
            val number2 = num2.toDouble()

            val result = when (op) {
                "+" -> number1 + number2
                "-" -> number1 - number2
                "*" -> number1 * number2
                "/" -> if (number2 != 0.0) number1 / number2 else Double.NaN
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0..9) {
                Button(onClick = { currentNumber += i.toString() }) { Text(i.toString()) }
            }
        }

        // Operator Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { currentNumber += "+" }) { Text("+") }
            Button(onClick = { currentNumber += "-" }) { Text("-") }
            Button(onClick = { currentNumber += "*" }) { Text("x") }
            Button(onClick = { currentNumber += "/" }) { Text("/") }
        }

        // Calculate and Clear Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { calculate() }) { Text("=") }
            Button(onClick = { currentNumber = "" }) { Text("C") }
        }
    }
}
