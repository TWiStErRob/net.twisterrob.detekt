package net.twisterrob.detekt.testing

import net.twisterrob.detekt.testing.rules.ChillRule
import net.twisterrob.detekt.testing.rules.UptightFunRule
import net.twisterrob.detekt.testing.rules.UptightFileRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test

/**
 * @see lint
 */
class LintTest {

	@Test
	fun `lint returns no violations for good code`() {
		val findings = lint<ChillRule>("")

		assertThat(findings, empty())
	}

	@Test
	fun `lint returns a violation for bad code`() {
		val findings = lint<UptightFileRule>("")

		assertThat(findings, hasSize(1))
	}

	@Test
	fun `lint returns multiple violations for bad code`() {
		val findings = lint<UptightFunRule>(
			"""
				fun a() { }
				fun b() { }
			""".trimIndent()
		)

		assertThat(findings, hasSize(2))
	}
}
