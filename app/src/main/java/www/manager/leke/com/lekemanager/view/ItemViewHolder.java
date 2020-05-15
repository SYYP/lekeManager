package www.manager.leke.com.lekemanager.view;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import www.manager.leke.com.lekemanager.R;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class ItemViewHolder extends ChildViewHolder {
    private TextView mTextTitle, mTextPage;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mTextTitle = itemView.findViewById(R.id.text_title);
        mTextPage = itemView.findViewById(R.id.text_page);
    }
    public void setData(String name, String textpage) {
        mTextTitle.setText(name);
        mTextPage.setText(textpage);

    }


}
