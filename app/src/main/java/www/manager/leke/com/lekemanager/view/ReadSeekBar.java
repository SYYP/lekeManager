package www.manager.leke.com.lekemanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.dialog.BookJumpPageDialog;
import www.manager.leke.com.lekemanager.dialog.DialogUtils;
import www.manager.leke.com.lekemanager.utils.LogUtils;

/**
 * 功能：教材 页码进度条
 * 作者: Cao ZH
 * 日期: 2020/3/20 0020
 * 时间: 下午 15:21
 */
public class ReadSeekBar extends RelativeLayout {

    //    private TextView tv_book_menu;
    private Context activity;
    private SeekBar mSeekBar;
    private ImageButton imgBtn_page_pre;
    private ImageButton imgBtn_page_next;
    private TextView tv_number;
    private int mTotalPage;
    private int mCurrentPage;
    private OnPageChangeListener mOnPageChangeListener;


    public ReadSeekBar(Context context) {
        super(context);
        init(context);
    }

    public ReadSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReadSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        activity = context;
        LayoutInflater.from(context).inflate(R.layout.book_seek_layout, this);
//        tv_book_menu = findViewById(R.id.tv_book_menu);
        mSeekBar = findViewById(R.id.seekbar_page);
        tv_number = findViewById(R.id.tv_number);
        imgBtn_page_pre = findViewById(R.id.imgBtn_page_pre);
        imgBtn_page_next = findViewById(R.id.imgBtn_page_next);
        imgBtn_page_pre.setOnClickListener(v -> onPrePageBtnClick());
        imgBtn_page_next.setOnClickListener(v -> onNextPageBtnClick());
        tv_number.setOnClickListener(v -> {
            if (mTotalPage<1)return;
            if (mOnPageChangeListener != null) mOnPageChangeListener.leaveWrite();
            DialogUtils.showBookJumpPageDialog(activity, mCurrentPage, mTotalPage, new BookJumpPageDialog.IJumpPageListener() {
                @Override
                public void jumpPage(int page) {
                    mCurrentPage = page;
                    if (mOnPageChangeListener != null)
                        mOnPageChangeListener.onPageChange(mCurrentPage);
                }

                @Override
                public void onCancel() {
                    if (mOnPageChangeListener != null) mOnPageChangeListener.intoWrite();
                }
            });
        });
    }

    public void setTotalPage(int totalPage,Context context) {
        activity = context;
        mTotalPage = totalPage;
        mSeekBar.setMax(totalPage - 1);
        mSeekBar.setVisibility(VISIBLE);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                int page = seekBar.getProgress();
                if (page >= 0 && page <= totalPage) {
                    mCurrentPage = page;
                    if (mOnPageChangeListener != null)
                        mOnPageChangeListener.onPageChange(mCurrentPage);
                    tv_number.setText((mCurrentPage + 1) + "/" + mTotalPage);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mOnPageChangeListener != null)
                    mOnPageChangeListener.onStartTrackingTouch(seekBar);
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_number.setText((progress + 1) + "/" + mTotalPage);
            }
        });
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
        tv_number.setText((mCurrentPage + 1) + "/" + mTotalPage);
        mSeekBar.setProgress(currentPage);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void onPrePageBtnClick() {
        LogUtils.e("imgBtn_page_pre OnClickListener mCurrentPage = " + mCurrentPage + "  mTotalPage = " + mTotalPage);
        if (mCurrentPage > 0) {
            mCurrentPage--;
            mSeekBar.setProgress(mCurrentPage);
            if (mOnPageChangeListener != null) mOnPageChangeListener.onPageChange(mCurrentPage);
        }
    }

    public void onNextPageBtnClick() {
        LogUtils.e("imgBtn_page_next OnClickListener mCurrentPage = " + mCurrentPage + "  mTotalPage = " + mTotalPage);
        if (mCurrentPage < (mTotalPage - 1)) {
            mCurrentPage++;
            mSeekBar.setProgress(mCurrentPage);
            if (mOnPageChangeListener != null) mOnPageChangeListener.onPageChange(mCurrentPage);
        }
    }

    public interface OnPageChangeListener {
        void onPageChange(int page);

        void onStartTrackingTouch(SeekBar seekBar);

        void leaveWrite();

        void intoWrite();
    }
}
