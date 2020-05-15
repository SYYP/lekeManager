package www.manager.leke.com.lekemanager.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.utils.UIUtils;

public class LoadingProgressDialog extends BaseDialog {
    private TextView mTvMsg;

    public LoadingProgressDialog(Context context) {
        super(context);
    }

    public LoadingProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
//        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }


    @Override
    protected View initLayout() {
        View view = UIUtils.inflate(R.layout.load_layout);
        setContentView(view);
        return view;
    }
}
