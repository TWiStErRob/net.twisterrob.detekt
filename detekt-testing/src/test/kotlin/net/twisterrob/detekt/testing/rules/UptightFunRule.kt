package net.twisterrob.detekt.testing.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtNamedFunction

internal class UptightFunRule(config: Config = Config.empty) : Rule(config) {

	override val issue: Issue = Issue(
		id = "UptightFun",
		description = "Everything is wrong.",
		severity = Severity.Minor,
		debt = Debt.FIVE_MINS,
	)

	override fun visitNamedFunction(function: KtNamedFunction) {
		super.visitNamedFunction(function)
		report(CodeSmell(issue, Entity.from(function), MESSAGE))
	}

	companion object {

		const val MESSAGE = "Bad code!"
	}
}
