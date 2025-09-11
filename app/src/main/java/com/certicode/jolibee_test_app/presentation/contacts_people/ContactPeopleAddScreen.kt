package com.certicode.jolibee_test_app.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.presentation.contacts_people.PeopleUiState
import com.certicode.jolibee_test_app.presentation.contacts_people.ContactsPeopleViewModel
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPeopleAddScreen(
    navController: NavController,
    viewModel: ContactsPeopleViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var businessExpanded by remember { mutableStateOf(false) }
    var selectedBusiness by remember { mutableStateOf("No Business") }

    // State for the Tags Dropdown
    var tagsExpanded by remember { mutableStateOf(false) }
    var selectedTags by remember { mutableStateOf(setOf<String>()) }

    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Collect the business names state from the ViewModel using collectAsStateWithLifecycle
    val businessNameState by viewModel.businessNameState.collectAsStateWithLifecycle()

    // 1. Collect the tags state from the ViewModel
    val tagsNameState by viewModel.tagsNameState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is PeopleUiState.ContactPeopleAdded -> {
                // Show a toast for a successful addition
                name = ""
                email = ""
                phone = ""
                selectedBusiness = "No Business"
                selectedTags = emptySet()

                Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
                // Reset the ViewModel's state
                viewModel.resetPersonAddedState()
                navController.popBackStack()
            }
            is PeopleUiState.Error -> {
                val errorMessage = (uiState as PeopleUiState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add new Person") },
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
                    navigationIconContentColor = colorResource(id = R.color.white)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )

            // Business Dropdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = businessExpanded,
                    onExpandedChange = { businessExpanded = !businessExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedBusiness,
                        onValueChange = {},
                        label = { Text("Business") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(businessExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = businessExpanded,
                        onDismissRequest = { businessExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        when (val state = businessNameState) {
                            is Result.Loading -> {
                                DropdownMenuItem(
                                    text = { CircularProgressIndicator(Modifier.width(24.dp).height(24.dp)) },
                                    onClick = {}
                                )
                            }
                            is Result.Error -> {
                                DropdownMenuItem(
                                    text = { Text("Error loading businesses") },
                                    onClick = {}
                                )
                            }
                            is Result.Success -> {
                                val businessNames = listOf("No Business") + state.data.map { it.businessName }
                                businessNames.forEach { business ->
                                    Text(
                                        text = business,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedBusiness = business
                                                businessExpanded = false
                                            }
                                            .padding(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Tags Dropdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = tagsExpanded,
                    onExpandedChange = { tagsExpanded = !tagsExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedTags.joinToString(", "),
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
                        // 2. Use the tagsNameState to populate the dropdown
                        when (val state = tagsNameState) {
                            is Result.Loading -> {
                                DropdownMenuItem(
                                    text = { CircularProgressIndicator(Modifier.width(24.dp).height(24.dp)) },
                                    onClick = {}
                                )
                            }
                            is Result.Error -> {
                                DropdownMenuItem(
                                    text = { Text("Error loading tags") },
                                    onClick = {}
                                )
                            }
                            is Result.Success -> {
                                // 3. Extract the tag names from the list of TagsModel
                                val tagsList = state.data.map { it.tagName }
                                tagsList.forEach { tag ->
                                    if (tag.isNotEmpty()) { // Filter out empty strings
                                        DropdownMenuItem(
                                            text = {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
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
                                                    Text(text = tag, modifier = Modifier.padding(start = 8.dp))
                                                }
                                            },
                                            onClick = {
                                                selectedTags = if (selectedTags.contains(tag)) {
                                                    selectedTags - tag
                                                } else {
                                                    selectedTags + tag
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        val newPerson = PeopleModel(
                            name = name,
                            email = email,
                            phone = phone,
                            business = selectedBusiness,
                            tags = selectedTags.joinToString()
                        )
                        viewModel.addPerson(newPerson)
                    }
                ) {
                    Text("Add Person")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContactAddScreen() {
    val navController = rememberNavController()
    ContactPeopleAddScreen(navController = navController)
}