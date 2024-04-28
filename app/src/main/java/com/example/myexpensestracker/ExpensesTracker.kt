package com.example.myexpensestracker

import android.service.autofill.OnClickAction
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ExpensesItem(val no: Int ,
                        var name:String ,
                        var amount : Int,
                        var isEditing: Boolean = false)



@Composable
fun MyExpenses() {

    var inputvalue by remember { mutableStateOf("") }
    var outputvalue by remember { mutableStateOf("") }

    var eItem by remember { mutableStateOf(listOf<ExpensesItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemAmount by remember { mutableStateOf("") }


    Column (modifier = Modifier.fillMaxSize()){

        Button(
            onClick = { showDialog = true},
            modifier = Modifier.padding( 140.dp),
        ) {
            Text ("Add Item")

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            items(eItem){
                    item ->
                if (item.isEditing){
                    ExpensesItemEditor(item = item , onEditComplete = {
                            editname , editamount->
                        eItem = eItem.map { it.copy(isEditing = false) }
                        val edititem = eItem.find { it.no == item.no }
                        edititem?.let{
                            it.name = editname
                            it.amount = editamount
                        }
                    })
                }else{
                    ExpensesListItem(item = item, onEditClick = {

                        eItem=eItem.map { it.copy(isEditing = it.no == it.no) }
                    } ,
                        onDeleteClick = {
                            eItem = eItem-item
                        })
                }
            }
        }

    }
    if (showDialog){

        AlertDialog(onDismissRequest = { showDialog = false },
            confirmButton = {
                Row ( modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(
                        onClick = {
                            if (itemName.isNotBlank()){
                                val newItem = ExpensesItem(
                                    no = eItem.size+1,
                                    name = itemName,
                                    amount = itemAmount.toInt()
                                )
                                eItem = eItem + newItem
                                showDialog = false
                                itemAmount=""



                            }
                        })
                    {
                        Text("Add")

                    }
                    Button(onClick = { showDialog= false})
                    {
                        Text("Cancel")

                    }

                }
            },
            title = {
                Text("Expenses Item List")

            },
            text ={
                Column {
                    OutlinedTextField(value = itemName,
                        onValueChange = {itemName=it},
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = itemAmount,
                        onValueChange ={itemAmount= it},
                        singleLine = true,
                    )
                }
            }


        )
    }

    fun Expensesunit() {
        var inputvalueDouble = inputvalue.toDoubleOrNull()
    }

    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 30.sp,
        color = Color.DarkGray,
        textAlign = TextAlign.Center

    )

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(text = "EXPENSES TRACKER" , style = customTextStyle)
        Spacer(modifier = Modifier.height(16.dp))
        Row (modifier = Modifier.padding(20.dp)) {

            Box {
                OutlinedTextField(value = inputvalue,
                    onValueChange ={
                        inputvalue = it
                        Expensesunit()
                    }, label = { Text(text = "Enter Amount") } ,
                    modifier = Modifier.width(200.dp))


            }
            Spacer(modifier = Modifier.height(16.dp))




        }



    }


}




@Composable
fun ExpensesItemEditor(item: ExpensesItem, onEditComplete:(String, Int)-> Unit) {
    var EditName by remember { mutableStateOf(item.name) }
    var EditAmount by remember { mutableStateOf(item.amount.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row ( modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Column {
            BasicTextField(value = EditName,
                onValueChange = {EditName = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
            BasicTextField(value = EditAmount,
                onValueChange = {EditAmount = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )

        }
        Button(
            onClick = {
                isEditing=false
                onEditComplete(EditName,EditAmount.toInt() ?:1)
            }) {
            Text("Save")

        }

    }
}

@Composable
fun ExpensesListItem(
    item: ExpensesItem,
    onEditClick:()->Unit,
    onDeleteClick:()->Unit
){
    Row(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .border(
            border = BorderStroke(2.dp, Color(0XFF018734)),
            shape = RoundedCornerShape(20)
        ), horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text= item.name , modifier = Modifier.padding(8.dp))
        Text(text= "$ ${item.amount}" , modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier .padding(8.dp)
        ){
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit,contentDescription = null)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete,contentDescription = null)
            }

        }

    }

}

