@echo off
echo ========================================
echo App Blocker APK Builder
echo ========================================
echo.

REM Check if Java is installed
echo Checking for Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install JDK 17 or higher from:
    echo https://adoptium.net/
    pause
    exit /b 1
)

echo Java found!
echo.

REM Set ANDROID_HOME if not already set
if not defined ANDROID_HOME (
    echo WARNING: ANDROID_HOME is not set
    echo You may need to install Android SDK Command Line Tools
    echo Download from: https://developer.android.com/studio#command-tools
    echo.
)

echo Building APK...
echo.

REM Make gradlew executable (if needed)
if exist gradlew.bat (
    echo Using gradlew.bat
) else (
    echo ERROR: gradlew.bat not found
    pause
    exit /b 1
)

REM Clean and build the debug APK
call gradlew.bat clean assembleDebug

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo BUILD SUCCESSFUL!
    echo ========================================
    echo.
    echo Your APK is located at:
    echo app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo Transfer this file to your Android tablet and install it.
    echo You may need to enable "Install from Unknown Sources" in your tablet settings.
    echo.
) else (
    echo.
    echo ========================================
    echo BUILD FAILED!
    echo ========================================
    echo.
    echo Please check the error messages above.
    echo.
)

pause
