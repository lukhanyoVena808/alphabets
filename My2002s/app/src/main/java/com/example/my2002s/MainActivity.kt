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

/*
* MainActivity contains the buttons to all letters including their listeners.
* When activity starts, the saved state is loaded, if empty start the app as usual
* else navigate to letter page to view the saved state
* */

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    private var strIntent: String? = null  //holds  an intent uri (address)
    private lateinit var buttonText: String   //text of button/ current Image
    private var theIntent: Intent? = null //intent sent to letters' page


    private lateinit var myBar: ProgressBar  // activity bar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonText = getString(R.string.empty)
        loadData()  //load saved state
        val alphabets = arrayListOf( //keeps id's of all buttons
            R.id.buttonA, R.id.buttonB, R.id.buttonC,
            R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI,
            R.id.buttonJ, R.id.buttonK, R.id.buttonL,
            R.id.buttonM, R.id.buttonN, R.id.buttonO,
            R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS,
            R.id.buttonT, R.id.buttonU, R.id.buttonV, R.id.buttonW,
            R.id.buttonX, R.id.buttonY, R.id.buttonZ
        )
        myBar = findViewById(R.id.progressBar1)  //initialize activity bar

        //for each button, create a listener, if the button is clicked, its text which corresponds
        //to the image name is sent to the letter page to be viewed
        for (alpha in alphabets) {
            val btn = findViewById<Button>(alpha)
            btn.setOnClickListener {
                buttonText = btn.text.toString()  //gets the text of the button (image name)
                theIntent = Intent(this, MainActivity2::class.java)
                strIntent = theIntent!!.toUri(0)
                val delayed = DelayTask()  //to activate activity bar and send intent using threads
                delayed.execute()

            }
        }

        //After the loading data, check if there is a current state saved and display the image
        // else start the app as normal
        // *** null is not a string and cannot be saved to strings.xml
        if (buttonText != "null" && buttonText != getString(R.string.empty)) {
            Toast.makeText(this@MainActivity, getString(R.string.loading), Toast.LENGTH_SHORT).show()
            SystemClock.sleep(25)  //creates a delay before sending current state to letter page
            val newIntent = Intent(this, MainActivity2::class.java)
            newIntent.putExtra(getString(R.string.intent1), buttonText)

            //clears the saved state, *** null is not a string and cannot be saved to strings.xml
            buttonText = "null"
            saveData()  //upDates the save state to null
            startActivity(newIntent) //send current state

        }
        else {
            opening()

        }

    }

    //message to user, when starting the app afresh
    private fun opening(){
        Toast.makeText(this@MainActivity, getString(R.string.opening), Toast.LENGTH_SHORT).show()

    }

    @SuppressLint("StaticFieldLeak")
    //inner class to create a delay before sending the intent to letter page using threading
    open inner class DelayTask : AsyncTask<Void, Any, Any>() {

        var count = 0

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            myBar.visibility = View.VISIBLE   //makes the activity bar visible
        }


        @Deprecated("Deprecated in Java")
        @Suppress("DEPRECATION")
        // creates a delay
        override fun doInBackground(vararg params: Void?) {
            while (count < 1) {
                SystemClock.sleep(500)
                count++
                publishProgress(count * 20)
            }

            //send intent to letter page
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
            myBar.visibility = View.GONE   //removes activity bar after execution
        }
    }

    @SuppressLint("CommitPrefEdits")
    //saves the current state of the app, by saving the buttonText which contains the current image
    fun saveData() {
        val sharedPreferences : SharedPreferences = getSharedPreferences(getString(R.string.password), Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString(getString(R.string.key), buttonText)
        }.apply()

    }


    //loads the current state of app by retrieving the last stored image
    private fun loadData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(getString(R.string.password), Context.MODE_PRIVATE)
        buttonText = sharedPreferences.getString(getString(R.string.key),null).toString()

    }




}