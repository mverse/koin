plugins {
  id("io.mverse.project")
  kotlin("jvm")
}

description = "Koin - simple dependency injection for Kotlin"

repositories {
  mavenLocal()
}
mverse {
  isDefaultDependencies = false
  coverageRequirement = 0.60
  dependencies {
    compile(kotlinStdlib())
    testCompile(kotlinTest())
    testCompile("kotlinx-coroutines-core")
  }
}

dependencyManagement {
  dependencies {
    val kotlinCoroutines: String by project
    val kotlin: String by project
    val kotlinIO: String by project

    // None
    dependencySet("org.jetbrains.kotlin:$kotlin") {
      entry("kotlin-stdlib")
      entry("kotlin-runtime")
      entry("kotlin-stdlib-common")
      entry("kotlin-stdlib-jdk7")
      entry("kotlin-stdlib-jdk8")
      entry("kotlin-reflect")
      entry("kotlin-test-annotations-common")
      entry("kotlin-test")
      entry("kotlin-test-junit")
    }

    dependencySet("org.jetbrains.kotlinx:$kotlinCoroutines") {
      entry("kotlinx-coroutines-core")
      entry("kotlinx-coroutines-core-common")
      entry("kotlinx-coroutines-jdk8")
    }

    dependencySet("org.jetbrains.kotlinx:$kotlinIO") {
      entry("kotlinx-io")
      entry("kotlinx-io-jvm")
      entry("kotlinx-coroutines-io")
      entry("kotlinx-coroutines-io-jvm")
    }
  }

}
