package com.golden.gate.core.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class RoomArticles(

    @[PrimaryKey(autoGenerate = true) ColumnInfo("id")] val id: Int = 0,
    @ColumnInfo("image") val image: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("status") val status: Boolean,
    @ColumnInfo("currentPrice") val currentPrice: String,
    @ColumnInfo("tenantName") val tenantName: String?,
    @ColumnInfo("tenantDate") val tenantDate: String?,

    )