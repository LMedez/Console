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
    testImplementation(TestDeps.Truth.TRUTH)
    testImplementation(TestDeps.Kotlinx.COROUTINES_TEST)
}