package hoge.hogehoge.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * タッチイベントを消費するView
 * タッチイベントを子Viewに送りたくない時に使用する
 *
 * @constructor
 *
 * @param context Context
 * @param attrs AttributeSet
 * @param defStyle Int
 */
class ConsumingTouchEventLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }
}
