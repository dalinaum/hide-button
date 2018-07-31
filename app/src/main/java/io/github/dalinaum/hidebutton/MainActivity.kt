package io.github.dalinaum.hidebutton

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val start: Button by lazy {
        // SDK 22 T.T
        findViewById(R.id.start) as Button
    }

    val stop: Button by lazy {
        findViewById(R.id.stop) as Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.setOnClickListener {
            startService(Intent(application, HeadService::class.java))
        }

        stop.setOnClickListener {
            stopService(Intent(application, HeadService::class.java))
        }
    }
}
