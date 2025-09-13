package com.certicode.jolibee_test_app.presentation.contacts_people

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
import androidx.navigation.NavController
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListUpdatePeopleScreen(
    navController: NavController,
    personId: String,
    viewModel: ContactsPeopleViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var businessExpanded by remember { mutableStateOf(false) }
    var selectedBusiness by remember { mutableStateOf("No Business") }
    var tagsExpanded by remember { mutableStateOf(false) }
    var selectedTags by remember { mutableStateOf(setOf<String>()) }

    val uiState by viewModel.uiState.collectAsState()
    val businessNameState by viewModel.businessNameState.collectAsState()
    val tagsNameState by viewModel.tagsNameState.collectAsState() // Corrected: Added missing variable declaration.
    val context = LocalContext.current

    val personIdAsLong = personId.toLongOrNull()

    LaunchedEffect(personIdAsLong) {
        if (personIdAsLong != null) {
            viewModel.getPersonById(personIdAsLong)
            viewModel.getBusinessNames()
            viewModel.getTagsName() // Corrected: Added missing function call.
        }
    }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is PeopleUiState.PersonLoaded -> {
                val person = state.person
                name = person.name
                email = person.email
                phone = person.phone
                selectedBusiness = person.business
                selectedTags = person.tags.split(",").map { it.trim() }.filter { it.isNotEmpty() }.toSet()
            }
            is PeopleUiState.ContactPeopleUpdated -> {
                Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show()
                viewModel.resetPersonAddedState()

            }
            is PeopleUiState.Error -> {
                val errorMessage = state.message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Update People") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colorResource(id = R.color.black)
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
                            is com.certicode.jolibee_test_app.Result.Loading -> {
                                DropdownMenuItem(
                                    text = { CircularProgressIndicator(Modifier.width(24.dp).height(24.dp)) },
                                    onClick = {}
                                )
                            }
                            is com.certicode.jolibee_test_app.Result.Error -> {
                                DropdownMenuItem(
                                    text = { Text("Error loading businesses") },
                                    onClick = {}
                                )
                            }
                            is Result.Success -> {
                                val businessNames = listOf("No Business") + state.data.map { it.businessName }
                                businessNames.forEach { business ->
                                    DropdownMenuItem(
                                        text = { Text(business) },
                                        onClick = {
                                            selectedBusiness = business
                                            businessExpanded = false
                                        }
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
                                val tagsList = state.data.map { it.tagName }
                                tagsList.forEach { tag ->
                                    if (tag.isNotEmpty()) {
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
                        val updatedPerson = PeopleModel(
                            id = personId.toLong(),
                            name = name,
                            email = email,
                            phone = phone,
                            business = selectedBusiness,
                            tags = selectedTags.joinToString(",")
                        )
                        viewModel.editPerson(updatedPerson)
                    }
                ) {
                    Text("Update")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContactAddScreen() {
    // Preview function content remains the same
}