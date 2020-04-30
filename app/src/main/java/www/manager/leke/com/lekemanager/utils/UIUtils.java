package www.manager.leke.com.lekemanager.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.manager.AppManager;


/**
 * Created by lipan on 2014/6/19.
 */
public class UIUtils {

    /**
     * 获取Context
     */
    public static Context getContext() {
        return AppManager.getAppContext();
    }

    /**
     * 获取应用资源对象
     */
    public static Resources getResources() {
        if (AppManager.getAppContext() != null) {
            return AppManager.getAppContext().getResources();
        } else {
            return null;
        }
    }
    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        if (getResources() != null) {
            final float scale = getResources().getDisplayMetrics().density;
            return (int) (dip * scale + 0.5f);
        } else {
            return 0;
        }
    }

    /**
     * px转换dip
     */
    public static int px2dip(int px) {
        if (getResources() != null) {
            final float scale = getResources().getDisplayMetrics().density;
            return (int) (px / scale + 0.5f);
        } else {
            return 0;
        }
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取dimen值
     */
    public static int getDimens(int resId) {
        if (getResources() != null) {
            return getResources().getDimensionPixelSize(resId);
        } else {
            return 0;
        }
    }

    /**
     * 获取图片
     */
    public static Drawable getDrawable(int resId) {
        if (getResources() != null) {
            return getResources().getDrawable(resId);
        } else {
            return null;
        }
    }

    /**
     * 获取图片
     */
    public static Bitmap getBitmap(int resId) {
        if (getResources() != null) {
            return BitmapFactory.decodeResource(getResources(), resId);
        } else {
            return null;
        }
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        if (getResources() != null) {
            return getResources().getColor(resId);
        } else {
            return 0;
        }
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        if (getResources() != null) {
            return getResources().getColorStateList(resId);
        } else {
            return null;
        }
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        if (getResources() != null) {
            return getResources().getString(resId);
        } else {
            return null;
        }
    }

    /**
     * 获取文字，并按照后面的参数进行格式化
     */
    public static String getString(int resId, Object... formatAgrs) {
        if (getResources() != null) {
            return getResources().getString(resId, formatAgrs);
        } else {
            return null;
        }
    }

    /**
     * 根据指定的layout索引，创建一个View
     *
     * @param resId 指定的layout索引
     * @return 新的View
     */
    public static View inflate(int resId) {
        Context context = UIUtils.getContext();
        if (context != null) {
            return LayoutInflater.from(context).inflate(resId, null);
        }
        return null;
    }

    public static void showToast(final int resId, final int duration) {

        if (AppManager.getMainThreadId() == Process.myTid()) {
            Toaster.showDefaultToast(getContext(), resId, duration);
        } else {
            AppManager.postHandler(new Runnable() {
                @Override
                public void run() {
                    Toaster.showDefaultToast(getContext(), resId, duration);
                }
            });
        }

    }



    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     * @param resId    Toast内容的资源id
     */
    public static void showToastSafe(final int resId) {

        if (AppManager.getMainThreadId() == Process.myTid()) {
            Toaster.showDefaultToast(getContext(), resId, Toast.LENGTH_SHORT);
        } else {
            AppManager.postHandler(new Runnable() {
                @Override
                public void run() {
                    Toaster.showDefaultToast(getContext(), resId, Toast.LENGTH_SHORT);
                }
            });
        }
        
    }





    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     *
     * @param text     Toast内容
     * @param duration Toast的持续时间
     */
    public static void showToastSafe(final CharSequence text, final int duration) {
        if (AppManager.getMainThreadId() == Process.myTid()) {
                Toaster.showDefaultToast(getContext(), text, duration);
        } else {
            AppManager.postHandler(new Runnable() {
                @Override
                public void run() {
                Toaster.showDefaultToast(getContext(), text, duration);
                }
            });
        }
        
    }
    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     *
     * @param text     Toast内容
     */
    public static void showToastSafe(final CharSequence text) {
        showToastSafe(text , Toast.LENGTH_SHORT);
    }

    public static int getScreenWidth() {
        BaseFragmentActivity activity = BaseFragmentActivity.getCurrentActivity();
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.widthPixels;     // 屏幕宽度（像素）
        }
        return 0;
    }

    public static int getScreenHeight() {
        BaseFragmentActivity activity =BaseFragmentActivity.getCurrentActivity();
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.heightPixels;     // 屏幕高度（像素）
        }
        return 0;
    }

    public static DisplayMetrics getDisplayMetrics() {
        BaseFragmentActivity activity = BaseFragmentActivity.getCurrentActivity();
        if (activity != null) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            return dm;
        }
        return null;
    }

    public static int getStatusBarHeight() {
        int statusHeight = 0;
        BaseFragmentActivity activity = BaseFragmentActivity.getForegroundActivity();
        if (activity == null) {
            return statusHeight;
        }

        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /***
     * 测量 控件的  宽高 ，主要是在getMeasured==0使用 ，
     *  == 0的场景 在oncreat方法
     * @param view
     * @return
     */
    public static int [] getViewWidthAndHeight( View view ) {
        int [] result = new int[2] ;

        int w  = view.getMeasuredWidth() ;
        int h  = view.getMeasuredHeight() ;

        if (w == 0 || h == 0){
            view.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
            w  = view.getMeasuredWidth() ;
            h  = view.getMeasuredHeight() ;
        }

//        System.out.println("w =="+w);
//        System.out.println("h =="+h) ;
        result[0] = w;
        result[1] = h;
        return  result ;
    }


    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in {@link View#setId(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static String getTopActivityName(Context context) {
        ActivityManager manager = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE));
        ActivityManager.RunningTaskInfo runningTaskInfo = manager.getRunningTasks(1).get(0);
        String topActivityName = "";
        if (runningTaskInfo!=null) {
            topActivityName = runningTaskInfo.topActivity.getClassName();
        }
        return topActivityName;
    }

}
