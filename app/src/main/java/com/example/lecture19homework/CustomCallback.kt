package com.example.lecture19homework

interface CustomCallback {
    fun onSuccess(response: String, message: String)
    fun onError(response: String, message: String)
    fun onFailure(response: String)
}