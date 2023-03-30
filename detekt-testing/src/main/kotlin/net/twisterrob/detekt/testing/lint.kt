package net.twisterrob.detekt.testing

import io.gitlab.arturbosch.detekt.api.BaseRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.intellij.lang.annotations.Language
import javax.annotation.CheckReturnValue
import kotlin.reflect.KClass

/**
 * Generic wrapper for instantiating and running a rule.
 */
@CheckReturnValue
inline fun <reified T : BaseRule> lint(
	@Language("kotlin") originalCode: String,
	config: Config = Config.empty,
): List<Finding> {
	@Suppress("DEPRECATION_ERROR")
	val rule = T::class.newInstance(config)
	return rule.compileAndLint(originalCode)
}

@CheckReturnValue
@Deprecated("Do not use this method, only visible so that inlining works.", level = DeprecationLevel.ERROR)
@Suppress("UndocumentedPublicFunction") // Only public because of inlining.
fun <T : BaseRule> KClass<T>.newInstance(config: Config): T {
	val constructor =
		this.constructors.singleOrNull { it.parameters.size == 1 && it.parameters[0].type.classifier == Config::class }
			?: error("Rule ${this} should have a constructor where Config is the only parameter.")

	return constructor.call(config)
}
