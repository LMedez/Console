plugins {
    id(ModulePlugins.ANDROID_LIBRARY)
    id("kotlin-parcelize")
    kotlin("android")
}

android {
}

dependencies {
    implementation(Deps.AndroidX.Room.COMMON)
    implementation(Deps.Koin.ANDROID)

    testImplementation(TestDeps.JUnit.JUNIT)
    testImplementation(TestDeps.Truth.TRUTH)
    testImplementation(TestDeps.Mockito.CORE)
    testImplementation(TestDeps.Mockito.MOCKK)
    testImplementation(TestDeps.Mockito.KOTLIN)
    testImplementation(TestDeps.AndroidX.Arch.CORE_TESTING)
    testImplementation(TestDeps.Kotlinx.COROUTINES_TEST)
}