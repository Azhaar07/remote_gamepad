package com.remotegamepad.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "layout")

class LayoutStore(private val context: Context) {
    private val leftX = floatPreferencesKey("left_x")
    private val leftY = floatPreferencesKey("left_y")
    private val rightX = floatPreferencesKey("right_x")
    private val rightY = floatPreferencesKey("right_y")

    suspend fun save(left: Pair<Float, Float>, right: Pair<Float, Float>) {
        context.dataStore.edit { prefs ->
            prefs[leftX] = left.first
            prefs[leftY] = left.second
            prefs[rightX] = right.first
            prefs[rightY] = right.second
        }
    }

    suspend fun load(): Pair<Pair<Float, Float>, Pair<Float, Float>> {
        val prefs = context.dataStore.data.first()
        val left = Pair(prefs[leftX] ?: 0.2f, prefs[leftY] ?: 0.75f)
        val right = Pair(prefs[rightX] ?: 0.8f, prefs[rightY] ?: 0.75f)
        return Pair(left, right)
    }
}
