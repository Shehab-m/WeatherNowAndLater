# Weather App with Dynamic Feature Module (DFM)

This repository contains a Weather App built using **Jetpack Compose** and modern Android development practices. The project demonstrates the use of **Dynamic Feature Modules (DFM)** to download the `Forecast` feature only when needed, optimizing app performance. Additionally, we have set up **GitHub Actions** for CI/CD with secret variables and other essential configurations.

## Key Features
- **Dynamic Feature Module (DFM)**: Forecast module is downloaded only when the user requests it.
- **Deep Link Navigation**: Users can navigate between modules using deep links, passing necessary parameters.
- **Jetpack Compose**: The app is fully built with Jetpack Compose for UI rendering.
- **Coroutines**: Used for background tasks such as downloading the dynamic feature module.
- **CI/CD with GitHub Actions**: Automated build and test pipeline with secret variables.
- **Dependency Injection with Dagger-Hilt**
- **Unit Testing**
- **Clean Architecture**

## Project Structure

- **App Module**: Base module that manages navigation, common resources, and dynamic module installation.
- **Weather Module**: Handles the core weather functionality and UI for the app with (MVVM) arciticture.
- **Forecast Module**: A dynamic feature module that is downloaded on-demand when the user accesses the forecast functionality with (MVI) arciticture.
- **Core Module**: Contains common utilities and shared components ex: Design System, Domain Models And UseCases.
- **Data Module**: Contains data sources(ex: network, local) and repository.

## Functional Requirements

1. **User Input:** Allow users to input a city name.

2. **Display Current Weather:** Show the current weather (temperature, condition, and appropriate icon).

   ![weatherLight](https://github.com/user-attachments/assets/86610f19-0ae1-4f97-8426-359e392b6b9e)
   ![currentWeatherDark](https://github.com/user-attachments/assets/38cf4b6a-1372-449e-82fc-21a09399b48b)

3. **7-Day Forecast:** Display the forecast for the next 7 days.

   ![forecast](https://github.com/user-attachments/assets/634a75f8-20b2-49e1-8da4-fe4edab1c231)
   ![forecastDark](https://github.com/user-attachments/assets/ff7c8bc8-fdc2-45ba-b3d2-b25160536a33)

4. **Persistence:** Store the last searched city and show its weather on app reopen.

 

## Dynamic Feature Module (DFM) Setup

### Dynamic Feature Module Creation
The `Forecast` module was set up as a **dynamic feature module** that gets downloaded on-demand:

![dialouge](https://github.com/user-attachments/assets/b7c62042-b82c-4005-aefd-6038ddb57ff8)

1. **Module Setup**:
   - Added the **Dynamic Feature** module via Android Studio (`File > New > New Module > Dynamic Feature Module`).
   - Configured the module to depend on the app and access shared resources.
   
2. **Module Manifest**:
   - Ensure the dynamic module's manifest includes necessary declarations like activities and permissions.

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.vodafone.forecast">

    <application>
        <activity android:name=".ForecastActivity"
            android:theme="@style/Theme.AppCompat" /> 
    </application>

</manifest>
```
### Dynamic Feature Module Download
We utilized the **Play Core Library** to handle dynamic module downloads in our app. This allows the app to download the `Forecast` module only when required, optimizing performance.

```kotlin
internal data class Actions(val context: Context) {
    fun onDynamicClick(extra: String): () -> Unit = {
        installForecastModule(
            context = context,
            onSuccess = {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("app://com.example.dynamic?city=$extra")
                    `package` = context.packageName
                }
                context.startActivity(intent)
            },
            onFailure = { e -> Log.e("Dynamic Actions", "${e.message}") }
        )
    }
}
```
Before triggering the module download, we check if the module is already installed.

```kotlin
fun isModuleInstalled(context: Context): Boolean {
    val splitInstallManager = SplitInstallManagerFactory.create(context)
    return splitInstallManager.installedModules.contains("Forecast")
}
```
### Deferred Uninstallation
To uninstall the dynamic feature when no longer needed or when the app is uninstalled, we use the deferred uninstall function.

```kotlin
splitInstallManager.deferredUninstall(listOf("Forecast"))
```

### Deep Link Usage Example

To handle modularized navigation between the main app module and the Dynamic Feature Module (DFM), you can use deep links. This allows you to seamlessly transition to different features of your app based on user actions or input.

To open a deep link in the app, you can use the following code snippet:

```kotlin
val intent = Intent(Intent.ACTION_VIEW).apply {
    data = Uri.parse("app://com.example.dynamic?city=$extra")
    `package` = context.packageName
}
context.startActivity(intent)
```
### CI/CD: GitHub Actions Setup
We set up GitHub Actions for Continuous Integration (CI) and Continuous Delivery (CD). The pipeline automatically builds the app and runs tests when code is pushed to the repository.

### Secret Variables
Sensitive data (e.g., API keys) are stored securely using GitHub Secrets. We have configured the secrets as environment variables in the pipeline to ensure the security of sensitive data.

```yaml
env:
  API_KEY: ${{ secrets.API_KEY }}
```

