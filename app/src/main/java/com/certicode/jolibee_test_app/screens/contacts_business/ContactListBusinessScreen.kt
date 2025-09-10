package com.certicode.jolibee_test_app.screens.contacts_business

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.screens.TaskButton



@Composable
fun ContactListBusinessScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                ExtendedFloatingActionButton(
                    onClick = { navController.navigate("contact_add_business_screen")  },
                    modifier = Modifier.padding(bottom = 6.dp),
                    text = { Text("Add Business") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Add Business") }
                )
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding) // Important: Apply padding from Scaffold
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Example with mutable state
            var isTaskCompleted by remember { mutableStateOf(true) }

            Text(
                text = "Contact List - Business",
                fontSize = 22.sp,
                color = colorResource(id = R.color.blue),
                fontWeight = FontWeight.Bold
            )

            BookingCard(
                isTaskCompleted = isTaskCompleted,
                onCompleteClick = { /* No action needed */ },
                onReopenClick = { isTaskCompleted = false }
            )
        }
    }
}

@Composable
fun BookingCard(
    isTaskCompleted: Boolean,
    onCompleteClick: () -> Unit,
    onReopenClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text("Tags :", fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp))

                    // --- Chip Layout Logic ---
                    Column {
                        // Check if there are more than 3 chips
                        if (allChips.size > 3) {
                            // First Row for the first three chips
                            Row(modifier = Modifier.padding(bottom = 6.dp)) {
                                Chip(text = allChips[0])
                                Spacer(modifier = Modifier.width(8.dp))
                                Chip(text = allChips[1])
                                Spacer(modifier = Modifier.width(8.dp))
                                Chip(text = allChips[2])
                            }
                            // Second Row for the rest of the chips
                            Row(modifier = Modifier.padding(bottom = 6.dp)) {
                                allChips.subList(3, allChips.size).forEachIndexed { index, text ->
                                    Chip(text = text)
                                    if (index < allChips.subList(3, allChips.size).size - 1) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                            }
                        } else {
                            // Single Row if there are 3 or fewer chips
                            Row(modifier = Modifier.padding(bottom = 6.dp)) {
                                allChips.forEachIndexed { index, text ->
                                    Chip(text = text)
                                    if (index < allChips.size - 1) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                            }
                        }
                    }
                    // --- End of Chip Layout Logic ---

                    // The rest of your original layout remains unchanged
                    Row(
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(0.3f)
                        ) {
                            Text("Name", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text("TOM OLIVER CHUA", fontSize = 14.sp, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(0.7f)
                        ) {
                            Text("Email", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text("chuatomoliver@gmail.com", fontSize = 14.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(0.3f)
                        ) {
                            Text("Phone", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text("09499673244", fontSize = 14.sp, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(0.7f)
                        ) {
                            Text("Business", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text("Jollibee Food Corp.", fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                TaskButton(
                    isTaskCompleted = isTaskCompleted,
                    onCompleteClick = onCompleteClick,
                    onReopenClick = onReopenClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// This is a placeholder for your custom Chip composable.
@Composable
fun Chip(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.LightGray)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

// A list of chips to demonstrate the logic. You can change this list.
val allChips = listOf("Popular", "New Market", "Technology", "Web 3.0", "AI", "Cloud", "Security")

@Preview(showBackground = true, name = "Completed Screen Preview")
@Composable
fun CompletedScreenPreview() {
    val navController = rememberNavController()
    ContactListBusinessScreen(navController = navController)
}