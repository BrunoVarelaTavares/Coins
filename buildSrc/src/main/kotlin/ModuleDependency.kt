import kotlin.reflect.full.memberProperties

private const val FEATURE_PREFIX = ":feature_"

// "Module" means "project" in terminology of Gradle API. To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object ModuleDependency {
    // All consts are accessed via reflection
    const val APP = ":app"
    const val FEATURE_HOME = ":feature_home"
    const val FEATURE_PORTFOLIO = ":feature_portfolio"
    const val FEATURE_PRICES = ":feature_prices"
    const val FEATURE_SETTINGS = ":feature_settings"
    const val FEATURE_COIN_DETAIL = ":feature_coin_detail"
    const val FEATURE_DATABASE = ":feature_database"
    const val LIBRARY_BASE = ":library_base"
    const val LIBRARY_TEST_UTILS = ":library_test_utils"

    // False positive" function can be private"
    // See: https://youtrack.jetbrains.com/issue/KT-33610
    fun getAllModules() = ModuleDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()

    fun getDynamicFeatureModules() = getAllModules()
        .filter { it.startsWith(FEATURE_PREFIX) }
        .toSet()
}
