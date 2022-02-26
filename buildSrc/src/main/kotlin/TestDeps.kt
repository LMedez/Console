object TestDeps {
    object JUnit {
        private const val VERSION = "4.12"
        const val JUNIT = "junit:junit:$VERSION"
        const val JUNIT_EXT = "androidx.test.ext:junit-ktx:1.1.3"
    }

    object Kotlinx {
        private const val VERSION = Deps.Kotlinx.Coroutines.VERSION
        const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$VERSION"
    }

    object AndroidX {
        object Arch {
            private const val VERSION = "2.1.0"
            const val CORE_TESTING = "androidx.arch.core:core-testing:${VERSION}"
        }
    }

    object Truth {
        private const val VERSION = "1.1.3"
        const val TRUTH = "com.google.truth:truth:$VERSION"
    }

    object Mockito {
        const val KOTLIN = "org.mockito.kotlin:mockito-kotlin:4.0.0"
        const val CORE = "org.mockito:mockito-core:4.3.1"
        const val MOCKK = "io.mockk:mockk:1.12.2"
    }
}