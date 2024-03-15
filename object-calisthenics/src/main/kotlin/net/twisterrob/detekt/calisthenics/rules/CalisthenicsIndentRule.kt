package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Rule
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.psiUtil.parents

/**
 * Object Calisthenics: Rule #1 - One level of indentation per method.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * if (true) {
 *     if (true) {
 *         println()
 *     }
 * }
 * </noncompliant>
 *
 * <compliant>
 * if (true) {
 *     println()
 * }
 * </compliant>
 */
class CalisthenicsIndentRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Object Calisthenics: Rule #1 - One level of indentation per method.",
) {

	override val ruleId = Id("CalisthenicsIndent")

	override fun visitBlockExpression(expression: KtBlockExpression) {
		super.visitBlockExpression(expression)

		val parents = expression.blockParents()
		if (parents.size > 1) {
			report(CodeSmell(Entity.from(expression), description))
		}
	}
}

private fun KtElement.blockParents(): List<KtBlockExpression> =
	this.parentsOfTypeUntil<KtBlockExpression, KtClassOrObject>().toList()

private inline fun <reified T, reified Stop> PsiElement.parentsOfTypeUntil(): Sequence<T> =
	this.parentsUpTo<Stop>().filterIsInstance<T>()

private inline fun <reified T> PsiElement.parentsUpTo(): Sequence<PsiElement> =
	this.parents.takeWhile { it !is T }
