package dev.muyiwa.logging

import timber.log.*

class TimberLogging: Timber.DebugTree() {
	override fun createStackElementTag(element: StackTraceElement): String? {
		return "${element.fileName}: ${element.lineNumber} on ${element.methodName}"
	}
}