import org.jetbrains.dokka.gradle.DokkaTask
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
    id(AppPlugins.DOKKA) version DependenciesVersion.DOKKA_VERSION
    id(AppPlugins.PUBLISH) version DependenciesVersion.PUBLISH_VERSION
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            allWarningsAsErrors = true
            jvmTarget = AppConfig.JVM_TARGET
            freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn", "-Xjvm-default=all")
        }
    }
    afterEvaluate {
        configureDokka()
    }
}

fun Project.configureDokka() {
    if (plugins.hasPlugin(AppPlugins.DOKKA)) {
        tasks.withType<DokkaTask>().configureEach {
            dokkaSourceSets {
                named(DokkaConfig.MAIN) {
                    failOnWarning.set(true)
                    reportUndocumented.set(true)
                    skipEmptyPackages.set(true)
                    skipDeprecated.set(true)
                    jdkVersion.set(8)
                    noAndroidSdkLink.set(false)
                    samples.from(rootProject.file(DokkaConfig.SAMPLES))
                    externalDocumentationLink {
                        url.set(URL(DokkaConfig.ANDROID_REF_URL))
                        packageListUrl.set(URL(DokkaConfig.ANDROIDX_REF_URL))
                    }
                    externalDocumentationLink {
                        url.set(URL(DokkaConfig.ANDROID_KOTLIN_REF_URL))
                        packageListUrl.set(URL(DokkaConfig.ANDROIDX_KOTLIN_REF_URL))
                    }
                    sourceLink {
                        localDirectory.set(project.file(DokkaConfig.LOCAL_SRC_DIR))
                        remoteUrl.set(URL(DokkaConfig.REMOTE_SRC_DIR.format(project.name)))
                        remoteLineSuffix.set(DokkaConfig.LINE_NUM)
                    }
                }
            }
            outputDirectory.set(file(DokkaConfig.OUTPUT_DIR))
        }
    }
}