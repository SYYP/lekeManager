package www.manager.leke.com.lekemanager.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class XqFragment extends BaseFragment {
    @BindView(R.id.text_booktitle)
    TextView textBooktitle;
    @BindView(R.id.text_subject)
    TextView textSubject;
    @BindView(R.id.text_book_type)
    TextView textBookType;
    @BindView(R.id.text_grade)
    TextView textGrade;
    @BindView(R.id.text_project)
    TextView textProject;
    @BindView(R.id.text_cyan)
    TextView textCyan;
    @BindView(R.id.text_number)
    TextView textNumber;
    @BindView(R.id.text_isbn)
    TextView textIsbn;
    @BindView(R.id.text_keyword)
    TextView textKeyword;
    @BindView(R.id.text_yins)
    TextView textYins;
    @BindView(R.id.text_banc)
    TextView textBanc;
    @BindView(R.id.text_yuanj)
    TextView textYuanj;
    @Override
    public void setChildTag() {

    }

    @Override
    protected View getXmlView() {
        return UIUtils.inflate(R.layout.fragment_xq);
    }

    @Override
    protected void initXmlData() {

    }

    @Override
    protected void loadData() {
    }

}
