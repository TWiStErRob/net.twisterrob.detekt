package net.twisterrob.detekt.calisthenics.rules

import dev.detekt.api.Config
import dev.detekt.api.RuleName
import net.twisterrob.detekt.testing.PsiTestingExtension
import net.twisterrob.detekt.testing.verifyNoFindings
import net.twisterrob.detekt.testing.verifySimpleFinding
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito

/**
 * @see CalisthenicsIndentRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsIndentRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsIndentRule()) {
				assertThat(ruleName, equalTo(RuleName("CalisthenicsIndent")))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #1 - One level of indentation per method."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsIndentRule().config, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock()

			val issue = CalisthenicsIndentRule(mockConfig)

			assertThat(issue.config, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {

		@Test
		fun `does not flag statement in top level function`() {
			verifyNoFindings<CalisthenicsIndentRule>(
				originalCode = """
					fun foo() {
						println()
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `does not flag if in top level function`() {
			verifyNoFindings<CalisthenicsIndentRule>(
				originalCode = """
					fun foo() {
						if (true) {
							println()
						}
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `does not flag statement in class member function`() {
			verifyNoFindings<CalisthenicsIndentRule>(
				originalCode = """
					class Foo {
						fun foo() {
							println()
						}
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `does not flag if in class member function`() {
			verifyNoFindings<CalisthenicsIndentRule>(
				originalCode = """
					class Foo {
						fun foo() {
							if (true) {
								println()
							}
						}
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `flags statement in double nested if`() {
			verifySimpleFinding<CalisthenicsIndentRule>(
				originalCode = """
					fun foo() {
						if (true) {
							if (true) {
								println()
							}
						}
					}
				""".trimIndent(),
				message = "Object Calisthenics: Rule #1 - One level of indentation per method.",
				pointedCode = "{ println() }",
			)
		}

		@Test
		fun `flag anonymous lambda inside lambda`() {
			verifySimpleFinding<CalisthenicsIndentRule>(
				originalCode = """
					fun f(list: List<Any>) {
						val lambdas = list.map {
							{
								println(it)
							}
						}
					}
				""".trimIndent(),
				message = "Object Calisthenics: Rule #1 - One level of indentation per method.",
				pointedCode = "println(it)",
			)
		}

		@Test
		fun `does not flag statement in for loop`() {
			verifyNoFindings<CalisthenicsIndentRule>(
				originalCode = """
					fun foo() {
						for (j in 0..10) {
							println()
						}
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `flags statement in double nested for`() {
			verifySimpleFinding<CalisthenicsIndentRule>(
				originalCode = """
					fun foo() {
						for (i in 0..10) {
							for (j in 0..10) {
								println()
							}
						}
					}
				""".trimIndent(),
				message = "Object Calisthenics: Rule #1 - One level of indentation per method.",
				pointedCode = "{ println() }",
			)
		}

		@Test
		fun `does not flag lambda`() {
			verifyNoFindings<CalisthenicsIndentRule>(
				originalCode = """
					fun foo(list: List<Any>) {
						list.forEach {
							println()
						}
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `flags lambda inside if`() {
			verifySimpleFinding<CalisthenicsIndentRule>(
				originalCode = """
					fun foo(list: List<Any>) {
						if (true) {
							list.forEach {
								println(it)
							}
						}
					}
				""".trimIndent(),
				message = "Object Calisthenics: Rule #1 - One level of indentation per method.",
				pointedCode = "println(it)",
			)
		}
	}
}
