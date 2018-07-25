package qrcode.compare.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.FirebaseApp
import com.google.zxing.integration.android.IntentIntegrator
import qrcode.compare.R

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var scanner1Btn: Button
    private lateinit var scanner2Btn: Button
    private lateinit var scanner3Btn: Button
    private lateinit var scanner4Btn: Button
    private lateinit var scanner5Btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        FirebaseApp.initializeApp(this)
        initView()
        onClickInit()
    }

    private fun initView() {
        scanner1Btn = findViewById(R.id.scanner1_btn)
        scanner2Btn = findViewById(R.id.scanner2_btn)
        scanner3Btn = findViewById(R.id.scanner3_btn)
        scanner4Btn = findViewById(R.id.scanner4_btn)
        scanner5Btn = findViewById(R.id.scanner5_btn)

    }

    private fun onClickInit() {
        scanner1Btn.setOnClickListener(this)
        scanner2Btn.setOnClickListener(this)
        scanner3Btn.setOnClickListener(this)
        scanner4Btn.setOnClickListener(this)
        scanner5Btn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.scanner1_btn -> {
                startActivity(Intent(this, MobileVisionActivity::class.java))
            }
            R.id.scanner2_btn -> {
                IntentIntegrator(this)
                        .setOrientationLocked(true)
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                        .setCaptureActivity(CustomScannerActivity::class.java).initiateScan()
            }
            R.id.scanner3_btn -> {
                startActivity(Intent(this, ZXingActivity::class.java))
            }
            R.id.scanner4_btn -> {
                startActivity(Intent(this, FirebaseQRActivity::class.java))
            }
            R.id.scanner5_btn -> {
                startActivity(Intent(this, Camera2Activity::class.java))
            }
        }
    }
}
