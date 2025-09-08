package com.certicode.jolibee_test_app.screens.contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.screens.BookingCard

@Composable
fun ContactListScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("contact_add_screen") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Contacts")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding) // Important: Apply padding from Scaffold
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Completed Task List",
                fontSize = 22.sp,
                color = colorResource(id = R.color.green),
                fontWeight = FontWeight.Bold
            )
            // Example with mutable state
            var isTaskCompleted by remember { mutableStateOf(true) }
            BookingCard(
                isTaskCompleted = isTaskCompleted,
                onCompleteClick = { /* No action needed */ },
                onReopenClick = { isTaskCompleted = false }
            )
        }
    }
}

@Preview(showBackground = true, name = "Completed Screen Preview")
@Composable
fun CompletedScreenPreview() {
    val navController = rememberNavController()
    ContactListScreen(navController = navController)
}