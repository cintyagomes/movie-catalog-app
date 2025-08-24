# **Movie Catalog App**

This is an Android application that consumes the [OMDB API](https://www.omdbapi.com/) to allow  
movie searches. The project uses modern technologies such as Kotlin, Retrofit, Dagger, and the MVVM  
Architecture, focusing on clean architecture, best practices, and an intuitive user interface.

---

## ✨ **Features**

- Search for movies by title using the OMDB API.
- Display results returned by the API, such as movie title, release year, and poster.
- Display detailed movie information when selecting an item from the list, such as:
  description, awards, and directors.

---

## 🏗️ **Architecture**

The project follows the **MVVM (Model-View-ViewModel)** pattern to maintain separation of  
concerns and facilitate testability.

### Main Components:

- **ViewModel:** Responsible for managing UI data and business logic.
- **Repository:** Interacts with the API and manages data.
- **Retrofit:** Handles network requests.
- **Dagger/ Hilt:** Manages dependency injection.

---

## 🛠️ **Technologies Used**

- **Kotlin:** Development language.
- **Retrofit:** For communication with RESTful APIs.
- **Dagger/ Hilt:** For dependency injection.
- **Coroutines:** For asynchronous calls and data flow handling.
- **XML:** For layout creation.
- **Jetpack Components:**
    - ViewModel
    - LiveData
    - RecyclerView
- **Glide:** For image loading.
- **Navigation:** For screen navigation.
- **Material Design:** For a modern and consistent interface.
- **Mockk:** For creating mock objects in unit tests.
- **JUnit:** Framework for unit testing and running test cases.

---

## 🚀 **Installation and Setup**

### Prerequisites:

- Android Studio.
- Gradle properly configured.
- An API key from [OMDB API](https://www.omdbapi.com/apikey.aspx).

### Step-by-step:

1. Clone the repository:

```bash
git clone https://github.com/cintyagomes/movie-catalog-app.git
```

2. Open the project in Android Studio.

3. Sync the project with Gradle.

4. Run the application on an emulator or physical device.
   
---

## 🎬 **Media**

https://github.com/user-attachments/assets/83020715-de38-4f55-966f-b727324e6dd1

---

## 🧑‍💻 **Contributing**

Contributions are welcome! Follow the steps below to contribute:

1. Fork the repository.

2. Create a branch for your feature:

```bash
git checkout -b feature/feature-name
```

3. Commit your changes:

```bash
git commit -m "feat: Adds new feature"
```

4. Push your changes:

```bash
git push origin added/feature-name
```

5. Open a Pull Request.
