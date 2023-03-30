package net.twisterrob.detekt.calisthenics.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtIfExpression

/**
 * Object Calisthenics: Rule #2 - Don't use the ELSE keyword.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * fun f() {
 *     if (cond) {
 *         something()
 *     } else {
 *         somethingMore()
 *     }
 * }
 *
 * fun f() {
 *     val x = something()
 *     if (x == null) {
 *         error("Whoa!")
 *     } else {
 *         somethingMore(x)
 *     }
 * }
 * </noncompliant>
 *
 * <compliant>
 * fun f() {
 *     if (cond) {
 *         something()
 *         return
 *     }
 *     somethingMore()
 * }
 *
 * fun f() {
 *     val x = something() ?: error("Whoa!")
 *     somethingMore(x)
 * }
 * </compliant>
 */
class CalisthenicsNoElseRule(
	config: Config = Config.empty
) : Rule(config) {

	override val issue: Issue =
		Issue(
			id = "CalisthenicsNoElse",
			severity = Severity.Maintainability,
			description = "Object Calisthenics: Rule #2 - Don't use the ELSE keyword.",
			debt = Debt.FIVE_MINS
		)

	override fun visitIfExpression(expression: KtIfExpression) {
		super.visitIfExpression(expression)
		if (expression.`else` != null) {
			val target = expression.elseKeyword ?: error("No `else` in `${expression.text}`.")
			report(CodeSmell(issue, Entity.from(target), issue.description))
		}
	}
}
