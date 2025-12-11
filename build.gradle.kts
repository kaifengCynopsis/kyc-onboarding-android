dependencies {
    // KYC Onboarding SDK (Binary Distribution)
    implementation("com.cynopsis.onboarding:sdk:0.1.0-beta.1@aar") {
        isTransitive = true
    }
    // 必需依赖（自动下载）
    implementation("com.amplifyframework.ui:liveness:1.2.3")
    implementation("com.amplifyframework:aws-auth-cognito:2.14.7")
    implementation("com.amplifyframework:core:2.14.7")
    implementation("com.veriff:veriff-library:6.+")
    // AndroidX 依赖
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    // 网络和序列化
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}
