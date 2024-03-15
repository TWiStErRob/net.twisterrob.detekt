package net.twisterrob.detekt.testing.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.config
import org.jetbrains.kotlin.psi.KtFile

internal class UptightFileRule(
	config: Config = Config.empty,
) : Rule(
	config = config,
	description = "Everything is wrong.",
) {

	private val extra: String by config("")

	override fun visitKtFile(file: KtFile) {
		super.visitKtFile(file)
		report(CodeSmell(Entity.from(file), MESSAGE + extra))
	}

	companion object {

		const val MESSAGE = "Bad code!"
	}
}
