import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object BatteryStatusReceiver {
    private val _batteryLevel = MutableLiveData<Int>()
    val batteryLevel: LiveData<Int> = _batteryLevel

    private val _batteryLow = MutableLiveData<Boolean>()
    val batteryLow: LiveData<Boolean> = _batteryLow

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                when (it.action) {
                    Intent.ACTION_BATTERY_CHANGED -> {
                        val level = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                        _batteryLevel.postValue(level)
                    }
                    Intent.ACTION_BATTERY_LOW -> {
                        _batteryLow.postValue(true)
                    }
                    Intent.ACTION_BATTERY_OKAY -> {
                        _batteryLow.postValue(false)
                    }
                }
            }
        }
    }

    fun register(context: Context) {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_OKAY)
        }
        context.registerReceiver(batteryReceiver, filter)
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(batteryReceiver)
    }
}
