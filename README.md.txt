# Fide-Go

This repository contains a Spring Boot backend and an Android frontend.

## Backend (Spring Boot)

Requirements:

- Java 17
- Maven (or use the provided Maven wrapper)

### Build

Use Maven to compile and package the application:

```bash
./mvnw package
```

The resulting WAR file will be placed in `target/`.

### Run

Run the application directly using the Maven Spring Boot plugin or by
executing the WAR:

```bash
# Option 1: run with Maven
./mvnw spring-boot:run

# Option 2: run the packaged artifact
java -jar target/fide-go-0.0.1-SNAPSHOT.war
```

The backend listens on port `8000` by default (see
`src/main/resources/application.properties`).

## Frontend (Android)

The Android client is provided as a zip archive named
`fidego-frontend.zip`. It contains a standard Android Studio project.

### Prepare the project

1. Unzip `fidego-frontend.zip`.
2. Open the extracted `Fide_go` directory in Android Studio.
3. Adjust the backend endpoint if necessary.
   The file
   `app/src/main/java/com/example/fide_go/data/retrofit/RetrofitApi.kt`
   defines the `BASE_URL` constant used by the app. Update it so the
   Android client can reach your running backend.

### Build and run

From Android Studio you can simply press **Run**. On the command line
execute:

```bash
./gradlew assembleDebug      # Build APK
./gradlew installDebug       # Deploy to a connected device or emulator
```

The generated debug APK can be found under
`app/build/outputs/apk/debug/` inside the Android project.