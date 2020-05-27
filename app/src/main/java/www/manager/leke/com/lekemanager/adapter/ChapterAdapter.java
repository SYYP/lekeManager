package www.manager.leke.com.lekemanager.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.bean.ChapterInfo;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * 功能：目录
 * 作者: YUAN_YE
 * 日期: 2019/6/26
 * 时间: 22:42
 */
public class ChapterAdapter extends BaseAdapter {
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_CHILD = 1;
    private List<ChapterInfo> mChapterInfos;

    public ChapterAdapter(List<ChapterInfo> chapterInfos) {
        super();
        this.mChapterInfos = chapterInfos;
    }

    public void setInfos(List<ChapterInfo> chapterInfos) {
        this.mChapterInfos = chapterInfos;
    }

    @Override
    public int getCount() {
        return mChapterInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mChapterInfos.get(position).isHead()) {
            return TYPE_HEAD;
        } else {
            return TYPE_CHILD;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtils.e("袁野 ..getView ps:  "+position);
        HeaderViewHolder headHolder;
        ChildViewHolder childHolder;
        switch (getItemViewType(position)) {
            case TYPE_HEAD:
                if (convertView == null) {
                    convertView = UIUtils.inflate(R.layout.adapter_chapter_head);
                    headHolder = new HeaderViewHolder();
                    headHolder.headTitle = (TextView) convertView.findViewById(R.id.tv_header_title);
                    headHolder.headPage = (TextView) convertView.findViewById(R.id.tv_header_page);
                    convertView.setTag(headHolder);
                } else {
                    headHolder = (HeaderViewHolder) convertView.getTag();
                }

                headHolder.headPage.setText((Integer.parseInt(mChapterInfos.get(position).getPosition())+1) + "页");
                headHolder.headTitle.setText(mChapterInfos.get(position).getTitle());
                break;
            case TYPE_CHILD:
                if (convertView == null) {
                    convertView = UIUtils.inflate(R.layout.adapter_chapter_child);
                    childHolder = new ChildViewHolder();
                    childHolder.childTitle = (TextView) convertView.findViewById(R.id.tv_child_title);
                    childHolder.childPage = (TextView) convertView.findViewById(R.id.tv_child_page);
                    convertView.setTag(childHolder);
                } else {
                    childHolder = (ChildViewHolder) convertView.getTag();
                }


                childHolder.childPage.setText((Integer.parseInt(mChapterInfos.get(position).getPosition())+1) + "页");
                childHolder.childTitle.setText(mChapterInfos.get(position).getTitle());

                break;
        }
        convertView.setBackgroundResource(mChapterInfos.get(position).isSelect()?R.color.color_cc:R.color.white);
        return convertView;
    }

    class HeaderViewHolder {
        TextView headTitle;
        TextView headPage;
    }

    class ChildViewHolder {
        TextView childTitle;
        TextView childPage;
    }
}
