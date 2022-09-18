@file:Suppress("DEPRECATION")

package com.example.my2002s
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/*
* MainActivity2 contains 5 buttons (Overview, Previous, Next, Last and First) including their listeners.
* An intent is received from the MainActivity and is display. A user can then get the previous letter,
* next letter, last or fast letter of the alphabet.
* The Overview(Top Left of screen) allows the user the navigate back to MainActivity
* the current state if the is only saved when the user exits the app without navigating back
* to the home app...in other words if the app is destroyed while an image is bring viewed
*
*  --- Added:  android:parentActivityName=".MainActivity" in under the MainActivity2 activity in
*               the AndroidManifest.xml file to enable the actionbar (Overview Button) to navigate
*               to the Overview page when pressed.
* */

@Suppress("DEPRECATION")
class MainActivity2 : AppCompatActivity(){


    private var nextImg : String? = null  //contains the the name of image to be viewed
    private lateinit var slide: String  //string slicing variable
    private var currentPosition: Int = 0   //local variable //retains current position of image
    private lateinit var imgViewNew: ImageView //the imageView to display teh images
    private lateinit var theBar: ProgressBar  // the activity bar
    private var DestroyApp = true  //boolean to check if is destroyed

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        theBar = findViewById(R.id.bar2)  //initialize activity bar
        val actionBar = supportActionBar //initialize Overview button

        actionBar!!.setDefaultDisplayHomeAsUpEnabled(true)  //activates overview to return to
        val nIntent: String = getString(R.string.intent1)  //has name of intent to be retrieved
        val alpha = intent.getStringExtra(nIntent)  //get the value of the intent
        imgViewNew = findViewById(R.id.imageView1)  //initialize the imageView
        slide = getString(R.string.slider)  //initialize string slicer variable
        nextImg = alpha  //update current image name with the intent value
        displayOnThread(alpha, imgViewNew) //display image from intent

        //get current position of image
        currentPosition = (alpha?.substring(slide.length, alpha.length))!!.toInt()

        //button listener for previous button
        previous(imgViewNew)

        //button listener for next button
        next(imgViewNew)

        //button listener for 'last' button
        last()

        //button listener for 'first' button
        first()
    }

    // Enabled to actionbar (Overview button to navigate to the home page
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }


    @SuppressLint("DiscouragedApi")
    //Returns the id of the image given its name
    private fun getImage(imageName: String?): Int {
        val draw = getString(R.string.draw)
        return resources.getIdentifier(imageName, draw,packageName)
    }

    //When the previous button is pressed, the function checks if the current image is not
    //the first image if not then decrement, else start form the last letter
    private fun previous(imgView: ImageView){
        val btnPrevious = findViewById<Button>(R.id.button_p)
        btnPrevious.setOnClickListener{

            if (currentPosition !=1){
                currentPosition--
                nextImg = slide+"$currentPosition"  //update name of image being viewed
                displayOnThread(nextImg, imgView)  //display image
            }
            else{
                currentPosition=26
                val logic = Logic()   //show activity bar while going to last image
                logic.execute()

            }
        }
    }

    //When the next button is pressed, the function checks if the current image is not
    //the last image if not then increment, else start form the first letter
    private fun next(imgView: ImageView){

        val btnNext = findViewById<Button>(R.id.button_n)
        btnNext.setOnClickListener{
            if (currentPosition !=26){
                currentPosition++
                nextImg = slide+"$currentPosition" //update name of image being viewed
                displayOnThread(nextImg, imgView) //display image
            }
            else{
                currentPosition=1
                val logic = Logic() //activate activity while loading to last image
                logic.execute()
            }
        }
    }

    //When the 'last' button is pressed, the function checks if the current image is not
    //the last image if not navigate and display image, else send message to user
    private fun last() {
        val btnNext = findViewById<Button>(R.id.button_last)
        btnNext.setOnClickListener{
            if (currentPosition ==26){
                onLastPage()  //message to tell the user this the last letter
            }
            else{
                currentPosition=26
                val logic = Logic() //loads activity and displays image
                logic.execute()
            }
        }
    }

    //When the 'first' button is pressed, the function checks if the current image is not
    //the first image if not navigate to and display image, else send message to user
    private fun first(){
        val btnNext = findViewById<Button>(R.id.button_first)
        btnNext.setOnClickListener{
            if (currentPosition ==1){
                onFirstPage() //message to tell the user this the last letter
            }
            else{
                currentPosition=1
                val logic = Logic() //loads activity and displays image
                logic.execute()
            }
        }
    }

    //Message alert sent to user when already on last page
    private fun onLastPage(){
        Toast.makeText(this@MainActivity2, getString(R.string.lastPhrase), Toast.LENGTH_SHORT).show()
    }

    //Message alert sent to user when already on first page
    private fun  onFirstPage(){
        Toast.makeText(this@MainActivity2, getString(R.string.firstPhrase), Toast.LENGTH_SHORT).show()
    }

    //display image using Runnable thread and scaling image down using BitmapFactory
    private fun displayOnThread(string: String?, imgView: ImageView) {
        Thread {
            this@MainActivity2.runOnUiThread {
                val bm = BitmapFactory.decodeResource(resources, getImage(string))
                imgView.setImageBitmap(Bitmap.createScaledBitmap(bm, 500, 500, false))
            }
        }.start()
    }


    @SuppressLint("StaticFieldLeak")
    //Thread activates the activity bar and creates a delay before displaying image
    inner class Logic : AsyncTask<Void, Any, Any>() {
        var count = 0
        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            theBar.visibility = View.VISIBLE  //enables action bar
        }

        @Deprecated("Deprecated in Java")
        @Suppress("DEPRECATION")
        //creates delay
        override fun doInBackground(vararg params: Void?) {
            while (count < 2) {
                SystemClock.sleep(300)
                count++
                publishProgress(count * 20)
            }
            nextImg = slide+"$currentPosition" //updates name of image viewed

            //display scaled image using
            val bm = BitmapFactory.decodeResource(resources, getImage(nextImg))
            imgViewNew.setImageBitmap(Bitmap.createScaledBitmap(bm, 500, 500, false))
        }

        @Deprecated("Deprecated in Java")
        //increments speed of activity bar
        override fun onProgressUpdate(vararg values: Any?) {
            theBar.progress = values[0] as Int
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Any?) {
            theBar.visibility = View.GONE  //remove activity bar
        }
    }

    //save current state only when the app is about to be destroyed (closing app)
    override fun onPause(){
        if(DestroyApp){
            saveData()  //save name of current image
        }
        super.onPause()

    }

    //saves the current state by saving teh name of the current image, using SharedPreferences
    private fun saveData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(getString(R.string.password),Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString(getString(R.string.key),nextImg)
        }.apply()
    }


    //Listener for actionbar (Overview button)
    //when pressed, make sure current state is not saved by setting the 'DestroyApp
    // boolean to false
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Display Message to user, that the are returning to the Overview Page
        Toast.makeText(this@MainActivity2, getString(R.string.overview), Toast.LENGTH_SHORT).show()
        DestroyApp = false  //DestroyApp to False, to prevent saving
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}



