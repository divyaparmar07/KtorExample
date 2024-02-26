package com.example.ktorexample.data.remote.dto

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

class UsersServiceImpl(
    //use this for network calls
    private val client: HttpClient
) : UsersService {
    override suspend fun getUserNames(): List<UserReqRes> {
        Log.d("threadOfUsersServiceImp", Thread.currentThread().toString())
        return client.get { url(HttpRoutes.USERS) }
    }
}