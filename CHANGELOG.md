**CHANGELOG.md:**
```markdown
# Changelog
All notable changes to the KYC Onboarding Android SDK will be documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
---
## [0.1.0-beta.1] - 2024-12-11
### 🎉 Initial Beta Release
First binary distribution release of the Android SDK.
### 📦 Distribution Type
- **SDK Core:** Compiled binary (AAR)
- **Dependencies:** Source code (auto-downloaded by Gradle)
  - AWS Amplify UI Liveness 1.2.3
  - AWS Amplify Core 2.14.7
  - Veriff SDK 6.+
### ✨ Features
- 🔐 Complete authentication flow (username/password, OTP, client credentials)
- 📄 Document upload with OCR support
- 🎭 AWS Rekognition liveness detection
- ✅ Full KYC onboarding workflow with native UI
- 📊 Real-time progress tracking
- 🌍 13-language localization support
- 🔒 Secure token management (EncryptedSharedPreferences)
- 📱 Native Jetpack Compose components
- 🔄 Auto token refresh mechanism
### 📋 Technical Details
- **Minimum SDK:** 23 (Android 6.0)
- **Target SDK:** 35 (Android 15)
- **Kotlin:** 1.9+
- **Gradle:** 8.0+
- **AAR Size:** 3.1 MB
- **Checksum (SHA-256):** `f3f7b3e9069a0963ebebc6b390ba7fd4af6837cc691a21850791c9283da0ba1f`
### 🔗 Assets
- `kyc-onboarding-sdk-0.1.0-beta.1.aar` - Compiled SDK library
- `kyc-onboarding-sdk-0.1.0-beta.1.aar.sha256` - SHA-256 checksum
- `kyc-onboarding-sdk-0.1.0-beta.1.pom` - Maven POM metadata
### ⚠️ Known Limitations
- Beta version - API may change in future releases
- Limited to binary distribution (no source code)
- Requires manual dependency declaration in build.gradle
---
## [Unreleased]
### Planned Features
- Maven Central publication
- Improved error handling
- Enhanced documentation
- Sample app improvements
