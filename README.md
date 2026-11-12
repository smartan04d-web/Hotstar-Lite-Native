# Hotstar Lite Native — independent demonstration app

An independent native Android Media3/ExoPlayer demonstration project. **Not affiliated with JioHotstar.** No official logos, JioHotstar APK contents, credentials, or proprietary streaming URLs are included.

## Features
- Native Android playback (no WebView) for **authorized** HTTPS MP4, HLS, and DASH URLs
- Play / pause / seek / stop, fullscreen-area player controls
- No advertising SDK included; no ads inserted by this app
- Java 17, Android API 26+, Gradle 8.9, AGP 8.7.3, Media3 1.5.1
- GitHub Actions debug APK build

## Limits
This project **does not** implement JioHotstar login, catalog, API access, DRM license handling, or ad removal from JioHotstar. JioHotstar protected content is not expected to work without legitimate authorized access. This project does not bypass authentication, DRM, ad entitlements, or app integrity.

## Build on GitHub
1. Create a new repository (e.g., `Hotstar-Lite-Native`).
2. Upload the **contents** of this ZIP to the repository root, including hidden `.github/workflows/build-apk.yml`.
3. Commit to `main`; open the Actions tab and run **Build Hotstar Lite Native APK** (or wait for push).
4. Open the successful run, download artifact `Hotstar-Lite-Native-debug-APK`, extract `app-debug.apk` and install it.

The APK is signed with the automatically generated debug key. The GitHub runner may produce a different debug signing key from your existing apps; uninstall an older conflicting package if necessary (back up its data first).

## Local build
Install JDK 17, Android SDK Platform 35, and Gradle 8.9; run `gradle assembleDebug`.

## Test
Enter an HTTPS media URL that you own or have permission to play, then tap Play URL. For protected services, use the provider's authorized SDK and licensing workflow instead.
