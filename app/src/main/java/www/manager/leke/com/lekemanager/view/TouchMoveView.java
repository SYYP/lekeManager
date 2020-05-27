package www.manager.leke.com.lekemanager.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;



import www.manager.leke.com.lekemanager.utils.LogUtils;

/**
 * 功能：拖动移动位置view
 * 作者: Cao ZH
 * 日期: 2020/5/25 0012
 * 时间: 下午 16:13
 */
@SuppressLint("AppCompatCustomView")
public class TouchMoveView extends ImageButton implements View.OnTouchListener {
    private GestureDetector mGestureDetector;
    public TouchMoveView(Context context) {
        super(context);
        init();
    }

    public TouchMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), new MyOnGestureListener());
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtils.e(TouchMoveView.class.getName(), "。。。。。。。。。。。。onTouch");
            if (mListener != null) {
                mListener.onTouchViewListener();
            }
        }
        return false;
    }

    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            performClick();
            LogUtils.e(TouchMoveView.class.getName(), "。。。。。。。。。。。。mFlagSingleTapUp" + e.getAction());
            return true;
        }
    }


    private UpdateViewListener mListener;

    public void setListener(UpdateViewListener listener) {
        mListener = listener;
    }

    public interface UpdateViewListener {
        void onUpdateViewListener(float upX, float upY);

        void onTouchViewListener();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        LogUtils.e(TouchMoveView.class.getName(), "onTouchEvent");
        boolean result = mGestureDetector.onTouchEvent(event);
        LogUtils.e(TouchMoveView.class.getName(), "result:" + result);

        if (event.getAction() == MotionEvent.ACTION_UP && !result) {
            LogUtils.e(TouchMoveView.class.getName(), "ACTION_UP:");
            float upX = event.getRawX();
            float upY = event.getRawY();
            if (null != mListener) {
                mListener.onUpdateViewListener(upX,upY);
            }
        }
        return true;
    }
}
