package com.example.myfirstappalone
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity

data class Game(
    @PrimaryKey val id : Int,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="sum") val sum : Int
)
