package qrcode.compare.barcodeprocess

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import qrcode.compare.widget.GraphicOverlay
import java.io.IOException

/**
 * Created by David on 20/07/2018.
 */
class BarcodeScanningProcessor : VisionProcessorBase<List<FirebaseVisionBarcode>>() {

    private val TAG = "BarcodeScanProc"
    private val detector = FirebaseVision.getInstance().visionBarcodeDetector

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionBarcode>> {
        return detector.detectInImage(image)
    }

    override fun onSuccess(results: List<FirebaseVisionBarcode>, frameMetadata: FrameMetadata,
                           graphicOverlay: GraphicOverlay) {
        graphicOverlay.clear()
        for (result in results.iterator()) {
            graphicOverlay.add(BarcodeGraphic(graphicOverlay, result))
        }
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Barcode detection failed $e")
    }

    override fun stop() {
        try {
            detector.close();
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Barcode Detector: $e")
        }
    }
}