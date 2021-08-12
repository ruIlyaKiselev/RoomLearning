package com.example.roomlearning.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_data")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    @Embedded
    val address: Address,
    val profilePhoto: Bitmap
): Parcelable

@Parcelize
data class Address(
    val streetName: String,
    val streetNumber: Int
): Parcelable