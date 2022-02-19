plugins {
    id(ModulePlugins.ANDROID_LIBRARY)
    kotlin("android")
}

android {

}

dependencies {
    implementation(project(":common"))
    implementation(Deps.Koin.ANDROID)
    implementation(Deps.Kotlinx.Coroutines.ANDROID)

    testImplementation(TestDeps.JUnit.JUNIT)
    testImplementation(TestDeps.Truth.TRUTH)
    testImplementation(TestDeps.Mockito.CORE)
    testImplementation(TestDeps.Mockito.MOCKK)
    testImplementation(TestDeps.Mockito.KOTLIN)
    testImplementation(TestDeps.AndroidX.Arch.CORE_TESTING)
    testImplementation(TestDeps.Kotlinx.COROUTINES_TEST)
}