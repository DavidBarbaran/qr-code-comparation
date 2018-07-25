package qrcode.compare.barcodeprocess

import android.graphics.Bitmap
import android.media.Image
import android.support.annotation.NonNull
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import qrcode.compare.widget.GraphicOverlay
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by David on 20/07/2018.
 */

abstract class VisionProcessorBase<T> : VisionImageProcessor {

    private val shouldThrottle = AtomicBoolean(false)

    override fun process(data: ByteBuffer, frameMetadata: FrameMetadata, graphicOverlay: GraphicOverlay) {
        if (shouldThrottle.get()) {
            return
        }

        val metadata = FirebaseVisionImageMetadata.Builder()
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                .setWidth(frameMetadata.width)
                .setHeight(frameMetadata.height)
                .setRotation(frameMetadata.rotation)
                .build()

        detectInVisionImage(
                FirebaseVisionImage.fromByteBuffer(data, metadata), frameMetadata, graphicOverlay)
    }

    override fun process(image: Image, rotation: Int, graphicOverlay: GraphicOverlay) {
        if (shouldThrottle.get()) {
            return
        }

        val frameMetadata = FrameMetadata.Builder()
                .setWidth(image.width)
                .setHeight(image.height).build()

        val fbVisionImage = FirebaseVisionImage.fromMediaImage(image, rotation)
        detectInVisionImage(fbVisionImage, frameMetadata, graphicOverlay)
    }

    override fun process(bitmap: Bitmap, graphicOverlay: GraphicOverlay) {
        if (shouldThrottle.get()) {
            return
        }
        detectInVisionImage(FirebaseVisionImage.fromBitmap(bitmap), null, graphicOverlay)
    }

    private fun detectInVisionImage(image: FirebaseVisionImage,
                                    metadata: FrameMetadata?,
                                    graphicOverlay: GraphicOverlay) {
        detectInImage(image)
                .addOnSuccessListener { p0 ->
                    shouldThrottle.set(false)
                    this.onSuccess(p0, metadata!!,
                            graphicOverlay)
                }
                .addOnFailureListener { e ->
                    shouldThrottle.set(false)
                    this.onFailure(e)
                }
        shouldThrottle.set(true)
    }

    override fun stop() {
    }

    protected abstract fun detectInImage(image: FirebaseVisionImage): Task<T>

    protected abstract fun onSuccess(
            @NonNull results: T,
            @NonNull frameMetadata: FrameMetadata,
            @NonNull graphicOverlay: GraphicOverlay)

    protected abstract fun onFailure(@NonNull e: Exception)
}