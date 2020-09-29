package com.wholedetail.recreateactivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val RECREATE_KEY = "recreate_count"
private const val TAG = "DanilovVA"

class MainActivity : AppCompatActivity() {

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(arg0: Context, intent: Intent) {
            if (PRIMARY_SERVICE_RESULT == intent.action) {
                val primary = intent.getIntExtra(N_PRIMARY, 0)
                nPrimaryNumberTV.text = getString(R.string.nth_primary, primary)
                Log.d(TAG, primary.toString())
                unregisterReceiver(this)
            }
        }
    }

    private var recreationsCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(broadcastReceiver, IntentFilter(PRIMARY_SERVICE_RESULT))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(RECREATE_KEY, recreationsCount)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        recreationsCount = savedInstanceState.getInt(RECREATE_KEY) + 1

        val recreateCountOutput = getString(R.string.recreations_count, recreationsCount)
        recreationsCountTV.text = recreateCountOutput
        Log.w(TAG, recreateCountOutput)

        startService(Intent(this, PrimaryCalculationService::class.java).putExtra(RECREATE_KEY, recreationsCount))
    }

}