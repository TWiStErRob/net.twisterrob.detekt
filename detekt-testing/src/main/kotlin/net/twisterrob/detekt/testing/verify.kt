package net.twisterrob.detekt.testing

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Rule
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions

/**
 * Test helper to verify that the [T] rule doesn't find any findings in the given [originalCode].
 */
inline fun <reified T : Rule> verifyNoFindings(
	@Language("kotlin") originalCode: String,
	config: Config = Config.empty,
) {
	val findings = lint<T>(originalCode, config)

	Assertions.assertEquals(
		0,
		findings.size,
		findings.joinToString(prefix = "Found findings:\n", separator = "\n"),
	)
}

/**
 * Test helper to verify that the [T] rule finds exactly one finding in the given [originalCode]
 * at location defined by [pointedCode] with expected [message].
 *
 * Optionally also verifies that [Rule.autoCorrect] is modifying the code correctly.
 * By default [autoCorrectedCode] will validate that the execution didn't change the code.
 * This is the expectation for most Detekt rules.
 * If the [Rule] supports [Rule.autoCorrect] then [autoCorrectedCode] should be provided.
 */
inline fun <reified T : Rule> verifySimpleFinding(
	config: Config = Config.empty,
	@Language("kotlin") originalCode: String,
	message: String,
	pointedCode: String,
	@Language("kotlin") autoCorrectedCode: String = originalCode,
) {
	verifySingleFinding<T>(config = config, originalCode = originalCode, message = message, pointedCode = pointedCode)
	verifyNoChangesWithoutAutoCorrect<T>(originalCode = originalCode)
	verifyAutoCorrect<T>(originalCode = originalCode, autoCorrectedCode = autoCorrectedCode)
}

/**
 * Test helper to verify that the [T] rule finds exactly one finding in the given [originalCode]
 * at location defined by [pointedCode] with expected [message].
 *
 * Usually this should not be called directly, use [verifySimpleFinding] instead.
 */
inline fun <reified T : Rule> verifySingleFinding(
	config: Config = Config.empty,
	@Language("kotlin") originalCode: String,
	message: String,
	pointedCode: String
) {
	val findings = lint<T>(originalCode, config)
	assertSingleMessage(findings, message)
	assertSingleHighlight(findings, pointedCode)
}

/**
 * Test helper to verify that the [T] rule fixes the [originalCode]
 * as expected in [autoCorrectedCode] when [Rule.autoCorrect] is enabled.
 *
 * Usually this should not be called directly, use [verifySimpleFinding] instead.
 */
inline fun <reified T : Rule> verifyAutoCorrect(
	@Language("kotlin") originalCode: String,
	@Language("kotlin") autoCorrectedCode: String,
) {
	val actualAutoCorrectedCode = fix<T>(originalCode.trimIndent(), autoCorrect = true)
	Assertions.assertEquals(autoCorrectedCode.trimIndent(), actualAutoCorrectedCode) {
		"Unexpected changes with autoCorrect = true."
	}
}

/**
 * Test helper to verify that the [T] rule does not modify the [originalCode] when [Rule.autoCorrect] is not enabled.
 *
 * Usually this should not be called directly, use [verifySimpleFinding] instead.
 */
@Suppress("FunctionMaxLength")
inline fun <reified T : Rule> verifyNoChangesWithoutAutoCorrect(
	@Language("kotlin") originalCode: String,
) {
	val actualNotCorrectedCode = fix<T>(originalCode, autoCorrect = false)
	Assertions.assertEquals(originalCode.trimIndent(), actualNotCorrectedCode) {
		"No changes with autoCorrect = false."
	}
}
