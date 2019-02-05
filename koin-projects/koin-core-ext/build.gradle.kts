plugins {
  kotlin("jvm")
}

description = "Koin - simple dependency injection for Kotlin - koin-core-ext"

dependencies {
  // Koin
  compile(project(":koin-core"))
  testCompile(project(":koin-test"))
}