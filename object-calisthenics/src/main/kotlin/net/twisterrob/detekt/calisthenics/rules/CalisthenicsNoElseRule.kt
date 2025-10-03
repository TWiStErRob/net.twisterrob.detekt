package net.twisterrob.detekt.calisthenics.rules

import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
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
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Object Calisthenics: Rule #2 - Don't use the ELSE keyword.",
) {

	override val ruleId = Id("CalisthenicsNoElse")

	override fun visitIfExpression(expression: KtIfExpression) {
		super.visitIfExpression(expression)
		if (expression.`else` != null) {
			val target = expression.elseKeyword ?: error("No `else` in `${expression.text}`.")
			report(Finding(Entity.from(target), description))
		}
	}
}
