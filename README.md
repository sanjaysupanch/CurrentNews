## A modern **Android News App** built using **Jetpack Compose, MVVM, and Hilt**, allowing users to fetch, read, save and share news articles.

---

## Features

* Fetches news articles from API  
* Displays articles in a **scrollable list**  
* Opens full article in **WebView**  
* Allows **saving articles to Room Database**  
* Uses **MVVM architecture with Repository Pattern**  
* **Dependency Injection with Hilt**  
* **Jetpack Compose for UI**  
* **Bottom Navigation for navigation**  
* **Search functionality** for finding specific news articles
* **Background sync** using WorkManager
* **Push notifications** for new article updates
* **Dark mode support** with Material3 dynamic colors
* **Share articles** with other apps

---

## Tech Stack

| Component            | Library Used |
|----------------------|-------------|
| **UI Framework**    | Jetpack Compose  |
| **Architecture**    | MVVM  |
| **Dependency Injection** | Hilt  |
| **Networking**      | Retrofit  |
| **Local Database**  | Room  |
| **Navigation**      | Jetpack Navigation  |
| **Image Loading**   | Coil  |
| **Background Tasks** | WorkManager |
| **Notifications** | NotificationManager & NotificationCompat |
| **JSON Serialization** | Gson |

---

## Folder Structure

<img width="588" height="1116" alt="Screenshot 2025-11-27 at 5 44 21 PM" src="https://github.com/user-attachments/assets/174100ac-9fb0-40fc-846b-9cb351661e1e" />


* Follows Clean Architecture Principles.  
* Separates concerns (UI, Data, Business Logic).  
* Uses Repository pattern for better testability. 

---

## API Usage

The app fetches news articles using **NewsAPI**.

- **Base URL:** `https://newsapi.org/`
- **Endpoint:** `v2/top-headlines`
- **Authentication:** API Key stored securely in `gradle.properties`
- **Example API Call:**
  ```kotlin
  interface ArticlesAPIService {
      @GET("v2/top-headlines")
      suspend fun getArticles(
          @Query("country") country: String = "us",
          @Query("q") query: String? = null,
          @Query("apiKey") apiKey: String
      ): ArticlesResponse
  }
  ```

* Uses `BuildConfig.NEWS_API_KEY` instead of hardcoding.
* Ensures API key security using `gradle.properties`.

---

## Feature How It Works?

| Feature | Description |
|---------|-------------|
| **Fetch News** | Fetches news using Retrofit from NewsAPI |
| **Display News** | Shows articles in a LazyColumn using Jetpack Compose |
| **Search News** | Allows users to search for specific articles using query parameter |
| **Save Article** | Saves articles to Room Database for offline access |
| **Read Full Article** | Opens full article in WebView with JavaScript enabled |
| **Navigation** | Uses BottomNavigationBar for switching between Home and Saved Articles screens |
| **Share Article** | Shares article content (title, description, URL) using Android Intent |
| **Dark Mode** | Automatically adapts to system theme with Material3 dynamic colors |
| **Background Sync** | Periodically syncs news articles using WorkManager |
| **Notifications** | Shows push notifications when new articles are available, using NotificationChannel and NotificationCompat |

---

## App Demo

* Link: [https://drive.google.com/file/d/19vqCF-YW2tz167iwrc8-xeaLDZblfnu2t/view
](https://drive.google.com/file/d/1fxzyoMTAVnRRyxySYsxMYE0i0pimA9kj/view?usp=sharing)
---

## APK Link

* Link: [https://drive.google.com/file/d/1FYhGfgJXtO2dRIZPVv4w3XwnRBw0S14vu/view?usp=sharing
](https://drive.google.com/file/d/1ldVJjvN-fXKSTbDlx_SXaPQJvG0PWbY4/view?usp=sharing)
