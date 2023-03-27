package net.twisterrob.detekt.testing

import net.twisterrob.detekt.testing.rules.HodorRule
import net.twisterrob.detekt.testing.rules.UptightFunRule
import net.twisterrob.detekt.testing.rules.UptightFileRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError

/**
 * @see assertSingleMessage
 * @see assertSingleHighlight
 */
class AssertSingleTest {

	@Nested
	inner class `assertSingleMessage function` {

		@Test
		fun `passes when single message matches`() {
			val findings = lint<UptightFileRule>("")

			assertSingleMessage(findings, UptightFileRule.MESSAGE)
		}

		@Test
		fun `fails on multiple findings`() {
			val findings = lint<UptightFunRule>(
				"""
					fun aaa() { }
					fun bbb() { }
				""".trimIndent()
			)

			val failure = assertThrows<AssertionFailedError> {
				assertSingleMessage(findings, UptightFunRule.MESSAGE)
			}

			assertThat(failure.message, containsString(UptightFunRule().issue.toString()))
			assertThat(failure.message, containsString(UptightFunRule.MESSAGE))
			assertThat(failure.message, containsString("aaa"))
			assertThat(failure.message, containsString("bbb"))
		}

		@Test
		fun `fails when message expectation is wrong`() {
			val findings = lint<UptightFileRule>("")

			val failure = assertThrows<AssertionFailedError> {
				assertSingleMessage(findings, HodorRule.MESSAGE)
			}

			assertThat(
				"Show reason for failure.",
				failure.message,
				containsString("Finding message matches.")
			)
			assertThat(
				"Show expected message.",
				failure.message,
				containsString(HodorRule.MESSAGE)
			)
			assertThat(
				"Show actual message.",
				failure.message,
				containsString(UptightFileRule.MESSAGE)
			)
		}
	}

	/**
	 * @see assertSingleHighlight
	 */
	@Nested
	inner class `assertSingleHighlight function` {

		@Test
		fun `passes when single message matches`() {
			val findings = lint<UptightFileRule>("")

			assertSingleHighlight(findings, ".Test.kt")
		}

		@Test
		fun `fails on multiple findings`() {
			val findings = lint<UptightFunRule>(
				"""
					fun aaa() { }
					fun bbb() { }
				""".trimIndent()
			)

			val failure = assertThrows<AssertionFailedError> {
				assertSingleHighlight(findings, UptightFunRule.MESSAGE)
			}

			assertThat(failure.message, containsString(UptightFunRule().issue.toString()))
			assertThat(failure.message, containsString(UptightFunRule.MESSAGE))
			assertThat(failure.message, containsString("aaa"))
			assertThat(failure.message, containsString("bbb"))
		}

		@Test
		fun `fails when location expectation is wrong`() {
			val findings = lint<UptightFileRule>("")

			val failure = assertThrows<AssertionFailedError> {
				assertSingleHighlight(findings, "file")
			}

			assertThat(
				"Show reason for failure.",
				failure.message,
				containsString("Highlight location matches.")
			)
			assertThat(
				"Show expected location.",
				failure.message,
				containsString("""Test.kt${'$'}file""")
			)
			assertThat(
				"Show actual location.",
				failure.message,
				containsString("""Test.kt${'$'}.Test.kt""")
			)
		}
	}
}
