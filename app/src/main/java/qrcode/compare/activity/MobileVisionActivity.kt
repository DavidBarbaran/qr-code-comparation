package qrcode.compare.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.ImageButton
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic
import com.google.android.gms.vision.barcode.Barcode
import qrcode.compare.R
import qrcode.compare.config.Setting
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever

class MobileVisionActivity : AppCompatActivity(), BarcodeRetriever, View.OnClickListener {

    private lateinit var flashBtn: ImageButton
    private lateinit var barcodeCapture: BarcodeCapture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_vision)
        initView()
        onClickInit()
    }

    private fun initView() {
        barcodeCapture = supportFragmentManager.findFragmentById(R.id.barcode) as BarcodeCapture
        barcodeCapture.setRetrieval(this)
        flashBtn = findViewById(R.id.flash_btn)
    }

    private fun onClickInit() {
        flashBtn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.flash_btn -> {
                barcodeCapture.isShowFlash = true
                barcodeCapture.refresh()
            }
        }
    }

    override fun onRetrieved(barcode: Barcode?) {
        finish()
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Setting.VALUE, barcode!!.displayValue)
        startActivity(intent)

    }

    override fun onRetrievedMultiple(closetToClick: Barcode?, barcode: MutableList<BarcodeGraphic>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRetrievedFailed(reason: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionRequestDenied() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
