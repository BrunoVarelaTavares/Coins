import com.android.build.gradle.internal.dsl.BaseFlavor
import com.android.build.gradle.internal.dsl.DefaultConfig

plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
    id(GradlePluginId.KTLINT_GRADLE)
    id(GradlePluginId.SAFE_ARGS)
    id(GradlePluginId.KOTLIN_KAPT)
}


android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = AndroidConfig.ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        buildConfigFieldFromGradleProperty("coingeckoBaseUrl")
        buildConfigFieldFromGradleProperty("cryptocontrolBaseUrl")
        buildConfigFieldFromGradleProperty("cryptocontrolApiKey")
        buildConfigFieldFromGradleProperty("currencyconverterBaseUrl")
        buildConfigFieldFromGradleProperty("currencyconverterApiKey")

//
        buildConfigField("FEATURE_MODULE_NAMES", getDynamicFeatureModuleNames())
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            //Modificado
            isMinifyEnabled = BuildTypeRelease.isMinifyEnable
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            //Modificado
            isMinifyEnabled = BuildTypeDebug.isMinifyEnable
            /*isDebuggable = BuildTypeDebug.isDebuggable*/
        }

        testOptions {
            unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    // Each feature module that is included in settings.gradle.kts is added here as dynamic feature
    dynamicFeatures = ModuleDependency.getDynamicFeatureModules().toMutableSet()

    lintOptions {
        // By default lint does not check test sources, but setting this option means that lint will not even parse them
        isIgnoreTestSources = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}


dependencies {
    api(project(ModuleDependency.LIBRARY_BASE))

    implementation(LibraryDependency.OK_HTTP)
    implementation(LibraryDependency.LOGGING_INTERCEPTOR)
    implementation(LibraryDependency.PLAY_CORE)
    implementation(LibraryDependency.STETHO)
    implementation(LibraryDependency.STETHO_OK_HTTP)

    api(LibraryDependency.RETROFIT)
    api(LibraryDependency.RETROFIT_MOSHI_CONVERTER)
    api(LibraryDependency.SUPPORT_CONSTRAINT_LAYOUT)
    api(LibraryDependency.COORDINATOR_LAYOUT)
    api(LibraryDependency.RECYCLER_VIEW)
    api(LibraryDependency.MATERIAL)
    api(LibraryDependency.DESIGN)
    api(LibraryDependency.FRAGMENT_KTX)
    api(LibraryDependency.K_ANDROID)
    api(LibraryDependency.LOTTIE)
    api(LibraryDependency.THREE_TEN_ABP)
    api(LibraryDependency.ANDROID_X_BROWSER)
    api(LibraryDependency.MP_ANDROID_CHART)
    api(LibraryDependency.MT_COLLAPSINGTOOLBAR_SUBTITLE)
    api(LibraryDependency.SKYDOVES_EXPANDABLELAYOUT)
    api(LibraryDependency.ROOM_RUNTIME)
    api(LibraryDependency.ROOM_KTX)
    kapt(LibraryDependency.ROOM_COMPILER)



}

fun BaseFlavor.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
    buildConfigField("String", androidResourceName, propertyValue)
}

fun getDynamicFeatureModuleNames() = ModuleDependency.getDynamicFeatureModules()
    .map { it.replace(":feature_", "") }
    .toSet()

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }

fun DefaultConfig.buildConfigField(name: String, value: Set<String>) {
    val strValue = value.joinToString(prefix = "{", separator = ",", postfix = "}", transform = { "\"$it\"" })
    buildConfigField("String[]", name, strValue)
}