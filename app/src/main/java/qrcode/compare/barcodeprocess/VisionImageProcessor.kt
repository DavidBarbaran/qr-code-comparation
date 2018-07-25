package qrcode.compare.barcodeprocess

import android.graphics.Bitmap
import android.media.Image
import qrcode.compare.widget.GraphicOverlay
import java.nio.ByteBuffer

/**
 * Created by David on 20/07/2018.
 */

/** An inferface to process the images with different ML Kit detectors and custom image models. */
interface VisionImageProcessor {

    /** Processes the images with the underlying machine learning models. */
    fun process(data: ByteBuffer, frameMetadata: FrameMetadata, graphicOverlay: GraphicOverlay)

    /** Processes the bitmap images. */
    fun process(bitmap: Bitmap, graphicOverlay: GraphicOverlay)

    /** Processes the images. */
    fun process(image: Image, rotation: Int, graphicOverlay: GraphicOverlay)

    /** Stops the underlying machine learning model and release resources. */
    fun stop()
}