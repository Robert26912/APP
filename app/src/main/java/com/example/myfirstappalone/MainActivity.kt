package com.example.myfirstappalone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var database = scoreDatabase.ScoreDatabase.getInstance(applicationContext)
        var dao=database.scoreDatabaseDao


            GlobalScope.launch (context= Dispatchers.Default){
                d("jpk","Enter dao.clear()")
                dao.clear()
                d("jpk","dao.clear( completed")
                d("jpk","Insert data")
                dao.insertGame("first game", 10)
                dao.insertGame ("second game", 100)
                dao.insertGame("third game", 1000)
                dao.insertGame("fourth", 10000)
                d("jpk", "data insered")
                var scores=dao.getOrdered()
                scores.forEach{
                    d("jpk",it.id.toString()+" : " +it.name+" :"+it.sum)
                }
            }


    }

}
