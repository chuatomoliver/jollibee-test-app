package com.certicode.jolibee_test_app.presentation.tags

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsViewModel
import com.certicode.jolibee_test_app.presentation.category.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagScreen(
    navController: NavController,
    viewModel: TagsViewModel = hiltViewModel()
) {
    val uiState by viewModel.tagsState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("tag_add_screen") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Tag")
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
            when (uiState) {
                is TagsUiState.Loading -> {
                    LoadingIndicator()
                }
                is TagsUiState.Success -> {
                    val tags = (uiState as TagsUiState.Success).tags
                    if (tags.isEmpty()) {
                        Text(
                            text = "No tags found.",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(tags) { tag ->
                                TagItem(
                                    tag = tag,
                                    onDelete = { viewModel.deleteTag(tag) },
                                    onUpdate = { navController.navigate("tag_update_screen/${tag.id}") }
                                )
                            }
                        }
                    }
                }
                is TagsUiState.Error -> {
                    Text("Error: ${(uiState as TagsUiState.Error).message}")
                }
                is TagsUiState.TagsAdded, is TagsUiState.TagsDeleted, is TagsUiState.TagsUpdated -> {
                    // These are transient states, the UI will be updated by the Success state.
                    // No UI changes are needed here.
                }
            }
        }
    }
}

/**
 * Composable to display a single tag item as a Card.
 * @param tag The [TagsModel] to display.
 * @param onDelete The callback function to be invoked when the delete button is clicked.
 * @param onUpdate The callback function to be invoked when the update button is clicked.
 */
@Composable
private fun TagItem(
    tag: TagsModel,
    onDelete: () -> Unit,
    onUpdate: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
                text = tag.tagName,
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
                        contentDescription = "Edit Tag"
                    )
                }

                // Delete Icon
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Tag"
                    )
                }
            }
        }
    }
}
