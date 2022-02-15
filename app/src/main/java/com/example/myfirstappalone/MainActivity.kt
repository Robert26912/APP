package com.example.myfirstappalone

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstappalone.MainActivity.Companion.ctx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch




class MainActivity : AppCompatActivity() {
    private lateinit var database: scoreDatabase.ScoreDatabase
    private lateinit var dao: ScoreDatabaseDao

    companion object {
        lateinit var ctx: Context
        lateinit var scores : List<Game>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = scoreDatabase.ScoreDatabase.getInstance(applicationContext)
        dao=database.scoreDatabaseDao

        GlobalScope.launch(context=Dispatchers.Default) {
            d("jpk", "Enter dao.clear()")
            dao.clear()
            d("jpk", "dao.clear() completed")

            d("jpk", "Insert data")
            dao.insertGame("first game", 10)
            dao.insertGame("second game", 100)
            dao.insertGame("third game", 1000)
            dao.insertGame("fourth game", 10000)
            d("jpk", "data inserted")

            scores=dao.getOrdered()
            scores.forEach {
                d("jpk", it.id.toString()+" : "+it.name+" : "+it.sum)
            }

            val gameRecyclerView = findViewById<RecyclerView>(R.id.gameRecyclerView)
            gameRecyclerView.apply {
                GlobalScope.launch(context=Dispatchers.Main) {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = GameAdapter()
                }
            }
        }
    }
}

class GameAdapter : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.game_row, parent, false)
        return ViewHolder(view)
    }

    //Number of items is RecyclerView
    override fun getItemCount() = 100

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scores = MainActivity.scores
        val num = scores.size
        val game=scores[position%num]
        holder.bind(game)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val button : Button = itemView.findViewById(R.id.gameButton)
        fun bind(item : Game) {
            d("jpk", "bind("+item.id.toString()+" : "+item.name+" : "+item.sum+")")
            button.setText(item.id.toString()+" : "+item.name+" : "+item.sum)
            button.setOnClickListener {
                val ctx=it.context

                var dialog = AlertDialog.Builder(ctx)
                with(dialog) {
                    setMessage(item.name)
                    setTitle("Player:")

                    var inputText = EditText(ctx)
                    setView(inputText)

                    setPositiveButton(
                        "ok",
                        DialogInterface.OnClickListener() {
                                dialog, which->
                            d("jpk", "ok button pressed, text="+inputText.text)
                        }
                    )
                    setNegativeButton(
                        "cancel",
                        DialogInterface.OnClickListener() {
                                dialog, which->
                            d("jpk", "cancel button pressed, text="+inputText.text)
                        }
                    )
                    dialog.show()
                }

            }
        }
    }
}


