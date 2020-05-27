package www.manager.leke.com.lekemanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.adapter.ChapterAdapter;
import www.manager.leke.com.lekemanager.bean.ChapterInfo;
import www.manager.leke.com.lekemanager.utils.LogUtils;

/**
 * 功能：教材的 章节、书签
 * 作者: Cao ZH
 * 日期: 2020/3/20 0020
 * 时间: 下午 15:21
 */
public class BookMenuView extends RelativeLayout {

    private FrameLayout fl_menu_left;
    private FrameLayout fm_chapter;
    private FrameLayout fm_book_mark;
    private TextView tv_chapter;
    private TextView tv_book_mark;
    private View driver_chapter;
    private View driver_book_mark;
    private ListView lv_chapter;
    private ListView lv_book_mark;

    private ChapterAdapter mChapterAdapter;
    private List<ChapterInfo> mChapterInfos = new ArrayList<>();

    private OnMenuChangeListener mOnMenuChangeListener;

    public BookMenuView(Context context) {
        super(context);
        init(context);
    }

    public BookMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BookMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.menu_left, this);
        fl_menu_left = findViewById(R.id.fl_menu_left);
        fm_chapter = findViewById(R.id.fm_chapter);
        fm_book_mark = findViewById(R.id.fm_book_mark);
        tv_chapter = findViewById(R.id.tv_chapter);
        tv_book_mark = findViewById(R.id.tv_book_mark);
        driver_chapter = findViewById(R.id.driver_chapter);
        driver_book_mark = findViewById(R.id.driver_book_mark);
        lv_chapter = findViewById(R.id.lv_chapter);
        lv_book_mark = findViewById(R.id.lv_book_mark);
        initListViewAdapter();
        initListener();

    }

    private void initListener() {
        fl_menu_left.setOnClickListener(v -> {
            setVisibility(GONE);
            if (mOnMenuChangeListener!=null)mOnMenuChangeListener.intoWrite();
        });
        fm_chapter.setOnClickListener(v -> {
            tv_chapter.setTextColor(getResources().getColor(R.color.black));
            tv_book_mark.setTextColor(getResources().getColor(R.color.gray));
            driver_chapter.setVisibility(VISIBLE);
            driver_book_mark.setVisibility(INVISIBLE);
            lv_chapter.setVisibility(VISIBLE);
            lv_book_mark.setVisibility(GONE);
        });
        fm_book_mark.setOnClickListener(v -> {
            tv_chapter.setTextColor(getResources().getColor(R.color.gray));
            tv_book_mark.setTextColor(getResources().getColor(R.color.black));
            driver_chapter.setVisibility(INVISIBLE);
            driver_book_mark.setVisibility(VISIBLE);
            lv_chapter.setVisibility(GONE);
            lv_book_mark.setVisibility(VISIBLE);
        });
    }

    private void initListViewAdapter() {

        mChapterAdapter = new ChapterAdapter(mChapterInfos);
        lv_chapter.setAdapter(mChapterAdapter);
        lv_chapter.setDividerHeight(0);
        lv_chapter.setOnItemClickListener((parent, view, position, id) -> {
            int page = Integer.parseInt(mChapterInfos.get(position).getPosition());
            LogUtils.e("BookMenuView", "点击item页码：" + page);
            setVisibility(GONE);
            if (mOnMenuChangeListener != null) mOnMenuChangeListener.onPageChange(page);
        });

    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public void showChapter(int page){
        setVisibility(VISIBLE);
        fm_chapter.callOnClick();
        int position = -1;
        for (int i = 0; i < mChapterInfos.size(); i++) {
            int chapterPage =Integer.parseInt(mChapterInfos.get(i).getPosition());
            if (page>=chapterPage){
                position = i;
            }else {
                break;
            }
        }
        for (int i = 0; i < mChapterInfos.size(); i++) {
            mChapterInfos.get(i).setSelect(i == position);
        }
        mChapterAdapter.notifyDataSetChanged();
    }

    public void setChapterData(final List<ChapterInfo> chapterInfos) {
        mChapterInfos = chapterInfos;
        mChapterAdapter.setInfos(chapterInfos);
        mChapterAdapter.notifyDataSetChanged();
    }


    public void setOnMenuChangeListener(OnMenuChangeListener onMenuChangeListener) {
        mOnMenuChangeListener = onMenuChangeListener;
    }

    public interface OnMenuChangeListener {
        void onPageChange(int page);
        void intoWrite();
        void onItemDeleteClick(int page);
    }
}
