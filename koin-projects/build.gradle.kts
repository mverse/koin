plugins {
  id("io.mverse.project")
  id("io.mverse.multi-module")
  kotlin("jvm")
}

description = "Koin - simple dependency injection for Kotlin"

allprojects {
  repositories {
    mavenLocal()
  }

  mverse {
    isDefaultDependencies = false
    coverageRequirement = 0.01
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
      val mockito: String by project

      // Incremental versions of Mockito library not considered "notable", not included in JCenteral,
      // and not synced to Maven Central. See Mockito Continuous Delivery Pipeline 2.0 at
      // https://github.com/mockito/mockito/issues/911
      dependencySet("org.mockito:$mockito") {
        entry("mockito-inline")
        entry("mockito-core")
      }

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
}
