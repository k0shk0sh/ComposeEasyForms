plugins {
    id(AppPlugins.ANDROID_APP)
    id(AppPlugins.KOTLIN_ANDROID)
    id(AppPlugins.KOTLIN_PARCELIZE)
}

kotlin {
    explicitApi()
}

android {
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.EXAMPLE_PACKAGE_NAME
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = 1
        versionName = "1.0"
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
}

dependencies {
//    implementation("com.github.k0shk0sh:compose-easyforms:<version>")
    implementation(project(":easyforms"))
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
}