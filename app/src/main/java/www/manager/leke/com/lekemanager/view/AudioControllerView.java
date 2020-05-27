package www.manager.leke.com.lekemanager.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.manager.NewMediaHelper;

/**
 * 功能：教材点读控制view
 * 作者: Cao ZH
 * 日期: 2020/4/22 0020
 * 时间: 下午 15:21
 */
public class AudioControllerView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private FrameLayout fl_right;
    private FrameLayout fl_left;
    private LinearLayout ll_controller;
    private ImageButton imgBtn_play;
    private ImageButton imgBtn_pre;
    private ImageButton imgBtn_next;
    private ImageButton imgBtn_close;
    private TouchMoveView imgBtn_show_hide;
    private LinearLayout ll_controller2;
    private ImageButton imgBtn_play2;
    private ImageButton imgBtn_pre2;
    private ImageButton imgBtn_next2;
    private ImageButton imgBtn_close2;
    private TouchMoveView imgBtn_show_hide2;
    private int playPosition = -1;
    private List<String> audioPaths = new ArrayList<>();
    private NewMediaHelper mNewMediaHelper;
    private AudioControllerListener mControllerListener;
    private List<View> unWriteViews = new ArrayList<>();
    private boolean isContinuousPlay;//是否连续播放
    private boolean isShowRight = true;
    private onTouchListener mOnTouchListener;

    public AudioControllerView(Context context) {
        super(context);
        initView(context);
    }

    public AudioControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AudioControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_audio_controller, this);
        fl_left = findViewById(R.id.fl_left);
        fl_right = findViewById(R.id.fl_right);
        ll_controller = findViewById(R.id.ll_controller);
        imgBtn_play = findViewById(R.id.imgBtn_play);
        imgBtn_pre = findViewById(R.id.imgBtn_pre);
        imgBtn_next = findViewById(R.id.imgBtn_next);
        imgBtn_close = findViewById(R.id.imgBtn_close);
        imgBtn_show_hide = findViewById(R.id.imgBtn_show_hide);
        imgBtn_play.setSelected(true);
        imgBtn_play.setOnClickListener(this);
        imgBtn_pre.setOnClickListener(this);
        imgBtn_next.setOnClickListener(this);
        imgBtn_close.setOnClickListener(this);
        imgBtn_show_hide.setOnClickListener(this);

        ll_controller2 = findViewById(R.id.ll_controller2);
        imgBtn_play2 = findViewById(R.id.imgBtn_play2);
        imgBtn_pre2 = findViewById(R.id.imgBtn_pre2);
        imgBtn_next2 = findViewById(R.id.imgBtn_next2);
        imgBtn_close2 = findViewById(R.id.imgBtn_close2);
        imgBtn_show_hide2 = findViewById(R.id.imgBtn_show_hide2);
        imgBtn_play2.setSelected(true);
        imgBtn_play2.setOnClickListener(this);
        imgBtn_pre2.setOnClickListener(this);
        imgBtn_next2.setOnClickListener(this);
        imgBtn_close2.setOnClickListener(this);
        imgBtn_show_hide2.setOnClickListener(this);

        mOnTouchListener = new onTouchListener();
        imgBtn_show_hide.setListener(mOnTouchListener);
        imgBtn_show_hide2.setListener(mOnTouchListener);
    }

    private void starPlayFromFirst() {
        if (mControllerListener != null) mControllerListener.leaveWrite();
        imgBtn_show_hide.setVisibility(GONE);
        ll_controller.setVisibility(VISIBLE);
        imgBtn_show_hide2.setVisibility(GONE);
        ll_controller2.setVisibility(VISIBLE);
        //TODO 开始重头播放
        playPosition = 1;
        if (mNewMediaHelper.isPlay()) {
            mNewMediaHelper.reset();
        }
        startPlay(playPosition);
        if (mControllerListener != null) mControllerListener.intoWrite();
    }

    private void stopPlay() {
        if (mControllerListener != null) mControllerListener.leaveWrite();
        playPosition = -1;
        setPlayViewSelected(true);
        mNewMediaHelper.reset();
        ll_controller.setVisibility(GONE);
        imgBtn_show_hide.setVisibility(VISIBLE);
        ll_controller2.setVisibility(GONE);
        imgBtn_show_hide2.setVisibility(VISIBLE);
        if (mControllerListener != null) {
            mControllerListener.playPositionChange(playPosition);
            mControllerListener.intoWrite();
        }
    }

    private void playPre() {
        startPlay(playPosition - 1);
    }

    private void playNext() {
        startPlay(playPosition + 1);
    }

    public void init(Activity activity) {
        mNewMediaHelper = new NewMediaHelper();
        mNewMediaHelper.setCompletionListener(voicePs -> {
            if (isContinuousPlay) {
                //连续播放
                playPosition++;
                if (playPosition >= audioPaths.size()) {
                    //最后一个了  停止播放
                    stopPlay();
                } else {
                    //播放下一个
                    startPlay(playPosition);
                }
            } else {
                //非连续播放  直接停止播放
                stopPlay();
            }
        });
        mNewMediaHelper.init(activity);
    }

    public void setAudioPaths(List<String> audioPaths) {
        this.audioPaths = audioPaths;
        mNewMediaHelper.reset();
        ll_controller.setVisibility(GONE);
        ll_controller2.setVisibility(GONE);
        playPosition = -1;
        setPlayViewSelected(true);
    }

    public LinearLayout getLlController() {
        return ll_controller;
    }

    private void startPlay(int position) {
        if (audioPaths == null || audioPaths.size() < position || position <= 0) return;
        isContinuousPlay = true;
        playPosition = position;
        mNewMediaHelper.start(audioPaths.get(position - 1), position);
        setPlayViewSelected(false);
        if (mControllerListener != null) mControllerListener.playPositionChange(position);
    }

    /**
     * 外面点击某个音频按钮  单独播放 不连播
     *
     * @param position 点击位置
     */
    public void startPlaySingle(int position) {
        if (audioPaths == null || audioPaths.size() < position || position <= 0) return;
        isContinuousPlay = false;
        if (mNewMediaHelper.isPlay()) {
            mNewMediaHelper.reset();
            ll_controller.setVisibility(GONE);
            imgBtn_show_hide.setVisibility(VISIBLE);
            ll_controller2.setVisibility(GONE);
            imgBtn_show_hide2.setVisibility(VISIBLE);
        }
        mNewMediaHelper.start(audioPaths.get(position - 1), position);
        if (mControllerListener != null) mControllerListener.playPositionChange(position);
    }

    public void setControllerListener(AudioControllerListener controllerListener) {
        mControllerListener = controllerListener;
    }

    public List<View> getUnWriteView() {
        unWriteViews.clear();
        if (isShowRight) {
            unWriteViews.add(ll_controller);
            unWriteViews.add(imgBtn_show_hide);
        } else {
            unWriteViews.add(ll_controller2);
            unWriteViews.add(imgBtn_show_hide2);
        }
        return unWriteViews;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public void pause() {
        if (!playViewIsSelected()) {
            playViewCallOnClick();
        }
    }

    public void destroy() {
        mNewMediaHelper.reset();
        playPosition = -1;
    }

    private void playViewCallOnClick() {
        if (isShowRight) {
            imgBtn_play.callOnClick();
        } else {
            imgBtn_play2.callOnClick();
        }
    }

    private boolean playViewIsSelected() {
        if (isShowRight) {
            return imgBtn_play.isSelected();
        } else {
            return imgBtn_play2.isSelected();
        }
    }

    private void setPlayViewSelected(boolean isSelected) {
        if (isShowRight) {
            imgBtn_play.setSelected(isSelected);
        } else {
            imgBtn_play2.setSelected(isSelected);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtn_play:
            case R.id.imgBtn_play2:
                if (mControllerListener != null) mControllerListener.leaveWrite();
                if (playViewIsSelected()) {
                    if (playPosition < 0) {
                        startPlay(1);
                        setPlayViewSelected(false);
                    } else {
                        mNewMediaHelper.continuePlay();
                    }
                    setPlayViewSelected(false);
                } else {
                    mNewMediaHelper.pause();
                    setPlayViewSelected(true);
                }
                if (mControllerListener != null) mControllerListener.intoWrite();
                break;
            case R.id.imgBtn_pre:
            case R.id.imgBtn_pre2:
                if (mControllerListener != null) mControllerListener.leaveWrite();
                playPre();
                if (mControllerListener != null) mControllerListener.intoWrite();
                break;
            case R.id.imgBtn_next:
            case R.id.imgBtn_next2:
                if (mControllerListener != null) mControllerListener.leaveWrite();
                playNext();
                if (mControllerListener != null) mControllerListener.intoWrite();
                break;
            case R.id.imgBtn_close:
            case R.id.imgBtn_close2:
                stopPlay();
                break;
            case R.id.imgBtn_show_hide:
            case R.id.imgBtn_show_hide2:
                starPlayFromFirst();
                break;
            default:
                break;
        }
    }

    private class onTouchListener implements TouchMoveView.UpdateViewListener {

        @Override
        public void onUpdateViewListener(float upX, float upY) {
            float centerX = getResources().getDimension(R.dimen.x702);
            if (upX < centerX) {
                setViewGravityLeft();
            } else {
                setViewGravityRight();
            }
            setViewLocation(upY);
            if (mControllerListener != null) mControllerListener.intoWrite();
        }

        @Override
        public void onTouchViewListener() {
            if (mControllerListener != null) mControllerListener.leaveWrite();
        }
    }

    private void setViewLocation(float upY) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        float limitTop = getResources().getDimension(R.dimen.y120);
        float limitBottom = getResources().getDimension(R.dimen.y1666);
        if (upY < limitTop) {
            upY = limitTop;
        } else if (upY > limitBottom) {
            upY = limitBottom;
        }
        params.topMargin = (int) upY;
        setLayoutParams(params);
    }

    private void setViewGravityLeft() {
        if (isShowRight) {
            fl_left.setVisibility(VISIBLE);
            fl_right.setVisibility(GONE);
            imgBtn_play2.setSelected(imgBtn_play.isSelected());
            isShowRight = false;
        }
    }

    private void setViewGravityRight() {
        if (!isShowRight) {
            fl_left.setVisibility(GONE);
            fl_right.setVisibility(VISIBLE);
            imgBtn_play.setSelected(imgBtn_play2.isSelected());
            isShowRight = true;
        }
    }

    public interface AudioControllerListener {
        void leaveWrite();

        void intoWrite();

        void playPositionChange(int position);
    }
}
