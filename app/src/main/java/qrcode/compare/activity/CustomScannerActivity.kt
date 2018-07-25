package qrcode.compare.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import qrcode.compare.custom.CustomCaptureManager
import qrcode.compare.R
import qrcode.compare.config.Setting

class CustomScannerActivity : AppCompatActivity(), DecoratedBarcodeView.TorchListener {

    private lateinit var capture: CustomCaptureManager
    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var switchFlashlightButton: ImageButton

    private var statusTorch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_scanner)
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        barcodeScannerView.setTorchListener(this)
        switchFlashlightButton = findViewById(R.id.switch_flashlight)
        switchFlashlightButton.setOnClickListener({
            switchFlashLight()
        })

        if (!hasFlash()) {
            switchFlashlightButton.visibility = View.GONE
        }

        capture = CustomCaptureManager(this, barcodeScannerView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.barcodeCallback = object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                finish()
                val intent = Intent(this@CustomScannerActivity, ResultActivity::class.java)
                intent.putExtra(Setting.VALUE, result!!.result.text)
                startActivity(intent)
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {

            }
        }
        capture.decode()

    }

    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    private fun switchFlashLight() {
        if (statusTorch) {
            barcodeScannerView.setTorchOff()

        } else {
            barcodeScannerView.setTorchOn()

        }
        statusTorch = !statusTorch
    }

    override fun onTorchOn() {
        switchFlashlightButton.setImageResource(R.drawable.ic_flash_press)
    }

    override fun onTorchOff() {
        switchFlashlightButton.setImageResource(R.drawable.ic_flash_unpress)
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }
}
