package www.manager.leke.com.lekemanager.manager;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;

import www.manager.leke.com.lekemanager.utils.FileUtil;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.StringUtils;

/**
 * 功能：
 * 作者: YUAN_YE
 * 日期: 2019/11/4
 * 时间: 19:39
 */
public class NewMediaHelper {
    // 播放音频控件
    private MediaPlayer mMediaPlayer;
    private String mUrl;
    private Activity mActivity;
    //资源是否准备完成
    private boolean isPrepare;
    private int mVoicePs = 1;

    public void init(Activity activity) {
        mActivity = activity;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 当流媒体播放完毕的时候回调
        mMediaPlayer.setOnCompletionListener(mp -> {
            isPrepare = false;
//                mp.reset();
            if (mListener != null) {
                mListener.onCompletionPlayerListener(mVoicePs);
            }
        });

        // 当播放中发生错误的时候回调
        mMediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(mActivity, "播放失败", Toast.LENGTH_SHORT).show();
            isPrepare = false;
            mp.reset();
            return false;
        });

        //  当装载流媒体完毕的时候回调
        mMediaPlayer.setOnPreparedListener(mp -> {
            isPrepare = true;
            mp.start();
        });
    }

    public boolean isPlay() {
        return mMediaPlayer.isPlaying();
    }

    public boolean isPrepare() {
        return isPrepare;
    }

    private void start() {
        try {
            mMediaPlayer.setDataSource(mActivity, Uri.parse(mUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    //暂停
    public void pause() {
        if (isPlay())
            mMediaPlayer.pause();
    }

    // 继续播放
    public void continuePlay() {
        if (!isPlay())
            mMediaPlayer.start();
    }

    public void reset() {
        isPrepare = false;
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        }

    }

    private void changeUrl() {
        isPrepare = false;//进入资源为准备状态
        mMediaPlayer.reset();//初始化
        start();//开始播放
    }


    public void start(String url, int voicePs) {

        mVoicePs = voicePs;

        if (StringUtils.isEmpty(url) || !FileUtil.exists(url)) {
            return;
        }

        if (StringUtils.isEmpty(mUrl)) {
            this.mUrl = url;
            start();
            LogUtils.e("start ....... mUrl === null");
        } else {
            this.mUrl = url;
            LogUtils.e(" changeUrl...........................");
            changeUrl();
        }

    }

    public CompletionPlayerListener mListener;

    public void setCompletionListener(CompletionPlayerListener listener) {
        mListener = listener;
    }

    public interface CompletionPlayerListener {
        void onCompletionPlayerListener(int voicePs);
    }

    public void playerRelease() {
        if (mMediaPlayer != null) {
            pause();
            mMediaPlayer.release();
        }
    }

}
