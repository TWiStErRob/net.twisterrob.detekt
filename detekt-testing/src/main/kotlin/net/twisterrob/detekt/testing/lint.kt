package net.twisterrob.detekt.testing

import dev.detekt.api.Config
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import dev.detekt.test.compileAndLint
import net.twisterrob.detekt.testing.internal.newInstance
import org.intellij.lang.annotations.Language
import javax.annotation.CheckReturnValue

/**
 * Generic wrapper for instantiating and running a rule.
 */
@CheckReturnValue
public inline fun <reified T : Rule> lint(
	config: Config = Config.empty,
	@Language("kotlin") originalCode: String,
): List<Finding> {
	val rule = T::class.newInstance(config)
	return rule.compileAndLint(originalCode)
}
