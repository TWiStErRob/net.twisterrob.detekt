package net.twisterrob.detekt.testing

import io.gitlab.arturbosch.detekt.api.BaseRule
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.test.lint
import org.intellij.lang.annotations.Language
import javax.annotation.CheckReturnValue
import kotlin.reflect.full.createInstance

/**
 * Generic wrapper for instantiating and running a rule.
 */
@CheckReturnValue
inline fun <reified T : BaseRule> lint(
	@Language("kotlin") originalCode: String,
): List<Finding> {
	val rule = T::class.createInstance()
	return rule.lint(originalCode)
}
