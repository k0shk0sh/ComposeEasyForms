import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(AppPlugins.AGP)
        classpath(AppPlugins.KGP)
        classpath(AppPlugins.PUBLISH)
    }
}

plugins {
    id(AppPlugins.DOKKA) version DependenciesVersion.DOKKA_VERSION
    id(AppPlugins.GITHUB_RELEASE) version DependenciesVersion.GITHUB_RELEASE_VERSION
    id(AppPlugins.EASY_VERSION) version DependenciesVersion.EASY_VERSION
}

subprojects {
    configureKotlinCompile()
    afterEvaluate {
        configureDokka()
    }
}

easyVersion {
    setToProjectVersion = true
    logVersion = true
}

githubRelease {
    val version =  { project.version.toString() }
    token(System.getenv("GITHUB_TOKEN"))
    owner("k0shk0sh")
    repo("ComposeEasyForms")
    tagName(version)
    targetCommitish("main")
    releaseName(version)
    body(changelog())
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

fun Project.configureKotlinCompile() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            allWarningsAsErrors = true
            jvmTarget = AppConfig.JVM_TARGET
            freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn", "-Xjvm-default=all")
        }
    }
}