package com.example.wsaudiology.data.datasource.remote

interface PropertyProvider {

    fun getProperty(key: String): String
}