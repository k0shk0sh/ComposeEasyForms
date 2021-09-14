import java.net.URL

plugins {
    id(AppPlugins.ANDROID_LIB)
    id(AppPlugins.KOTLIN_ANDROID)
    id(AppPlugins.DOKKA)
}

android {
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.javaCompileOptions
        targetCompatibility = AppConfig.javaCompileOptions
    }
    kotlinOptions {
        jvmTarget = AppConfig.JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = DependenciesVersion.COMPOSE_VERSION
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
        animationsDisabled = true
    }
}

dependencies {

    implementation(AndroidXDependencies.CORE_KTX)
    implementation(AndroidXDependencies.APPCOMPAT)
    implementation(AndroidXDependencies.MATERIAL)
    implementation(AndroidXDependencies.LIFECYCLE_KTX)
    implementation(ComposeDependencies.COMPOSE_UI)
    implementation(ComposeDependencies.COMPOSE_MATERIAL)
    implementation(ComposeDependencies.COMPOSE_MATERIAL_ICONS)
    implementation(ComposeDependencies.COMPOSE_TOOLING_PREVIEW)
    implementation(ComposeDependencies.COMPOSE_ACTIVITY)

    debugImplementation(ComposeDependencies.COMPOSE_UI_TOOLING)

    testImplementation(UnitTestDependencies.JUNIT)
    testImplementation(UnitTestDependencies.MOCKITO)

    androidTestImplementation(AndroidTestDependencies.EXT_JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO_CORE)
    androidTestImplementation(AndroidTestDependencies.COMPOSE_UI_TEST)
}

tasks.dokkaGfm.configure {
    dokkaSourceSets {
        named("main") {
            failOnWarning.set(true)
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
            skipDeprecated.set(true)
            jdkVersion.set(8)
            noAndroidSdkLink.set(false)
            samples.from(rootProject.file("../app/src/main/java/"))
            externalDocumentationLink {
                url.set(URL("https://developer.android.com/reference/"))
                packageListUrl.set(URL("https://developer.android.com/reference/androidx/package-list"))
            }
            externalDocumentationLink {
                url.set(URL("https://developer.android.com/reference/kotlin/"))
                packageListUrl.set(URL("https://developer.android.com/reference/kotlin/androidx/package-list"))
            }
            sourceLink {
                localDirectory.set(project.file("src/main/java"))
                remoteUrl.set(URL("https://github.com/k0shk0sh/ComposeEasyForms/blob/main/${project.name}/src/main/java"))
                remoteLineSuffix.set("#L")
            }
        }
    }
    outputDirectory.set(file("../dokka"))
}
