package com.golden.gate.core.room

import androidx.room.Dao
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.Update


@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCar(data: RoomArticles)

    @Query("SELECT * From cars")
    fun getCars(): List<RoomArticles>

    @Query("DELETE FROM cars")
    fun clearCars()

    @Query("UPDATE cars SET tenantDate =:tenantDate, tenantName=:tenantName, status=1 WHERE id = :id")
    fun addTenant(tenantName: String, tenantDate: String, id: String)

    @Update
    fun updateData(data: RoomArticles)

    @Query("DELETE FROM cars WHERE id = :id")
    fun deleteCar(id: String)

    @Query("SELECT * FROM cars WHERE id =:id")
    fun getById(id: String): List<RoomArticles>

    @Query("DELETE FROM cars")
    fun clearBase()
}