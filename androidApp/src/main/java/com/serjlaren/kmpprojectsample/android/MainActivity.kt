package com.serjlaren.kmpprojectsample.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serjlaren.KmpProjectSample.core.network.common.ApiResponse
import com.serjlaren.KmpProjectSample.core.network.userpost.UserPostApi
import com.serjlaren.KmpProjectSample.core.network.userpost.UserPostRequestBody
import com.serjlaren.KmpProjectSample.core.network.userpost.UserPostResponseDto
import com.serjlaren.core.storage.SettingsStorage
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postsApi = UserPostApi()
        val settingsStorage = SettingsStorage()

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    val ctx = LocalContext.current
                    var items: List<UserPostResponseDto> by remember { mutableStateOf(listOf()) }

                    LaunchedEffect(key1 = Unit) {
                        coroutineScope.launch {
                            when (val apiResult = postsApi.getPosts()) {
                                is ApiResponse.Success -> {
                                    items = apiResult.body
                                }

                                is ApiResponse.Error.HttpError,
                                ApiResponse.Error.NetworkError,
                                ApiResponse.Error.SerializationError -> {
                                    // Error happens
                                }
                            }
                            settingsStorage.lastStartTimestamp =
                                System.currentTimeMillis()
                        }
                    }

                    Column {
                        Text(
                            text = "Last start timestamp: ${settingsStorage.lastStartTimestamp}",
                            modifier = Modifier.background(color = Color.Blue)
                        )
                        Text(
                            text = "Post post to api",
                            lineHeight = 40.sp,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(color = Color.Red)
                                .clickable(onClick = {
                                    coroutineScope.launch {
                                        when (postsApi.postPost(
                                            UserPostRequestBody(
                                                userId = 1,
                                                title = "Test title",
                                                body = "Test body",
                                            )
                                        )) {
                                            is ApiResponse.Success -> {
                                                Toast.makeText(ctx, "Post added successfully!", Toast.LENGTH_SHORT).show()
                                            }

                                            is ApiResponse.Error.HttpError,
                                            ApiResponse.Error.NetworkError,
                                            ApiResponse.Error.SerializationError -> {
                                                // Error happens
                                            }
                                        }
                                    }
                                }),
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
private fun CommentView(userPost: UserPostResponseDto) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("User: ${userPost.userId} - ${userPost.title}")
        Spacer(modifier = Modifier.height(12.dp))
        Text("Comment: ${userPost.body}")
        Spacer(modifier = Modifier.height(24.dp))
    }
}
