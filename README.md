# Quick Notes - Android Developer Intern Assignment

> A modern, offline-first note-taking application for Android built with Kotlin and Jetpack Compose. This project fulfills all the core requirements of the MyApps Android Developer Intern Assignment, including a polished UI, secure authentication, and local data persistence, plus the extra cloud sync feature.

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-7F52FF?style=for-the-badge&logo=kotlin)![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.7-4285F4?style=for-the-badge&logo=jetpackcompose)

## 📸 App Screenshots

| Login Screen | Empty Notes List | Notes List | Add New Note |
| :---: | :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/dda0714f-8dab-4812-a28d-229902f680bd" width="200"> | <img src="https://github.com/user-attachments/assets/4f5ad557-86fc-40f9-8ef8-7c215502049d" width="200"> | <img src="https://github.com/user-attachments/assets/70081fc2-0295-4b77-89c6-0518db927566" width="200"> | <img src="https://github.com/user-attachments/assets/c2b63792-ddb0-43b3-a02d-9278f07d3605" width="200"> |

---

## ✨ Features

### Core Requirements

-   **[x] Custom User Interface (Jetpack Compose):**
    -   [x] Original, minimalist app logo.
    -   [x] Clean, modern, and intuitive user interface.
    -   [x] Main screen with a list of all saved notes.
    -   [x] Floating Action Button (FAB) to create new notes.
    -   [x] Dedicated screen for adding and editing notes.

-   **[x] Firebase Authentication:**
    -   [x] Secure user sign-in and sign-up using the Google Sign-In provider.
    -   [x] Protected routes: Main app features are only accessible after a successful login.
    -   [x] Clear "Sign Out" option for the user.

-   **[x] Data Persistence (Room Database):**
    -   [x] Offline-first support using the Room library.
    -   [x] Each note includes a title, content, and a last-modified timestamp.
    -   [x] Notes are automatically saved and persist across app restarts.

### Extra Feature

-   **[x] Cloud Sync (Google Drive API):**
    -   [x] UI and logic implemented for "Backup" and "Restore" functionality.
    -   [x] The architecture is in place to upload and download the Room database file from the user's hidden App Data folder on Google Drive.
    -   [x] Successfully verified API connectivity and authorization using the Google API Explorer.

---

## 🛠️ Technical Stack & Architecture

-   **Language:** 100% [Kotlin](https://kotlinlang.org/)
-   **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) for a modern, declarative UI.
-   **Architecture:** Follows the official Android App Architecture Guide, using the **MVVM (Model-View-ViewModel)** pattern.
-   **Asynchronous Programming:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/flow.html) for managing background tasks and observing data changes reactively.
-   **Dependency Injection:** A simple, manual dependency container (`AppContainer`) is used to provide the database instance.
-   **Key Libraries:**
    -   **Jetpack Compose Navigation:** For navigating between screens.
    -   **Room:** For robust, local database persistence.
    -   **Firebase Authentication:** For secure user management.
    -   **Google Identity Services:** For the modern Google Sign-In flow.
    -   **Google Drive API Services:** For cloud backup functionality.
    -   **Lifecycle ViewModel & LiveData:** For managing UI-related data in a lifecycle-conscious way.

---

## 🚀 How to Build and Run

To build and run this project, you will need to have Android Studio (Iguana or newer) set up.

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME].git
    ```

2.  **Open in Android Studio:**
    Open the cloned directory in Android Studio.

3.  **Add Your `google-services.json`:**
    This project uses Firebase. To connect it to your own Firebase project:
    -   Go to your project in the [Firebase Console](https://console.firebase.google.com/).
    -   Go to **Project Settings > General** and download your `google-services.json` file.
    -   Place this file in the `app/` directory of this project.

4.  **Add Your SHA-1 Key to Firebase:**
    -   In Android Studio, open the **Terminal** window.
    -   Run `./gradlew signingReport` (or `gradlew signingReport` on Windows) to generate your SHA-1 debug key.
    -   In your Firebase project settings, under the Android app configuration, add this SHA-1 key. This is required for Google Sign-In to work.

5.  **Build and Run:**
    -   Let Android Studio sync the Gradle files.
    -   Click the "Run" button to build and install the app on your emulator or physical device.

---

## Challenges & Learnings

Building this application was a fantastic experience. A key challenge was navigating the complex Gradle build system, especially ensuring version compatibility between the Kotlin compiler, KSP, and the Compose Compiler plugin. Debugging these issues provided a deep understanding of modern Android project configuration and dependency resolution. Implementing the Google Drive API conceptually was also a great exercise in understanding OAuth scopes and the separation of authentication and authorization in modern Google APIs.

---

*This project was submitted as part of the MyApps Android Developer Intern assignment.*
