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

    api(TestDeps.JUnit.JUNIT)
    api(TestDeps.Truth.TRUTH)
}