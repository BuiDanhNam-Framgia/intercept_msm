package framgia.vn.interceptsms

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import framgia.vn.interceptmsm.OnReceiveSMSListener
import framgia.vn.interceptmsm.SmsVerifyCode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var smsVerifyCode: SmsVerifyCode? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        smsVerifyCode = SmsVerifyCode(this, object : OnReceiveSMSListener {
            override fun onSuccess(code: String) {
                edt_code.setText(code)
            }

            override fun onError(ex: Exception) {

            }

            override fun onComplete() {

            }

        })
        smsVerifyCode?.setPhoneFilter("1111")
    }

    override fun onStart() {
        super.onStart()
        smsVerifyCode?.onStart()
    }

    override fun onStop() {
        super.onStop()
        smsVerifyCode?.onStop()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        smsVerifyCode?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
