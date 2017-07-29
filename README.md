## Step 1: Separate logic from effects in Cycle

In our ```MainActivity``` we can see an example of a basic timer 
app that increases a counter.
 
The goal is to point out the difference between logic and effects.

The entire program boils down to,

```
// Logic
Observable.interval(1, TimeUnit.SECONDS)
  .scan({ t1: Long, t2: Long -> t1+1 })
  .map { "Seconds elapsed: " + it }
  .observeOn(AndroidSchedulers.mainThread())
  
  // Effects
  .subscribe(
      { next -> tv.text = next },
      { throwable -> throwable.printStackTrace() })
```

Logic are ideas such as,
 
* ```Observable.interval(1, TimeUnit.SECONDS)``` is the idea of seconds
* ```scan({ t1: Long, t2: Long -> t1+1 })``` is the idea of accumalating numbers over time
* ```map { "Seconds elapsed: " + it }``` is the idea of mapping a number to a stream

Effects mean anything that changes the external world such as, 

* ```tv.text = next``` is affecting the state of the TextView
* ```throwable.printStackTrace()``` is affecting the state of the console

The guiding principle in Cycle is to seperate Logic and Effects by 
having Effects in framework and Logic in your app. 
 
This should make it easier to test, and easier to swap out effects. 