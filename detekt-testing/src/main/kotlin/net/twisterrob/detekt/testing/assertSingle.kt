package net.twisterrob.detekt.testing

import dev.detekt.api.Finding
import dev.detekt.api.Rule
import org.junit.jupiter.api.Assertions
import kotlin.collections.joinToString
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 * Short assertion for checking no [findings] found.
 */
@PublishedApi
internal fun assertSize(expected: Int, findings: List<Finding>, rule: KClass<out Rule>) {
	Assertions.assertEquals(
		expected,
		findings.size,
		findings.joinToString(prefix = "Found findings for ${rule.jvmName}:\n", separator = "\n"),
	)
}

/**
 * Short assertion for checking the [message] of a single one of the [findings].
 *
 * Implicitly validates that there is only one finding.
 */
@PublishedApi
internal fun assertSingleMessage(findings: List<Finding>, message: String, rule: KClass<out Rule>) {
	val finding = assertSingleFinding(findings, rule)
	Assertions.assertEquals(message, finding.message) {
		"Finding message matches."
	}
}

/**
 * Short assertion for checking the [location] of a single one of the [findings].
 *
 * Implicitly validates that there is only one finding.
 */
@PublishedApi
internal fun assertSingleHighlight(findings: List<Finding>, location: String, rule: KClass<out Rule>) {
	val finding = assertSingleFinding(findings, rule)
	Assertions.assertEquals(location, finding.entity.signature) {
		"Highlight location matches."
	}
}

private fun assertSingleFinding(findings: List<Finding>, rule: KClass<out Rule>): Finding {
	assertSize(1, findings, rule)
	return findings[0]
}
