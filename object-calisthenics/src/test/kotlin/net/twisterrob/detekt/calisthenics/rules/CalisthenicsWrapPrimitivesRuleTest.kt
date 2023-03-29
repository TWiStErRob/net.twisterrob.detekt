package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Severity
import net.twisterrob.detekt.testing.PsiTestingExtension
import net.twisterrob.detekt.testing.verifyNoFindings
import net.twisterrob.detekt.testing.verifySimpleFinding
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.jetbrains.kotlin.builtins.PrimitiveType
import org.jetbrains.kotlin.name.Name
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito

/**
 * @see CalisthenicsWrapPrimitivesRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsWrapPrimitivesRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsWrapPrimitivesRule().issue) {
				assertThat(id, equalTo("CalisthenicsWrapPrimitives"))
				assertThat(debt, equalTo(Debt.FIVE_MINS))
				assertThat(severity, equalTo(Severity.Maintainability))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #3 - Wrap all primitives and Strings."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsWrapPrimitivesRule().ruleSetConfig, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock(Config::class.java)

			val issue = CalisthenicsWrapPrimitivesRule(mockConfig)

			assertThat(issue.ruleSetConfig, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {

		@Nested
		inner class `function parameters` {

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#primitiveTypes")
			@ParameterizedTest fun `flags primitive function parameters`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						fun f(primitive: ${type}) {
							println(primitive)
						}
					""".trimIndent(),
					message = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
					pointedCode = "primitive: ${type}",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#primitiveTypes")
			@ParameterizedTest fun `flags primitive function parameters - nullable`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						fun f(primitive: ${type}?) {
							println(primitive)
						}
					""".trimIndent(),
					message = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
					pointedCode = "primitive: ${type}?",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#nonPrimitiveTypes")
			@ParameterizedTest fun `does not flag non-primitive function parameters`(type: Name) {
				verifyNoFindings<CalisthenicsWrapPrimitivesRule>(
					"""
						fun f(nonPrimitive: ${type}) {
							println(nonPrimitive)
						}
					""".trimIndent(),
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#nonPrimitiveTypes")
			@ParameterizedTest fun `does not flag non-primitive function parameters - nullable`(type: Name) {
				verifyNoFindings<CalisthenicsWrapPrimitivesRule>(
					"""
						fun f(nonPrimitive: ${type}?) {
							println(nonPrimitive)
						}
					""".trimIndent(),
				)
			}
		}

		@Nested
		inner class `function return types` {

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#primitiveTypes")
			@ParameterizedTest fun `flags primitive return types`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						fun primitive(): ${type} = TODO()
					""".trimIndent(),
					message = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
					pointedCode = "fun primitive(): ${type}",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#primitiveTypes")
			@ParameterizedTest fun `flags primitive return types - nullable`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						fun primitive(): ${type}? = TODO()
					""".trimIndent(),
					message = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
					pointedCode = "fun primitive(): ${type}?",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#nonPrimitiveTypes")
			@ParameterizedTest fun `does not flag non-primitive return types`(type: Name) {
				verifyNoFindings<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						fun primitive(): ${type} = TODO()
					""".trimIndent(),
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#nonPrimitiveTypes")
			@ParameterizedTest fun `does not flag non-primitive return types - nullable`(type: Name) {
				verifyNoFindings<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						fun primitive(): ${type}? = TODO()
					""".trimIndent(),
				)
			}
		}

		@Nested
		inner class `property types` {

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#primitiveTypes")
			@ParameterizedTest fun `flags primitive val properties`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						val primitive: ${type}
					""".trimIndent(),
					message = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
					pointedCode = "val primitive: ${type}",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#primitiveTypes")
			@ParameterizedTest fun `flags primitive val properties - nullable`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						val primitive: ${type}?
					""".trimIndent(),
					message = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
					pointedCode = "val primitive: ${type}?",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#nonPrimitiveTypes")
			@ParameterizedTest fun `does not flag non-primitive val properties`(type: Name) {
				verifyNoFindings<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						val primitive: ${type}
					""".trimIndent(),
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#nonPrimitiveTypes")
			@ParameterizedTest fun `does not flag non-primitive val properties - nullable`(type: Name) {
				verifyNoFindings<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						val primitive: ${type}?
					""".trimIndent(),
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#primitiveTypes")
			@ParameterizedTest fun `flags primitive var properties`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						var primitive: ${type}
					""".trimIndent(),
					message = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
					pointedCode = "var primitive: ${type}",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#primitiveTypes")
			@ParameterizedTest fun `flags primitive var properties - nullable`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						var primitive: ${type}?
					""".trimIndent(),
					message = "Object Calisthenics: Rule #3 - Wrap all primitives and Strings.",
					pointedCode = "var primitive: ${type}?",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#nonPrimitiveTypes")
			@ParameterizedTest fun `does not flag non-primitive var properties`(type: Name) {
				verifyNoFindings<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						var primitive: ${type}
					""".trimIndent(),
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapPrimitivesRuleTest#nonPrimitiveTypes")
			@ParameterizedTest fun `does not flag non-primitive var properties - nullable`(type: Name) {
				verifyNoFindings<CalisthenicsWrapPrimitivesRule>(
					originalCode = """
						var primitive: ${type}?
					""".trimIndent(),
				)
			}
		}
	}

	companion object {

		@Suppress("UnusedPrivateMember") // Unaware of JUnit 5's @MethodSource.
		@JvmStatic
		private fun primitiveTypes(): List<Name> {
			val primitiveLikeNames = listOf(
				Name.identifier("String"),
				Name.identifier("kotlin.String"),
				Name.identifier("java.lang.String"),
				Name.identifier("Number"),
				Name.identifier("kotlin.Number"),
				Name.identifier("java.lang.Number"),
			)

			@Suppress("CalisthenicsDots")
			val primitiveNames = PrimitiveType.values().map { it.typeName }
			return primitiveNames + primitiveLikeNames
		}

		@Suppress("UnusedPrivateMember") // Unaware of JUnit 5's @MethodSource.
		@JvmStatic
		private fun nonPrimitiveTypes(): List<Name> =
			listOf(
				Name.identifier("Regex"),
				Name.identifier("kotlin.text.Regex"),
				Name.identifier("java.lang.Pattern"),
			)
	}
}
