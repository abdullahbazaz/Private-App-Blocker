# App Blocker - Android Application

An Android application that helps you stay focused by blocking distracting apps like YouTube Shorts, Instagram Reels, and other apps you choose to block.

## Features

- 📱 **Block Distracting Apps**: Automatically blocks YouTube, Instagram, and other selected apps
- 🎯 **Custom App Selection**: Choose which apps to block from your installed apps
- 🔒 **Accessibility Service**: Uses Android's Accessibility Service to monitor app usage
- 🏠 **Auto-Redirect**: Automatically redirects you to the home screen when blocked apps are opened
- 💾 **Persistent Settings**: Your preferences are saved and persist across app restarts

## Required Permissions

The app requires the following permissions to function:

1. **Accessibility Service**: To monitor which apps are currently running
2. **Usage Stats Access**: To track app usage statistics
3. **Draw Over Other Apps**: To display blocking overlay when needed

## How to Use

1. Install the app on your Android device (API 24+)
2. Open the app and grant all required permissions:
   - Enable Accessibility Service for App Blocker
   - Grant Usage Stats Access
   - Allow Draw Over Other Apps
3. Toggle "Enable App Blocking" to activate the service
4. Select which apps you want to block from the list
5. The app will now automatically block selected apps when you try to open them

## Default Blocked Apps

- YouTube (including YouTube Shorts)
- Instagram (including Instagram Reels)

You can add or remove apps from the blocked list in the main screen.

## Technical Details

- **Language**: Kotlin
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Architecture**: MVVM pattern with ViewBinding
- **Key Technologies**:
  - AccessibilityService for app monitoring
  - UsageStatsManager for app usage tracking
  - SharedPreferences for data persistence
  - RecyclerView for app list display

## Building the Project

### Prerequisites
- Android Studio (latest version recommended)
- JDK 17 or higher
- Android SDK with API 34

### Build Steps

1. Clone or download the project
2. Open the project in Android Studio
3. Let Gradle sync complete
4. Connect an Android device or start an emulator
5. Click Run or use `./gradlew installDebug`

### Build from Command Line

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## Project Structure

```
AppBlocker/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/appblocker/
│   │       │   ├── adapter/
│   │       │   │   └── BlockedAppsAdapter.kt
│   │       │   ├── model/
│   │       │   │   └── AppInfo.kt
│   │       │   ├── service/
│   │       │   │   └── AppBlockingService.kt
│   │       │   ├── utils/
│   │       │   │   └── AppBlockerPreferences.kt
│   │       │   ├── BlockOverlayActivity.kt
│   │       │   └── MainActivity.kt
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   ├── drawable/
│   │       │   ├── values/
│   │       │   └── xml/
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
└── settings.gradle
```

## How It Works

1. **Detection**: The AccessibilityService monitors when apps are launched or switched
2. **Checking**: When an app is detected, it checks against the blocked apps list
3. **Blocking**: If the app is blocked, an overlay screen is immediately shown
4. **Redirection**: After 2 seconds, the user is automatically redirected to the home screen

## Limitations

- Requires manual permission grants (Android security requirement)
- May not work with some system apps or highly integrated apps
- Battery usage may increase slightly due to background monitoring
- Some manufacturers may have additional restrictions on accessibility services

## Troubleshooting

**Service not working?**
- Ensure all three permissions are granted
- Check if the Accessibility Service is enabled in Android Settings
- Try disabling and re-enabling the service
- Restart your device

**Apps not being blocked?**
- Make sure "Enable App Blocking" toggle is ON
- Verify the app is checked in the blocked apps list
- Some apps may require additional time to be detected

## Privacy

This app:
- Does NOT collect any personal data
- Does NOT send data to external servers
- All settings are stored locally on your device
- Only monitors app package names, not app content

## License

This project is created for educational and personal use.

## Contributing

Feel free to submit issues, fork the repository, and create pull requests for any improvements.

## Support

For issues or questions, please create an issue in the repository.
