plugins {
    kotlin("jvm")
}

description = "Koin - simple dependency injection for Kotlin - koin-test"

mverse {
    dependencies {
        compile("junit")
        compile("mockito-inline")
        compile("mockito-core")
}   }
dependencies {
    // Koin
    compile(project(":koin-core"))
}