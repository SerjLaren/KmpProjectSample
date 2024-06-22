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
import com.serjlaren.memorycache.MemoryCache
//import com.serjlaren.settings.SettingsStorage
import com.serjlaren.sharedumbrella.SharedUmbrellaData
import com.serjlaren.sharedumbrella.common.RemoteResult
import com.serjlaren.sharedumbrella.userpost.UserPost
import com.serjlaren.sharedumbrella.userpost.UserPostRepository
import kotlinx.coroutines.launch

// Yes, code here is not beauty, but its just for testing of shared logic
// Will be better later (after integration of ViewModel)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedUmbrellaData.initialize(this)
        val userPostRepository = UserPostRepository()
//        val settingsStorage = SettingsStorage()

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    val ctx = LocalContext.current
                    var items: List<UserPost> by remember { mutableStateOf(listOf()) }

                    val mc = MemoryCache()
                    mc.put("qwe", UserPost(0, 0, "1", "2"))
                    val aaa = mc.getTyped<UserPost>("qwe")

                    mc.put("qweqwe", 123)
                    val bbb = mc.getTyped<Int>("qweqwe")

                    LaunchedEffect(key1 = Unit) {
                        coroutineScope.launch {
                            val localPosts = userPostRepository.getUserPostsLocal()
                            if (localPosts.isEmpty()) {
                                when (val remoteResult = userPostRepository.getUserPostsRemote()) {
                                    is RemoteResult.Success -> {
                                        userPostRepository.saveUserPostsLocal(remoteResult.data)
                                        items = remoteResult.data
                                    }

                                    RemoteResult.Error.NetworkError,
                                    RemoteResult.Error.SerializationError,
                                    is RemoteResult.Error.ServerError -> {
                                        Toast.makeText(ctx, "Error happens", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            } else {
                                items = localPosts
                            }

//                            settingsStorage.lastStartTimestamp =
//                                System.currentTimeMillis()
                        }
                    }

                    Column {
                        Text(
                            text = "qweqwe",
//                            text = "Last start timestamp: ${settingsStorage.lastStartTimestamp}",
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
                                        when (userPostRepository.sendUserPostRemote(
                                            UserPost(
                                                1, 1, "Test title", "Test body"
                                            )
                                        )) {
                                            is RemoteResult.Success -> {
                                                Toast
                                                    .makeText(
                                                        ctx,
                                                        "Post added successfully!",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            }

                                            RemoteResult.Error.NetworkError,
                                            RemoteResult.Error.SerializationError,
                                            is RemoteResult.Error.ServerError -> {
                                                Toast
                                                    .makeText(
                                                        ctx,
                                                        "Error happens",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
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
private fun CommentView(userPost: UserPost) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("User: ${userPost.userId} - ${userPost.title}")
        Spacer(modifier = Modifier.height(12.dp))
        Text("Comment: ${userPost.body}")
        Spacer(modifier = Modifier.height(24.dp))
    }
}
