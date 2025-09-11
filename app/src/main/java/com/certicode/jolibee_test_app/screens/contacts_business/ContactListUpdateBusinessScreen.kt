package com.certicode.jolibee_test_app.screens.contacts_business

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListUpdateBusinessScreen(
    navController: NavController,
    businessId: Long,
    viewModel: ContactsBusinessViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var businessName by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }

    val availableTags = listOf("Popular", "Market", "aaaa", "bbbb", "Trending")
    var tagsExpanded by remember { mutableStateOf(false) }
    var selectedTags by remember { mutableStateOf(emptySet<String>()) }

    // Removed unused availableCategories variable
    var categoryExpanded by remember { mutableStateOf(false) }
    // Changed to a set of strings, not a single string
    var selectedCategory by remember { mutableStateOf(emptySet<String>()) }

    LaunchedEffect(key1 = businessId) {
        viewModel.getBusinessById(businessId)
    }

    LaunchedEffect(uiState) {
        when (val currentState = uiState) {
            is BusinessUiState.BusinessLoaded -> {
                businessName = currentState.business.businessName
                contactEmail = currentState.business.email
                selectedTags = currentState.business.tags.split(",").map { it.trim() }.toSet()
                // Split categories from the loaded business and set to selectedCategory
                selectedCategory = currentState.business.categories.split(",").map { it.trim() }.toSet()
            }
            is BusinessUiState.ContactBusinessUpdated -> {
                Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show()
                viewModel.getBusinesses()
                navController.popBackStack()

                viewModel.resetBusinessUpdatedState()
            }
            is BusinessUiState.Error -> {
                Toast.makeText(context, "Error: ${currentState.message}", Toast.LENGTH_LONG).show()
            }
            else -> { /* Do nothing for other states */ }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Update Business") }, // Changed title to reflect the update function
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
                // Added a vertical scroll modifier to handle long content
                .verticalScroll(rememberScrollState())
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
                        // Corrected variable name from selectedCategories to selectedCategory
                        value = if (selectedCategory.isEmpty()) "Select Categories" else selectedCategory.joinToString(", "),
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
                        // Using a hardcoded list for demonstration, this should likely be from a ViewModel
                        listOf("RCG", "Cat 1", "Cat 2", "aaaa","bbbb").forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category) },
                                onClick = {
                                    // Corrected variable name from selectedCategories to selectedCategory
                                    selectedCategory = if (selectedCategory.contains(category)) {
                                        selectedCategory - category
                                    } else {
                                        selectedCategory + category
                                    }
                                },
                                leadingIcon = {
                                    Checkbox(
                                        checked = selectedCategory.contains(category),
                                        onCheckedChange = { isChecked ->
                                            // Corrected variable name from selectedCategories to selectedCategory
                                            selectedCategory = if (isChecked) {
                                                selectedCategory + category
                                            } else {
                                                selectedCategory - category
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
                        // The 'tags' variable in the original code wasn't defined. Changed to use the 'availableTags'
                        availableTags.forEach { tag ->
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

            // Update Business Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    val updatedBusiness = BusinessModel(
                        businessName = businessName,
                        email = contactEmail,
                        categories = selectedCategory.joinToString(separator = ","),
                        tags = selectedTags.joinToString(separator = ","),
                        id = businessId // The ID is required for the update operation
                    )
                    viewModel.editBusiness(updatedBusiness)
                }) {
                    Text("Update Business") // Changed button text
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun UpdateBusinessScreenPreview() {
    val navController = rememberNavController()
    // For preview, we can pass a dummy ID
    // ContactListUpdateBusinessScreen(navController = navController, businessId = 1L)
}