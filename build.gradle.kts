// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id(GradlePluginId.DETEKT)
    id(GradlePluginId.KTLINT_GRADLE)
    id(GradlePluginId.GRADLE_VERSION_PLUGIN)
    id(GradlePluginId.KOTLIN_JVM) apply false
    id(GradlePluginId.KOTLIN_ANDROID) apply false
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS) apply false
    id(GradlePluginId.ANDROID_APPLICATION) apply false
    id(GradlePluginId.ANDROID_DYNAMIC_FEATURE) apply false
    id(GradlePluginId.ANDROID_LIBRARY) apply false
    id(GradlePluginId.SAFE_ARGS) apply false
    id(GradlePluginId.KOTLIN_KAPT) apply false
}

// all projects = root project + sub projects
allprojects {
    repositories {
        google()
        jcenter()
        maven { url =
            uri("https://jitpack.io") }
    }

    // We want to apply ktlint at all project level because it also checks build gradle files
    apply(plugin = GradlePluginId.KTLINT_GRADLE)

    // Ktlint configuration for sub-projects
    ktlint {
        version.set(CoreVersion.KTLINT)
        verbose.set(true)
        android.set(true)

        // Uncomment below line and run .\gradlew ktlintCheck to see check ktlint experimental rules
        // enableExperimentalRules.set(true)

        reporters {
            reporter(ReporterType.CHECKSTYLE)
        }

        filter {
            exclude { element -> element.file.path.contains("generated/") }
        }
    }
}

subprojects {
    tasks.withType<Test> {
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    }

    apply(plugin = GradlePluginId.DETEKT)

}

// JVM target applied to all Kotlin tasks across all sub-projects
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

tasks {
    // Gradle versions plugin configuration
    "dependencyUpdates"(com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask::class) {
        resolutionStrategy {
            componentSelection {
                all {
                    // Do not show pre-release version of library in generated dependency report
                    val rejected = kotlin.collections.listOf(
                        "alpha",
                        "beta",
                        "rc",
                        "cr",
                        "m",
                        "preview"
                    )
                        .map { qualifier -> kotlin.text.Regex("(?i).*[.-]$qualifier[.\\d-]*") }
                        .any { it.matches(candidate.version) }
                    if (rejected) {
                        reject("Release candidate")
                    }

                    if (candidate.group == "com.pawegio.kandroid") {
                        reject("version ${candidate.version} is broken for ${candidate.group}'")
                    }
                }
            }
        }
    }
}
