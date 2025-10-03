package net.twisterrob.detekt.testing

import dev.detekt.test.TestConfig
import net.twisterrob.detekt.testing.rules.ChillRule
import net.twisterrob.detekt.testing.rules.UptightFileRule
import net.twisterrob.detekt.testing.rules.UptightFunRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test

/**
 * @see lint
 */
class LintTest {

	@Test
	fun `lint returns no violations for good code`() {
		val findings = lint<ChillRule>(originalCode = "")

		assertThat(findings, empty())
	}

	@Test
	fun `lint returns a violation for bad code`() {
		val findings = lint<UptightFileRule>(originalCode = "")

		assertThat(findings, hasSize(1))
	}

	@Test
	fun `lint returns multiple violations for bad code`() {
		val findings = lint<UptightFunRule>(
			originalCode = """
				fun a() { }
				fun b() { }
			""".trimIndent()
		)

		assertThat(findings, hasSize(2))
	}

	@Test
	fun `lint passes config to rule`() {
		val config = TestConfig("extra" to "test")
		val findings = lint<UptightFileRule>(originalCode = "", config = config)
		assertThat(findings, hasSize(1))
		assertThat(findings[0].message, equalTo(UptightFileRule.MESSAGE + "test"))
	}
}
