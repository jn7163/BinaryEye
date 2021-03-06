package de.markusfisch.android.binaryeye.app

import android.app.Application
import android.support.v8.renderscript.RenderScript
import de.markusfisch.android.binaryeye.BuildConfig
import de.markusfisch.android.binaryeye.data.Database
import de.markusfisch.android.binaryeye.preference.Preferences

val db = Database()
val prefs = Preferences()

class BinaryEyeApp : Application() {
	override fun onCreate() {
		super.onCreate()

		// `RenderScript.forceCompat()` will crash the app when compiled
		// on macOS Catalina with build tools 29.0.3 :( To make things
		// even more complicated, `forceCompat()` will continue to work
		// if the app is compiled on Linux with the exact same version
		// 29.0.3 of the build tools
		if (!BuildConfig.IS_CATALINA &&
			System.getProperty("os.version")?.contains(
				"lineageos", true
			) == true
		) {
			// required to make RenderScript work for Lineage 16.0
			// possibly because of a system/device bug
			RenderScript.forceCompat()
		}

		db.open(this)
		prefs.init(this)
	}
}
