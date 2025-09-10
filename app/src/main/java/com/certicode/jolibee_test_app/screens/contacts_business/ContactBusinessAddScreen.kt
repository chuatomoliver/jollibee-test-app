package com.certicode.jolibee_test_app.screens.contacts_business

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactBusinessAddScreen(navController: NavController) {
    // State variables for text fields
    var businessName by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }

    // State for the "Business" dropdown
    val businesses = listOf("No Business", "Acme Corp", "Globex Inc.")
    var expandedBusiness by remember { mutableStateOf(false) }
    var selectedBusiness by remember { mutableStateOf(businesses[0]) }

    // State for the "Tags" dropdown
    val tags = listOf("VIP", "Partner", "Client", "Internal")
    var tagsExpanded by remember { mutableStateOf(false) }
    var selectedTags by remember { mutableStateOf(emptySet<String>()) }

    // State for the "Business" dropdown
    val businessOptions = listOf("Retail", "FPCG", "Pastry")
    var businessExpanded by remember { mutableStateOf(false) }
    var selectedBusinessOptions by remember { mutableStateOf(emptySet<String>()) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Business") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black // Set the tint color to black
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
            // Email Field
            OutlinedTextField(
                value = businessName,
                onValueChange = { businessName = it },
                label = { Text("Business Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Field
            OutlinedTextField(
                value = contactEmail,
                onValueChange = { contactEmail = it },
                label = { Text("Contact Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Business Dropdown Menu (Checkbox style)
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = businessExpanded,
                    onExpandedChange = { businessExpanded = !businessExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = if (selectedBusinessOptions.isEmpty()) "Select Category" else selectedBusinessOptions.joinToString(", "),
                        onValueChange = {},
                        label = { Text("Categories") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(businessExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = businessExpanded,
                        onDismissRequest = { businessExpanded = false }
                    ) {
                        businessOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    selectedBusinessOptions = if (selectedBusinessOptions.contains(option)) {
                                        selectedBusinessOptions - option
                                    } else {
                                        selectedBusinessOptions + option
                                    }
                                },
                                leadingIcon = {
                                    Checkbox(
                                        checked = selectedBusinessOptions.contains(option),
                                        onCheckedChange = { isChecked ->
                                            selectedBusinessOptions = if (isChecked) {
                                                selectedBusinessOptions + option
                                            } else {
                                                selectedBusinessOptions - option
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

            // Tags Dropdown Menu (Checkbox style)
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

            // Add Person Button aligned to the end
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { /* Handle form submission */ }) {
                    Text("Add Business")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPersonScreenPreview() {
    val navController = rememberNavController()
    ContactBusinessAddScreen(navController = navController)
}