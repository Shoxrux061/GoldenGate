package com.golden.gate.core.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class RoomArticles(


    @[PrimaryKey ColumnInfo("id")] val id: Int,
    @ColumnInfo("image") val image: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("status") val status: Boolean,
    @ColumnInfo("currentPrice") val currentPrice: Int,
    @ColumnInfo("tenantName") val tenantName: String,
    @ColumnInfo("tenantDate") val tenantDate: String,

    )