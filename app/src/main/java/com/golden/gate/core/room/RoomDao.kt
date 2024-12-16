package com.golden.gate.core.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCar(data: RoomArticles)

    @Query("SELECT * From cars")
    fun getCars(): List<RoomArticles>

    @Query("DELETE FROM cars")
    fun clearCars()

    @Query("UPDATE cars SET tenantDate =:tenantDate, tenantName=:tenantName, status=1 WHERE id = :id")
    fun updateCar(tenantName: String, tenantDate: String, id: Int)


}