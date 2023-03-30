package net.twisterrob.detekt.testing

import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.test.TestConfig
import net.twisterrob.detekt.testing.rules.ChillRule
import net.twisterrob.detekt.testing.rules.HodorRule
import net.twisterrob.detekt.testing.rules.UptightFileRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * @see fix
 * @see Rule.fix
 */
@ExtendWith(PsiTestingExtension::class)
class FixTest {

	/**
	 * @see fix
	 */
	@Nested
	inner class `generic fix` {

		@Test
		fun `fix passes config to rule`() {
			val originalCode = """
				fun main() {
					println()
				}
			""".trimIndent()

			@Language("kotlin")
			val expectedCode = """
				fun test() {
					test()
				}
			""".trimIndent()

			val config = TestConfig("replacement" to "test")
			val fixedCode = fix<HodorRule>(config = config, originalCode = originalCode)

			assertThat(fixedCode, equalTo(expectedCode))
		}

		@Test
		fun `returns original code when rule does not support autoCorrect`() {
			val originalCode = """
				fun main() {
					println("Hello, world!")
				}
			""".trimIndent()

			val fixedCode = fix<ChillRule>(originalCode = originalCode)

			assertThat(fixedCode, equalTo(originalCode))
		}

		@Test
		fun `returns original code when rule does not support autoCorrect even when there are findings`() {
			val originalCode = """
				fun main() {
					println("Hello, world!")
				}
			""".trimIndent()

			val fixedCode = fix<UptightFileRule>(originalCode = originalCode)

			assertThat(fixedCode, equalTo(originalCode))
		}

		@Test
		fun `returns modified code when rule supports autoCorrect`() {
			val originalCode = """
				fun main() {
					println()
				}
			""".trimIndent()

			@Language("kotlin")
			val expectedCode = """
				fun hodor() {
					hodor()
				}
			""".trimIndent()

			val fixedCode = fix<HodorRule>(originalCode = originalCode)

			assertThat(fixedCode, equalTo(expectedCode))
		}

		@Test
		fun `returns original code when rule supports autoCorrect but autoCorrect is off`() {
			val originalCode = """
				fun main() {
					println()
				}
			""".trimIndent()

			val fixedCode = fix<HodorRule>(originalCode = originalCode, autoCorrect = false)

			assertThat(fixedCode, equalTo(originalCode))
		}
	}

	/**
	 * @see Rule.fix
	 */
	@Nested
	inner class `fix on rule` {

		@Test
		fun `returns modified code when rule supports autoCorrect and autoCorrect is on`() {
			val originalCode = """
				fun main() {
					println()
				}
			""".trimIndent()

			@Language("kotlin")
			val expectedCode = """
				fun hodor() {
					hodor()
				}
			""".trimIndent()

			val rule = HodorRule(TestConfig("autoCorrect" to true))
			val fixedCode = rule.fix(originalCode)

			assertThat(fixedCode, equalTo(expectedCode))
		}

		@Test
		fun `returns original code when rule supports autoCorrect but autoCorrect is off`() {
			val originalCode = """
				fun main() {
					println()
				}
			""".trimIndent()

			val rule = HodorRule(TestConfig("autoCorrect" to false))
			val fixedCode = rule.fix(originalCode)

			assertThat(fixedCode, equalTo(originalCode))
		}
	}
}
