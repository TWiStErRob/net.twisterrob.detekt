package net.twisterrob.detekt.testing

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.InvocationInterceptor
import org.junit.jupiter.api.extension.ReflectiveInvocationContext
import java.lang.reflect.Method

/**
 * JUnit Jupiter extension that enables [com.intellij.psi.impl.DebugUtil.CHECK] for each test.
 *
 * This ensures that the potential modifications that are done to the PSI tree are valid.
 *
 * @see fix
 */
public class PsiTestingExtension : InvocationInterceptor {

	override fun interceptTestMethod(
		@Suppress("detekt.ForbiddenVoid") // JUnit API.
		invocation: InvocationInterceptor.Invocation<Void?>,
		invocationContext: ReflectiveInvocationContext<Method>,
		extensionContext: ExtensionContext,
	) {
		@Suppress("detekt.UnnecessaryFullyQualifiedName")
		com.intellij.psi.impl.DebugUtil.runWithCheckInternalInvariantsEnabled {
			invocation.proceed()
		}
	}
}
