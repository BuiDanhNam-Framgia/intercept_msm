package framgia.vn.interceptmsm


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage

class SMSReceiver : BroadcastReceiver() {

    private var callback: OnReceiveSMSListener? = null
    private var phoneNumber: String? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.getAction()) {
            Constant.ACTION_SMS_RECEIVER -> handleReceiveSMS(intent)
        }
    }

    /**
     * handle data when receive SMS via intent
     * @param Intent
     * @return   .
     */
    private fun handleReceiveSMS(intent: Intent?) {
        var bundles = intent?.extras
        try {
            var smsBody: StringBuffer = StringBuffer()
            val pdus = bundles?.get(Constant.KEY_PDUS) as Array<Any>
            var msgs = arrayOfNulls<SmsMessage>(pdus.size)
            for (i in msgs.indices) {
                msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray, bundles.getString(Constant.KEY_FORMAT_SMS))
                val phoneAddress = msgs[i]?.getDisplayOriginatingAddress()
                if (phoneNumber.equals(phoneAddress, true)) {
                    var sms = msgs[i]?.messageBody
                    smsBody.append(sms)
                }
            }
            callback?.onComplete()
            callback?.onSuccess(smsBody.toString())
            // result activity or service

        } catch (ex: Exception) {
            callback?.onError(ex)
        }
    }

    fun setPhoneNumberFilter(phone: String) {
        this.phoneNumber = phone
    }

    fun setListtener(callback: OnReceiveSMSListener) {
        this.callback = callback
    }

}
