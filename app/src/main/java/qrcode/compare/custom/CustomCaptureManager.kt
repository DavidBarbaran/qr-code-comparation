package qrcode.compare.custom

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

/**
 * Created by David on 19/07/2018.
 */
class CustomCaptureManager(activity: Activity?, barcodeView: DecoratedBarcodeView?) :
        CaptureManager(activity, barcodeView) {

    private val barcodeView: DecoratedBarcodeView = barcodeView!!
    lateinit var barcodeCallback : BarcodeCallback

    override fun decode() {
                barcodeView.barcodeView.decodeSingle(barcodeCallback)
    }

}