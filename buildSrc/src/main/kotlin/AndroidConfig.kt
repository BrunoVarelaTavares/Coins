object AndroidConfig{
    const val COMPILE_SDK_VERSION = 29
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 29
    const val BUILD_TOOLS_VERSION = "29.0.3"

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val ID = "com.btavares.coins"
    const val TEST_INSTRUMENTATION_RUNNER = "android.support.test.runner.AndroidJUnitRunner"

}

interface BuildType{

    companion object{
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnable: Boolean
/*    val isDebuggable: Boolean
    val isShrinkResources: Boolean*/
}


object BuildTypeDebug : BuildType {
    override val isMinifyEnable = false
/*    override val isDebuggable = true
    override val isShrinkResources = false*/
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnable = false
/*    override val isDebuggable = false
    override val isShrinkResources = false*/
}


object TestOptions {
    const val  IS_RETURN_DEFAULT_VALUES = false
}