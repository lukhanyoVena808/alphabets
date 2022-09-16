@file:Suppress("DEPRECATION")

package com.example.my2002s
import android.annotation.SuppressLint
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


@Suppress("DEPRECATION")
class MainActivity2 : AppCompatActivity(){


    private var nextImg : String? = null
    private var slide ="@string/empty"
    private var currentPosition: Int = 0   //local variable
    private lateinit var imgViewNew: ImageView
    private lateinit var theBar: ProgressBar

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        slide = getString(R.string.slider)
        val nIntent: String = getString(R.string.intent1)
        theBar = findViewById(R.id.bar2)
        val actionBar = supportActionBar
        val alpha = intent.getStringExtra(nIntent)
        imgViewNew = findViewById(R.id.imageView1)

        actionBar!!.setDefaultDisplayHomeAsUpEnabled(true)  //activate back button
        displayOnThread(alpha,imgViewNew) //display image using thread

        currentPosition = (alpha?.substring(slide.length, alpha.length))!!.toInt()

        previous(imgViewNew)  //displays previous image
        next(imgViewNew)  //next image
        last() //last image
        first() //first image
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
            when (item.itemId){
                android.R.id.home -> {
                    finish()
                    return true
                }
            }

        return super.onContextItemSelected(item)
    }

    /*
    * Get Id on String
    * */
    @SuppressLint("DiscouragedApi")
    private fun getImage(imageName: String?): Int {
        val draw = getString(R.string.draw)
//        return resources.getIdentifier(imageName, draw, packageName)
        return resources.getIdentifier(imageName, draw,packageName)
    }

//
    // Get previous letter
    private fun previous(imgView: ImageView){
        val btnPrevious = findViewById<Button>(R.id.button_p)
        btnPrevious.setOnClickListener{

            if (currentPosition !=1){
                currentPosition--
                nextImg = slide+"$currentPosition"
                displayOnThread(nextImg, imgView)
            }
            else{
                currentPosition=26
                val logic = Logic()
                logic.execute()

            }

        }
    }

    // * Get next letter
    private fun next(imgView: ImageView){
        //get next letter
        val btnNext = findViewById<Button>(R.id.button_n)
        btnNext.setOnClickListener{
            if (currentPosition !=26){
                currentPosition++
                nextImg = slide+"$currentPosition"
                displayOnThread(nextImg, imgView)
            }
            else{
                currentPosition=1
                val logic = Logic()
                logic.execute()
            }

        }
    }

    //get last letter
    private fun last() {
        val btnNext = findViewById<Button>(R.id.button_last)
        btnNext.setOnClickListener{
            if (currentPosition ==26){
                    onLastPage()
            }
            else{
                currentPosition=26
                val logic = Logic()
                logic.execute()
            }
        }
    }

    //get first letter
    private fun first(){
        val btnNext = findViewById<Button>(R.id.button_first)
        btnNext.setOnClickListener{
            if (currentPosition ==1){
                onFirstPage()
            }
            else{
                currentPosition=1
                val logic = Logic()
                logic.execute()
            }
        }

    }

    private fun onLastPage(){
        Toast.makeText(this@MainActivity2, getString(R.string.lastPhrase), Toast.LENGTH_SHORT).show()
    }

    private fun  onFirstPage(){
        Toast.makeText(this@MainActivity2, getString(R.string.firstPhrase), Toast.LENGTH_SHORT).show()
    }

    //display image using Runnable thread
    private fun displayOnThread(string: String?, imgView: ImageView) {
        Thread {
            this@MainActivity2.runOnUiThread {
                imgView.setImageResource(getImage(string))
            }

        }.start()
    }


    @SuppressLint("StaticFieldLeak")
    inner class Logic : AsyncTask<Void, Any, Any>() {

        var count = 0

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            theBar.visibility = View.VISIBLE
        }


        @Deprecated("Deprecated in Java")
        @Suppress("DEPRECATION")
        override fun doInBackground(vararg params: Void?) {
            while (count < 2) {
                SystemClock.sleep(300)
                count++
                publishProgress(count * 20)
            }
            nextImg = slide+"$currentPosition"
            imgViewNew.setImageResource(getImage(nextImg))

        }

        @Deprecated("Deprecated in Java")
        override fun onProgressUpdate(vararg values: Any?) {
            theBar.progress = values[0] as Int
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Any?) {
            theBar.visibility = View.GONE
        }
    }
}



