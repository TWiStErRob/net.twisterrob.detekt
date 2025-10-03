package net.twisterrob.detekt.testing.rules

import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import dev.detekt.api.config
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
		report(Finding(Entity.from(file), MESSAGE + extra))
	}

	companion object {

		const val MESSAGE = "Bad code!"
	}
}
