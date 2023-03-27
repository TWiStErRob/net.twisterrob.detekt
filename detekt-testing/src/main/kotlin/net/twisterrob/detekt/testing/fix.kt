package net.twisterrob.detekt.testing

import io.github.detekt.test.utils.compileContentForTest
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.test.TestConfig
import org.intellij.lang.annotations.Language
import javax.annotation.CheckReturnValue
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

/**
 * Run the rule [T] with [Rule.autoCorrect] turned on.
 *
 * It is recommended to use [PsiTestingExtension] to when this function is used.
 *
 * @return the modified code
 */
@CheckReturnValue
inline fun <reified T : Rule> fix(
	@Language("kotlin") originalCode: String,
	autoCorrect: Boolean = true,
): String {
	val sutChecker: T =
		if (autoCorrect) {
			T::class.primaryConstructor!!.call(TestConfig("autoCorrect" to true))
		} else {
			T::class.createInstance()
		}
	return sutChecker.fix(originalCode.trimIndent())
}

/**
 * Run with [this] with [Rule.autoCorrect] turned on.
 *
 * It is recommended to use [PsiTestingExtension] to when this function is used.
 *
 * @return the modified code
 */
fun Rule.fix(@Language("kotlin") content: String): String {
	val ktFile = compileContentForTest(content.trimIndent())
	visit(ktFile)
	return ktFile.text
}
