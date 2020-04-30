package www.manager.leke.com.lekemanager.dialog;

import android.content.Context;


import www.manager.leke.com.lekemanager.R;

public class LoadingProgressDialog extends BaseDialog {
    public LoadingProgressDialog(Context context) {
        super(context);
    }

    public LoadingProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.dialog_loading);
    }
}
