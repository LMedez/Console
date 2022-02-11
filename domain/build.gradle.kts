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


}