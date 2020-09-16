# Android-Location-WeatherApp-MVVM
An android app coded in Kotlin that uses the Model-View-ViewModel (MVVM) design pattern and other modern Android Jetpack components, Material Design and FusedLocation API for displaying the current location and it's weather details from the OpenWeatherMap API.

### Overview
The app mainly has two screens. The first one requests for access to the user's location, and has a button to get the current weather.

<img src="https://github.com/Saad29/Android-Location-WeatherApp-MVVM/blob/master/ReadMeImages/LocationScreen.jpg" width="250" height="444" />

On pressing the button, the weather information is obtained from the site [OpenWeather](https://openweathermap.org/) and some of them are displayed like this. 

<img src="https://github.com/Saad29/Android-Location-WeatherApp-MVVM/blob/master/ReadMeImages/WeatherScreen.jpg" width="250" height="444" />

Some significant features of Android development which are used in this project are described below.

### Model-View-ViewModel (MVVM) 
This is a modern architectural design pattern which uses many of the modern [architecture components](https://developer.android.com/topic/libraries/architecture) recommended officially for android app development. MVVM follows the seperation of concern principle
where view or presentation layer (the Activities along with their layouts) only deal with interaction and the presenation of the data, while the data they display are managed by the ViewModel classes which in turn communicate with the Model classes which actually act as the placeholder for the data. 
In our case, the models are used to hold the data fetched by the FusedLocation API and the OpenWeatherMap API. The ViewModels expose the data via LiveData which the views can consume. The views, which are the LifeCycle owners observe the LiveData objects via 'Observers', and update accordingly.

Both the Location and the Weather view have their own ViewModels and Models. The WeatherViewModel uses a ViewModelFactory to be initialised. Factory method is a creational design pattern.  The directory is structured so that all relevant types of classes are grouped togehter.
So the main components used for implementing MVVM are ViewModel, LiveData, LifeCycle, DataBinding and ViewBinding. These are all part of [Android Jetpack libraries](https://developer.android.com/jetpack#foundation-components)

### Retrofit 2 and Moshi

[Retrofit 2](https://square.github.io/retrofit/) is used to make network calls to the OpenWeatherMap API and Moshi is used to parse the JSON response obtained from the API call. Both of these are highly recommended third party libraries to be used in networking and managing network calls.

### Coroutines

For the asynchronous network calls, instead of callback functions, [Kotlin coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) are used. 

### FusedLocation API

For obtaining the current location coordinates of the user, [fused location provider](https://developer.android.com/training/location/request-updates) is utilised. 

### Material Design 

Modern material design is used to design the app, which is also the [recommended](https://developer.android.com/guide/topics/ui/look-and-feel) way by Google for the look and feel of the UI.

### Build Instructions

For successfully building this project, you will need to do two things.

1. Obtain an API key upon registration at the [OpenWeatherMap API](http://openweathermap.org) site, as it will be used to obtain the weather details.

2. Setting this API key in the [WeatherViewModel](https://github.com/Saad29/Android-Location-WeatherApp-MVVM/blob/master/app/src/main/java/com/example/weatherapp1/viewModels/WeatherViewModel.kt) file.

### Possible future improvements

1. Using Dependency Injection instead of ViewModelTaskFactory to instantiate the ViewModel.
2. Using two fragments instead of activities and providing a Navigation graph for navigating between them and using Safe Args to pass data.








