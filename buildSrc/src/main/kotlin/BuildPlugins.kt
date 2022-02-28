object BuildPlugins {
    const val ANDROID = "com.android.tools.build:gradle:7.0.4"
    const val SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:2.4.0"
    const val GMS = "com.google.gms:google-services:4.3.10"
    const val GRADLE_PUBLISHER = "com.github.triplet.gradle:play-publisher:3.7.0"

    interface BuildPlugin {
        val ID: String
        val VERSION: String
        val APPLY: Boolean
            get() = true
    }

    object Kotlin : BuildPlugin {
        override val ID = "gradle-plugin"
        override val VERSION = "1.6.10"
    }

    object GradlePublisher: BuildPlugin {
        override val VERSION = "3.7.0"
        override val ID = "com.github.triplet.play"
    }

    object SafeArgs : BuildPlugin {
        override val ID = "androidx.navigation.safeargs"
        override val VERSION = "2.4.0"
    }

    private object Detekt : BuildPlugin {
        override val ID: String = "io.gitlab.arturbosch.detekt"
        override val VERSION: String = "1.17.0"
        override val APPLY: Boolean = false
    }

    private object Spotless : BuildPlugin {
        override val ID: String = "com.diffplug.gradle.spotless"
        override val VERSION: String = "4.5.1"
    }

    private object DependencyAnalysis : BuildPlugin {
        override val ID: String = "com.autonomousapps.dependency-analysis"
        override val VERSION: String = "0.73.0"
        override val APPLY: Boolean = false
    }

    val plugins = listOf(Spotless, Detekt, DependencyAnalysis)
}