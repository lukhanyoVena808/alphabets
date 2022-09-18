@file:Suppress("DEPRECATION")

package com.example.my2002s


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    private var strIntent: String? = null
    private var buttonText: String? = ""
    private var theIntent: Intent? = null


    private lateinit var myBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        val alphabets = arrayListOf(
            R.id.buttonA, R.id.buttonB, R.id.buttonC,
            R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI,
            R.id.buttonJ, R.id.buttonK, R.id.buttonL,
            R.id.buttonM, R.id.buttonN, R.id.buttonO,
            R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS,
            R.id.buttonT, R.id.buttonU, R.id.buttonV, R.id.buttonW,
            R.id.buttonX, R.id.buttonY, R.id.buttonZ
        )
        myBar = findViewById(R.id.progressBar1)

        for (alpha in alphabets) {
            val btn = findViewById<Button>(alpha)
            btn.setOnClickListener {
                buttonText = btn.text.toString()
                theIntent = Intent(this, MainActivity2::class.java)
                strIntent = theIntent!!.toUri(0)
                val delayed = DelayTask()
                delayed.execute()

            }
        }


        if ("$buttonText" != "null" && "$buttonText" != "") {
            Toast.makeText(this@MainActivity, "Loading Previous Image", Toast.LENGTH_SHORT).show()
            SystemClock.sleep(25)
            val newIntent = Intent(this, MainActivity2::class.java)
            newIntent.putExtra(getString(R.string.intent1), buttonText)
            buttonText = "null"
            saveData()
            startActivity(newIntent)

        }
        else {
            opening()

        }

    }

    private fun opening(){
        Toast.makeText(this@MainActivity, getString(R.string.opening), Toast.LENGTH_SHORT).show()

    }

    @SuppressLint("StaticFieldLeak")
    open inner class DelayTask : AsyncTask<Void, Any, Any>() {

        var count = 0

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            myBar.visibility = View.VISIBLE
        }


        @Deprecated("Deprecated in Java")
        @Suppress("DEPRECATION")
        override fun doInBackground(vararg params: Void?) {
            while (count < 1) {
                SystemClock.sleep(500)
                count++
                publishProgress(count * 20)
            }
            val newIntent = Intent.getIntent(strIntent)
            newIntent!!.putExtra(getString(R.string.intent1), buttonText)
            startActivity(newIntent)

        }

        @Deprecated("Deprecated in Java")
        override fun onProgressUpdate(vararg values: Any?) {
            myBar.progress = values[0] as Int
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Any?) {
            myBar.visibility = View.GONE
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun saveData() {
        val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString("b8", "$buttonText")
        }.apply()

    }


    private fun loadData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("s", Context.MODE_PRIVATE)
        buttonText = sharedPreferences.getString("b8",null)


    }




}