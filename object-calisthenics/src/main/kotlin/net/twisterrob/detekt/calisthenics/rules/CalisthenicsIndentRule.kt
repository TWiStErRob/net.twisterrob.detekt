package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.rules.parentsOfTypeUntil
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtElement

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
) : Rule(config) {

	override val issue: Issue =
		Issue(
			id = "CalisthenicsIndent",
			severity = Severity.Maintainability,
			description = "Object Calisthenics: Rule #1 - One level of indentation per method.",
			debt = Debt.FIVE_MINS
		)

	override fun visitBlockExpression(expression: KtBlockExpression) {
		super.visitBlockExpression(expression)

		val parents = expression.blockParents()
		if (parents.size > 1) {
			report(CodeSmell(issue, Entity.from(expression), issue.description))
		}
	}
}

private fun KtElement.blockParents(): List<KtBlockExpression> =
	this.parentsOfTypeUntil<KtBlockExpression, KtClassOrObject>().toList()
