## Step 3: Customizing Effects from the Main Function

In our ```MainActivity``` we now would like to add some more logic 
to our main Logic function.

### Logic Function

We may want more than one sink. To do that we define an interface 
with all of our sinks,

```kotlin
interface Sinks {
  fun view(): Observable<String>
  fun log(): Observable<Long>
}
```
 
and then return an implementation of that interface in our main 
function.

```kotlin
fun main(): Sinks {
  return object: Sinks {
    override fun view(): Observable<String> =
        Observable.interval(1, TimeUnit.SECONDS)
        .scan({ secondsElapsed: Long, _ -> secondsElapsed+1 })
        .map { "Seconds elapsed: " + it }

    override fun log(): Observable<Long> =
        Observable.interval(2, TimeUnit.SECONDS)
        .scan({ secondsElapsed: Long, _ -> secondsElapsed+1 })
  }
}
```

This routing of data is Logic, and Logic should live in the main 
function.
