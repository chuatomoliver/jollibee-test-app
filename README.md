# jollibee-test-app

jollibee-test-app
This is a test application for Jollibee, built using Kotlin and Jetpack Compose. The app demonstrates user registration and authentication by integrating with a Laravel API.

Features
User Registration: Allows new users to create an account by providing their name, email, and password.

API Integration: Uses Retrofit to handle network calls to the Laravel backend for user registration and authentication.

Input Validation: Includes client-side validation for empty fields and password length to ensure data integrity before making an API request.

Technologies Used
Kotlin: The primary programming language for Android development.

Jetpack Compose: A modern toolkit for building native Android UI.

Retrofit: A type-safe HTTP client for Android and Java.

Coroutines: Used for asynchronous API calls to prevent blocking the main thread.

Setup
To run this project on your local machine, follow these steps:

Clone the repository:

git clone 

Open in Android Studio:
Open the cloned project in Android Studio.

Run the app:
Connect an Android device or use an emulator and click the "Run" button in Android Studio. The app requires an internet connection to connect to the registration API.

Screenshots
API Endpoints
The application uses the following API endpoint for user registration:

POST https://test-app-laravel.tmc-innovations.com/api/auth/register

Project Structure
app/src/main/java/com/certicode/jolibee_test_app/: The main source code directory.

network/: Contains ApiService.kt and RetrofitClient.kt for network operations.

presentation/: Contains RegistrationScreen.kt for the UI.
