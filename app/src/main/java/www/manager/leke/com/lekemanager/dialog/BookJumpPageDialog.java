package www.manager.leke.com.lekemanager.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;



import butterknife.BindView;
import butterknife.OnClick;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.utils.SoftInputUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * 功能：跳转页面 弹窗
 * 作者: Cao ZH
 * 日期: 2020/6/13 0016
 * 时间: 下午 13:49
 */
public class BookJumpPageDialog extends BaseDialog {

    @BindView(R.id.et_page)
    EditText editPage;
    private int mCurrentPage;//页码从1开始
    private int mTotalPage;
    private IJumpPageListener iJumpPageListener;


    @Override
    protected View initLayout() {
        View view = UIUtils.inflate(R.layout.dialog_jump_page);
        setContentView(view);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        return view;
    }

    @OnClick({R.id.btn_confirm, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                String inputText = editPage.getText().toString().trim();
                if (TextUtils.isEmpty(inputText))return;
                int inputNumber = 0;
                try {
                    inputNumber = Integer.parseInt(inputText);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return;
                }
                if (inputNumber<1 || inputNumber>mTotalPage){
                    UIUtils.showToastSafe("超出区域", Toast.LENGTH_SHORT);
                    editPage.setText("");
                    return;
                }
                SoftInputUtils.closeKeybord(editPage,getContext());
                dismiss();
                mCurrentPage = inputNumber-1;
                if (iJumpPageListener != null) iJumpPageListener.jumpPage(mCurrentPage);
                break;
            case R.id.btn_cancel:
                SoftInputUtils.closeKeybord(editPage,getContext());
                dismiss();
                if (iJumpPageListener != null) iJumpPageListener.onCancel();
                break;
        }
    }

    public BookJumpPageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void init() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);

    }

    @SuppressLint("SetTextI18n")
    public void showDialog(int currentPage, int totalPage, IJumpPageListener jumpPageListener) {
        show();
        mCurrentPage = currentPage;
        mTotalPage = totalPage;
        iJumpPageListener = jumpPageListener;
        editPage.setHint(String.valueOf(currentPage+1));
        editPage.postDelayed(() -> SoftInputUtils.openKeybord(editPage,getContext()),200);

    }

    public interface IJumpPageListener {
        void jumpPage(int page);
        void onCancel();
    }
}
