import com.github.triplet.gradle.androidpublisher.ReleaseStatus
import java.io.FileInputStream
import java.util.*

plugins {
    id(ModulePlugins.ANDROID_APPLICATION)
    kotlin("android")
    kotlin("kapt")
    id(BuildPlugins.SafeArgs.ID)
    id(BuildPlugins.GradlePublisher.ID)
}

android {
    // Create a variable called keystorePropertiesFile, and initialize it to your
    // keystore.properties file, in the rootProject folder.
    val keystorePropertiesFile = rootProject.file("keystore.properties")

    // Initialize a new Properties() object called keystoreProperties.
    val keystoreProperties = Properties()

    // Load your keystore.properties file into the keystoreProperties object.
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    play {
        val apiKeyFile = keystoreProperties.getProperty("GOOGLE_PLAY_API_KEY")
        serviceAccountCredentials.set(file(apiKeyFile))
        track.set("internal")
        releaseStatus.set(ReleaseStatus.DRAFT)
        updatePriority.set(2)
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
