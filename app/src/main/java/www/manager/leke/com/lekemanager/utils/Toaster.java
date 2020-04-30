package www.manager.leke.com.lekemanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.bean.BookVoiceBean;
import www.manager.leke.com.lekemanager.manager.AppManager;

public class Toaster {
    private static Toast mToast = null;
    private static View mView;
    private static TextView mText;

    public static void showDefaultToast(BookVoiceBean.VoiceBean bean) {
        if (mToast == null) {
            mToast = Toast.makeText(AppManager.getAppContext(), bean.toString(), Toast.LENGTH_LONG);
            mView = LayoutInflater.from(AppManager.getAppContext()).inflate(R.layout.toast_layout, null);
            mText = mView.findViewById(R.id.customToast_innerLayout_txtMessage);
            mToast.setView(mView);
        }

        mText.setText(bean.toString());
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.TOP, 0, 0);
        mToast.show();
    }

    public static void showDefaultToast(Context context, int text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
            mView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            mText = mView.findViewById(R.id.customToast_innerLayout_txtMessage);
            mToast.setView(mView);
        }

        mText.setText(text);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.TOP, 0, 0);
        mToast.show();
    }

    public static void showDefaultToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
            mView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            mText = mView.findViewById(R.id.customToast_innerLayout_txtMessage);
            mToast.setView(mView);
        }

        mText.setText(text);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.TOP, 0, 0);
        mToast.show();
    }

    @SuppressLint("InflateParams")
    public static void showGravityToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
            mView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            mText = mView.findViewById(R.id.customToast_innerLayout_txtMessage);
            mToast.setView(mView);
        }
        mText.setText(text);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.TOP, 0, 0);
        mToast.show();
    }

    @SuppressLint("InflateParams")
    public static void showGravityToast(Context context, int text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
            mView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            mText = mView.findViewById(R.id.customToast_innerLayout_txtMessage);
            mToast.setView(mView);
        }
        mText.setText(text);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.TOP, 0, 0);
        mToast.show();
    }

}
