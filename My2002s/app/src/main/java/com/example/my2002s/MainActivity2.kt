package com.example.my2002s

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


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
        Toast.makeText(this@MainActivity2, alpha, Toast.LENGTH_SHORT).show()
        currentPosition = (alpha?.substring(slide.length, alpha.length))!!.toInt()

        previous(imgViewNew)
        next(imgViewNew)
        last(imgViewNew)
        first(imgViewNew)
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
    private fun getImage(imageName: String?): Int {
        val draw = getString(R.string.draw)
        return resources.getIdentifier(imageName, draw, packageName)
    }

//
    // Get previous letter
    private fun previous(imgView: ImageView){
        val btnPrevious = findViewById<Button>(R.id.button_p)
        btnPrevious.setOnClickListener{

            if (currentPosition !=1){
                currentPosition--
            }
            else{
                theDelay()
                currentPosition=26
            }

            nextImg = slide+"$currentPosition"
            displayOnThread(nextImg, imgView)
        }
    }
//
    // * Get next letter
    private fun next(imgView: ImageView){
        //get next letter
        val btnNext = findViewById<Button>(R.id.button_n)
        btnNext.setOnClickListener{
            if (currentPosition !=26){
                currentPosition++
            }
            else{
                theDelay()
                currentPosition=1
            }
            nextImg = slide+"$currentPosition"
            displayOnThread(nextImg, imgView)
        }
    }

    //get last letter
    private fun last(imgView: ImageView){
        val btnNext = findViewById<Button>(R.id.button_last)
        btnNext.setOnClickListener{
            if (currentPosition ==26){
                    onLastPage()
            }
            else{
                currentPosition=26
                nextImg = slide+"$currentPosition"
                displayOnThread(nextImg, imgView)
            }
        }
    }

    //get first letter
    private fun first(imgView: ImageView){
        val btnNext = findViewById<Button>(R.id.button_first)
        btnNext.setOnClickListener{
            if (currentPosition ==1){
                onFirstPage()
            }
            else{
                currentPosition=1

                nextImg = slide+"$currentPosition"
                displayOnThread(nextImg, imgView)
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

    private fun showProgress() {
        theBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        theBar.visibility = View.GONE
    }

    private fun theDelay(){
        Thread {
            this@MainActivity2.runOnUiThread {
                showProgress()
            }

            try {
                var i = 0
                while (i < 85000000) {
                    i++
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            this@MainActivity2.runOnUiThread{
                hideProgress()
            }
        }.start()
    }

}

