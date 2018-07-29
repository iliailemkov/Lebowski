Lebowski


*** 

### Questions
* `@JvmStatic` when creating `newInstance` of fragment

***

### Used libraries

* [Data Binding](https://developer.android.com/topic/libraries/data-binding/)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [LeakCanary](https://github.com/square/leakcanary)

***

### Used materials / Thanks

* #### DataBinding
    * concat two strings 
        * [concate it with grave accent (`) ](https://stackoverflow.com/a/40039962/6696410) 
        ```
        android:text="@{`Hello ` + user.firstName}"/>
        ```        
        * [use string formatting](https://stackoverflow.com/a/38984088/6696410)  
        ```
        android:text= "@{@string/Generic_Text(Profile.name)}"
        ```
        string should be like this:
        ```
        <string name="Generic_Text">My Name is %s</string>
        ```
    * bind viewmodel to fragment using [`AutoClearedValue`](https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/util/AutoClearedValue.kt)
    

* #### Navigation
    * navigation to fragment
    ```kotlin
    private fun navigateToFragment(fragmentInstance: () -> Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragmentInstance())
                .addToBackStack(null)
                .commit()
    }
    ```

* #### Gradle
    * [Manage Android dependencies versions using gradle extra properties](https://segunfamisa.com/posts/android-gradle-extra-properties) (can be also done using [`config.gradle` file](https://gist.github.com/truongngoclinh/af143066468ec5456cea6867350331fe#file-config-gradle))


* #### Kotlin
    * [Anonymous Classes in enum](https://kotlinlang.org/docs/reference/enum-classes.html#anonymous-classes)