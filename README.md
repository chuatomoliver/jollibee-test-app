###jollibee-test-app
This is a test application for Jollibee, built using Kotlin and Jetpack Compose. The app demonstrates user registration and authentication by integrating with a Laravel API.

##Features
User Registration: Allows new users to create an account by providing their name, email, and password.

API Integration: Uses Retrofit to handle network calls to the Laravel backend for user registration and authentication.

Input Validation: Includes client-side validation for empty fields and password length to ensure data integrity before making an API request.

##Technologies Used
Kotlin: The primary programming language for Android development.

Jetpack Compose: A modern toolkit for building native Android UI.

Retrofit: A type-safe HTTP client for Android and Java.

Coroutines: Used for asynchronous API calls to prevent blocking the main thread.

##Architecture
This application follows the principles of MVVM (Model-View-ViewModel) and Clean Architecture.

MVVM: The UI (RegistrationScreen.kt) is the View, which observes a ViewModel (not explicitly shown in the provided files but implied in a real-world app) for state changes. The ViewModel exposes data from the Model layer, which includes the data classes and the network service. This separation of concerns makes the UI more robust and easier to test.

Clean Architecture: The project is organized into distinct layers to separate the UI from the business logic and data.

Presentation Layer: Contains the UI components (RegistrationScreen.kt).

Data Layer: Handles data retrieval from network sources (network/).

##Setup
To run this project on your local machine, follow these steps:

Clone the repository:

git clone [[repository-url]](https://github.com/chuatomoliver/jollibee-test-app.git)

Open in Android Studio:
Open the cloned project in Android Studio.

Run the app:
Connect an Android device or use an emulator and click the "Run" button in Android Studio. The app requires an internet connection to connect to the registration API.

Screenshots
API Endpoints
The application uses the following API endpoint for user registration:

POST https://test-app-laravel.tmc-innovations.com/api/auth//login
POST https://test-app-laravel.tmc-innovations.com/api/auth/register

<img width="1308" height="739" alt="image" src="https://github.com/user-attachments/assets/8f08c059-4def-4dcd-9387-0ae3e252dfd3" />
<img width="1293" height="699" alt="image" src="https://github.com/user-attachments/assets/af85d9a2-0b5e-48b5-bd09-2b7274f9d6f6" />



##Project Structure
app/src/main/java/com/certicode/jolibee_test_app/: The main source code directory.

network/: Contains ApiService.kt and RetrofitClient.kt for network operations.

presentation/: Contains RegistrationScreen.kt for the UI.
