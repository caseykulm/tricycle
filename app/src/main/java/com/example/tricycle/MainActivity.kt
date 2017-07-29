package com.example.tricycle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val view = findViewById<View>(R.id.app)
    val tv = findViewById<TextView>(R.id.textView)

    // Logic
    Observable.interval(1, TimeUnit.SECONDS)
        .scan({ t1: Long, t2: Long -> t1+1 })
        .map { "Seconds elapsed: " + it }
        .observeOn(AndroidSchedulers.mainThread())

        // Effects
        .subscribe(
            { next -> tv.text = next },
            { throwable -> throwable.printStackTrace() })
  }
}
