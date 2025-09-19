package com.example.launchcamera.data.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.launchcamera.data.model.UserDataEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userData: UserDataEntity)


    @Query("UPDATE user_data SET email = :email, phone = :phone WHERE document_id = :id")
    suspend fun updateUserContactInfo(
        id: String,
        email: String,
        phone: String
    )

    @Query("DELETE FROM user_data WHERE document_id = :id")
    suspend fun deleteUserData(id: String)

    @Query("SELECT * FROM user_data WHERE document_id = :id")
    suspend fun getUserById(id: String): UserDataEntity?
}