package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import net.twisterrob.detekt.testing.PsiTestingExtension
import net.twisterrob.detekt.testing.verifySimpleFinding
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito

/**
 * @see CalisthenicsNoElseRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsNoElseRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsNoElseRule().issue) {
				assertThat(id, equalTo("CalisthenicsNoElse"))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #2 - Don't use the ELSE keyword."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsNoElseRule().ruleSetConfig, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock()

			val issue = CalisthenicsNoElseRule(mockConfig)

			assertThat(issue.ruleSetConfig, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {

		@Test
		fun `reports if-else statement`() {
			verifySimpleFinding<CalisthenicsNoElseRule>(
				originalCode = """
					fun f(value: String) {
						if (value.isEmpty()) {
							something()
						} else {
							somethingElse()
						}
					}
					fun something() { }
					fun somethingElse() { }
				""".trimIndent(),
				message = "Object Calisthenics: Rule #2 - Don't use the ELSE keyword.",
				pointedCode = "else",
			)
		}

		@Test
		fun `reports if-else body`() {
			verifySimpleFinding<CalisthenicsNoElseRule>(
				originalCode = """
					fun f(value: String): String =
						if (value.isEmpty()) {
							something()
						} else {
							somethingElse()
						}
					fun something(): String = "something"
					fun somethingElse(): String = "somethingElse"
				""".trimIndent(),
				message = "Object Calisthenics: Rule #2 - Don't use the ELSE keyword.",
				pointedCode = "else",
			)
		}

		@Test
		fun `reports if-else expression`() {
			verifySimpleFinding<CalisthenicsNoElseRule>(
				originalCode = """
					fun f(value: String): String {
						val result = if (value.isEmpty()) {
							something()
						} else {
							somethingElse()
						}
						return result
					}
					fun something(): String = "something"
					fun somethingElse(): String = "somethingElse"
				""".trimIndent(),
				message = "Object Calisthenics: Rule #2 - Don't use the ELSE keyword.",
				pointedCode = "else",
			)
		}

		@Test
		fun `reports ternary expression`() {
			verifySimpleFinding<CalisthenicsNoElseRule>(
				originalCode = """
					fun f(value: String): String? {
						val result = if (value.isEmpty()) something() else somethingElse()
						return result.takeIf { it.isNotEmpty() }
					}
					fun something(): String = "something"
					fun somethingElse(): String = "somethingElse"
				""".trimIndent(),
				message = "Object Calisthenics: Rule #2 - Don't use the ELSE keyword.",
				pointedCode = "else",
			)
		}

		@Test
		fun `reports if-else-if statement`() {
			verifySimpleFinding<CalisthenicsNoElseRule>(
				originalCode = """
					fun f(value: String) {
						if (value.isEmpty()) {
							something()
						} else if (value.isNotEmpty()) {
							somethingElse()
						}
					}
					fun something(): String = "something"
					fun somethingElse(): String = "somethingElse"
				""".trimIndent(),
				message = "Object Calisthenics: Rule #2 - Don't use the ELSE keyword.",
				pointedCode = "else",
			)
		}
	}
}
