package www.manager.leke.com.lekemanager.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import www.manager.leke.com.lekemanager.bean.BaseEvent;
import www.manager.leke.com.lekemanager.bean.BookVoiceBean;
import www.manager.leke.com.lekemanager.dialog.LoadingProgressDialog;
import www.manager.leke.com.lekemanager.dialog.ShowTextDialog;
import www.manager.leke.com.lekemanager.manager.AppManager;
import www.manager.leke.com.lekemanager.utils.Toaster;


public abstract class BaseFragmentActivity extends RxAppCompatActivity {

    public static BaseFragmentActivity mForegroundActivity;
    public LoadingProgressDialog mLoadingDialog;
    public ShowTextDialog mShowTextDialog;
    private static final List<BaseFragmentActivity> mActivities = new LinkedList<>();
    private Unbinder unbind;
    protected BaseFragmentActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActivities.add(this);
        EventBus.getDefault().register(this);
        setContentView(layout());
        unbind = ButterKnife.bind(this);
        processExtraData();
        mContext = this;
        init();
        loadData();
    }

    public void onEventMainThread(BaseEvent event) {
        if (event == null) {
            return;
        }
    }

    @Override
    protected void onResume() {
        mForegroundActivity = this;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mForegroundActivity = null;
        super.onPause();
    }

    //初始化参数
    public abstract void init();

    //初始化布局
    public abstract View layout();

    //加载数据
    public abstract void loadData();
    public static Context getContext() {
        return BaseFragmentActivity.getForegroundActivity();
//        return weakReference.get();
    }
    public View inflate(int resId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        return inflater.inflate(resId, null);
    }

    public void loadIntent(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    public void loadIntentFlag(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    public void loadIntentWithExtras(Class<? extends Activity> cls, Bundle extras) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void loadIntentWithExtra(Class<? extends Activity> cls, String key, int value) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * 获取当前处于前台的activity
     */
    public static BaseFragmentActivity getForegroundActivity() {
        return mForegroundActivity;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processExtraData();
    }

    public static int getScreenWidth() {
        if (mForegroundActivity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            mForegroundActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.widthPixels;     // 屏幕宽度（像素）
        }
        return 0;
    }

    public static int getScreenHeight() {

        if (mForegroundActivity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            mForegroundActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.heightPixels;     // 屏幕高度（像素）
        }
        return 0;
    }


    public void processExtraData() {
    }

    public static void showToast(final String str) {
        if (AppManager.getMainThreadId() == Process.myTid()) {
            Toast.makeText(AppManager.getAppContext(), str, Toast.LENGTH_LONG).show();
        } else {
            AppManager.postHandler(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AppManager.getAppContext(), str, Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    public static void showDefaultToast(final BookVoiceBean.VoiceBean bean) {
        if (AppManager.getMainThreadId() == Process.myTid()) {
            Toaster.showDefaultToast(bean);
        } else {
            AppManager.postHandler(new Runnable() {
                @Override
                public void run() {
                    Toaster.showDefaultToast(bean);
                }
            });
        }
    }


    /***
     *  显示加载框
     */
    public void showTextDialog(String str) {
//        if (mShowTextDialog == null) {
//            mShowTextDialog = new ShowTextDialog(this);
//        }
//        if (!mShowTextDialog.isShowing()) {
//            mShowTextDialog.show();
//            mShowTextDialog.setMsg(str);
//        }
    }

    /***
     *  关闭加载框
     */
    public void dimissTextDialog() {
        if (mShowTextDialog != null && mShowTextDialog.isShowing()) {
            mShowTextDialog.dismiss();
        }
    }


    /***
     *  显示加载框
     */
    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog = new LoadingProgressDialog(this);
            mLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dimissHtpp();
                    dimissLoadingDialog();
                }
            });
            mLoadingDialog.show();
        }
    }

    /***
     *  关闭加载框
     */
    public void dimissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
        EventBus.getDefault().unregister(this);
        mActivities.remove(this);
    }

    protected abstract void dimissHtpp();

    /**
     * 获取当前处于栈顶的activity，无论其是否处于前台
     */
    public static BaseFragmentActivity getCurrentActivity() {
        List<BaseFragmentActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseFragmentActivity>(mActivities);
        }
        if (copy.size() > 0) {
            return copy.get(copy.size() - 1);
        }
        return null;
    }
    public void startIntentWithExtras(Class<?> cls, Bundle extras) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }
    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseFragmentActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseFragmentActivity>(mActivities);
        }
        for (BaseFragmentActivity activity : copy) {
            activity.finish();
        }
        copy.clear();
    }


}
