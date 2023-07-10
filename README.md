# CalculatorCurrencyConversion

This is a simple numeric calculator Android app that also supports currency conversion. The app is written in Kotlin and utilizes various modern libraries and frameworks, including Jetpack Compose, Compose Navigation, Flows and Coroutines, kotlinx.serialization, Koin, Ktor Client, MVI architecture, Material Design theming, and unit tests with Mockito, JUnit, 
and Turbine.

## Features

- Basic arithmetic operations: addition, subtraction, multiplication, and division.
- Currency conversion using real-time exchange rates with exchangerate-api.com .
- Beautiful and intuitive user interface built with Jetpack Compose.
- Navigation between different screens using Compose Navigation.
- Asynchronous programming with Flows and Coroutines.
- JSON serialization and deserialization using kotlinx.serialization.
- Dependency injection with Koin.
- Network communication with Ktor Client.
- Model-View-Intent (MVI) architecture for better separation of concerns.
- Material Design theming with dynamic color support and Material 3 components for a modern and cohesive look.
- Comprehensive unit tests with JUnit, Mockito, and Turbine.

## Installation

- Clone the repository:
````bash
git clone https://github.com/athanasiosgkouvas/CalculatorCurrencyConversion.git
````
- Open the project in Android Studio.
- Add a valid API key in local.properties as: 
`````
API_KEY = xxxxxxxxxxxxxxxxxxx
``````

- Build and run the app on an emulator or a physical device.
