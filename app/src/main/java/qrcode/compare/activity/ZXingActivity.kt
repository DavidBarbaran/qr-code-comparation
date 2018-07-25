package qrcode.compare.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import com.google.zxing.Result
import me.dm7.barcodescanner.core.IViewFinder
import qrcode.compare.R
import me.dm7.barcodescanner.zxing.ZXingScannerView
import qrcode.compare.config.Setting
import qrcode.compare.custom.CustomViewFinderView

class ZXingActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var contentFrame: ViewGroup
    private lateinit var mScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zxing)

        contentFrame = findViewById(R.id.content_frame)
        mScannerView = object : ZXingScannerView(this) {
            override fun createViewFinderView(context: Context?): IViewFinder {
                return CustomViewFinderView(context = context!!)
            }
        }
        mScannerView.setMaskColor(ContextCompat.getColor(this, R.color.zxing_transparent))
        mScannerView.setBorderColor(ContextCompat.getColor(this, R.color.zxing_transparent))
        contentFrame.addView(mScannerView)
    }

    override fun handleResult(p0: Result?) {
        finish()
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Setting.VALUE, p0!!.text)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }
}