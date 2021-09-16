package com.github.k0shk0sh.plugins

import org.gradle.api.*

class LibVersionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.afterEvaluate {
            val libFile = LibVersionProvider.versionFile()
            setProjectVersion(target, libFile)
            tasks.create("nextMajor", nextMajor())
            tasks.create("nextMinor", nextMinor())
            tasks.create("nextPatch", nextPatch())
        }
    }

    private fun nextMajor(): (Task).() -> Unit = {
        group = "libVersion"
        description = "updating major version"
        actions = listOf(
            object : Action<Task> {
                override fun execute(t: Task) {
                    val nextMajor = LibVersionProvider.versionFile().increaseMajor()
                    LibVersionProvider.saveVersionFile(nextMajor)
                    setProjectVersion(t.project, nextMajor)
                }
            },
        )
    }

    private fun nextMinor(): (Task).() -> Unit = {
        group = "libVersion"
        description = "updating minor version"
        actions = listOf(
            object : Action<Task> {
                override fun execute(t: Task) {
                    val nextMinor = LibVersionProvider.versionFile().increaseMinor()
                    LibVersionProvider.saveVersionFile(nextMinor)
                    setProjectVersion(t.project, nextMinor)
                }
            },
        )
    }

    private fun nextPatch(): (Task).() -> Unit = {
        group = "libVersion"
        description = "updating patch version"
        actions = listOf(
            object : Action<Task> {
                override fun execute(t: Task) {
                    val nextPatch = LibVersionProvider.versionFile().increasePatch()
                    LibVersionProvider.saveVersionFile(nextPatch)
                    setProjectVersion(t.project, nextPatch)
                }
            },
        )
    }

    private fun setProjectVersion(
        project: Project,
        libFile: LibVersion,
    ) {
        val version = libFile.getLibVersion()
        project.version = version
        project.extensions.extraProperties.set("VERSION_NAME", version)
    }
}