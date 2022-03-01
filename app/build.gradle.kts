plugins {
    id(ModulePlugins.ANDROID_APPLICATION)
    kotlin("android")
    kotlin("kapt")
    id(BuildPlugins.SafeArgs.ID)
    id(BuildPlugins.GradlePublisher.ID)
}

android {


    play {
        val apiKeyFile = project.property("googlePlayApiKey").toString()
        serviceAccountCredentials.set(file(apiKeyFile))
        track.set("internal")

        defaultToAppBundles.set(true)
    }

    viewBinding {
        isEnabled = true
    }

    dataBinding {
        isEnabled = true
    }

}


dependencies {
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":common"))

    implementation(kotlin("stdlib"))
    implementation(Deps.AndroidX.AppCompat.APPCOMPAT)
    implementation(Deps.Google.Material.MATERIAL)
    implementation(Deps.AndroidX.ConstraintLayout.CL)
    implementation(Deps.Koin.ANDROID)
    implementation(Deps.Kotlinx.Coroutines.CORE)
    implementation(Deps.AndroidX.Lifecycle.LIVEDATA_KTX)
    implementation(Deps.AndroidX.Navigation.FRAGMENT_KTX)
    implementation(Deps.AndroidX.Navigation.UI_KTX)
    implementation(Deps.Glide.GLIDE)
    implementation(Deps.Google.PlayCore.CORE)
    implementation(Deps.Google.PlayCore.KTX)


    androidTestImplementation(TestDeps.JUnit.JUNIT_EXT)
    androidTestImplementation(TestDeps.JUnit.JUNIT)
    androidTestImplementation(TestDeps.Kotlinx.COROUTINES_TEST)
    androidTestImplementation(TestDeps.Truth.TRUTH)
    androidTestImplementation(TestDeps.AndroidX.Arch.CORE_TESTING)
    androidTestImplementation(Deps.AndroidX.Room.ROOM_KTX)
    androidTestImplementation( "androidx.test.espresso:espresso-core:3.4.0")

    testImplementation(TestDeps.JUnit.JUNIT)
    testImplementation(TestDeps.Truth.TRUTH)
    testImplementation(TestDeps.Mockito.CORE)
    testImplementation(TestDeps.Mockito.MOCKK)
    testImplementation(TestDeps.Mockito.KOTLIN)
    testImplementation(TestDeps.AndroidX.Arch.CORE_TESTING)
    testImplementation(TestDeps.Kotlinx.COROUTINES_TEST)

    kapt(Deps.Glide.PROCESSOR)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

}
