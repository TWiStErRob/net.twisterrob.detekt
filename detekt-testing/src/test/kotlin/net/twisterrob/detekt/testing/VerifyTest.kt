package net.twisterrob.detekt.testing

import io.gitlab.arturbosch.detekt.test.TestConfig
import net.twisterrob.detekt.testing.rules.ChillRule
import net.twisterrob.detekt.testing.rules.HodorRule
import net.twisterrob.detekt.testing.rules.UptightFileRule
import net.twisterrob.detekt.testing.rules.UptightFunRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError

/**
 * @see verifyNoFindings
 * @see verifySimpleFinding
 * @see verifyAutoCorrect
 * @see verifyNoChangesWithoutAutoCorrect
 */
class VerifyTest {

	/**
	 * @see verifyNoFindings
	 */
	@Nested
	inner class `verifyNoFindings function` {

		@Test
		fun `passes on no findings`() {
			verifyNoFindings<ChillRule>("")
		}

		@Test
		fun `fails on one finding`() {
			val failure = assertThrows<AssertionFailedError> {
				verifyNoFindings<UptightFileRule>("")
			}
			assertThat(failure.message, containsString(UptightFileRule().issue.toString()))
			assertThat(failure.message, containsString(UptightFileRule.MESSAGE))
		}

		@Test
		fun `passes config to rule`() {
			val config = TestConfig("extra" to "test")
			val failure = assertThrows<AssertionFailedError> {
				verifyNoFindings<UptightFileRule>(config = config, originalCode = "")
			}
			assertThat(failure.message, containsString(UptightFileRule.MESSAGE + "test"))
		}

		@Test
		fun `fails on multiple findings`() {
			val failure = assertThrows<AssertionFailedError> {
				verifyNoFindings<UptightFunRule>(
					"""
						fun aaa() { }
						fun bbb() { }
					""".trimIndent()
				)
			}

			assertThat(failure.message, containsString(UptightFunRule().issue.toString()))
			assertThat(failure.message, containsString(UptightFunRule.MESSAGE))
			assertThat(failure.message, containsString("aaa"))
			assertThat(failure.message, containsString("bbb"))
		}
	}

	/**
	 * @see verifySimpleFinding
	 */
	@Nested
	inner class `verifySimpleFinding function` {

		@Test
		fun `passes on one finding`() {
			verifySimpleFinding<UptightFileRule>(
				originalCode = "",
				message = UptightFileRule.MESSAGE,
				pointedCode = ".Test.kt",
			)
		}

		@Test
		fun `passes config to the rule`() {
			verifySimpleFinding<UptightFileRule>(
				config = TestConfig("extra" to "test"),
				originalCode = "",
				message = UptightFileRule.MESSAGE + "test",
				pointedCode = ".Test.kt",
			)
		}

		@Test
		fun `passes on one finding with autoCorrect`() {
			verifySimpleFinding<HodorRule>(
				originalCode = "fun main() { }",
				message = HodorRule.MESSAGE,
				pointedCode = "main",
				autoCorrectedCode = "fun hodor() { }",
			)
		}

		@Test
		fun `fails on no findings`() {
			assertThrows<AssertionFailedError> {
				verifySimpleFinding<ChillRule>(
					originalCode = "",
					message = "",
					pointedCode = "",
				)
			}
		}

		@Test
		fun `fails on multiple findings`() {
			val failure = assertThrows<AssertionFailedError> {
				verifySimpleFinding<UptightFunRule>(
					originalCode = """
						fun aaa() { }
						fun bbb() { }
					""".trimIndent(),
					message = UptightFunRule.MESSAGE,
					pointedCode = "",
				)
			}

			assertThat(failure.message, containsString(UptightFunRule().issue.toString()))
			assertThat(failure.message, containsString(UptightFunRule.MESSAGE))
			assertThat(failure.message, containsString("aaa"))
			assertThat(failure.message, containsString("bbb"))
		}

		@Test
		fun `fails when message expectation is wrong`() {
			val failure = assertThrows<AssertionFailedError> {
				verifySimpleFinding<UptightFileRule>(
					originalCode = "",
					message = HodorRule.MESSAGE,
					pointedCode = ".Test.kt",
				)
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

		@Test
		fun `fails when location expectation is wrong`() {
			val failure = assertThrows<AssertionFailedError> {
				verifySimpleFinding<UptightFileRule>(
					originalCode = "",
					message = UptightFileRule.MESSAGE,
					pointedCode = "file",
				)
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

		@Test
		fun `fails when autoCorrect is misbehaving`() {
			val failure = assertThrows<AssertionFailedError> {
				verifySimpleFinding<HodorRule>(
					originalCode = """fun main() { "Hello, world!" }""",
					message = HodorRule.MESSAGE,
					pointedCode = "main",
					autoCorrectedCode = """fun hodor() { "Hello, world!" }""",
				)
			}

			assertThat(
				"Show reason for failure.",
				failure.message,
				containsString("No changes with autoCorrect = false.")
			)
			assertThat(
				"Show original code.",
				failure.message,
				containsString("""fun main() { "Hello, world!" }""")
			)
			assertThat(
				"Show actual auto-corrected code.",
				failure.message,
				containsString("""fun main() { "hodor" }""")
			)
		}

		@Test
		fun `fails when autoCorrect expectation is wrong`() {
			val failure = assertThrows<AssertionFailedError> {
				verifySimpleFinding<HodorRule>(
					originalCode = """fun main(vararg args: String) { }""",
					message = HodorRule.MESSAGE,
					pointedCode = "main",
					autoCorrectedCode = """fun hodor(vararg hodor: String) { }""",
				)
			}

			assertThat(
				"Show reason for failure.",
				failure.message,
				containsString("Unexpected changes with autoCorrect = true.")
			)
			assertThat(
				"Show expected auto-corrected code.",
				failure.message,
				containsString("""fun hodor(vararg hodor: String) { }""")
			)
			assertThat(
				"Show actual auto-corrected code.",
				failure.message,
				containsString("""fun hodor(vararg args: String) { }""")
			)
		}
	}

	/**
	 * @see verifySingleFinding
	 */
	@Nested
	inner class `verifySingleFinding function` {

		@Test
		fun `passes on one finding`() {
			verifySingleFinding<UptightFileRule>(
				originalCode = "",
				message = UptightFileRule.MESSAGE,
				pointedCode = ".Test.kt",
			)
		}

		@Test
		fun `passes config to the rule`() {
			verifySingleFinding<UptightFileRule>(
				config = TestConfig("extra" to "test"),
				originalCode = "",
				message = UptightFileRule.MESSAGE + "test",
				pointedCode = ".Test.kt",
			)
		}

		@Test
		fun `fails on no findings`() {
			assertThrows<AssertionFailedError> {
				verifySingleFinding<ChillRule>(
					originalCode = "",
					message = "",
					pointedCode = "",
				)
			}
		}

		@Test
		fun `fails on multiple findings`() {
			val failure = assertThrows<AssertionFailedError> {
				verifySingleFinding<UptightFunRule>(
					originalCode = """
						fun aaa() { }
						fun bbb() { }
					""".trimIndent(),
					message = UptightFunRule.MESSAGE,
					pointedCode = "",
				)
			}

			assertThat(failure.message, containsString(UptightFunRule().issue.toString()))
			assertThat(failure.message, containsString(UptightFunRule.MESSAGE))
			assertThat(failure.message, containsString("aaa"))
			assertThat(failure.message, containsString("bbb"))
		}

		@Test
		fun `fails when message expectation is wrong`() {
			val failure = assertThrows<AssertionFailedError> {
				verifySingleFinding<UptightFileRule>(
					originalCode = "",
					message = HodorRule.MESSAGE,
					pointedCode = ".Test.kt",
				)
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

		@Test
		fun `fails when location expectation is wrong`() {
			val failure = assertThrows<AssertionFailedError> {
				verifySingleFinding<UptightFileRule>(
					originalCode = "",
					message = UptightFileRule.MESSAGE,
					pointedCode = "file",
				)
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

	/**
	 * @see verifyAutoCorrect
	 */
	@Nested
	inner class `verifyAutoCorrect function` {

		@Test
		fun `passes when code is modified`() {
			verifyAutoCorrect<HodorRule>(
				originalCode = """
					fun main() {
						println("Hello, world!")
					}
				""".trimIndent(),
				autoCorrectedCode = """
					fun hodor() {
						hodor("hodor")
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `fails when code is not modified`() {
			val failure = assertThrows<AssertionFailedError> {
				verifyAutoCorrect<ChillRule>(
					originalCode = "fun originalCode() { }",
					autoCorrectedCode = "fun autoCorrectedCode() { }",
				)
			}

			assertThat(failure.message, containsString("Unexpected changes with autoCorrect = true."))
			assertThat(failure.message, containsString("fun originalCode() { }"))
			assertThat(failure.message, containsString("fun autoCorrectedCode() { }"))
		}
	}

	/**
	 * @see verifyNoChangesWithoutAutoCorrect
	 */
	@Nested
	inner class `verifyNoChangesWithoutAutoCorrect function` {

		@Test
		fun `passes when code is not modified`() {
			verifyNoChangesWithoutAutoCorrect<ChillRule>(
				"""
					fun main() {
						println("Hello, world!")
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `passes rule supports autoCorrect and autoCorrect is implemented correctly`() {
			verifyNoChangesWithoutAutoCorrect<HodorRule>(
				"""
					fun main() {
						// Not using a string, as that's implemented incorrectly.
						println()
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `passes rule supports autoCorrect but autoCorrect is not implemented correctly`() {
			val failure = assertThrows<AssertionFailedError> {
				verifyNoChangesWithoutAutoCorrect<HodorRule>(
					"""fun main() { println("Hello, world!") }""",
				)
			}

			assertThat(
				"Show reason for failure.",
				failure.message,
				containsString("No changes with autoCorrect = false.")
			)
			assertThat(
				"Show original code.",
				failure.message,
				containsString("""fun main() { println("Hello, world!") }""")
			)
			assertThat(
				"Show actual auto-corrected code.",
				failure.message,
				containsString("""fun main() { println("hodor") }""")
			)
		}
	}
}
