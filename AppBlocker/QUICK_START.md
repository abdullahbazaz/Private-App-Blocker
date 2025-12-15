# 🚀 EASY APK BUILD GUIDE - No Android Studio Needed!

## ⚠️ IMPORTANT: Java Required

Your system doesn't have Java installed. You need to install it first to build the APK.

## 📥 Step 1: Install Java (5 minutes)

1. **Download Java JDK 17:**
   - Go to: https://adoptium.net/temurin/releases/
   - Select:
     - Version: 17 (LTS)
     - Operating System: Windows
     - Architecture: x64
     - Package Type: JDK
   - Click the `.msi` installer download button

2. **Install Java:**
   - Run the downloaded `.msi` file
   - Click "Next" through the installation
   - ✅ Make sure "Add to PATH" is checked!
   - Complete the installation

3. **Verify Installation:**
   - Open a NEW PowerShell window
   - Run: `java -version`
   - You should see: `openjdk version "17.x.x"`

## 🔨 Step 2: Build the APK

Once Java is installed:

### Method A: Double-click to build (Easiest)
1. Simply double-click the file: `build-apk.bat`
2. Wait for the build (may take 5-10 minutes first time)
3. Your APK will be at: `app\build\outputs\apk\debug\app-debug.apk`

### Method B: PowerShell command
```powershell
cd d:\Mannu\AppBlocker
.\gradlew.bat assembleDebug
```

## 📱 Step 3: Install on Your Tablet

1. **Transfer the APK:**
   - Copy `app-debug.apk` to your tablet
   - You can use: USB cable, email, Google Drive, etc.

2. **Enable Installation:**
   - On tablet: Settings → Security
   - Enable "Unknown Sources" or "Install Unknown Apps"

3. **Install:**
   - Tap the APK file on your tablet
   - Tap "Install"
   - Wait for installation to complete

4. **Setup Permissions:**
   - Open "App Blocker" on your tablet
   - Grant all 3 required permissions:
     ✓ Accessibility Service
     ✓ Usage Stats Access
     ✓ Draw Over Apps
   - Toggle "Enable App Blocking"
   - Select apps to block

## 🎯 Alternative: Pre-built APK Service

If you don't want to install Java, you can use online services:

### Option 1: GitHub Actions (Free & Automated)
1. Create a free GitHub account at https://github.com
2. Create a new repository
3. Upload the AppBlocker folder
4. GitHub will automatically build the APK for you
5. Download the APK from the "Actions" tab

### Option 2: Use a Friend's Computer
- Copy the AppBlocker folder to a computer with Java or Android Studio
- Build the APK there
- Transfer back to your computer

### Option 3: Online Build Tools (May require payment)
- Appetize.io
- AppCenter (Microsoft)
- Codemagic

## 🆘 Quick Troubleshooting

**"Java not recognized"**
→ Install Java from https://adoptium.net/
→ Make sure to restart PowerShell after installation

**Build fails**
→ Make sure you have internet connection (downloads dependencies)
→ Try running as Administrator

**Can't install on tablet**
→ Enable "Unknown Sources" in tablet Security settings
→ Some tablets require enabling per-app (enable for your file manager)

**App doesn't work**
→ Make sure you granted ALL 3 permissions in the app
→ Some manufacturers block accessibility services - check your tablet's settings

## 📊 What You'll Get

- File: `app-debug.apk`
- Size: ~2-5 MB
- Min Android: 7.0 (API 24)
- Works on: Most Android tablets and phones

## 💡 Need Help?

If you're stuck, you have these options:
1. Install Java (recommended, takes 5 minutes)
2. Ask someone with Java/Android Studio to build it
3. Use GitHub Actions (free, but requires GitHub account)
4. Use an online Android emulator to test first

---

**Ready to build?** → Install Java, then double-click `build-apk.bat`
