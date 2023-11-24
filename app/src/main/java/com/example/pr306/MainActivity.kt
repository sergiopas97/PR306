package com.example.pr306

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pr306.ui.theme.PR306Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR306Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Invoca la pantalla principal del compositor
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    // Columna principal que organiza los elementos en la pantalla
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Variables para almacenar la entrada del usuario y el resultado del cálculo
        var day by remember { mutableStateOf("") }
        var month by remember { mutableStateOf("") }
        var year by remember { mutableStateOf("") }
        var result by remember { mutableStateOf<String?>(null) }

        // Campo de texto para ingresar el día
        OutlinedTextField(
            value = day,
            onValueChange = { day = it },
            label = { Text("Día") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Campo de texto para ingresar el mes
        OutlinedTextField(
            value = month,
            onValueChange = { month = it },
            label = { Text("Mes") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Campo de texto para ingresar el año
        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Año") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Botón para realizar el cálculo
        Button(
            onClick = {
                result = calcularDiasRestantesEnMes(day, month, year)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular días restantes en el mes")
        }

        // Espaciador vertical
        Spacer(modifier = Modifier.height(16.dp))

        // Muestra el resultado del cálculo si hay uno
        result?.let {
            Text(it)
        }
    }
}

// Función que verifica si un año es bisiesto
fun bisiesto(año: Int): Boolean {
    return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0)
}

// Función que devuelve la cantidad de días en un mes para un año dado
fun diasEnMes(mes: Int, año: Int): Int {
    return when (mes) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (bisiesto(año)) 29 else 28
        else -> -1 // Mes no válido
    }
}

// Función que calcula los días restantes en el mes dado el día, mes y año ingresados
fun calcularDiasRestantesEnMes(dia: String, mes: String, año: String): String {
    try {
        // Convierte las cadenas a enteros
        val diaInt = dia.toInt()
        val mesInt = mes.toInt()
        val añoInt = año.toInt()

        // Obtiene la cantidad de días en el mes
        val diasEnEsteMes = diasEnMes(mesInt, añoInt)

        // Comprueba si la fecha es válida y calcula los días restantes
        return if (diasEnEsteMes == -1 || diaInt < 1 || diaInt > diasEnEsteMes) {
            "Día o mes no válido"
        } else {
            "Días restantes en el mes: ${diasEnEsteMes - diaInt}"
        }
    } catch (e: NumberFormatException) {
        // Captura el error si la entrada no es un número válido
        return "Por favor, ingrese números válidos para día, mes y año."
    }
}

// Vista previa del componente principal
@Preview(showBackground = true, widthDp = 360)
@Composable
fun Preview() {
    PR306Theme {
        MainActivity()
    }
}
