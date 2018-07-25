package qrcode.compare.activity

import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.common.annotation.KeepName
import qrcode.compare.R
import qrcode.compare.barcodeprocess.BarcodeScanningProcessor
import qrcode.compare.widget.CameraSource
import qrcode.compare.barcodeprocess.CameraSourcePreview
import qrcode.compare.widget.GraphicOverlay
import java.io.IOException

@KeepName
class FirebaseQRActivity : AppCompatActivity() {

    private val TAG = "LivePreviewActivity"
    private val BARCODE_DETECTION = "Barcode Detection"

    private val PERMISSION_REQUESTS = 1

    private var cameraSource: CameraSource? = null
    private lateinit var preview: CameraSourcePreview
    private lateinit var graphicOverlay: GraphicOverlay
    private var selectedModel = BARCODE_DETECTION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_qr)

        preview = findViewById(R.id.firePreview);
        if (preview == null) {
            Log.e(TAG, "Preview is null");
        }
        graphicOverlay = findViewById(R.id.fireFaceOverlay);
        if (graphicOverlay == null) {
            Log.e(TAG, "graphicOverlay is null")
        }

        if (allPermissionsGranted()) {
            createCameraSource(selectedModel)
        } else {
            getRuntimePermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    override fun onPause() {
        super.onPause()
        preview.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (cameraSource != null) {
            cameraSource!!.release()
        }
    }

    private fun createCameraSource(model: String) {
        if (cameraSource == null) {
            cameraSource = CameraSource(this, graphicOverlay)
        }

        try {

            when (model) {
                BARCODE_DETECTION -> {
                    Log.e(TAG, "Using Barcode Detector Processor");
                    cameraSource!!.setMachineLearningFrameProcessor(BarcodeScanningProcessor())
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "can not create camera source: $model :" + e.toString())
        }
    }

    private fun startCameraSource(){
        if (cameraSource != null){
            try {
                if (preview == null){
                    Log.e(TAG, "resume: Preview is null")
                }
                if (graphicOverlay == null){
                    Log.e(TAG, "resume: graphOverlay is null")
                }
                preview.start(cameraSource, graphicOverlay)
            } catch (e: IOException){
                Log.e(TAG, "Unable to start camera source.", e)
                cameraSource!!.release()
                cameraSource = null
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false
            }
        }
        return true
    }

    private fun getRequiredPermissions(): List<String> {
        return try {
            val info = this.packageManager
                    .getPackageInfo(this.packageName, PackageManager.GET_PERMISSIONS)
            val ps = info.requestedPermissions
            if (ps != null && ps.isNotEmpty()) {
                ps.toMutableList()
            } else {
                mutableListOf()
            }
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    private fun isPermissionGranted(context: Context, permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: $permission")
            return true
        }
        Log.i(TAG, "Permission NOT granted: $permission")
        return false
    }

    private fun getRuntimePermissions() {
        var allNeededPermissions = mutableListOf<String>()
        for (permission in getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission)
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    allNeededPermissions.toTypedArray(), PERMISSION_REQUESTS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.e(TAG, "Permission granted!")
        if (allPermissionsGranted()){
            createCameraSource(selectedModel)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}