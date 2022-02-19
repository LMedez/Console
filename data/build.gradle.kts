plugins {
    id(ModulePlugins.ANDROID_LIBRARY)
    kotlin("android")
    kotlin("kapt")
    /*
    * The google services plugin cannot function without the google-services.json file
    * so, add the gms file and apply the plugin
    *
    * id(ModulePlugins.GOOGLE_SERVICES)
    * */
}

android {
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(platform(Deps.Firebase.BOM))
    implementation(Deps.Firebase.FIRESTORE)
    implementation(Deps.Firebase.COROUTINES)
    implementation(Deps.Koin.ANDROID)
    implementation(Deps.Google.Gson.GSON)
    implementation(Deps.AndroidX.Room.ROOM_KTX)
    kapt(Deps.AndroidX.Room.COMPILER)

    testImplementation(TestDeps.JUnit.JUNIT)
    testImplementation(TestDeps.Truth.TRUTH)
    testImplementation(TestDeps.Mockito.CORE)
    testImplementation(TestDeps.Mockito.MOCKK)
    testImplementation(TestDeps.Mockito.KOTLIN)
    testImplementation(TestDeps.AndroidX.Arch.CORE_TESTING)
    testImplementation(TestDeps.Kotlinx.COROUTINES_TEST)
}