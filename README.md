# 🎌 MyAnimeList

An Android anime browsing app built with **modern Android architecture
and libraries**.\
Browse anime with **infinite scrolling**, view **detailed information**,
and watch **trailers** seamlessly.

------------------------------------------------------------------------

## ✨ Features

-   📜 Unlimited / Infinite Anime Scrolling\
-   🎬 Anime Detail Screen with Trailer Support\
-   🖼 High-quality Anime Posters using Coil\
-   💾 Offline Caching using Room Database\
-   ⚡ Fast API Fetching with Retrofit\
-   🧩 Clean MVVM Architecture\
-   💉 Dependency Injection with Koin

------------------------------------------------------------------------

## 🏗 Architecture

This project follows **MVVM (Model - View - ViewModel)** architecture.

    UI (Compose / Activity / Fragment)
        ↓
    ViewModel
        ↓
    Repository
        ↓
    Local (Room) + Remote (Retrofit API)

------------------------------------------------------------------------

## 🛠 Tech Stack

### 🧱 Architecture

-   MVVM Architecture\
-   Repository Pattern\
-   Clean Separation of Concerns

### 📚 Libraries Used

  Library and Purpose\
  -----------------------------------------------\
  Koin for Dependency Injection\
  Retrofit for Network API Calls\
  Room for Local Database Persistence\
  Coil for Image Loading\
  Coroutines + Flow for Async Programming\

------------------------------------------------------------------------

## 📱 App Modules Overview

### 🔍 Anime List

-   Paginated / Infinite scrolling list\
-   Loads data from API\
-   Caches data locally

### 📖 Anime Details

-   Full anime information\
-   Trailer playback support\
-   Poster and metadata display

------------------------------------------------------------------------

## 🚀 Getting Started

### Prerequisites

-   Android Studio Latest Version\
-   Kotlin\
-   Minimum SDK: (Add yours)\
-   Target SDK: (Add yours)

------------------------------------------------------------------------

### 🔧 Setup

1.  Clone the repository

``` bash
git clone https://github.com/amitvishdlw/MyAnimeList.git
```

2.  Open in Android Studio\
3.  Sync Gradle\
4.  Run the app 🚀

------------------------------------------------------------------------

## 📸 Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/b5cae5e9-d37c-4bb1-a8b9-8dc8452cbcef" width="45%" />
  <img src="https://github.com/user-attachments/assets/ea926afc-d388-4712-8573-cb84d6aa3424" width="45%" />
</p>



------------------------------------------------------------------------

## 📦 API Source

Anime data fetched using public Anime APIs (Jikan).

------------------------------------------------------------------------

## 🤝 Contributing

Pull requests are welcome.\
For major changes, please open an issue first to discuss what you would
like to change.

------------------------------------------------------------------------

## 👨‍💻 Author

**Amit Vishwakarma**
