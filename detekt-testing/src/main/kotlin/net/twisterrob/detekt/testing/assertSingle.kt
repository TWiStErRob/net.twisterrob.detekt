package net.twisterrob.detekt.testing

import dev.detekt.api.Finding
import org.junit.jupiter.api.Assertions
import kotlin.collections.joinToString

/**
 * Short assertion for checking the [message] of a single one of the [findings].
 *
 * Implicitly validates that there is only one finding.
 */
public fun assertSingleMessage(findings: List<Finding>, message: String, ruleName: String? = null) {
	val finding = assertSingleFinding(findings, ruleName)
	Assertions.assertEquals(message, finding.message) {
		"Finding message matches."
	}
}

/**
 * Short assertion for checking the [location] of a single one of the [findings].
 *
 * Implicitly validates that there is only one finding.
 */
public fun assertSingleHighlight(findings: List<Finding>, location: String, ruleName: String? = null) {
	val finding = assertSingleFinding(findings, ruleName)
	Assertions.assertEquals(location, finding.entity.signature) {
		"Highlight location matches."
	}
}

private fun assertSingleFinding(findings: List<Finding>, ruleName: String? = null): Finding {

	val title = if (ruleName != null) "Found findings from ${ruleName}:" else "Found findings:"
	Assertions.assertEquals(
		1,
		findings.size,
		findings.joinToString(prefix = "${title}\n", separator = "\n"),
	)
	return findings[0]
}
