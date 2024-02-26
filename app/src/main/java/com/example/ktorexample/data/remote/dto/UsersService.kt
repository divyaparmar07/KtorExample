package com.example.ktorexample.data.remote.dto

interface UsersService {

    //Use for get Names
    suspend fun getUserNames(): List<UserReqRes>
}