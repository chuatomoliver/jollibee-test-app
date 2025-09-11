package com.certicode.jolibee_test_app.presentation.contacts_business

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactBusinessAddScreen(
    navController: NavController,
    viewModel: ContactsBusinessViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // State variables for text fields
    var businessName by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }

    // State for the "Tags" dropdown
    val tags = listOf("Popular", "Market", "aaaa", "bbbb","Trending")
    var tagsExpanded by remember { mutableStateOf(false) }
    var selectedTags by remember { mutableStateOf(emptySet<String>()) }

    // State for the "Business" dropdown
    var categoryExpanded by remember { mutableStateOf(false) }
    var selectedCategories by remember { mutableStateOf(emptySet<String>()) } // Corrected: Use a set for multi-selection

    LaunchedEffect(uiState) {
        // Your logic for LaunchedEffect
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is BusinessUiState.ContactBusinessAdded -> {
                // Show a toast for a successful addition
                // Reset the local state variables
                businessName = ""
                contactEmail = ""
                selectedCategories = emptySet()
                selectedTags = emptySet()

                // Navigate back
//                navController.popBackStack()
                Toast.makeText(context, "Business Added Successfully", Toast.LENGTH_SHORT).show()
                // Reset the ViewModel's state to prevent the toast from showing again
                viewModel.resetBusinessAddedState()
            }
            is BusinessUiState.Error -> {
                // Show an error toast
                val errorMessage = (uiState as BusinessUiState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {
                // Do nothing for Loading, Success, etc.
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Business") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.white),
                    titleContentColor = colorResource(id = R.color.black),
                    navigationIconContentColor = colorResource(id = R.color.black)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = businessName,
                onValueChange = { businessName = it },
                label = { Text("Business Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = contactEmail,
                onValueChange = { contactEmail = it },
                label = { Text("Contact Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Categories Dropdown Menu
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = !categoryExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = if (selectedCategories.isEmpty()) "Select Categories" else selectedCategories.joinToString(", "),
                        onValueChange = {},
                        label = { Text("Categories") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(categoryExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        listOf("RCG", "Cat 1", "Cat 2", "aaaa","bbbb").forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category) },
                                onClick = {
                                    selectedCategories = if (selectedCategories.contains(category)) {
                                        selectedCategories - category
                                    } else {
                                        selectedCategories + category
                                    }
                                },
                                leadingIcon = {
                                    Checkbox(
                                        checked = selectedCategories.contains(category),
                                        onCheckedChange = { isChecked ->
                                            selectedCategories = if (isChecked) {
                                                selectedCategories + category
                                            } else {
                                                selectedCategories - category
                                            }
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tags Dropdown Menu
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = tagsExpanded,
                    onExpandedChange = { tagsExpanded = !tagsExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = if (selectedTags.isEmpty()) "Select Tags" else selectedTags.joinToString(", "),
                        onValueChange = {},
                        label = { Text("Tags") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(tagsExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = tagsExpanded,
                        onDismissRequest = { tagsExpanded = false }
                    ) {
                        tags.forEach { tag ->
                            DropdownMenuItem(
                                text = { Text(text = tag) },
                                onClick = {
                                    selectedTags = if (selectedTags.contains(tag)) {
                                        selectedTags - tag
                                    } else {
                                        selectedTags + tag
                                    }
                                },
                                leadingIcon = {
                                    Checkbox(
                                        checked = selectedTags.contains(tag),
                                        onCheckedChange = { isChecked ->
                                            selectedTags = if (isChecked) {
                                                selectedTags + tag
                                            } else {
                                                selectedTags - tag
                                            }
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Add Business Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    val newBusiness = BusinessModel(
                        businessName = businessName,
                        email = contactEmail,
                        categories = selectedCategories.joinToString(separator = ","), // Pass the list of selected categories
                        tags = selectedTags.joinToString(separator = ",")
                    )
                    viewModel.addBusiness(newBusiness)
                }) {
                    Text("Add Business")
                }
            }
        }
    }
}
