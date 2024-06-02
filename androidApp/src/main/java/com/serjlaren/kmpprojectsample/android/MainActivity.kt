package com.serjlaren.kmpprojectsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.serjlaren.KmpProjectSample.core.network.CommentDto
import com.serjlaren.KmpProjectSample.core.network.CommentsApi
import com.serjlaren.core.storage.SettingsStorage
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val commentsApi = CommentsApi()
        val settingsStorage = SettingsStorage()

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    var items: List<CommentDto> by remember { mutableStateOf(listOf()) }

                    LaunchedEffect(key1 = Unit) {
                        coroutineScope.launch {
                            items = commentsApi.getComments()
                            settingsStorage.lastStartTimestamp =
                                System.currentTimeMillis()
                        }
                    }

                    Column {
                        Text(
                            text = "Last start timestamp: ${settingsStorage.lastStartTimestamp}",
                            modifier = Modifier.background(color = Color.Blue)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        LazyColumn {
                            items(items) { comment ->
                                CommentView(comment)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CommentView(comment: CommentDto) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("User: ${comment.userId}")
        Text("Comment: ${comment.comment}")
        Spacer(modifier = Modifier.height(20.dp))
    }
}
