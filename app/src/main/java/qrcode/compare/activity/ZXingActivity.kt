package qrcode.compare.activity

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import com.google.zxing.Result
import me.dm7.barcodescanner.core.IViewFinder
import qrcode.compare.R
import me.dm7.barcodescanner.core.ViewFinderView
import me.dm7.barcodescanner.zxing.ZXingScannerView
import qrcode.compare.config.Setting


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

    class CustomViewFinderView(context: Context?, attrs: AttributeSet?) : ViewFinderView(context, attrs) {

        private val TRADE_MARK_TEXT = "ZXing"
        private val TRADE_MARK_TEXT_SIZE_SP = 40
        private val PAINT = Paint()

        constructor(context: Context) : this(context, null)

        init {
            init()
        }

        private fun init() {
            PAINT.color = Color.WHITE
            PAINT.isAntiAlias = true
            val textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP.toFloat(), resources.displayMetrics)
            PAINT.textSize = textPixelSize
            setSquareViewFinder(true)
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            drawTradeMark(canvas!!)
        }

        private fun drawTradeMark(canvas: Canvas) {
            val framingRect = framingRect
            val tradeMarkTop: Float
            val tradeMarkLeft: Float
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom.toFloat() + PAINT.textSize + 10f
                tradeMarkLeft = framingRect.left.toFloat()
            } else {
                tradeMarkTop = 10f
                tradeMarkLeft = canvas.height.toFloat() - PAINT.textSize - 10f
            }
            canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT)
        }

    }
}
