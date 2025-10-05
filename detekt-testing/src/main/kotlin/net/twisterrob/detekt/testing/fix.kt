package net.twisterrob.detekt.testing

import io.github.detekt.test.utils.compileContentForTest
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.core.config.CompositeConfig
import io.gitlab.arturbosch.detekt.test.TestConfig
import net.twisterrob.detekt.testing.internal.newInstance
import org.intellij.lang.annotations.Language
import javax.annotation.CheckReturnValue

/**
 * Run the rule [T] with [Rule.autoCorrect] turned on.
 *
 * It is recommended to use [PsiTestingExtension] to when this function is used.
 *
 * @return the modified code
 */
@CheckReturnValue
public inline fun <reified T : Rule> fix(
	config: Config = Config.empty,
	@Language("kotlin") originalCode: String,
	autoCorrect: Boolean = true,
): String {
	val realConfig =
		if (autoCorrect) {
			CompositeConfig(lookFirst = TestConfig("autoCorrect" to true), lookSecond = config)
		} else {
			config
		}

	val rule = T::class.newInstance(realConfig)
	return rule.fix(originalCode.trimIndent())
}

/**
 * Run with [this] with [Rule.autoCorrect] turned on.
 *
 * It is recommended to use [PsiTestingExtension] to when this function is used.
 *
 * @return the modified code
 */
public fun Rule.fix(@Language("kotlin") content: String): String {
	val ktFile = compileContentForTest(content.trimIndent())
	visit(ktFile)
	return ktFile.text
}
