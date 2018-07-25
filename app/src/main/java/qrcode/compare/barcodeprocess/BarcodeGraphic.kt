package qrcode.compare.barcodeprocess

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import qrcode.compare.widget.GraphicOverlay

/**
 * Created by David on 20/07/2018.
 */

class BarcodeGraphic internal constructor(overlay: GraphicOverlay, private val barcode: FirebaseVisionBarcode?) : GraphicOverlay.Graphic(overlay) {

    private val TEXT_COLOR = Color.WHITE
    private val TEXT_SIZE = 54.0f
    private val STROKE_WIDTH = 4.0f
    private val rectPaint = Paint()
    private val barcodePaint: Paint

    init {
        rectPaint.color = TEXT_COLOR
        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = STROKE_WIDTH

        barcodePaint = Paint()
        barcodePaint.color = TEXT_COLOR
        barcodePaint.textSize = TEXT_SIZE
        postInvalidate()
    }


    /**
     * Draws the barcode block annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas) {
        if (barcode == null) {
            throw IllegalStateException("Attempting to draw a null barcode.")
        }

        val rect = RectF(barcode.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        canvas.drawRect(rect, rectPaint)

        canvas.drawText(barcode.rawValue!!, rect.left, rect.bottom, barcodePaint)
    }
}
