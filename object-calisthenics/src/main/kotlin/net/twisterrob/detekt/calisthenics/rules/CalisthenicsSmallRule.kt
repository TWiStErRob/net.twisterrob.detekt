package net.twisterrob.detekt.calisthenics.rules

import io.github.detekt.metrics.linesOfCode
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.config
import net.twisterrob.detekt.calisthenics.rules.internal.Count
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtParameterList
import org.jetbrains.kotlin.psi.psiUtil.getParentOfType
import org.jetbrains.kotlin.psi.psiUtil.parents

private typealias Lines = Count

/**
 * Object Calisthenics: Rule #7 - Keep all entities small.
 *
 * See [Object Calisthenics by Jeff Bay](https://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).
 *
 * <noncompliant>
 * </noncompliant>
 *
 * <compliant>
 * </compliant>
 */
class CalisthenicsSmallRule(
	config: Config = Config.empty
) : Rule(config) {

	override val issue: Issue =
		Issue(
			id = "CalisthenicsSmall",
			severity = Severity.Maintainability,
			description = "Object Calisthenics: Rule #7 - Keep all entities small.",
			debt = Debt.FIVE_MINS
		)

	private val maxAllowedClassLines: Lines by config(@Suppress("MagicNumber") 50, ::Lines)
	private val maxAllowedFunctionLines: Lines by config(@Suppress("MagicNumber") 5, ::Lines)
	private val maxAllowedParameterCount: Count by config(@Suppress("MagicNumber") 2, ::Count)

	override fun visitParameterList(list: KtParameterList) {
		super.visitParameterList(list)
		val owner = list.owner
		owner.validate("Parameter list for function", count = list.parameterCount, threshold = maxAllowedParameterCount)
	}

	override fun visitNamedFunction(function: KtNamedFunction) {
		super.visitNamedFunction(function)
		function.validate("Function", count = function.linesOfCode, threshold = maxAllowedFunctionLines)
	}

	override fun visitClass(klass: KtClass) {
		super.visitClass(klass)
		klass.validate("Class", count = klass.linesOfCode, threshold = maxAllowedClassLines)
	}

	@Suppress("CalisthenicsSmall", "CalisthenicsWrapPrimitives") // Suggestions welcome.
	private fun KtNamedDeclaration.validate(type: String, count: Count, threshold: Count) {
		if (count > threshold) {
			val message = "${type} ${this.name ?: "<unnamed>"} is too long (${count})."
			report(CodeSmell(issue, Entity.atName(this), message))
		}
	}
}

private val KtParameterList.parameterCount: Count
	get() = Count(this.parameters.size)

private val KtFunction.linesOfCode: Lines
	get() = Lines(this.linesOfCode() - 2)

private val KtClass.linesOfCode: Lines
	get() = Lines(this.body?.let { it.linesOfCode() - 2 } ?: 0)

private val KtParameterList.owner: KtNamedDeclaration
	get() =
		this.ownerFunction as? KtNamedDeclaration
			?: this.getParentOfType<KtNamedDeclaration>(true)
			?: error("Where is ${this} in ${this.parents.take(2)}?.")
