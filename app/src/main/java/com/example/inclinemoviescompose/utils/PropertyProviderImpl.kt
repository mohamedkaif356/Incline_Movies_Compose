package com.example.inclinemoviescompose.utils

import android.content.res.AssetManager
import com.example.inclinemoviescompose.data.datasource.remote.PropertyProvider
import java.io.InputStream
import java.util.Properties

class PropertyProviderImpl (private val assetManager: AssetManager) : PropertyProvider {
    override fun getProperty(key: String): String {
        val inputStream: InputStream = assetManager.open("app.properties")
        val properties = Properties()
        properties.load(inputStream)
        return properties.getProperty(key)
    }
}