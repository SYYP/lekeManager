package www.manager.leke.com.lekemanager.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.manager.AppManager;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/9 0009
 */
public class DialogUtils {

    private BaseDialogs mDialogs;
    BaseDialogs.Builder mBuilder;
    private String mTitle;
    private String mTime;
    private String btnName;


    private volatile static DialogUtils mSingleton = null;

    public DialogUtils(Context context) {
        mBuilder = new BaseDialogs.Builder(context);
    }

    public DialogUtils(Context context, String title, String time, String btnName) {
        mBuilder = new BaseDialogs.Builder(context);
        this.mTitle = title;
        this.mTime = time;
        this.btnName = btnName;
    }
    public  void  setTitle(String title){
        this.mTitle=title;
    }
    public void setTime(String time){
        this.mTime=time;
    }
    public void setBtnName(String name){
        this.btnName=name;

    }



    public interface SunmissionOnClick {
        void OnClickListener(BaseDialogs mDialogs, String mEditText);
    }

    private SunmissionOnClick mSunmissionOnClick;

    public void setSunmissionOnClick(SunmissionOnClick sunmissionOnClick) {

        this.mSunmissionOnClick = sunmissionOnClick;
    }

    //提交
    public void TijiaoDialog() {
        //设置触摸dialog外围是否关闭
        mDialogs = mBuilder.setViewId(R.layout.dialog_edit_book)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.Alpah_aniamtion)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                .builder();
        mDialogs.show();
        final EditText editText = mDialogs.getView(R.id.edit_count);
        mDialogs.getView(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新
                String s = editText.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    UIUtils.showToastSafe("请填写编辑的内容");
                } else {
                    mSunmissionOnClick.OnClickListener(mDialogs, s);
                }
            }
        });
        mDialogs.getView(R.id.img_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogs.dismiss();

            }
        });
    }


    public interface XiaodOnClick {
        void OnClickListener(BaseDialogs mDialogs);
    }

    private XiaodOnClick mXiaodOnClick;

    public void setXiaodOnClick(XiaodOnClick xiaodOnClick) {

        this.mXiaodOnClick = xiaodOnClick;
    }

    public void XiaoduiDialog() {
        //设置触摸dialog外围是否关闭
        mDialogs = mBuilder.setViewId(R.layout.dialog_proofreadbook)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.Alpah_aniamtion)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                .builder();
        mDialogs.show();
      TextView textTitle=  mDialogs.getView(R.id.text_title);
      textTitle.setText(mTitle);
        mDialogs.getView(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新
                mXiaodOnClick.OnClickListener(mDialogs);
            }
        });
        mDialogs.getView(R.id.img_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogs.dismiss();

            }
        });
    }

    public interface BookOnClick {
        void OnClickListener(BaseDialogs mDialogs);
    }

    private BookOnClick mBookOnClick;

    public void setBookOnClick(BookOnClick bookOnClick) {

        this.mBookOnClick = bookOnClick;
    }

    /**
     * 是否更新
     */
    public void showUpdateBook() {
        //设置触摸dialog外围是否关闭
        mDialogs = mBuilder.setViewId(R.layout.dialog_updatebook)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.Alpah_aniamtion)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                .builder();
        mDialogs.show();
        TextView textTime = mDialogs.getView(R.id.text_time);
        textTime.setText(mTime);
        TextView textTitle = mDialogs.getView(R.id.text_title);
        textTitle.setText(mTitle);
        Button button = mDialogs.getView(R.id.btn_update);
        button.setText(btnName);
        mDialogs.getView(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookOnClick.OnClickListener(mDialogs);
            }
        });
        mDialogs.getView(R.id.img_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogs.dismiss();
            }
        });
    }


    /**
     * 跳页
     */
    public interface JumpOnClick {
        void OnClickListener(BaseDialogs mDialogs, String jumpOnClick);
    }

    private JumpOnClick mJumpOnClick;

    public void setBookOnClick(JumpOnClick jumpOnClick) {

        this.mJumpOnClick = jumpOnClick;
    }
//
//    private void showJumpBook() {
//        //设置触摸dialog外围是否关闭
//        mDialogs = mBuilder.setViewId(R.layout.dialog_jump_page)
//                .setGravity(Gravity.CENTER)
//                .setAnimation(R.style.Alpah_aniamtion)
//                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
//                //设置触摸dialog外围是否关闭
//                .isOnTouchCanceled(true)
//                .builder();
//        mDialogs.show();
//        final EditText editTextPage = mDialogs.getView(R.id.edit_page);
//        mDialogs.getView(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mJumpOnClick.OnClickListener(mDialogs, editTextPage.getText().toString());
//            }
//        });
//        mDialogs.getView(R.id.img_pause).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialogs.dismiss();
//            }
//        });
//    }
    public static void showBookJumpPageDialog(Context context, int currentPage, int totalPage, BookJumpPageDialog.IJumpPageListener jumpPageListener){
        BookJumpPageDialog dialog = new BookJumpPageDialog(context);
        dialog.showDialog(currentPage, totalPage, jumpPageListener);
    }
}

