import java.net.URL

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(AppPlugins.AGP)
        classpath(AppPlugins.KGP)
    }
}

plugins {
    id("org.jetbrains.dokka") version "1.5.0"
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            allWarningsAsErrors = true
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn", "-Xjvm-default=all")
        }
    }
    afterEvaluate {
        if (plugins.hasPlugin(AppPlugins.DOKKA)) {
            tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
                dokkaSourceSets {
                    named("main") {
                        failOnWarning.set(true)
                        reportUndocumented.set(true)
                        skipEmptyPackages.set(true)
                        skipDeprecated.set(true)
                        jdkVersion.set(8)
                        noAndroidSdkLink.set(false)
                        samples.from(rootProject.file("app/src/main/java/"))
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
                outputDirectory.set(file("../docs"))
            }
        }
    }
}