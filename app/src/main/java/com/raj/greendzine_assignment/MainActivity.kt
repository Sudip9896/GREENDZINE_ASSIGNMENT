package com.raj.greendzine_assignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.raj.greendzine_assignment.fragment.User_info

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Thread.sleep(2000)
        setContentView(R.layout.activity_main)
        openUserInfo()
    }


    fun openUserInfo() {
        val Fragment = User_info()
        val Transaction = supportFragmentManager.beginTransaction()
        Transaction.replace(R.id.Frame_layout, Fragment)
        // Transaction.addToBackStack("User_info_Fragment")
        Transaction.commit()
    }
}
