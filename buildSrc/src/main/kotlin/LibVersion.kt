import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.io.File

data class LibVersion(
    val major: Int,
    val minor: Int,
    val patch: Int,
)

fun LibVersion.increaseMajor() = this.copy(major = major + 1, minor = 0, patch = 0)
fun LibVersion.increaseMinor() = this.copy(minor = minor + 1)
fun LibVersion.increasePatch() = this.copy(minor = patch + 1)
fun LibVersion.getLibVersion() = "$major.$minor.$patch"
fun LibVersion.toMap(): Map<String, Int> = mapOf(
    "major" to major, "minor" to minor, "patch" to patch,
)

object LibVersionProvider {
    private const val versionFile = "version.json"

    @Suppress("unchecked_cast")
    fun versionFile(): LibVersion {
        val json = JsonSlurper()
        val map = json.parse(File(versionFile)) as Map<String, Any>
        return LibVersion(
            major = map["major"] as Int,
            minor = map["minor"] as Int,
            patch = map["patch"] as Int,
        )
    }

    fun saveVersionFile(version: LibVersion) {
        val jsonData = JsonOutput.toJson(version.toMap())
        File(versionFile).writeText(JsonOutput.prettyPrint(jsonData))
    }
}