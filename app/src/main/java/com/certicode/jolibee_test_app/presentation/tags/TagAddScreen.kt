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
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagAddScreen(
    navController: NavController,
    viewModel: TagsViewModel = hiltViewModel()
) {
    val (tagName, setTagName) = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Tag") },
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
                // The onClick lambda now contains the logic to add a new tag.
                onClick = {
                    if (tagName.isNotBlank()) {
                        val newTag = TagsModel(tagName = tagName)
                        viewModel.addTag(newTag)
                        navController.popBackStack()
                    }
                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Tag")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Tag")
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
                    value = tagName,
                    onValueChange = setTagName,
                    label = { Text("Tag Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTagAddScreen() {
    TagAddScreen(navController = rememberNavController())
}