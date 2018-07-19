package framgia.vn.interceptmsm

import android.Manifest
import android.app.Activity
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class SmsVerifyCode(private var onReceiveSMSListener: OnReceiveSMSListener) {

    private lateinit var smsReceiver: SMSReceiver
    private lateinit var activity: Activity

    constructor(activity: Activity, onReceiveSMSListener: OnReceiveSMSListener) : this(onReceiveSMSListener) {
        this.activity = activity
        smsReceiver = SMSReceiver()
        smsReceiver.setListtener(onReceiveSMSListener)

    }

    fun onStart() {
        registerReceiverSMS()
    }

    fun onStop() {
        unRegisterReceiverSMS()
    }

    fun setPhoneFilter(phoneFilter: String) {
        smsReceiver.setPhoneNumberFilter(phoneFilter)
    }

    private fun registerReceiverSMS() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constant.ACTION_SMS_RECEIVER)

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermissionSMS()) {
                activity.registerReceiver(smsReceiver, intentFilter)
            } else {
                requestPermissionSMS()
            }
        } else {
            activity.registerReceiver(smsReceiver, intentFilter)
        }

    }

    private fun unRegisterReceiverSMS() {
        try {
            activity.unregisterReceiver(smsReceiver)
        } catch (ignore: IllegalArgumentException) {
        }

    }

    private fun checkPermissionSMS(): Boolean {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissionSMS() {
        ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS), Constant.PERMISSION_REQUEST_CODE)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Constant.PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                registerReceiverSMS()
            }

        }
    }

}