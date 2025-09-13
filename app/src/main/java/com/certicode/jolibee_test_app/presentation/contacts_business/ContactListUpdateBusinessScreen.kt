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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListUpdateBusinessScreen(
    navController: NavController,
    businessId: Long,
    viewModel: ContactsBusinessViewModel = hiltViewModel()
) {
    // Collecting all three state flows from the ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val categoriesUiState by viewModel.categoriesUiState.collectAsState()
    val tagsUiState by viewModel.tagsUiState.collectAsState()

    val context = LocalContext.current

    // State variables for text fields and dropdown selections
    var businessName by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }
    var tagsExpanded by remember { mutableStateOf(false) }
    var selectedTags by remember { mutableStateOf(emptySet<String>()) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var selectedCategories by remember { mutableStateOf(emptySet<String>()) }

    LaunchedEffect(key1 = businessId) {
        viewModel.getBusinessById(businessId)
    }

    LaunchedEffect(uiState) {
        when (val currentState = uiState) {
            is BusinessUiState.BusinessLoaded -> {
                businessName = currentState.business.businessName
                contactEmail = currentState.business.email
                selectedTags = currentState.business.tags.split(",").map { it.trim() }.toSet()
                selectedCategories = currentState.business.categories.split(",").map { it.trim() }.toSet()
            }
            is BusinessUiState.ContactBusinessUpdated -> {
                Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show()
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
                title = { Text(text = "Update Business") },
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
        // Show loading indicator when the main business data is loading
        if (uiState is BusinessUiState.Loading || categoriesUiState is CategoryUiState.Loading || tagsUiState is TagsUiState.Loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
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
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = !categoryExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val labelText = if (selectedCategories.isEmpty()) "Select Categories" else selectedCategories.joinToString(", ")
                    OutlinedTextField(
                        readOnly = true,
                        value = labelText,
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
                        if (categoriesUiState is CategoryUiState.Success) {
                            val categories = (categoriesUiState as CategoryUiState.Success).categories
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(text = category.categoryName) },
                                    onClick = {
                                        selectedCategories = if (selectedCategories.contains(category.categoryName)) {
                                            selectedCategories - category.categoryName
                                        } else {
                                            selectedCategories + category.categoryName
                                        }
                                    },
                                    leadingIcon = {
                                        Checkbox(
                                            checked = selectedCategories.contains(category.categoryName),
                                            onCheckedChange = { isChecked ->
                                                selectedCategories = if (isChecked) {
                                                    selectedCategories + category.categoryName
                                                } else {
                                                    selectedCategories - category.categoryName
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
                ExposedDropdownMenuBox(
                    expanded = tagsExpanded,
                    onExpandedChange = { tagsExpanded = !tagsExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val labelText = if (selectedTags.isEmpty()) "Select Tags" else selectedTags.joinToString(", ")
                    OutlinedTextField(
                        readOnly = true,
                        value = labelText,
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
                        if (tagsUiState is TagsUiState.Success) {
                            val tags = (tagsUiState as TagsUiState.Success).tags
                            tags.forEach { tag ->
                                DropdownMenuItem(
                                    text = { Text(text = tag.tagName) },
                                    onClick = {
                                        selectedTags = if (selectedTags.contains(tag.tagName)) {
                                            selectedTags - tag.tagName
                                        } else {
                                            selectedTags + tag.tagName
                                        }
                                    },
                                    leadingIcon = {
                                        Checkbox(
                                            checked = selectedTags.contains(tag.tagName),
                                            onCheckedChange = { isChecked ->
                                                selectedTags = if (isChecked) {
                                                    selectedTags + tag.tagName
                                                } else {
                                                    selectedTags - tag.tagName
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
                            id = businessId,
                            businessName = businessName,
                            email = contactEmail,
                            categories = selectedCategories.joinToString(separator = ","),
                            tags = selectedTags.joinToString(separator = ",")
                        )
                        viewModel.editBusiness(updatedBusiness)
                    }) {
                        Text("Update Business")
                    }
                }
            }
        }
    }
}
