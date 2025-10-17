package com.example.launchcamera.data.datasource

import android.content.Context
import com.example.launchcamera.domain.repository.LoginLocalDataRepository
import javax.inject.Inject
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext

class LoginLocalDataSource @Inject constructor(
    @ApplicationContext context: Context
) : LoginLocalDataRepository {

    private val dataStore =
        context.applicationContext.getSharedPreferences(KEY_SAVE_SESSION, Context.MODE_PRIVATE)

    override suspend fun saveId(id: String) =
        dataStore.edit { putString(KEY_ID, id) }

    override suspend fun getId(): String? =
        dataStore.getString(KEY_ID, null)

    override suspend fun deleteId() =
        dataStore.edit { remove(KEY_ID) }

    companion object {
        private const val KEY_ID = "user_id"
        private const val KEY_SAVE_SESSION = "save_session"
    }
}
