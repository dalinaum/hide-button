package io.github.dalinaum.hidebutton

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView

class HeadService: Service() {
    val windowManager: WindowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    lateinit var imageView: ImageView

    val params: WindowManager.LayoutParams by lazy {
        val layoutParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)

        layoutParams.gravity = Gravity.TOP or Gravity.LEFT
        layoutParams.x = 0
        layoutParams.y = 100
        layoutParams.width = 100
        layoutParams.height = 100
        layoutParams;
    }

    private var initialX: Int = 0
    private var initialY: Int = 0
    private var initialTouchX: Float = 0f
    private var initialTouchY: Float = 0f

    override fun onCreate() {
        super.onCreate()
        imageView = ImageView(this)
        imageView.setImageResource(R.drawable.ingress)
        imageView.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = motionEvent.rawX
                    initialTouchY = motionEvent.rawY
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    params.x = initialX + (motionEvent.rawX - initialTouchX).toInt()
                    params.y = initialY + (motionEvent.rawY - initialTouchY).toInt()
                    windowManager.updateViewLayout(imageView, params)
                    true
                }

                MotionEvent.ACTION_UP -> true

                else -> false
            }
        }
        val size = (100 * Resources.getSystem().getDisplayMetrics().density).toInt()
        params.width = size
        params.height = size
        windowManager.addView(imageView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        imageView?.let {
            windowManager.removeViewImmediate(it)
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null
}