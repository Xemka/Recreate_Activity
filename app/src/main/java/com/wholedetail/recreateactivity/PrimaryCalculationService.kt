package com.wholedetail.recreateactivity

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import java.util.concurrent.atomic.AtomicInteger

const val PRIMARY_SERVICE_RESULT = "primary_service_result"
const val N_PRIMARY = "n_primary"

private const val SERVICE_NAME = "PrimaryCalculationService"

class PrimaryCalculationService : IntentService(SERVICE_NAME) {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onHandleIntent(intent: Intent?) {
        val primary = findPrimary(intent?.getIntExtra(RECREATE_KEY, 0) ?: 0)

        sendBroadcast(Intent(PRIMARY_SERVICE_RESULT).putExtra(N_PRIMARY, primary))
    }

    private fun findPrimary(n: Int) =
            if (n == 1) 2
            else with(AtomicInteger(1)) {
                val simpleDividers = ArrayList<Int>(n)
                var foundPrimary = 1

                while (foundPrimary < n) {
                    addAndGet(2)

                    if (isPrimary(get(), simpleDividers)) {
                        simpleDividers.add(get())
                        foundPrimary++
                    }
                }
                get()
            }

    private fun isPrimary(a: Int, dividers: List<Int>) = dividers.none { a % it == 0 }

}