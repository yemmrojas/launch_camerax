package com.example.launchcamera.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "document_id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "birth_date")
    val birthDate: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "email")
    val email: String? = null,
    @ColumnInfo(name = "phone")
    val contact: String? = null
)