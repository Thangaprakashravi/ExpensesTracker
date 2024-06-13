package com.example.myexpensestracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myexpensestracker.data.model.ExpenseEntity
import com.example.myexpensestracker.viewmodel.AddExpenseViewModel
import com.example.myexpensestracker.viewmodel.AddExpensesViewModelFactory
import kotlinx.coroutines.launch


@Composable
fun AddExpenses(navController: NavController){
    val viewModel = AddExpensesViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)
    val coroutineScope= rememberCoroutineScope()

    Surface (modifier = Modifier.fillMaxWidth()){
        ConstraintLayout (modifier = Modifier.fillMaxSize()){
            val (nameRow, list, card, topBar)= createRefs()
            Image(painter = painterResource(id =R.drawable.wave_haikei), contentDescription = null,
                modifier = Modifier.constrainAs(topBar){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                })
            Box (modifier= Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }){
                Image(painter = painterResource(id = R.drawable.left_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(30.dp))
                Text(
                    text = "AddExpenses",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
                Image(painter = painterResource(id = R.drawable.dot_menu_more_svgrepo_com),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(50.dp))
            }

            Dataform(modifier = Modifier.constrainAs(card){
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            }, onAddExpensesClick = {
                coroutineScope.launch {
                    if (viewModel.addExpense(it)){
                        navController.popBackStack()
                    }
                }


            })
    }   }

}


@Composable
fun Dataform(modifier: Modifier, onAddExpensesClick: (model:ExpenseEntity)-> Unit){
    val name = remember {
        mutableStateOf("")
    }

    val amount = remember{
        mutableStateOf("")
    }
    val date = remember{
        mutableStateOf(0L)
    }
    val dateDialogyVisibility = remember{
        mutableStateOf(false)
    }
    val category = remember{
        mutableStateOf("")
    }
    val type = remember{
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .padding(top = 170.dp)
        .padding(16.dp)
        .fillMaxWidth()
        .shadow(16.dp)
        .clip(
            RoundedCornerShape(16.dp)
        )
        .background(Color.LightGray)
        .padding(16.dp)
        .verticalScroll(rememberScrollState()))
    {
       Text(text = "Name", fontSize = 4.em,)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(value = name.value, onValueChange = {
            name.value = it
        }, modifier = Modifier.fillMaxWidth())
        Text(text = "Amount", fontSize = 4.em,)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(value = amount.value, onValueChange = {
            amount.value= it
        },modifier = Modifier.fillMaxWidth())
        
        Text(text = "Date", fontSize = 4.em)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(value = if(date.value == 0L)"" else Utilis.formatDateToHumanRedableForm(date.value),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dateDialogyVisibility.value = true },
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Black,
                disabledTextColor = Color.Black
            ))
        Spacer(modifier = Modifier.size(8.dp))



        Text(text = "Category", fontSize = 4.em)
        Spacer(modifier = Modifier.size(8.dp))
        ExpensesDropDown(
            listOf("Netflix", "AmazonPrime", "Hotstar", "CrunchyRoll", "H&M"),
            onItemSelected = {
                category.value=it
            }
        )
        Spacer(modifier = Modifier.size(8.dp))
        
        Text(text = "Type", fontSize = 4.em)
        Spacer(modifier = Modifier.size(8.dp))
        ExpensesDropDown(
            listOf("Expenses", "Income"),
            onItemSelected = {
                type.value=it
            }
        )
        Spacer(modifier = Modifier.size(8.dp))

        Button(onClick = {
                         val model = ExpenseEntity(
                             null, name.value, amount.value.toDoubleOrNull()?:0.0, date.value, category.value,
                             type.value
                         )
            onAddExpensesClick(model)
        },modifier = Modifier.fillMaxWidth()) {
            Text(text = "AddExpenses")
            
        }
    }
    if(dateDialogyVisibility.value){
        ExpensesDataPickerDialog(onDataSelected = {
            date.value= it
            dateDialogyVisibility.value=false} ,
            onDismiss =
            {dateDialogyVisibility.value=false})
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesDataPickerDialog(
    onDataSelected:(data: Long)-> Unit,
    onDismiss: ()-> Unit

){
    val dataPickerState = rememberDatePickerState()
    val selectedDate = dataPickerState.selectedDateMillis ?: 0L
    DatePickerDialog(onDismissRequest = {onDismiss()},
        confirmButton = { TextButton(onClick = { onDataSelected(selectedDate)}) {
            Text(text = "Confirm")
        }
        },
         dismissButton = { TextButton(onClick = { onDataSelected(selectedDate) }) {
             Text(text = "Cancel")
             
         }}
        ) {
        DatePicker(state = dataPickerState)
        
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesDropDown(listofItems:List<String>, onItemSelected:(item: String) -> Unit){
    val expanded = remember{
        mutableStateOf(false)
    }
    val selectionItem= remember {
        mutableStateOf(listofItems[0])
    }

    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = {expanded.value = it}) {
        TextField(value =selectionItem.value , onValueChange = {},
           modifier = Modifier
               .fillMaxWidth()
               .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = {  }) {
                    listofItems.forEach {
                        DropdownMenuItem(text = { Text(it) }, onClick = {
                            selectionItem.value=it
                            onItemSelected(selectionItem.value)
                        expanded.value=false})
                    }
                }
            })
    }
}

@Preview
@Composable
fun AddExpensespreview(){
    AddExpenses(rememberNavController())
}