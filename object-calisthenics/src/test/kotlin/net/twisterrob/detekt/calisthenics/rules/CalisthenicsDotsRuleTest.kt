package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import net.twisterrob.detekt.testing.PsiTestingExtension
import net.twisterrob.detekt.testing.verifyNoFindings
import net.twisterrob.detekt.testing.verifySimpleFinding
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito

/**
 * @see CalisthenicsDotsRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsDotsRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsDotsRule().issue) {
				assertThat(id, equalTo("CalisthenicsDots"))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #5 - One dot per line."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsDotsRule().ruleSetConfig, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock()

			val issue = CalisthenicsDotsRule(mockConfig)

			assertThat(issue.ruleSetConfig, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {

		@Test
		fun `does not flag when there is no dot`() {
			verifyNoFindings<CalisthenicsDotsRule>(
				originalCode = """
					fun f() {
						val a = 1
						val b = a + 2
						println(b)
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `does not flag a single dot`() {
			verifyNoFindings<CalisthenicsDotsRule>(
				originalCode = """
					fun f() {
						val a = listOf(1)
						println(a.size)
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `does not flag a chained vals`() {
			verifyNoFindings<CalisthenicsDotsRule>(
				originalCode = """
					fun f() {
						val a = listOf(1)
						val s = a.size
						println(s.toString())
					}
				""".trimIndent(),
			)
		}

		@Test
		fun `flags two dots`() {
			verifySimpleFinding<CalisthenicsDotsRule>(
				originalCode = """
					fun f() {
						val a = listOf(1)
						println(a.size.toString())
					}
				""".trimIndent(),
				message = "Object Calisthenics: Rule #5 - One dot per line.",
				pointedCode = ".",
			)
		}

		@Test
		fun `does not count this dot`() {
			verifyNoFindings<CalisthenicsDotsRule>(
				originalCode = """
					fun String.f() =
						this.length.toString()
				""".trimIndent(),
			)
		}

		@Test
		fun `does not count this dot, but still flags long chains`() {
			verifySimpleFinding<CalisthenicsDotsRule>(
				originalCode = """
					fun String.f() =
						this.length.toString().toInt()
				""".trimIndent(),
				message = "Object Calisthenics: Rule #5 - One dot per line.",
				pointedCode = ".",
			)
		}

		@Test
		fun `does not flag packages`() {
			verifyNoFindings<CalisthenicsDotsRule>(
				originalCode = """
					package com.example.nested
				""".trimIndent(),
			)
		}

		@Test
		fun `does not flag imports`() {
			verifyNoFindings<CalisthenicsDotsRule>(
				originalCode = """
					import kotlin.text.Regex
				""".trimIndent(),
			)
		}

		@Test
		fun `does not flag import aliases`() {
			verifyNoFindings<CalisthenicsDotsRule>(
				originalCode = """
					import kotlin.text.Regex as RegexAlias
				""".trimIndent(),
			)
		}

		@Test
		fun `does not flag typealiases`() {
			verifyNoFindings<CalisthenicsDotsRule>(
				originalCode = """
					typealias RegexAlias = kotlin.text.Regex
				""".trimIndent(),
			)
		}

		@Disabled("Probably needs type resolution, anyway FQCNs are not recommended either.")
		@Test
		fun `does not flag FQCN`() {
			verifyNoFindings<CalisthenicsDotsRule>(
				originalCode = """
					fun kotlin.text.Appendable.f() {
						kotlin.text.Regex(".*").matches(this.toString())
					}
				""".trimIndent(),
			)
		}
	}
}
