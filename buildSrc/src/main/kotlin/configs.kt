import org.gradle.api.JavaVersion

object AppConfig {
    const val EXAMPLE_PACKAGE_NAME = "com.fastaccess.compose.forms.example"
    const val Lib_PACKAGE_NAME = "com.fastaccess.compose.easyforms"
    const val COMPILE_SDK = 31
    const val MIN_SDK = 21
    const val TARGET_SDK = COMPILE_SDK
    const val JVM_TARGET = "1.8"
    val javaCompileOptions = JavaVersion.VERSION_1_8
}

object AppPlugins {
    const val ANDROID_APP = "com.android.application"
    const val ANDROID_LIB = "com.android.library"
    const val KOTLIN_ANDROID = "kotlin-android"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val DOKKA = "org.jetbrains.dokka"
    const val DOKKA_CLASSPATH = "org.jetbrains.dokka:dokka-gradle-plugin:1.5.0"
    const val AGP = "com.android.tools.build:gradle:${DependenciesVersion.AGP_VERSION}"
    const val KGP = "org.jetbrains.kotlin:kotlin-gradle-plugin:${DependenciesVersion.KGP_VERSION}"
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