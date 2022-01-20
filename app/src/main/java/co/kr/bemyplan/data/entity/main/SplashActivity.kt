package co.kr.bemyplan.data.entity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import co.kr.bemyplan.R
import co.kr.bemyplan.ui.login.LoginActivity
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, LoginActivity::class.java)

        thread(start=true) {
            for (i in 1..5) {
                Thread.sleep(1000)
            }
            isReady = true
        }

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (isReady) {
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        false
                    }
                }
            }
        )

        startActivity(intent)
        finish()
        setContentView(R.layout.activity_splash)
    }
}