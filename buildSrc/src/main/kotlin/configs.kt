import org.gradle.api.JavaVersion

object AppConfig {
    const val EXAMPLE_PACKAGE_NAME = "com.github.k0shk0sh.compose.easyforms.example"
    const val COMPILE_SDK = 31
    const val MIN_SDK = 21
    const val TARGET_SDK = COMPILE_SDK
    const val JVM_TARGET = "1.8"
    val javaCompileOptions = JavaVersion.VERSION_1_8
}

object DokkaConfig {
    const val MAIN = "main"
    const val SAMPLES = "app/src/main/java/"
    const val ANDROID_REF_URL = "https://developer.android.com/reference/"
    const val ANDROIDX_REF_URL = "https://developer.android.com/reference/androidx/package-list"
    const val ANDROID_KOTLIN_REF_URL = "https://developer.android.com/reference/kotlin/"
    const val ANDROIDX_KOTLIN_REF_URL =
        "https://developer.android.com/reference/kotlin/androidx/package-list"
    const val LOCAL_SRC_DIR = "src/main/java"
    const val REMOTE_SRC_DIR =
        "https://github.com/k0shk0sh/ComposeEasyForms/blob/main/%s/src/main/java"
    const val LINE_NUM = "#L"
    const val OUTPUT_DIR = "../docs"
}

object AppPlugins {
    const val ANDROID_APP = "com.android.application"
    const val ANDROID_LIB = "com.android.library"
    const val KOTLIN_ANDROID = "kotlin-android"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val DOKKA = "org.jetbrains.dokka"
    const val PUBLISH_PLUGIN = "com.vanniktech.maven.publish"
    const val GITHUB_RELEASE = "com.github.breadmoirai.github-release"
    const val AGP = "com.android.tools.build:gradle:${DependenciesVersion.AGP_VERSION}"
    const val KGP = "org.jetbrains.kotlin:kotlin-gradle-plugin:${DependenciesVersion.KGP_VERSION}"
    const val PUBLISH = "com.vanniktech:gradle-maven-publish-plugin:${DependenciesVersion.PUBLISH_VERSION}"
}

object DependenciesVersion {
    const val AGP_VERSION = "7.0.2"
    const val KGP_VERSION = "1.5.21"
    const val COMPOSE_VERSION = "1.0.2"
    const val COMPOSE_ACTIVITY_VERSION = "1.3.1"
    const val CORE_KTX_VERSION = "1.6.0"
    const val APPCOMPAT_VERSION = "1.3.1"
    const val MATERIAL_VERSION = "1.4.0"
    const val LIFECYCLE_KTX_VERSION = "2.3.1"
    const val JUNIT_VERSION = "4.13.2"
    const val TEST_EXT_VERSION = "1.1.3"
    const val ESPRESSO_CORE_VERSION = "3.4.0"
    const val MOCKITO_VERSION = "3.2.0"
    const val DOKKA_VERSION = "1.5.0"
    const val PUBLISH_VERSION = "0.18.0"
    const val GITHUB_RELEASE_VERSION = "2.2.12"
    const val SAVED_STATE_VERSION = "2.3.1"
}

object ComposeDependencies {
    const val COMPOSE_UI =
        "androidx.compose.ui:ui:${DependenciesVersion.COMPOSE_VERSION}"
    const val COMPOSE_MATERIAL =
        "androidx.compose.material:material:${DependenciesVersion.COMPOSE_VERSION}"
    const val COMPOSE_MATERIAL_ICONS =
        "androidx.compose.material:material-icons-extended:${DependenciesVersion.COMPOSE_VERSION}"
    const val COMPOSE_TOOLING_PREVIEW =
        "androidx.compose.ui:ui-tooling-preview:${DependenciesVersion.COMPOSE_VERSION}"
    const val COMPOSE_ACTIVITY =
        "androidx.activity:activity-compose:${DependenciesVersion.COMPOSE_ACTIVITY_VERSION}"
    const val COMPOSE_UI_TOOLING =
        "androidx.compose.ui:ui-tooling:${DependenciesVersion.COMPOSE_VERSION}"
}

object AndroidXDependencies {
    const val CORE_KTX =
        "androidx.core:core-ktx:${DependenciesVersion.CORE_KTX_VERSION}"
    const val APPCOMPAT =
        "androidx.appcompat:appcompat:${DependenciesVersion.APPCOMPAT_VERSION}"
    const val MATERIAL =
        "com.google.android.material:material:${DependenciesVersion.MATERIAL_VERSION}"
    const val LIFECYCLE_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${DependenciesVersion.LIFECYCLE_KTX_VERSION}"
    const val SAVED_STATE =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${DependenciesVersion.SAVED_STATE_VERSION}"
}

object UnitTestDependencies {
    const val JUNIT = "junit:junit:${DependenciesVersion.JUNIT_VERSION}"
    const val MOCKITO = "org.mockito.kotlin:mockito-kotlin:${DependenciesVersion.MOCKITO_VERSION}"
}

object AndroidTestDependencies {
    const val EXT_JUNIT =
        "androidx.test.ext:junit:${DependenciesVersion.TEST_EXT_VERSION}"
    const val ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${DependenciesVersion.ESPRESSO_CORE_VERSION}"
    const val COMPOSE_UI_TEST =
        "androidx.compose.ui:ui-test-junit4:${DependenciesVersion.COMPOSE_VERSION}"
}