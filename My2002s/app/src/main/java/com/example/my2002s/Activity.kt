package com.example.my2002s

import android.app.Application
import android.os.Bundle


open class Activity : Application() {
    fun onCreate(savedInstanceState: Bundle) {}
    fun onStart()   {}
    fun onRestart() {}
    fun onResume()  {}
    fun onPause()   {}
    fun onStop()    {}
    fun onDestroy() {}

}