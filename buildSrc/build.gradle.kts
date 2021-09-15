import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("libVersion-plugin") {
            id = "lib-version"
            implementationClass = "com.github.k0shk0sh.plugins.LibVersionPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
}