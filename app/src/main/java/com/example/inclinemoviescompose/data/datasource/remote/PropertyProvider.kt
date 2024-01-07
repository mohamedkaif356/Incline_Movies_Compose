package com.example.inclinemoviescompose.data.datasource.remote

interface PropertyProvider {

    fun getProperty(key: String): String
}