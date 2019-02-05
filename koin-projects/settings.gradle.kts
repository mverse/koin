import com.sun.javafx.scene.CameraHelper.project
import org.gradle.kotlin.dsl.maven

rootProject.name = "koin"
include("koin-core")
include("koin-core-ext")
include("koin-test")


pluginManagement {
  repositories {
    jcenter()
    gradlePluginPortal()
    google()
    maven("https://dl.bintray.com/mverse-io/mverse-public")
    maven ("https://kotlin.bintray.com/kotlinx" )
  }

  val kotlin:String by settings
  val mversePlugin:String by settings

  val pluginVersionMap = mapOf(
      "kotlinx-serialization" to kotlin,
      "kotlin-multiplatform" to kotlin,
      "org.jetbrains.kotlin.jvm" to kotlin,
      "org.jetbrains.kotlin.common" to kotlin,
      "io.mverse.project" to mversePlugin,
      "io.mverse.multi-module" to mversePlugin,
      "io.mverse.multi-platform" to mversePlugin)

  resolutionStrategy {
    eachPlugin {

      if (requested.id.id in pluginVersionMap) {
        useVersion(pluginVersionMap[requested.id.id])
      }

      if (requested.id.id == "kotlin-multiplatform") {
        useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${target.version}")
      }

      if (requested.id.id == "kotlinx-serialization") {
        useModule("org.jetbrains.kotlin:kotlin-serialization:${target.version}")
      }
    }
  }
}


