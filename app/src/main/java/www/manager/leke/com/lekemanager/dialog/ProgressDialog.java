package www.manager.leke.com.lekemanager.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;



import butterknife.BindView;
import butterknife.OnClick;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * 功能：
 * 作者: YUAN_YE
 * 日期: 2019/7/15
 * 时间: 18:52
 */
public abstract class ProgressDialog extends BaseDialog {
    @BindView(R.id.progress_bar)
    ProgressBar tvProgress;


    public ProgressDialog(@NonNull Context context) {
        super(context);
    }

    public ProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected View initLayout() {
        View view = UIUtils.inflate(R.layout.dialog_progress);
        setContentView(view);
        return view;
    }



    public abstract void cancel();

    public void setProgress( int progress) {
        tvProgress.setProgress(progress);
    }

}
