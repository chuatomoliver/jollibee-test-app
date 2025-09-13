package com.certicode.jolibee_test_app.presentation.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit // Import for the Edit icon
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryViewModel

/**
 * Composable function for the Category screen.
 * This screen displays a list of categories and provides a floating action button to add a new one.
 * It observes the [CategoryViewModel] for UI state changes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    // Collect the UI state from the ViewModel.
    val uiState by viewModel.categoryState.collectAsState()

    // This effect ensures the view model fetches the categories when the screen is active.
    LaunchedEffect(key1 = Unit) {
        viewModel.getAllCategory()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("category_add_screen") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Category")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopStart
        ) {
            when (val state = uiState) {
                is CategoryUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CategoryUiState.Success -> {
                    val categories = state.categories
                    if (categories.isEmpty()) {
                        Text(
                            text = "No categories found.",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(categories) { category ->
                                CategoryItem(
                                    category = category,
                                    onDelete = { viewModel.deleteCategory(category) },
                                    onUpdate = {
                                        navController.navigate("category_update_screen/${category.id}")
                                    }
                                )
                            }
                        }
                    }
                }
                is CategoryUiState.Error -> {
                    Text(
                        text = "Error: ${state.message}",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CategoryUiState.Empty -> {
                    Text(
                        text = "No categories found.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CategoryUiState.CategoryAdded -> {
                    // This state is handled by a LaunchedEffect to pop back, no UI needed here.
                    // We just reset the state to avoid re-triggering.
                    viewModel.resetCategoryState()
                }
                is CategoryUiState.CategoryUpdated -> {
                    viewModel.resetCategoryState()
                }
                is CategoryUiState.CategoryDeleted -> {
                    viewModel.resetCategoryState()
                }
            }
        }
    }
}

/**
 * Composable to display a single category item as a Card.
 * @param category The [CategoryModel] to display.
 * @param onDelete The callback function to be invoked when the delete button is clicked.
 * @param onUpdate The callback function to be invoked when the update button is clicked.
 */
@Composable
private fun CategoryItem(
    category: CategoryModel,
    onDelete: () -> Unit,
    onUpdate: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = category.categoryName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            // Row for the icons to keep them together
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Update Icon
                IconButton(onClick = onUpdate) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Category"
                    )
                }

                // Delete Icon
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Category"
                    )
                }
            }
        }
    }
}
