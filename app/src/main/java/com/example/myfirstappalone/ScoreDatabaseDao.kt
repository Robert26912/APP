package com.example.myfirstappalone

import androidx.room.Dao
import androidx.room.Query


@Dao
interface ScoreDatabaseDao {
    @Query("INSERT INTO game (name, sum) VALUES (:name, :sum)")
    fun insertGame(name: String, sum: Int)

    @Query("SELECT * from game WHERE id=:id")
    fun get(id: Int) : Game?

    @Query("DELETE from game")
    fun clear()

    @Query("SELECT * from game ORDER BY sum")
    fun getOrdered() : List<Game>
}


