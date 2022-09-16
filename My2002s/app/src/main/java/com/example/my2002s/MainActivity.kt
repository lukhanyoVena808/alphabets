@file:Suppress("DEPRECATION")

package com.example.my2002s


import android.annotation.SuppressLint
import android.content.Intent
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
    private var buttonText: String? = null
    private var theIntent: Intent? = null
    private lateinit var myBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            opening()
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
                SystemClock.sleep(250)
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
}