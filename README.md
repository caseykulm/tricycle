## Step 2: Main Function and Effect Functions

In our ```MainActivity``` we have now made two functions to 
clearly to give structure to our application with logic and 
effects.
 
The code was split into 3 functions.

### Logic Function

We break out the same bit of code for the logic into its own 
function named main.

```kotlin
fun main(): Observable<String> {
  return Observable.interval(1, TimeUnit.SECONDS)
      .scan({ secondsElapsed: Long, _ -> secondsElapsed+1 })
      .map { "Seconds elapsed: " + it }
}
```

### Effect Functions (i.e. Drivers)

We will introduce the term Drivers to describe functions that 
take a stream, and perform some side effect given the state of 
your app.

We break out the same bit of code for the effects of updating the 
TextView with our stream of Strings, or print an error to console.

```kotlin
fun viewDriver(view: View, viewStream: Observable<String>) {
  viewStream
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
      { next -> view.findViewById<TextView>(R.id.textView).text = next },
      { throwable -> throwable.printStackTrace() })
}
```

We would like to support more than one effect though so we will 
also introduce an additional driver with the effect of 
printing the state of the stream of Strings to console.

```kotlin
fun logDriver(messageStream: Observable<String>) {
  messageStream.subscribe({Log.d("LogDriver", it)})
}
```

### Wiring It Up

```kotlin
val sink = main().share() // convert to ConnectableObservable
viewDriver(view, sink)
logDriver(sink)
```

We call the stream of Strings sink because data is starting in 
main, and not coming back out.

Also ```.share()``` is so that we can use the same stream with 
many subscribers which is not enabled by default. To learn more 
I would recommend reading up on the [RxJava Wiki](https://github.com/ReactiveX/RxJava/wiki/Connectable-Observable-Operators) 
about them, and also [Kaushik Gopal's Talk](https://speakerdeck.com/kaushikgopal/rx-by-example-volume-3-the-multicast-edition) 
about them.
