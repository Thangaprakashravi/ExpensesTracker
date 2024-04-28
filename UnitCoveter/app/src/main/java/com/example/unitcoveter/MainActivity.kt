package com.example.unitcoveter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitcoveter.ui.theme.UnitCoveterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitCoveterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   UnitConveter()
                }
            }
        }
    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConveter(){

    var inputvalue by remember { mutableStateOf("") }
    var outputvalue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpandad by remember { mutableStateOf(false) }
    var oExpandad by remember { mutableStateOf(false) }
    val conversionFactor = remember{ mutableStateOf(1.00) }
    val oConversionFactor = remember{ mutableStateOf(1.00) }

    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 32.sp,
        color = Color.Red
    )


    fun convertionUnit(){
        //?: -> elvis operator
        val inputvalueDouble = inputvalue.toDoubleOrNull() ?: 0.0
        val result = (inputvalueDouble * conversionFactor.value * 100.0/oConversionFactor.value). roundToInt() /100.0
        outputvalue = result.toString()
    }



    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("UnitConvetre"  , style = customTextStyle)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value =inputvalue,
            onValueChange ={
             inputvalue=it
             convertionUnit()


        },
        label = {Text("Enter Value" )}    )
        Spacer(modifier = Modifier.height(16.dp))

        Row {

            Box{

                //input box

                Button(onClick = { iExpandad=true }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown,
                        contentDescription = "Array Down")
                    
                }
                //input button
                DropdownMenu(expanded = iExpandad, onDismissRequest = { iExpandad=true }) {
                    DropdownMenuItem(text = { Text("Centimeters")},
                        onClick = {
                            iExpandad = false
                            inputUnit = "Centimeters"
                            conversionFactor.value=0.01
                            convertionUnit()
                        }
                    )
                    DropdownMenuItem(text = { Text("Meters") },
                        onClick = {
                            iExpandad = false
                            inputUnit = "Meters"
                            conversionFactor.value=1.0
                            convertionUnit()

                        }
                    )
                    DropdownMenuItem(text = {Text("Feet")},
                        onClick = {
                            iExpandad = false
                            inputUnit = "Feet"
                            conversionFactor.value=0.3048
                            convertionUnit()
                        }
                    )
                    DropdownMenuItem(text = {Text("Milimeter")},
                        onClick = {
                            iExpandad = false
                            inputUnit = "Milimeter"
                            conversionFactor.value=0.001
                            convertionUnit()
                        }
                    )
                    
                }

            }
            Spacer(modifier = Modifier.width(16.dp))
            Box{
                //output box

                Button(onClick = { oExpandad=true }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown,
                        contentDescription = "Array Down")

                }
                //output button
                DropdownMenu(expanded = oExpandad, onDismissRequest = { oExpandad=false }) {
                    DropdownMenuItem(text = { Text("Centimeters")},
                        onClick = {
                            oExpandad = false
                            outputUnit = "Centimeters"
                            oConversionFactor.value=0.01
                            convertionUnit()
                        }
                    )
                    DropdownMenuItem(text = { Text("Meters") },
                        onClick = {
                            oExpandad = false
                            outputUnit = "Meters"
                            oConversionFactor.value=1.0
                            convertionUnit()
                        }
                    )
                    DropdownMenuItem(text = {Text("Feet")},
                        onClick = {
                            oExpandad = false
                            outputUnit = "Feet"
                            oConversionFactor.value=0.3048
                            convertionUnit() }
                    )
                    DropdownMenuItem(text = {Text("Milimeters")},
                        onClick = {
                            oExpandad = false
                            outputUnit = "Milimeters"
                            oConversionFactor.value=0.001
                            convertionUnit()
                        }
                    )

                }

            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        // Result Text
        Text("Reuslt: $outputvalue $outputUnit",
            style = MaterialTheme.typography.headlineLarge


            )

    }



}

@Preview(showBackground = true)
@Composable
fun UnitConveterPreview(){
    UnitConveter()
}