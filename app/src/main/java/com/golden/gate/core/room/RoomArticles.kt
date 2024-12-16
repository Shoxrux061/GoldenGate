package com.golden.gate.core.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "cars")
data class RoomArticles(

    @[PrimaryKey(autoGenerate = true) ColumnInfo("id")] val id: Int = 0,
    @ColumnInfo("image") val image: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("status") val status: Int,
    @ColumnInfo("currentPrice") val currentPrice: String,
    @ColumnInfo("tenantName") val tenantName: String?,
    @ColumnInfo("tenantDate") val tenantDate: String?,

    ) : Parcelable