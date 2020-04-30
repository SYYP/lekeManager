package www.manager.leke.com.lekemanager.dialog;

import android.content.Context;
import android.widget.TextView;


import www.manager.leke.com.lekemanager.R;

public class ShowTextDialog extends BaseDialog {
    private TextView tv_msg;

    public ShowTextDialog(Context context) {
        super(context);
    }

    public ShowTextDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void initLayout() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_loading);
        tv_msg = (TextView) this.findViewById(R.id.tv_msg);
    }

    public void setMsg(String str) {
        tv_msg.setText(str);
    }
}