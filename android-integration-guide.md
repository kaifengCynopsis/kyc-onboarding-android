# KycOnboardingSDK Android 集成指南

> 版本: 0.1.0-beta.2
> 更新日期: 2026-01-21

---

## 目录

1. [系统要求](#系统要求)
2. [安装方式](#安装方式)
3. [权限配置](#权限配置)
4. [快速开始](#快速开始)
5. [统一 KYC 入口](#统一-kyc-入口)
6. [配置选项](#配置选项)
7. [API 参考](#api-参考)
8. [常见问题](#常见问题)

---

## 系统要求

| 要求 | 最低版本 |
|------|----------|
| Android API | 24+ (Android 7.0) |
| Kotlin | 1.9+ |
| Gradle | 8.0+ |
| JDK | 17+ |
| Android Studio | Hedgehog (2023.1.1)+ |

---

## 安装方式

### 方式一：GitHub Releases（推荐，无需认证）

在项目根目录的 `settings.gradle` 或 `settings.gradle.kts` 添加仓库：

**Groovy (settings.gradle):**
```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url 'https://github.com/kaifengCynopsis/kyc-onboarding-android/releases/download/0.1.0-beta.2'
            content {
                includeGroup 'com.cynopsis.onboarding'
            }
        }
    }
}
```

**Kotlin DSL (settings.gradle.kts):**
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://github.com/kaifengCynopsis/kyc-onboarding-android/releases/download/0.1.0-beta.2")
            content {
                includeGroup("com.cynopsis.onboarding")
            }
        }
    }
}
```

在模块的 `build.gradle` 或 `build.gradle.kts` 添加依赖：

**Groovy:**
```groovy
dependencies {
    implementation 'com.cynopsis.onboarding:kyc-sdk:0.1.0-beta.2'
}
```

**Kotlin DSL:**
```kotlin
dependencies {
    implementation("com.cynopsis.onboarding:kyc-sdk:0.1.0-beta.2")
}
```

### 方式二：本地 AAR 文件

1. 下载 `kyc-sdk-release-0.1.0-beta.2.aar` 从 [GitHub Releases](https://github.com/kaifengCynopsis/kyc-onboarding-android/releases)

2. 将 AAR 文件放入 `app/libs/` 目录

3. 在 `build.gradle.kts` 添加：

```kotlin
dependencies {
    implementation(files("libs/kyc-sdk-release-0.1.0-beta.2.aar"))

    // 必需的传递依赖
    implementation("com.amplifyframework.ui:liveness:1.2.7")
    implementation("com.amplifyframework:core:2.14.11")
    implementation("com.veriff:veriff-library:7.6.2")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
}
```

### 方式三：JitPack

在 `settings.gradle.kts` 添加 JitPack 仓库：

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

添加依赖：

```kotlin
dependencies {
    implementation("com.github.kaifengCynopsis:kyc-onboarding-android:0.1.0-beta.2")
}
```

---

## 权限配置

在 `AndroidManifest.xml` 添加必要权限：

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- 网络权限 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <!-- 相机权限（用于身份验证和文档拍摄）-->
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" android:required="true" />
    <uses-feature android:name="android.hardware.camera.autofocus" android:required="false" />

    <!-- 存储权限（用于文档上传，Android 13+ 需要细分权限）-->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

    <application
        android:usesCleartextTraffic="false"
        ...>

        <!-- FileProvider 用于相机拍摄 -->
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>

    </application>
</manifest>
```

创建 `res/xml/file_paths.xml`：

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <cache-path name="cache" path="." />
    <files-path name="files" path="." />
    <external-cache-path name="external_cache" path="." />
</paths>
```

---

## 快速开始

### 1. 创建 SDK 配置

```kotlin
import com.cynopsis.onboarding.core.KycOnboarding
import com.cynopsis.onboarding.core.KycOnboardingConfig
import com.cynopsis.onboarding.core.LocalizationConfig

// 创建配置
val config = KycOnboardingConfig(
    apiBaseUrl = "https://api1.artemisdev.cynopsis.co",
    crmBaseUrl = "https://crm-dev.cynopsis.co",
    domainId = "your-domain-id",
    clientId = "your-client-id",
    clientSecret = "your-client-secret",
    enableLogging = true,
    livenessRegion = "ap-northeast-1"
)
```

### 2. 初始化 SDK

```kotlin
// 初始化 KycOnboarding 实例
val kycOnboarding = KycOnboarding(
    context = applicationContext,
    config = config
)
```

### 3. 启动统一 KYC 流程

```kotlin
import com.cynopsis.onboarding.ui.UnifiedOnboardingScreen
import com.cynopsis.onboarding.navigation.KycFlowManager
import com.cynopsis.onboarding.checkpoint.CheckpointManager

@Composable
fun MyApp() {
    val context = LocalContext.current

    // 初始化必要组件
    val kycOnboarding = remember {
        KycOnboarding(context, config)
    }

    val flowManager = remember {
        KycFlowManager(kycOnboarding)
    }

    val checkpointManager = remember {
        CheckpointManager(context)
    }

    // 使用统一入口
    UnifiedOnboardingScreen(
        kycOnboarding = kycOnboarding,
        flowManager = flowManager,
        checkpointManager = checkpointManager,
        onExit = {
            // 用户退出流程
            (context as? Activity)?.finish()
        },
        onComplete = {
            // KYC 流程完成
            println("KYC 完成!")
        }
    )
}
```

---

## 统一 KYC 入口

### UnifiedOnboardingScreen - 统一入口

`UnifiedOnboardingScreen` 是 SDK 的**统一入口**，自动处理：

1. **客户类型选择** - 用户选择"个人"或"企业"
2. **自动路由** - 根据选择进入对应流程：
   - **个人 KYC** → KycFlowScreen（文档选择 → 上传 → OCR → 活体 → 验证 → 完成）
   - **企业 KYC** → CorporateNavGraph（公司信息 → 关联方 → 文档 → 表单 → 审核）

### 完整示例

```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.cynopsis.onboarding.core.KycOnboarding
import com.cynopsis.onboarding.core.KycOnboardingConfig
import com.cynopsis.onboarding.ui.UnifiedOnboardingScreen
import com.cynopsis.onboarding.ui.UnifiedOnboardingConfig
import com.cynopsis.onboarding.navigation.KycFlowManager
import com.cynopsis.onboarding.checkpoint.CheckpointManager

class KycActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current

                    // 1. 创建配置
                    val config = remember {
                        KycOnboardingConfig(
                            apiBaseUrl = "https://api1.artemisdev.cynopsis.co",
                            crmBaseUrl = "https://crm-dev.cynopsis.co",
                            domainId = "your-domain-id",
                            clientId = "your-client-id",
                            clientSecret = "your-client-secret",
                            enableLogging = true,
                            livenessRegion = "ap-northeast-1"
                        )
                    }

                    // 2. 初始化 SDK
                    val kycOnboarding = remember {
                        KycOnboarding(context, config)
                    }

                    // 3. 创建 FlowManager 和 CheckpointManager
                    val flowManager = remember {
                        KycFlowManager(kycOnboarding)
                    }

                    val checkpointManager = remember {
                        CheckpointManager(context)
                    }

                    // 4. 使用统一入口
                    UnifiedOnboardingScreen(
                        kycOnboarding = kycOnboarding,
                        flowManager = flowManager,
                        checkpointManager = checkpointManager,
                        config = UnifiedOnboardingConfig(
                            // 可选：强制指定客户类型，跳过选择
                            // forceCustomerType = CustomerType.INDIVIDUAL,
                            showCustomerTypeSelection = true
                        ),
                        onExit = {
                            finish()
                        },
                        onComplete = {
                            // KYC 完成
                            setResult(RESULT_OK)
                            finish()
                        }
                    )
                }
            }
        }
    }
}
```


---

## 配置选项

### KycOnboardingConfig

| 参数 | 类型 | 必需 | 默认值 | 说明 |
|------|------|------|--------|------|
| `apiBaseUrl` | String | ✅ | - | Artemis API 基础 URL |
| `crmBaseUrl` | String | ✅ | - | CRM 系统基础 URL |
| `domainId` | String | ✅ | - | 域 ID |
| `clientId` | String | ✅ | - | 客户端 ID |
| `clientSecret` | String | ✅ | - | 客户端密钥 |
| `enableLogging` | Boolean | ❌ | false | 是否启用日志 |
| `livenessRegion` | String | ❌ | "ap-northeast-1" | AWS 活体检测区域 |

### UnifiedOnboardingConfig

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `forceCustomerType` | CustomerType? | null | 强制客户类型，跳过选择 |
| `showCustomerTypeSelection` | Boolean | true | 是否显示类型选择页面 |
| `corporateConfig` | CorporateConfig | CorporateConfig() | 企业流程配置 |

### CustomerType

```kotlin
enum class CustomerType {
    INDIVIDUAL,  // 个人客户
    CORPORATE    // 企业客户
}
```

### LocalizationConfig (支持 13 种语言 当前英语词条完整)

```kotlin
enum class LocalizationConfig(val code: String) {
    ENGLISH("en"),
    SIMPLIFIED_CHINESE("zh-Hans"),
    TRADITIONAL_CHINESE("zh-Hant"),
    JAPANESE("ja"),
    KOREAN("ko"),
    THAI("th"),
    VIETNAMESE("vi"),
    INDONESIAN("id"),
    MALAY("ms"),
    SPANISH("es"),
    FRENCH("fr"),
    GERMAN("de"),
    PORTUGUESE("pt")
}
```

---

## API 参考

### KycOnboarding

```kotlin
class KycOnboarding(
    context: Context,
    config: KycOnboardingConfig
) {
    /**
     * 获取当前客户类型（如果已设置）
     */
    fun getCustomerType(corporateConfig: CorporateConfig? = null): CustomerType?

    /**
     * 获取当前 SDK 版本
     */
    fun getVersion(): String

    /**
     * 清除本地缓存的会话数据
     */
    suspend fun clearSession()

    /**
     * 预取配置数据（可选，用于优化启动速度）
     */
    suspend fun prefetchConfiguration()
}
```

### KycFlowManager

```kotlin
class KycFlowManager(
    kycOnboarding: KycOnboarding
) {
    /**
     * 获取当前流程步骤
     */
    val currentStep: KycFlowStep

    /**
     * 流程是否完成
     */
    val isFlowCompleted: Boolean

    /**
     * 前往下一步
     */
    fun goToNextStep()

    /**
     * 返回上一步
     */
    fun goToPreviousStep()
}
```

### CheckpointManager

```kotlin
class CheckpointManager(
    context: Context
) {
    /**
     * 保存检查点
     */
    suspend fun saveCheckpoint(checkpoint: Checkpoint)

    /**
     * 恢复检查点
     */
    suspend fun restoreCheckpoint(): Checkpoint?

    /**
     * 清除检查点
     */
    suspend fun clearCheckpoint()
}
```

---

## 常见问题

### Q1: 如何处理权限请求？

SDK 会在需要时自动请求相机和存储权限。如果您想在启动 KYC 流程前预先请求权限：

```kotlin
val permissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    val allGranted = permissions.values.all { it }
    if (allGranted) {
        startKyc()
    } else {
        showPermissionRationale()
    }
}

permissionLauncher.launch(
    arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_MEDIA_IMAGES
    )
)
```

### Q2: 如何处理网络错误？

```kotlin
// 在 onExit 回调中检查错误
UnifiedOnboardingScreen(
    // ... 其他参数
    onExit = {
        // 可以在这里检查是否因为网络错误退出
        // 并提示用户重试
    }
)
```

### Q3: 如何启用调试日志？

```kotlin
val config = KycOnboardingConfig(
    // ... 其他配置
    enableLogging = true
)
```

在 Logcat 中过滤 `KycSDK` 标签查看日志。

### Q4: 如何在 ProGuard 中配置混淆规则？

SDK 已包含 ProGuard 规则，但如果遇到问题，可以添加：

```proguard
# KYC SDK
-keep class com.cynopsis.onboarding.** { *; }

# AWS Amplify
-keep class com.amplifyframework.** { *; }

# Veriff
-keep class com.veriff.** { *; }

# kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
```

### Q5: 如何恢复之前的会话？

SDK 使用 `CheckpointManager` 自动管理会话状态：

```kotlin
val checkpointManager = CheckpointManager(context)

// 检查是否有未完成的会话
val checkpoint = checkpointManager.restoreCheckpoint()
if (checkpoint != null) {
    // 有未完成的会话，可以提示用户是否继续
}

// 清除会话（用户明确要重新开始时）
checkpointManager.clearCheckpoint()
```

---

## 📞 支持

- **Email:** support@cynopsis.com
- **Issues:** https://github.com/kaifengCynopsis/kyc-onboarding-android/issues
- **文档:** https://docs.cynopsis.com

---

**© 2026 Cynopsis Solutions. All rights reserved.**
