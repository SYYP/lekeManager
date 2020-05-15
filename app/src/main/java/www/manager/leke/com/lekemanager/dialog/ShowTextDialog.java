package www.manager.leke.com.lekemanager.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;


import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.utils.UIUtils;

public class ShowTextDialog extends BaseDialog {
         TextView tv_msg;
    public ShowTextDialog(@NonNull Context context) {
        super(context);
    }

    public ShowTextDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_loading);
        tv_msg = (TextView) this.findViewById(R.id.tv_msg);
    }

    @Override
    protected View initLayout() {
        return UIUtils.inflate(R.layout.dialog_loading);
    }
}