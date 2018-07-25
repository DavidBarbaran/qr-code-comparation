package qrcode.compare.custom

import android.content.Context
import android.util.AttributeSet
import me.dm7.barcodescanner.core.ViewFinderView

/**
 * Created by David on 24/07/2018.
 */
class CustomViewFinderView(context: Context?, attrs: AttributeSet?) : ViewFinderView(context, attrs) {

    constructor(context: Context) : this(context, null)

    override fun updateFramingRect() {
        super.updateFramingRect()
        super.getFramingRect().set(0,0,width,height)
    }
}