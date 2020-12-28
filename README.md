# Archaeological Fieldwork Android Kotlin App

Android Build              |  App
:-------------------------:|:-------------------------:
![Android Build](https://github.com/Basler182/arch-fw-android/workflows/Android%20OPS/badge.svg)  |  ![](https://github.com/Basler182/arch-fw-android/blob/feat-screens/screens/screen_app_icon.jpg)


## Build

You can simply use Android Studio to build, debug and test the project.


## Architecture

The code is structured based on MVP architeccture and clean architecture with the goal of easier testability and maintainablity. 

### Core technologies and architectures
- [Kotlin](https://github.com/JetBrains/kotlin)

## Used libraries
- [Jetpack Navigation](https://developer.android.com/jetpack/)
- [Anko Commons](https://github.com/Kotlin/anko)
- [Firebase](https://github.com/firebase/)

## Continous integration/delivery

[Github actions](https://github.com/features/actions) are used as CI/CD. 
The Pipline gets triggered when a new push comes into the develop-branch. The pipline than will run a unit test and build it. It will also create a Lint-File and add it to the repo. Aftwards it will also generate a APK-File and add it as well. After both actions are finished a pull request to the main-branch will be generated. 

## Screens

Splash Screen              |  Home Screen
:-------------------------:|:-------------------------:
![](https://github.com/Basler182/arch-fw-android/blob/feat-screens/screens/screen_splash_page.jpg)  |  ![](https://github.com/Basler182/arch-fw-android/blob/feat-screens/screens/screen_home_page.jpg)
