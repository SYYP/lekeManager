package www.manager.leke.com.lekemanager.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.dialog.BaseDialogs;
import www.manager.leke.com.lekemanager.dialog.DialogUtils;

/**
 * Created by ypu
 * on 2020/5/11 0011
 */
public class ReadPdfBookActivity extends BaseFragmentActivity {
    @BindView(R.id.img_back)
    TextView imgBack;
    @BindView(R.id.real_more)
    RelativeLayout realMore;
    @BindView(R.id.real_ml)
    RelativeLayout realMl;
    @BindView(R.id.top_btn_bar1)
    RelativeLayout topBtnBar1;
    @BindView(R.id.img_pdf_update)
    TextView imgPdfUpdate;
    @BindView(R.id.img_read_update)
    TextView imgReadUpdate;
    @BindView(R.id.img_read_position)
    TextView imgReadPosition;
    @BindView(R.id.liner_more)
    LinearLayout linerMore;
    boolean isFirst = true;
    private DialogUtils mDialogUtils;
    private View mView;

    @Override
    public void init() {

    }

    @Override
    public View layout() {
        mView = inflate(R.layout.activity_read_pdf);
        return mView;
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void dimissHtpp() {

    }

    @OnClick({R.id.real_more, R.id.real_ml, R.id.img_read_update, R.id.img_read_position,R.id.img_pdf_update,R.id.img_back,R.id.img_refush})
    protected void OnClick(View view) {

        switch (view.getId()) {

            case R.id.real_more:
                if (isFirst) {
                    linerMore.setVisibility(View.VISIBLE);
                    isFirst = false;
                } else {
                    linerMore.setVisibility(View.GONE);
                    isFirst = true;
                }
                break;

            case R.id.real_ml:
                break;

            case R.id.img_pdf_update://pdf更新
                //先去接口差是否有更新如果有弹窗提示有，没有弹窗提示没有
                if (mDialogUtils == null) {
                    mDialogUtils = new DialogUtils(this);
                }
                mDialogUtils.setTitle("PDF更新，是否下载新版本？");
                mDialogUtils.setTime("更新时间:2020年04月10号");
                mDialogUtils.setBtnName("点击更新");
                mDialogUtils.showUpdateBook();
                mDialogUtils.setBookOnClick(new DialogUtils.BookOnClick() {
                    @Override
                    public void OnClickListener(BaseDialogs mDialogs) {
                        mDialogs.dismiss();
                    }
                });
                break;
            case R.id.img_read_update://点读更新
                if (mDialogUtils == null) {
                    mDialogUtils = new DialogUtils(this);
                }
                mDialogUtils.setTitle("点读文件有更新，是否更新新版本？");
                mDialogUtils.setTime("更新时间:2020年04月10号");
                mDialogUtils.setBtnName("点击更新");
                mDialogUtils.showUpdateBook();
                mDialogUtils.setBookOnClick(new DialogUtils.BookOnClick() {
                    @Override
                    public void OnClickListener(BaseDialogs mDialogs) {
                        mDialogs.dismiss();
                    }
                });
                break;
            case R.id.img_read_position://点读位置查阅
                break;

            case R.id.img_back:
                finish();
                break;
            case R.id.img_refush:
                EpdController.invalidate(mView, UpdateMode.GC);
                break;


        }
    }

}
