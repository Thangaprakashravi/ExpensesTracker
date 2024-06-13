package com.example.myexpensestracker

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myexpensestracker.data.model.ExpenseEntity
import com.example.myexpensestracker.viewmodel.HomeViewModel
import com.example.myexpensestracker.viewmodel.HomeViewModelFactory

@Composable
fun HomeScreen(navController: NavController){
    val viewModel: HomeViewModel=
        HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
    Surface(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout (modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)){
            val (nameRow, list, card, topBar,add)= createRefs()
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
                Text(
                    text = "AddExpenses",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )

            }



            val state = viewModel.expenses.collectAsState(initial = emptyList())
            val expense= viewModel.getTotalExpense(state.value)
            val income = viewModel.getTotalIncome(state.value)
            val balance = viewModel.getBalance(state.value)
            CardItem(modifier = Modifier.constrainAs(card){
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, balance=balance,income=income,expense=expense)
            TransactionList(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }, list = state.value, viewModel = viewModel
            )
            Image(painter = painterResource(id = R.drawable.add),
                contentDescription =null,
                modifier = Modifier
                    .constrainAs(add) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .size(48.dp)

                    .clip(CircleShape)
                    .clickable {
                        navController.navigate("/add")
                    }

            )


        }

    }
}



@Composable
fun CardItem(modifier: Modifier, balance:String, income: String,expense:String) {
    Column(
        modifier = Modifier
            .padding(top = 120.dp)
            .padding(30.dp)
            .height(200.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(65, 201, 226))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            Row (modifier = Modifier.fillMaxWidth()){
                Column {
                    Row {
                        Text(text = balance, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Total Balance", fontSize = 4.em, color = Color.White)


                    }



                }

            }
            Box (modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(0.dp, 10.dp)){
                Row ( modifier=Modifier.align(Alignment.CenterEnd)){
                    CardRowItem(modifier = Modifier.align(Alignment.CenterVertically), title = "Expense",
                        amount = expense )

                }

            }

            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

                ){
                Row (modifier=Modifier.padding(top = 80.dp)){
                    CardRowItem(modifier = Modifier.align(Alignment.CenterVertically), title = "Income",
                        amount = income )


                }

            }


        }

    }


}




@Composable
fun CardRowItem(modifier: Modifier, title:String, amount:String, ){
    Column(modifier = Modifier) {
        Row {

            Spacer(modifier = Modifier.size(8.dp))
            Text(text = title, fontSize = 4.em, color = Color.White)
        }
        Text(text = amount, fontSize = 4.em, color = Color.White)
    }

}

@Composable
fun TransactionItem(title: String,amount: String,icon:Int, date:String,color: Color ){
    Box (modifier = Modifier.fillMaxWidth()){
        Row {
            Image(painter = painterResource(id = icon), contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(text = title, fontSize = 16.sp)
                Text(text = date, fontSize = 12.sp)
            }
        }
        Text(text = amount, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterEnd),color= Color.White
        )


    }
}

@Composable
fun TransactionList(modifier: Modifier, list: List<ExpenseEntity>, viewModel: HomeViewModel){
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        item {
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 360.dp)){
                Text(text = "Recent Transactions", fontSize = 20.sp)
                Text(text = "See All", fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterEnd)
                )

            }
        }

        items(list){item ->
            val Icons = viewModel.getIconItem(item)
            TransactionItem(title = item.title,
                amount = item.amount.toString(),
                icon = viewModel.getIconItem(item),
                
                date =item.date.toString() ,
                color = if (item.type=="income") Color.Green else Color.Red)

        }



    }
}





@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen(){
    HomeScreen(rememberNavController())

}