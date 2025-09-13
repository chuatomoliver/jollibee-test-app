package com.certicode.jolibee_test_app.presentation.tags

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsViewModel
import com.certicode.jolibee_test_app.presentation.category.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagUpdateScreen (
    navController: NavController,
    tagId: Long,
    viewModel: TagsViewModel = hiltViewModel()
) {
    val uiState by viewModel.tagsState.collectAsState()
    var tagName by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Use a single LaunchedEffect to load the tag and update the local state.
    LaunchedEffect(key1 = tagId) {
        viewModel.getTagById(tagId)
    }

    // This LaunchedEffect listens for a successful update operation.
    // When the state is `TagsUpdated`, it navigates back.
    LaunchedEffect(key1 = uiState) {
        if (uiState is TagsUiState.TagsUpdated) {
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show()

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Tag") },
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
            // Only show the FAB if the tag is loaded and the name is not empty.
            if (uiState is TagsUiState.Success && tagName.isNotBlank()) {
                val currentTag = (uiState as TagsUiState.Success).tags.firstOrNull { it.id == tagId }
                if (currentTag != null) {
                    FloatingActionButton(
                        onClick = {
                            val tagToUpdate = currentTag.copy(tagName = tagName)
                            viewModel.updateTag(tagToUpdate)
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Edit, contentDescription = "Update Tag")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Update Tag")
                        }
                    }
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
                verticalArrangement = Arrangement.Top // Center the content vertically
            ) {
                when (val state = uiState) {
                    is TagsUiState.Loading -> {
                        LoadingIndicator()
                    }
                    is TagsUiState.Success -> {
                        val tag = state.tags.firstOrNull { it.id == tagId }
                        if (tag != null) {
                            // This LaunchedEffect ensures the text field is pre-populated
                            // only when the tag data is first loaded.
                            LaunchedEffect(key1 = tag) {
                                tagName = tag.tagName
                            }
                            OutlinedTextField(
                                value = tagName,
                                onValueChange = { tagName = it },
                                label = { Text("Tag Name") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            Text("Tag not found.")
                        }
                    }
                    is TagsUiState.Error -> {
                        Text("Error: ${state.message}")
                    }
                    else -> {
                        // All other states are handled implicitly, or we show a message
                        Text("Loading...")
                    }
                }
            }
        }
    )
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTagUpdateScreen() {
    TagUpdateScreen(navController = rememberNavController(), tagId = 1L)
}
