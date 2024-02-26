package com.example.ktorexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ktorexample.data.remote.dto.UsersService
import com.example.ktorexample.data.remote.dto.UserReqRes
import com.example.ktorexample.data.remote.dto.UsersServiceImpl
import com.example.ktorexample.ui.theme.KtorExampleTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    private val service = create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val usersNames = produceState<List<UserReqRes>>(
                initialValue = emptyList(),
                producer = {
                    withContext(Dispatchers.IO) {
                        value = service.getUserNames()
                        Log.d("threadOfGetUserNames()", Thread.currentThread().toString())
                    }
                }
            )
            KtorExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn {
                        items(usersNames.value) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(text = it.name, fontSize = 20.sp)
//                                Log.d("thread", Thread.currentThread().toString())
//                                Log.d("div", it.name)
                            }
                        }
                    }
                }
            }
        }
    }
}

//create a function use for to create an instance of Ktor Client
fun create(): UsersService {
    Log.d("threadOfCreate()", Thread.currentThread().toString())
    return UsersServiceImpl(
        client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    )
}