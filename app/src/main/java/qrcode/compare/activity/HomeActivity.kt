package qrcode.compare.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.zxing.integration.android.IntentIntegrator
import qrcode.compare.R

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var scanner1Btn: Button
    private lateinit var scanner2Btn: Button
    private lateinit var scanner3Btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        onClickInit()
    }

    private fun initView() {
        scanner1Btn = findViewById(R.id.scanner1_btn)
        scanner2Btn = findViewById(R.id.scanner2_btn)
        scanner3Btn = findViewById(R.id.scanner3_btn)
    }

    private fun onClickInit() {
        scanner1Btn.setOnClickListener(this)
        scanner2Btn.setOnClickListener(this)
        scanner3Btn.setOnClickListener(this)
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
        }
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    */
}
