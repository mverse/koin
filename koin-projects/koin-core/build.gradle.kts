plugins {
  kotlin("jvm")
}

description = "Koin - simple dependency injection for Kotlin - koin-core"

mverse {
  dependencies {
    compile("kotlin-reflect")
  }
}
dependencies {
  // Koin
  testCompile(project(":koin-test"))
}