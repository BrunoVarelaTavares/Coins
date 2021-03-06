# Coins

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.3.72-blue.svg)](https://kotlinlang.org)
[![AGP](https://img.shields.io/badge/AGP-3.6.3-blue?style=flat)](https://developer.android.com/studio/releases/gradle-plugin)
[![Gradle](https://img.shields.io/badge/Gradle-5.6.4-blue?style=flat)](https://gradle.org)

Coins is a app with market information and news about cryptucurrencies use [Coingecko](https://www.coingecko.com/en/api), [Cryptocontrol](https://api-docs.cryptocontrol.io/) and [CurrencyConverterApi](https://www.currencyconverterapi.com/)   APIs.


## Project characteristics
 
* [Kotlin](https://kotlinlang.org/)
* Modern architecture (Clean Architecture, Model-View-ViewModel, Model-View-Intent, Dynamic Feature Modules)
* [Android Jetpack](https://developer.android.com/jetpack)
* A single-activity architecture ([Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started))
* Testing (Unit)
* Dependency Injection
* Material design


## Tech-stack

<img src="https://github.com/BrunoVarelaTavares/Coins/blob/main/images/coins_two.gif" width="336" align="right" hspace="20">

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
    * [Kodein](https://kodein.org/Kodein-DI/) - dependency injection
    * [Retrofit](https://square.github.io/retrofit/) - networking
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - deal with whole in-app navigation
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about database change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Room](https://developer.android.com/topic/libraries/architecture/room?gclid=CjwKCAjwiOv7BRBREiwAXHbv3E5_bHBVq2eIwii6EwVdrxmbxTNtvsefDD6k58birmVMufa9AfcXchoCa7wQAvD_BwE&gclsrc=aw.ds) - Create, store, and manage persistent data backed by a SQLite database.
  *   [Coil](https://github.com/coil-kt/coil) - image loading library with Kotlin idiomatic API
  *   [Lottie](http://airbnb.io/lottie) - animation library
  * [Stetho](http://facebook.github.io/stetho/) - application debugging tool
  * [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) - chart library 
  * [Collapsingtoolbarlayout Subtitle](https://github.com/hendraanggrian/collapsingtoolbarlayout-subtitle) 
* Architecture
    * MVVM + MVI (presentation layer)
    * Clean Architecture (at module level)
    * [Dynamic feature modules](https://developer.android.com/studio/projects/dynamic-delivery)
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation), [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args) plugin)
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit](https://junit.org/junit4/))
    * [Mockk](https://mockk.io/)
    * [Kluent](https://github.com/MarkusAmshove/Kluent)
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
