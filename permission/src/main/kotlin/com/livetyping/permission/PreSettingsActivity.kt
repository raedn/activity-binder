package com.livetyping.permission

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi


abstract class PreSettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId())
        findViewById<View>(settingsButtonId())
                .setOnClickListener {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:$packageName"))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivityForResult(intent,
                            intent.getIntExtra(GlobalPermissionRequest.PERMISSION_REQUEST_CODE_KEY, 0))
                }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    final override fun onBackPressed() {
        finishApp()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun finishApp() {
        moveTaskToBack(true)
        finishAndRemoveTask()
    }

    final override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    @LayoutRes
    abstract fun layoutResId(): Int

    @IdRes
    abstract fun settingsButtonId(): Int
}
