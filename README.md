# KYC Onboarding Android SDK
[![Maven Central](https://img.shields.io/badge/Maven-Binary%20Distribution-blue)](https://github.com/kaifengCynopsis/kyc-onboarding-android)
[![Version](https://img.shields.io/badge/version-0.1.0--beta.1-orange)](https://github.com/kaifengCynopsis/kyc-onboarding-android/releases)
[![License](https://img.shields.io/badge/license-Proprietary-red)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%206.0%2B-green)](https://developer.android.com)
Official Android SDK for Cynopsis KYC (Know Your Customer) onboarding solution.
> **⚠️ Binary Distribution**
> This repository contains only the compiled AAR library and integration files.
> SDK source code is NOT included in this distribution.
---
## ✨ Features
- 🔐 Complete authentication flow (username/password, OTP, client credentials)
- 📄 Document upload with OCR support
- 🎭 AWS Rekognition liveness detection
- ✅ Full KYC onboarding workflow with native UI
- 📊 Real-time progress tracking
- 🌍 13-language localization support
- 🔒 Secure token management (EncryptedSharedPreferences)
- 📱 Native Jetpack Compose components
- 🔄 Auto token refresh mechanism
---
## 📋 Requirements
- **Minimum SDK:** Android 6.0 (API 23)
- **Target SDK:** Android 15 (API 35)
- **Kotlin:** 1.9+
- **Gradle:** 8.0+
- **Java:** 11+
---
## 📦 Installation
### Gradle (Kotlin DSL)
Add to your project-level `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        // 添加 Cynopsis SDK Maven 仓库
        maven {
            url = uri("https://github.com/kaifengCynopsis/kyc-onboarding-android/releases/download/0.1.0-beta.1")
            // 手动指定 artifact 映射
            content {
                includeGroup("com.cynopsis.onboarding")
            }
        }
    }
}
