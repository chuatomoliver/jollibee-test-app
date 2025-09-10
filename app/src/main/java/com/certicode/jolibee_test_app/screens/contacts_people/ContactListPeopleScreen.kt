package com.certicode.jolibee_test_app.screens.contacts_people

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.screens.tasks.TaskUiState
import com.certicode.jolibee_test_app.screens.tasks.TaskViewModel

@Composable
fun ContactListPeopleScreen(
    navController: NavController,
    viewModel: ContactsPeopleViewModel = hiltViewModel()
) {
    // Collect the UI state from the ViewModel.
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current // Get the current context

    // Handle the PersonAdded state to trigger a data refresh.
    LaunchedEffect(uiState) {
        if (false) {
            viewModel.resetPersonAddedState()
        }
    }

    LaunchedEffect(uiState) {
        val message = when (uiState) {
            is ContactsPeopleUiState.ContactPeopleAdded -> "Successfully Added"
            is ContactsPeopleUiState.ContactPeopleUpdated -> "Successfully Updated"
            is ContactsPeopleUiState.ContactPeopleDeleted -> "Successfully Deleted"
            is ContactsPeopleUiState.Error -> null // Handle error separately
            else -> null // Handle other states
        }

        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.resetPersonAddedState() // Resets the state to prevent re-triggering
            navController.popBackStack() // Navigates back after the operation
        } else if (uiState is ContactsPeopleUiState.Error) {
            Toast.makeText(context, (uiState as ContactsPeopleUiState.Error).message, Toast.LENGTH_LONG).show()
        }
    }




    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                ExtendedFloatingActionButton(
                    onClick = { navController.navigate("contact_add_people_screen") },
                    modifier = Modifier.padding(bottom = 6.dp),
                    text = { Text("Add People") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Add People") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Contact List - People",
                fontSize = 22.sp,
                color = colorResource(id = R.color.green),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Handle different UI states.
            when (val currentState = uiState) {
                is ContactsPeopleUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ContactsPeopleUiState.Success -> {
                    if (currentState.people.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("No contacts found.")
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(currentState.people) { people ->
                                ContactCard(
                                    person = people,
                                    onDeleteClick = { viewModel.deletePerson(people) },
                                    onEditClick = {
                                        navController.navigate("contact_update_people_screen/${people.id}")
                                    }
                                )
                            }
                        }
                    }
                }
                is ContactsPeopleUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${currentState.message}",
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                is ContactsPeopleUiState.ContactPeopleAdded -> {
                }
                ContactsPeopleUiState.ContactPeopleDeleted -> TODO()
                ContactsPeopleUiState.ContactPeopleUpdated -> TODO()
                is ContactsPeopleUiState.PersonLoaded -> TODO()
            }
        }
    }
}

/**
 * A Composable card to display a single contact's details.
 *
 * @param person The [PeopleModel] to display.
 * @param onEditClick Callback for when the edit button is clicked.
 * @param onDeleteClick Callback for when the delete button is clicked.
 */
@Composable
fun ContactCard(
    person: PeopleModel,
    onEditClick: (PeopleModel) -> Unit,
    onDeleteClick: (PeopleModel) -> Unit
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
                Column(
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text("Tags :", fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp))

                    // Chip Layout Logic
                    // This part dynamically handles multiple rows for tags.
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        person.tags.split(",").forEach { tag ->
                            Chip(text = tag.trim())
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Person details
                    Row(
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text("Name", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text(person.name, fontSize = 14.sp, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text("Email", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text(person.email, fontSize = 14.sp, color = Color.Black)
                        }
                    }

                    Row(
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text("Phone", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text(person.phone, fontSize = 14.sp, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text("Business", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text(person.business, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onEditClick(person) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green))
                ) {
                    Text(text = "Update", color = Color.White)
                }
                Button(
                    onClick = { onDeleteClick(person) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = "Delete", color = Color.White)
                }
            }
        }
    }
}

// A placeholder for your custom Chip composable.
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
    // You would provide a mock ViewModel here for the preview.
    // For simplicity, we just show the scaffold.
    ContactListPeopleScreen(navController = navController)
}
