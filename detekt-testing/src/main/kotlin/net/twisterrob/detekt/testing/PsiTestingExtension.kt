package net.twisterrob.detekt.testing

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * JUnit Jupiter extension that enables [org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil.CHECK] for each test.
 *
 * This ensures that the potential modifications that are done to the PSI tree are valid.
 *
 * @see fix
 */
public class PsiTestingExtension : BeforeEachCallback, AfterEachCallback {

	override fun beforeEach(context: ExtensionContext) {
		org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil.CHECK = true
	}

	override fun afterEach(context: ExtensionContext) {
		org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil.CHECK = false
	}
}
