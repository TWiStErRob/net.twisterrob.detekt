package net.twisterrob.detekt.testing

import io.gitlab.arturbosch.detekt.api.BaseRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.test.compileAndLint
import net.twisterrob.detekt.testing.internal.newInstance
import org.intellij.lang.annotations.Language
import javax.annotation.CheckReturnValue

/**
 * Generic wrapper for instantiating and running a rule.
 */
@CheckReturnValue
inline fun <reified T : BaseRule> lint(
	config: Config = Config.empty,
	@Language("kotlin") originalCode: String,
): List<Finding> {
	@Suppress("DEPRECATION_ERROR")
	val rule = T::class.newInstance(config)
	return rule.compileAndLint(originalCode)
}
