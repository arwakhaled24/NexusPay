package com.example.nexuspay.core.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.userDataStore by preferencesDataStore(name = "user_prefs")

class UserDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    companion object {
        val IDENTIFIER_KEY = stringPreferencesKey("identifier")
        val IMAGE_URL_KEY = stringPreferencesKey("image_url")
        val USER_ID_KEY = longPreferencesKey("user_id")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_BALANCE_KEY = longPreferencesKey("user_balance")
        val USER_CURRENCY_KEY = stringPreferencesKey("user_currency")
    }

    suspend fun getIdentifier(): String =
        context.userDataStore.data.first()[IDENTIFIER_KEY].orEmpty()

    suspend fun getImageUrl(): String =
        context.userDataStore.data.first()[IMAGE_URL_KEY].orEmpty()

    suspend fun saveIdentifier(value: String) {
        context.userDataStore.edit { preferences -> preferences[IDENTIFIER_KEY] = value }
    }

    suspend fun saveImageUrl(value: String) {
        context.userDataStore.edit { preferences -> preferences[IMAGE_URL_KEY] = value }
    }

    suspend fun getCachedUser(): CachedUser? {
        val preferences = context.userDataStore.data.first()
        val id = preferences[USER_ID_KEY] ?: return null
        val name = preferences[USER_NAME_KEY] ?: return null
        val identifier = preferences[IDENTIFIER_KEY] ?: return null
        val balance = preferences[USER_BALANCE_KEY] ?: return null
        val currency = preferences[USER_CURRENCY_KEY] ?: return null

        return CachedUser(
            id = id,
            name = name,
            identifier = identifier,
            balance = balance,
            currency = currency,
            avatarUrl = preferences[IMAGE_URL_KEY].orEmpty(),
        )
    }

    suspend fun saveCachedUser(user: CachedUser) {
        context.userDataStore.edit { preferences ->
            preferences[USER_ID_KEY] = user.id
            preferences[USER_NAME_KEY] = user.name
            preferences[IDENTIFIER_KEY] = user.identifier
            preferences[USER_BALANCE_KEY] = user.balance
            preferences[USER_CURRENCY_KEY] = user.currency
            preferences[IMAGE_URL_KEY] = user.avatarUrl
        }
    }
}

data class CachedUser(
    val id: Long,
    val name: String,
    val identifier: String,
    val balance: Long,
    val currency: String,
    val avatarUrl: String,
)