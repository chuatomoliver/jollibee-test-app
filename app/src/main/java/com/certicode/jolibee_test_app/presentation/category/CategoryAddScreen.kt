package com.certicode.jolibee_test_app.presentation.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryViewModel

/**
 * Composable function for the Category Add screen.
 * This screen provides a UI to add a new category to the database.
 *
 * @param navController The NavController used for navigation.
 * @param viewModel The Hilt-injected ViewModel for handling category operations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryAddScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    // State to hold the name of the new category.
    val (categoryName, setCategoryName) = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Category") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                // The onClick lambda now contains the logic to add a new category.
                onClick = {
                    if (categoryName.isNotBlank()) {
                        val newCategory = CategoryModel(categoryName = categoryName)
                        viewModel.addCategory(newCategory)
                        navController.popBackStack()
                    }
                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Category")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Category")
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = setCategoryName,
                    label = { Text("Category Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryAddScreen() {
    CategoryAddScreen(navController = rememberNavController())
}
