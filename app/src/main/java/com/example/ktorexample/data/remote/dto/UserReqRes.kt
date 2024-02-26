package com.example.ktorexample.data.remote.dto

import kotlinx.serialization.Serializable

//what data comes from the api
@Serializable
data class UserReqRes(
    val id : Int,
    val name : String,
    val username : String,
    val email: String
)
