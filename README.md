# Nonton Apa

A movie discovery Android app built with Kotlin and Jetpack Compose, powered by the public
[TMDB API](https://developers.themoviedb.org/3). Browse movie genres, explore movies per genre,
view movie details, and read user reviews.

Repository: `git@github.com:henrasn/Nontonapa.git`

---

## Requirements

- **Android Studio** — recent stable version that supports AGP 9.0.1 / Kotlin 2.3.20 / KSP 2.3.10
- **JDK 17** — the project pins a Java/Kotlin toolchain of 17
- **Android SDK Platform 36** — `compileSdk` / `targetSdk` are both 36
- **Min supported device** — `minSdk` 24 (Android 7.0); emulator/device must be API 24 or higher
- **TMDB bearer token** — required to authenticate API requests (see [Roadmap](#roadmap))

---

## Getting Started / Build & Run

**Open the project**

1. Launch Android Studio → *Open* → select this folder.
2. Let Gradle sync (AGP 9.0.1, Kotlin 2.3.20, KSP 2.3.10 will be downloaded automatically).

**Build the debug APK**

```bash
./gradlew :app:assembleDebug
```

The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

**Build the release APK**

```bash
./gradlew :app:assembleRelease
```

**Run on an emulator**

1. Open Android Studio → *Device Manager* → create an AVD (API 24–36).
2. Start the emulator, then either press **Run ▶** in Android Studio or run:

```bash
./gradlew :app:installDebug
```

**Run on a physical device**

1. Enable **Developer options** and **USB debugging** on the device.
2. Connect it via USB and allow the debugging prompt.
3. Press **Run ▶** in Android Studio (or use `./gradlew :app:installDebug`).

**Note:** `gradle.properties` enables the Gradle build cache and configuration cache for faster
incremental builds.

---

## Tech Stack

- **Language:** Kotlin 2.3.20
- **UI:** Jetpack Compose + Material 3 (Compose BOM 2026.03.01)
- **Navigation:** AndroidX Navigation 3 (1.0.1)
- **Architecture:** MVI (see [Architecture](#architecture))
- **Dependency injection:** Dagger Hilt 2.60.1, processed with KSP 2.3.10
- **Networking:** Retrofit 3.0.0 + OkHttp 4.12.0 + kotlinx-serialization 1.7.3
- **Local database:** Room + Room-Paging 2.7.1
- **Pagination:** AndroidX Paging 3 (3.3.6) with `RemoteMediator`
- **Image loading:** Coil 3 (3.2.0) with the OkHttp network fetcher
- **Concurrency:** kotlinx-coroutines 1.10.2
- **Testing:** JUnit 4, MockK 1.13.13, kotlinx-coroutines-test, Compose UI test, Espresso

All dependency versions are centralized in `gradle/libs.versions.toml`.

---

## Architecture

The app follows **MVI (Model-View-Intent)** with a unidirectional data flow:

- **Single Activity** — `MainActivity` hosts the whole Compose UI and is edge-to-edge enabled.
- **MVI pattern** — one immutable `UiState` per screen, exposed as a `StateFlow`, and updated
  only through intents.
- **Intent** — a `sealed interface` describing user actions (e.g. `loadMovieGenres`).
- **UiState** — an immutable `data class` representing the full screen state, mutated with
  `.update { }`.
- **Effect** — a `sealed interface` for one-shot events (e.g. `ShowError`) sent through a
  private `Channel` and shown as a snackbar.
- **Data flow** — `DataSource` (Retrofit) → `Repository` (`safeApiCall` → `Result<T>`) →
  `ViewModel` (reducer) → Compose.
- **Pagination** — movies and reviews are loaded through **Room + Paging 3 `RemoteMediator`**,
  cached in the local database and streamed as `Flow<PagingData>`.
- **Dependency injection** — Hilt `@Binds` / `@Provides` modules; IO work uses the
  `@IoDispatcher` qualifier to stay off the main thread.
- **Theming** — Material 3 with custom dark/light color schemes and dynamic color on Android 12+.

---

## Project Structure

```
Nontonapa/
├── build.gradle.kts                  # root: plugins only
├── settings.gradle.kts               # repo management, module :app
├── gradle/libs.versions.toml         # all dependency versions
└── app/
    ├── build.gradle.kts
    └── src/
        ├── main/java/com/henrasn/nontonapa/
        │   ├── NontonApaApp.kt       # @HiltAndroidApp Application
        │   ├── MainActivity.kt       # @AndroidEntryPoint single activity
        │   ├── Navigation.kt         # Nav3 NavDisplay / entryProvider wiring
        │   ├── NavigationKeys.kt     # @Serializable NavKeys (routes)
        │   ├── core/
        │   │   ├── di/               # @IoDispatcher / @MainDispatcher + module
        │   │   ├── error/            # AppException, ErrorUiText, Throwable.toUiText()
        │   │   └── network/          # Retrofit/TMDB layer, interceptors, safeApiCall
        │   ├── data/
        │   │   ├── mapper/           # DTO -> UI model mappers
        │   │   ├── model/dto/<feature>/     # API response DTOs (@Serializable)
        │   │   ├── model/uimodel/<feature>/ # UI data models
        │   │   ├── module/           # Hilt @Binds / @Provides modules
        │   │   ├── repo/             # Repository interface + Impl
        │   │   ├── source/           # DataSource interface + Impl
        │   │   ├── local/            # Room entities, DAOs, database
        │   │   └── paging/           # RemoteMediators
        │   └── ui/
        │       ├── component/        # reusable composables (GenreItem, MovieCard...)
        │       ├── pages/<feature>/  # Screen + ViewModel + Intent + UiState + Effect
        │       └── theme/            # Color.kt, Type.kt, Theme.kt
        ├── res/                      # drawable, mipmap, values, xml
        ├── test/                     # local JVM unit tests (JUnit + MockK)
        └── androidTest/              # instrumented Compose UI tests
```

---

## Available Pages

- **Genre list** — route `Genre` (`MovieGenreScreen`, `MovieGenreViewModel`)
  Lists movie genres via a single-shot network call (`Result<List<GenreUiData>>`), with
  pull-to-refresh and an empty state.
- **Movie list** — route `Movie(genreId)` (`MoviesScreen`, `MovieViewModel`)
  Discovers movies for a genre, loaded with **Room + Paging 3** (`getDiscoverMovies`).
- **Movie detail** — route `DetailMovie(movieId)` (`DetailMovieScreen`, `MovieDetailViewModel`)
  Shows the movie's overview and tagline plus a **one-page** preview of its reviews
  (`getMovieReviews` -> `List<MovieReviewUiData>`).
- **Review list** — route `ReviewList(movieId)` (`ReviewsScreen`, `ReviewListViewModel`)
  Full, paginated review list accessible from the detail screen's "See All", streamed with
  **Room + Paging 3** (`getMovieReviewsPaged`).

---

## Tech Stack Per Page

**Genre list**
- Single-shot network fetch (no paging, no local cache).
- `TmdbApiService.getMovieGenres()` → `MovieGenreDataSource` → `MovieRepository.getMovieGenres()`
  → `MovieGenreViewModel` (`StateFlow<MovieGenreUiState>` + `MovieGenreEffect.ShowError`).
- Pull-to-refresh triggers a refresh intent.

**Movie list**
- **Room + Paging 3** with `MovieRemoteMediator` (discover endpoint, keyed by genre).
- `TmdbApiService.getDiscoverMovies(page, with_genres)` → `MovieDataSource` →
  `MovieRepository.getDiscoverMovies(genreId): Flow<PagingData<MovieUiData>>`.
- `MovieViewModel` uses `flatMapLatest` on the genre id and `cachedIn(viewModelScope)`.
- `MoviesScreen` collects with `collectAsLazyPagingItems()`, shows a loader while appending and
  a retry button on append errors.

**Movie detail**
- Two concerns: movie metadata + a **one-page** review preview.
- `getMovieDetail` → `MovieDetailDataSource` → `MovieDetailRepository.getMovieDetail()`
  (`Result<MovieDetailUiData>`).
- `getMovieReviews(movieId)` → `MovieDetailRepositoryImpl` calls the same data source page 1 and
  maps to `List<MovieReviewUiData>` via `ReviewRepository`.
- `MovieDetailViewModel` exposes `MovieDetailUiState` (movie + `reviews: List`) and
  `MovieDetailEffect.ShowError`.
- `ReviewUiItem` renders an ellipsized review preview.

**Review list**
- **Room + Paging 3** with `ReviewRemoteMediator` (`3/movie/{movie_id}/reviews`,
  page-size 20, keyed by movie id).
- `ReviewRepository.getMovieReviewsPaged(movieId): Flow<PagingData<MovieReviewUiData>>`.
- `ReviewListViewModel` reacts to `setMovie(id)` via `flatMapLatest`, incrementally loading pages.
- `ReviewsScreen` shows a loader while appending, a retry on append errors, and a snackbar on
  refresh/append errors.

---

## Unit Testing

**Local (JVM) unit tests** — JUnit 4 + MockK + `kotlinx-coroutines-test`.

```bash
./gradlew :app:testDebugUnitTest
```

Filter a single class:

```bash
./gradlew :app:testDebugUnitTest --tests 'com.henrasn.nontonapa.data.repo.MovieRepositoryImplTest'
```

Existing suites:

- `data/repo/MovieRepositoryImplTest.kt` — mocks `MovieDataSource`, asserts `Result` success/failure.
- `data/mapper/GenreResponseMapperTest.kt` — pure mapper extension test.
- `ui/pages/genre/MovieGenreViewModelTest.kt` — swaps the Main dispatcher, asserts `ShowError`
  effect emission.

**Instrumented (Compose UI) tests** — run on an emulator/device.

```bash
./gradlew :app:connectedDebugAndroidTest
```

After changing code, run `:app:testDebugUnitTest` (and `:app:assembleDebug`) to verify nothing
breaks; run `connectedDebugAndroidTest` when touching the UI.

---

## Roadmap

- [ ] Move the TMDB bearer token out of `AuthInterceptor` into `local.properties` / BuildConfig
      so it is not committed to version control.
- [ ] Update the stale instrumented test `MainScreenTest.kt`, which still references the removed
      `MainScreen`, to target the current genre feature.
- [ ] Add unit tests for the review feature (`ReviewRepository`, `ReviewListViewModel`, and the
      `MovieReviewMapper`).
- [ ] Keep improving the visual design and adding more movie content surfaces (e.g. trailers,
      similar movies, search).
