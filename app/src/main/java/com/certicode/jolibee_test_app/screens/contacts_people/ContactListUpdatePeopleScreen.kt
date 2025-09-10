package com.certicode.jolibee_test_app.screens.contacts_people



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
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel


// Other imports remain the same

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListUpdatePeopleScreen(
    navController: NavController,
    personId: String,
    viewModel: ContactsPeopleViewModel = hiltViewModel()
) {
    // Other local state variables remain the same
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var businessExpanded by remember { mutableStateOf(false) }
    var selectedBusiness by remember { mutableStateOf("No Business") }
    var tagsExpanded by remember { mutableStateOf(false) }
    val tags = listOf("Popular", "New Market", "aaaa", "bbbb", "Trending")
    var selectedTags by remember { mutableStateOf(setOf<String>()) }

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Fetch the person's data when the screen is first composed
    val personIdAsLong = personId.toLongOrNull()

    LaunchedEffect(personIdAsLong) {
        if (personIdAsLong != null) {
            viewModel.getPersonById(personIdAsLong)
        }
    }
    LaunchedEffect(uiState) {
        val message = when (uiState) {
            is ContactsPeopleUiState.ContactPeopleAdded -> "Successfully Added"
            is ContactsPeopleUiState.ContactPeopleUpdated -> "Successfully Updated"
            is ContactsPeopleUiState.ContactPeopleDeleted -> "Successfully Deleted"
            else -> null
        }

        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.resetPersonAddedState() // Assuming this function also resets other states
            navController.popBackStack()
        } else if (uiState is ContactsPeopleUiState.Error) {
            Toast.makeText(context, (uiState as ContactsPeopleUiState.Error).message, Toast.LENGTH_LONG).show()
        }
    }

    // Update local state when the person's data is loaded from the ViewModel
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is ContactsPeopleUiState.PersonLoaded -> {
                val person = state.person
                name = person.name
                email = person.email
                phone = person.phone
                selectedBusiness = person.business
                selectedTags = person.tags.split(",").map { it.trim() }.toSet()
            }
            is ContactsPeopleUiState.ContactPeopleAdded -> {
                Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
                viewModel.resetPersonAddedState()
            }
            is ContactsPeopleUiState.Error -> {
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
                // The rest of the TopAppBar code remains the same
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

            // OutlinedTextFields and DropdownMenus remain the same

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
                        listOf("No Business", "Jollibee Foods Corp", "Red Ribbon", "aaaa","Mang Inasal").forEach { business ->
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
                        tags.forEach { tag ->
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
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        val updatedPerson = PeopleModel(
                            id = personId.toLong(), // Use the personId from the navigation argument
                            name = name,
                            email = email,
                            phone = phone,
                            business = selectedBusiness,
                            tags = selectedTags.joinToString(",") // Join tags with a comma
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

}
