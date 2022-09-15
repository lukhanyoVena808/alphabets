package com.example.my2002s


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    private lateinit var myBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        opening()
        val alphabets = arrayListOf(
            R.id.buttonA,R.id.buttonB,R.id.buttonC,
            R.id.buttonD, R.id.buttonE,R.id.buttonF
            ,R.id.buttonG,R.id.buttonH,R.id.buttonI,
            R.id.buttonJ,R.id.buttonK, R.id.buttonL,
            R.id.buttonM,R.id.buttonN,R.id.buttonO,
            R.id.buttonP,R.id.buttonQ,R.id.buttonR,R.id.buttonS,
            R.id.buttonT, R.id.buttonU,R.id.buttonV,R.id.buttonW,
            R.id.buttonX,R.id.buttonY, R.id.buttonZ)

        myBar = findViewById(R.id.progressBar1)
        for (alpha in alphabets){
            Thread {
                val btn = findViewById<Button>(alpha)
                btn.setOnClickListener {
                    theDelay()
                    val intent = Intent(this, MainActivity2::class.java)
                    intent.putExtra(getString(R.string.intent1), btn.text.toString())
                    startActivity(intent)
                }
            }.start()
        }
    }

    private fun theDelay(){
        Thread {
            this@MainActivity.runOnUiThread {
                showProgress()
            }

            try {
        var i = 0
        while (i < 11500000) {
            i++
        }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            this@MainActivity.runOnUiThread{
                hideProgress()
            }
        }.start()
    }

    private fun opening(){
        Toast.makeText(this@MainActivity, getString(R.string.opening), Toast.LENGTH_SHORT).show()

    }

    private fun showProgress() {
        myBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        myBar.visibility = View.GONE
    }


}