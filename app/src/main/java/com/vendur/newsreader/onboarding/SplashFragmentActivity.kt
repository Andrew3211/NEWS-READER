package com.vendur.newsreader.onboarding

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vendur.newsreader.R


class SplashFragmentActivity: AppCompatActivity() {

    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash_activity_main)

        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            // Toast.makeText(applicationContext, "Нажмите ещё раз, чтобы выйти.", Toast.LENGTH_SHORT).show()
            val a = Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            startActivity(a)
        }
        backPressedTime = System.currentTimeMillis()
    }

}