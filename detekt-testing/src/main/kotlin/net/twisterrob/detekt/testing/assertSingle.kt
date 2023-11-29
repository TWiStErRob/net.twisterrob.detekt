package net.twisterrob.detekt.testing

import io.gitlab.arturbosch.detekt.api.Finding
import org.junit.jupiter.api.Assertions

/**
 * Short assertion for checking the [message] of a single one of the [findings].
 *
 * Implicitly validates that there is only one finding.
 */
public fun assertSingleMessage(findings: List<Finding>, message: String) {
	val finding = assertSingleFinding(findings)
	Assertions.assertEquals(message, finding.message) {
		"Finding message matches."
	}
}

/**
 * Short assertion for checking the [location] of a single one of the [findings].
 *
 * Implicitly validates that there is only one finding.
 */
public fun assertSingleHighlight(findings: List<Finding>, location: String) {
	val finding = assertSingleFinding(findings)
	Assertions.assertEquals("Test.kt\$$location", finding.entity.signature) {
		"Highlight location matches."
	}
}

private fun assertSingleFinding(findings: List<Finding>): Finding {
	Assertions.assertEquals(
		1,
		findings.size,
		findings.joinToString(prefix = "Found findings:\n", separator = "\n"),
	)
	return findings[0]
}
