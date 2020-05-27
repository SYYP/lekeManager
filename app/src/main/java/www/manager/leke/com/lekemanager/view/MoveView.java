package www.manager.leke.com.lekemanager.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.utils.LogUtils;

public class MoveView extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener {
    private int lastX;
    private int lastY;
    private int mViewW;
    private int mViewH;
    private RelativeLayout.LayoutParams mLayoutParams;

    public MoveView(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        LogUtils.e("MoveView..onTouchEvent");
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                lastX = x;
                lastY = y;
                break;
//            case MotionEvent.ACTION_MOVE:
//
//                break;
            case MotionEvent.ACTION_UP:

                int offsetX = x - lastX;
                int offsetY = y - lastY;
                mLayoutParams = (RelativeLayout.LayoutParams) getLayoutParams();

                if (getLeft() + offsetX < 0) {
                    mLayoutParams.leftMargin = 0;
                } else if (getLeft() + offsetX > BaseFragmentActivity.getScreenWidth() - mViewW) {
                    mLayoutParams.leftMargin = BaseFragmentActivity.getScreenWidth() - mViewW;
                } else {
                    mLayoutParams.leftMargin = getLeft() + offsetX;
                }

                if (getTop() + offsetY < 0) {
                    mLayoutParams.topMargin = 0;
                } else if (getTop() + offsetY > BaseFragmentActivity.getScreenHeight() - mViewW) {
                    mLayoutParams.topMargin = BaseFragmentActivity.getScreenHeight() - mViewH;
                } else {
                    mLayoutParams.topMargin = getTop() + offsetY;
                }

                setLayoutParams(mLayoutParams);
                if (mListener!=null){
                    mListener.onUpLocalListener(mLayoutParams.leftMargin ,mLayoutParams.topMargin);
                }
                break;
        }
        super.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewH = getMeasuredHeight();
        mViewW = getMeasuredWidth();
//        LogUtils.e("mViewH..." + mViewH);
//        LogUtils.e("mViewW ...." + mViewW);
//        LogUtils.e("screenW..." + BaseFragmentActivity.getScreenWidth());
//        LogUtils.e("screenH ...." + BaseFragmentActivity.getScreenHeight());
    }

    /////////////////////////////////////////////////////////////////////////////////////
    private UpdataLocalListener mListener;

    public void setUpLocalListener(UpdataLocalListener listener) {
        mListener = listener;
    }

    public interface UpdataLocalListener {

        void onUpLocalListener(int left, int top);
    }
}
