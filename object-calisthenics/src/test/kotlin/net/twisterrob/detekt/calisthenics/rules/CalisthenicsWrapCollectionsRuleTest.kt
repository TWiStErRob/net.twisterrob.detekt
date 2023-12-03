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
 * @see CalisthenicsWrapCollectionsRule
 */
@ExtendWith(PsiTestingExtension::class)
class CalisthenicsWrapCollectionsRuleTest {

	@Nested
	inner class `Detekt Rule conventions` {

		@Test
		fun `the rule's metadata is correct`() {
			with(CalisthenicsWrapCollectionsRule().issue) {
				assertThat(id, equalTo("CalisthenicsWrapCollections"))
				assertThat(debt, equalTo(Debt.FIVE_MINS))
				assertThat(severity, equalTo(Severity.Maintainability))
				assertThat(
					description,
					equalTo(
						"Object Calisthenics: Rule #4 - First class collections."
					)
				)
			}
		}

		@Test
		fun `config is defaulted to empty`() {
			assertThat(CalisthenicsWrapCollectionsRule().ruleSetConfig, sameInstance(Config.empty))
		}

		@Test
		fun `config is propagated to parent`() {
			val mockConfig: Config = Mockito.mock()

			val issue = CalisthenicsWrapCollectionsRule(mockConfig)

			assertThat(issue.ruleSetConfig, sameInstance(mockConfig))
		}
	}

	@Nested
	inner class `Rule logic` {

		@Nested
		inner class `function parameters` {

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapCollectionsRuleTest#collectionTypes")
			@ParameterizedTest fun `flags collections used as function parameters`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapCollectionsRule>(
					originalCode = """
						fun f(collection: ${type}) {
							println(collection)
						}
					""".trimIndent(),
					message = "Object Calisthenics: Rule #4 - First class collections.",
					pointedCode = "collection: ${type}",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapCollectionsRuleTest#collectionTypes")
			@ParameterizedTest fun `flags collections function parameters - nullable`(type: Name) {
				verifySimpleFinding<CalisthenicsWrapCollectionsRule>(
					originalCode = """
						fun f(collection: ${type}?) {
							println(collection)
						}
					""".trimIndent(),
					message = "Object Calisthenics: Rule #4 - First class collections.",
					pointedCode = "collection: ${type}?",
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapCollectionsRuleTest#nonCollectionTypes")
			@ParameterizedTest fun `does not flag non-collection function parameters`(type: Name) {
				verifyNoFindings<CalisthenicsWrapCollectionsRule>(
					originalCode = """
						fun f(nonCollection: ${type}) {
							println(nonCollection)
						}
					""".trimIndent(),
				)
			}

			@MethodSource("net.twisterrob.detekt.calisthenics.rules.CalisthenicsWrapCollectionsRuleTest#nonCollectionTypes")
			@ParameterizedTest fun `does not flag non-collection function parameters - nullable`(type: Name) {
				verifyNoFindings<CalisthenicsWrapCollectionsRule>(
					originalCode = """
						fun f(nonCollection: ${type}?) {
							println(nonCollection)
						}
					""".trimIndent(),
				)
			}
		}
	}

	companion object {

		@Suppress("UnusedPrivateMember") // Unaware of JUnit 5's @MethodSource.
		@JvmStatic
		private fun collectionTypes(): List<Name> {
			val primitiveLikeNames = listOf(
				Name.identifier("List"),
				Name.identifier("java.util.List"),
				Name.identifier("kotlin.collections.List"),
			)

			@Suppress("CalisthenicsDots")
			val primitiveNames = PrimitiveType.values().map { it.typeName }
			return primitiveNames + primitiveLikeNames
		}

		@Suppress("UnusedPrivateMember") // Unaware of JUnit 5's @MethodSource.
		@JvmStatic
		private fun nonCollectionTypes(): List<Name> =
			listOf(
				Name.identifier("String"),
				Name.identifier("kotlin.text.Regex"),
				Name.identifier("Any"),
			)
	}
}
