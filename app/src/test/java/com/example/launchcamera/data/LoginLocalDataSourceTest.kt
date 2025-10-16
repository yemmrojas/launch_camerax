package com.example.launchcamera.data

import android.content.Context
import android.content.SharedPreferences
import com.example.launchcamera.MainCoroutineRule
import com.example.launchcamera.data.datasource.LoginLocalDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginLocalDataSourceTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var context: Context
    private lateinit var sut: LoginLocalDataSource

    @Before
    fun setUp() {
        sharedPreferences = mockk()
        editor = mockk(relaxed = true)
        context = mockk()

        every { context.applicationContext } returns context
        every { context.getSharedPreferences(any(), any()) } returns sharedPreferences
        every { sharedPreferences.edit() } returns editor

        sut = LoginLocalDataSource(context)
    }

    @Test
    fun `when saveId is called then it saves the id correctly`() = runTest {
        // When
        sut.saveId(USER_ID)

        // Then
        verify {
            editor.putString(KEY_ID, USER_ID)
            editor.apply()
        }
    }

    @Test
    fun `when getId is called then it returns the correct id`() = runTest {
        // Given
        every { sharedPreferences.getString(KEY_ID, null) } returns USER_ID

        // When
        val result = sut.getId()

        // Then
        assert(result == USER_ID)
        verify { sharedPreferences.getString(KEY_ID, null) }
    }

    @Test
    fun `when deleteId is called then it removes the id`() = runTest {
        // When
        sut.deleteId()

        // Then
        verify {
            editor.remove(KEY_ID)
            editor.apply()
        }
    }

    companion object {
        private const val KEY_ID = "user_id"
        private const val USER_ID = "1234456"
    }
}
