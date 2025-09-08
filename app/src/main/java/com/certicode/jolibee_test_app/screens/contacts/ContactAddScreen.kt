package com.example.jolibee_test_app.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactAddScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var businessExpanded by remember { mutableStateOf(false) }
    var selectedBusiness by remember { mutableStateOf("No Business") }
    var tagsExpanded by remember { mutableStateOf(false) }
    val tags = listOf("Popular", "New Market", "aaaa", "bbbb", "Trending")
    var selectedTags by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add new Person") },
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
            // No Text("Add new Person") here anymore, as it's now the TopAppBar title.

            Spacer(modifier = Modifier.height(8.dp)) // Add some space below the top bar

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
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

            // The rest of your form content (Dropdowns, Buttons, etc.) remains here...
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Business Dropdown
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
                // Tags Dropdown
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
                Button(onClick = { /* Handle Add Person */ }) {
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
    ContactAddScreen(navController = navController)
}