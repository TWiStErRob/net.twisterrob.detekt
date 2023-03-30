package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.test.TestConfig
import net.twisterrob.detekt.calisthenics.rules.internal.Count
import net.twisterrob.detekt.calisthenics.rules.internal.Count.Companion.repeat
import net.twisterrob.detekt.testing.PsiTestingExtension
import net.twisterrob.detekt.testing.verifyNoFindings
import net.twisterrob.detekt.testing.verifySimpleFinding
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito

/**
 * @see CalisthenicsSmallRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsSmallRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsSmallRule().issue) {
				assertThat(id, equalTo("CalisthenicsSmall"))
				assertThat(debt, equalTo(Debt.FIVE_MINS))
				assertThat(severity, equalTo(Severity.Maintainability))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #7 - Keep all entities small."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsSmallRule().ruleSetConfig, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock(Config::class.java)

			val issue = CalisthenicsSmallRule(mockConfig)

			assertThat(issue.ruleSetConfig, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {

		@Nested
		inner class `small functions` {

			@Test
			fun `oneliner is accepted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun f(): Unit = TODO()
					""".trimIndent(),
				)
			}

			@Test
			fun `empty function is accepted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun empty() {
						}
					""".trimIndent(),
				)
			}

			@Test
			fun `whitespace is not counted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					config = TestConfig("maxAllowedFunctionLines" to "2"),
					originalCode = """
						fun empty() {
							println()
						
						
						
							println()
						}
					""".trimIndent(),
				)
			}

			@ValueSource(ints = [1, 2, 3, 4, 5])
			@ParameterizedTest
			internal fun `few statements within threshold are accepted`(statements: Count) {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun f${statements}() {
							${"println()\n".repeat(statements)}
						}
					""".trimIndent(),
				)
			}

			@Test
			fun `more statements on threshold are flagged`() {
				verifySimpleFinding<CalisthenicsSmallRule>(
					config = TestConfig("maxAllowedFunctionLines" to "2"),
					originalCode = """
						fun f3() {
							println()
							println()
							println()
						}
					""".trimIndent(),
					message = "Function f3 is too long (3).",
					pointedCode = "fun f3()",
				)
			}

			@Test
			fun `complex expression within threshold is accepted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun f() = 
							(
								1 +
								1 +
								1
							)
					""".trimIndent(),
				)
			}

			@Test
			fun `complex expression on threshold is flagged`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun f() = 
							(
								1 +
								1 +
								1 +
								1
							)
					""".trimIndent(),
				)
			}
		}

		@Nested
		inner class `small classes` {

			@Test
			fun `oneliner is accepted`() {
				@Suppress("SelfReferenceConstructorParameter")
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						class C(private val c: C)
					""".trimIndent(),
				)
			}

			@Test
			fun `class stub is accepted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						class C
					""".trimIndent(),
				)
			}

			@Test
			fun `empty class is accepted`() {
				@Suppress("RemoveEmptyClassBody")
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						class C {
						}
					""".trimIndent(),
				)
			}

			@Test
			fun `whitespace is not counted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					config = TestConfig("maxAllowedClassLines" to "5"),
					originalCode = """
						class C {
							lateinit var v1: String
						
						
						
						
						
						
							lateinit var v8: String
						}
					""".trimIndent(),
				)
			}

			@Test
			fun `class heading is not counted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					config = TestConfig("maxAllowedClassLines" to "5"),
					originalCode = """
						class C {
							lateinit var v1: String
							lateinit var v2: String
							lateinit var v3: String
							lateinit var v4: String
							lateinit var v5: String
						}
					""".trimIndent(),
				)
			}

			@Test
			fun `long class is reported`() {
				verifySimpleFinding<CalisthenicsSmallRule>(
					config = TestConfig("maxAllowedClassLines" to "5"),
					originalCode = """
						class C {
							lateinit var v1: String
							lateinit var v2: String
							lateinit var v3: String
							lateinit var v4: String
							lateinit var v5: String
							lateinit var v6: String
						}
					""".trimIndent(),
					message = "Class C is too long (6).",
					pointedCode = "C",
				)
			}
		}

		@Nested
		inner class `small param lists` {

			@Test
			fun `no params is accepted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun f() { }
					""".trimIndent(),
				)
			}

			@Test
			fun `one param is accepted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun f(e: Exception) { }
					""".trimIndent(),
				)
			}

			@Test
			fun `two params are accepted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun f(k: KotlinVersion, e: Exception) { }
					""".trimIndent(),
				)
			}

			@Test
			fun `three params are flagged`() {
				verifySimpleFinding<CalisthenicsSmallRule>(
					originalCode = """
						fun f(k: KotlinVersion, e: Exception, error: Error) { }
					""".trimIndent(),
					message = "Parameter list for function f is too long (3).",
					pointedCode = "fun f(k: KotlinVersion, e: Exception, error: Error)",
				)
			}

			@Test
			fun `vararg counts as one`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun f(k: KotlinVersion, vararg e: Exception) { }
					""".trimIndent(),
				)
				verifySimpleFinding<CalisthenicsSmallRule>(
					originalCode = """
						fun f(k: KotlinVersion, e: Exception, vararg error: Error) { }
					""".trimIndent(),
					message = "Parameter list for function f is too long (3).",
					pointedCode = "fun f(k: KotlinVersion, e: Exception, vararg error: Error)",
				)
			}

			@Test
			fun `primary constructor is a function`() {
				verifySimpleFinding<CalisthenicsSmallRule>(
					originalCode = """
						class C(
							private val k: KotlinVersion,
							private val e: Exception,
							private val error: Error,
						)
					""".trimIndent(),
					message = "Parameter list for function C is too long (3).",
					pointedCode = "C\$( private val k: KotlinVersion, private val e: Exception, private val error: Error, )",
				)
			}

			@Test
			fun `typealias is detected`() {
				verifySimpleFinding<CalisthenicsSmallRule>(
					originalCode = """
						typealias f = (KotlinVersion, Exception, Error) -> Unit
					""".trimIndent(),
					message = "Parameter list for function f is too long (3).",
					pointedCode = "typealias f = (KotlinVersion, Exception, Error) -> Unit",
				)
			}

			@Test
			fun `typealias with two params is accepted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						typealias f = (KotlinVersion, Exception) -> Unit
					""".trimIndent(),
				)
			}

			@Test
			fun `function type is detected`() {
				verifySimpleFinding<CalisthenicsSmallRule>(
					originalCode = """
						fun f(ft: (KotlinVersion, Exception, Error) -> Unit): Unit = TODO()
					""".trimIndent(),
					message = "Parameter list for function ft is too long (3).",
					pointedCode = "ft: (KotlinVersion, Exception, Error) -> Unit",
				)
			}

			@Test
			fun `function type with two params is accepted`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						fun f(ft: (KotlinVersion, Exception) -> Unit): Unit = TODO()
					""".trimIndent(),
				)
			}

			@Test
			fun `property accessor is not detected`() {
				verifyNoFindings<CalisthenicsSmallRule>(
					originalCode = """
						var p: Exception
							get() = TODO()
							set(value) = TODO()
					""".trimIndent(),
				)
			}
		}
	}
}
