plugins {
    id(ModulePlugins.ANDROID_LIBRARY)
    kotlin("android")
}

android {
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(Deps.Koin.ANDROID)
    implementation(Deps.Kotlinx.Coroutines.CORE)
    implementation(Deps.AndroidX.Lifecycle.VIEWMODEL_KTX)
    implementation(Deps.AndroidX.Lifecycle.LIVEDATA_KTX)

    prodImplementation(Deps.Google.PlayCore.CORE)
    prodImplementation(Deps.Google.PlayCore.KTX)

    testImplementation(TestDeps.JUnit.JUNIT)
    testImplementation(TestDeps.Truth.TRUTH)
    testImplementation(TestDeps.Mockito.CORE)
    testImplementation(TestDeps.Mockito.MOCKK)
    testImplementation(TestDeps.Mockito.KOTLIN)
    testImplementation(TestDeps.AndroidX.Arch.CORE_TESTING)
    testImplementation(TestDeps.Kotlinx.COROUTINES_TEST)
}