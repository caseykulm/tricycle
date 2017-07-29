package com.example.tricycle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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

    // Run Program
    val sink = main().share() // convert to ConnectableObservable
    viewDriver(view, sink)
    logDriver(sink)
  }

  // Logic
  fun main(): Observable<String> {
    return Observable.interval(1, TimeUnit.SECONDS)
        .scan({ secondsElapsed: Long, _ -> secondsElapsed+1 })
        .map { "Seconds elapsed: " + it }
  }

  // Effect
  fun viewDriver(view: View, viewStream: Observable<String>) {
    viewStream
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
        { next -> view.findViewById<TextView>(R.id.textView).text = next },
        { throwable -> throwable.printStackTrace() })
  }

  // Effect
  fun logDriver(messageStream: Observable<String>) {
    messageStream.subscribe({Log.d("LogDriver", it)})
  }
}
