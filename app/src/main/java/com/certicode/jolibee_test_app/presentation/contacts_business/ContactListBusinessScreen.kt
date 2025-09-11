package com.certicode.jolibee_test_app.presentation.contacts_business

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.presentation.contacts_people.Chip

@Composable
fun ContactListBusinessScreen(
    navController: NavController,
    viewModel: ContactsBusinessViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

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
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Contact List - Business",
                fontSize = 22.sp,
                color = colorResource(id = R.color.blue),
                fontWeight = FontWeight.Bold
            )

            when (val state = uiState) {
                is BusinessUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is BusinessUiState.Success -> {
                    if (state.business.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No businesses found.", color = Color.Gray)
                        }
                    } else {
                        state.business.forEach { business ->
                            BusinessCard(
                                business = business,
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }
                }
                is BusinessUiState.Error -> {
                    Text(
                        text = "Error: ${state.message}",
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
fun BusinessCard(
    business: BusinessModel,
    navController: NavController,
    viewModel: ContactsBusinessViewModel
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
                    // Display Business details
                    Row(
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text("Name", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text(business.businessName, fontSize = 14.sp, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text("Email", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text(business.email, fontSize = 14.sp)
                        }
                    }

                    // Display Categories
                    Text(
                        "Categories :",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        business.categories.split(",").forEach { categories ->
                            Chip(text = categories.trim())
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Display Tags
                    Text(
                        "Tags :",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        business.tags.split(",").forEach { tag ->
                            Chip(text = tag)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { navController.navigate("contact_edit_business_screen/${business.id}") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green))
                        ) {
                            Text(text = "Update", color = Color.White)
                        }
                        Button(
                            onClick = { viewModel.deleteBusiness(business) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(text = "Delete", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

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

@Preview(showBackground = true, name = "Completed Screen Preview")
@Composable
fun CompletedScreenPreview() {
    val navController = rememberNavController()
    ContactListBusinessScreen(navController = navController)
}