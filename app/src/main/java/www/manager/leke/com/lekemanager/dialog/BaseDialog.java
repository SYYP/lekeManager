package www.manager.leke.com.lekemanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

public abstract class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }


    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, initLayout());
        init();
    }


    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        Display display = window.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        window.setLayout(width, height);
    }

    protected abstract void init();

    protected abstract View initLayout();

}