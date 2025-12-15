# How to Build the APK

Since you don't have Android Studio, follow these steps to build the APK:

## Option 1: Build on Windows (Simplest)

### Prerequisites:
1. **Install Java JDK 17**
   - Download from: https://adoptium.net/
   - Install and make sure Java is added to PATH
   - Verify by opening PowerShell and running: `java -version`

2. **Download Android SDK Command Line Tools** (Optional, but recommended)
   - Go to: https://developer.android.com/studio#command-tools
   - Download "Command line tools only" for Windows
   - Extract to a folder (e.g., `C:\Android\cmdline-tools`)
   - Set ANDROID_HOME environment variable to the SDK folder

### Build Steps:

1. Open PowerShell in the `AppBlocker` folder
2. Double-click `build-apk.bat` or run in PowerShell:
   ```powershell
   .\build-apk.bat
   ```

3. Wait for the build to complete (first build may take several minutes as it downloads dependencies)

4. Your APK will be at: `app\build\outputs\apk\debug\app-debug.apk`

## Option 2: Build with PowerShell Command

If the batch file doesn't work, run directly in PowerShell:

```powershell
cd d:\Mannu\AppBlocker
.\gradlew.bat assembleDebug
```

The APK will be created at: `app\build\outputs\apk\debug\app-debug.apk`

## Option 3: Online Build Services

If you can't install Java, use these free online services:

1. **GitHub Actions** (Recommended)
   - Create a GitHub repository
   - Upload your project
   - Add a workflow file to build the APK automatically

2. **AppCenter** or **Codemagic** (Free tiers available)

## Installing the APK on Your Tablet

1. Transfer `app-debug.apk` to your tablet (via USB, email, cloud storage, etc.)
2. On your tablet:
   - Go to Settings → Security
   - Enable "Install from Unknown Sources" or "Install Unknown Apps"
3. Open the APK file on your tablet
4. Tap "Install"
5. Once installed, open the app and grant all required permissions

## Troubleshooting

**"Java not found" error:**
- Install JDK 17 from https://adoptium.net/
- Make sure Java is in your system PATH

**Build fails with SDK errors:**
- The Gradle wrapper will automatically download the Android SDK components
- Just make sure you have a stable internet connection

**Permission errors on tablet:**
- The app requires special permissions (Accessibility, Usage Stats, Overlay)
- You must manually grant these in the app after installation
- Go through each permission request in the app

## Quick Start Without Building

If you just want to test quickly and building is too complicated, I can provide you with alternative approaches:
1. Use Android Studio's built-in emulator on a different computer
2. Use online Android emulators
3. Find someone with Android Studio to build it for you

The APK file will be around 2-5 MB in size and works on Android 7.0 (API 24) and above.
