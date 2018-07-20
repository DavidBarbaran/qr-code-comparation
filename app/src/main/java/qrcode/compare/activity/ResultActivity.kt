package qrcode.compare.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.os.Build
import android.widget.TextView
import qrcode.compare.R
import qrcode.compare.config.Setting


class ResultActivity : AppCompatActivity() {

    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        resultText = findViewById(R.id.result_text)

        val value = intent.extras.getString(Setting.VALUE)
        resultText.text = value
    }
}
