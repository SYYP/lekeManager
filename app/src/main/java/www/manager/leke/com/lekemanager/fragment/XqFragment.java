package www.manager.leke.com.lekemanager.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.bean.BookMessageDetail;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/7 0007
 * 图书详情页面
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
    private BookMessageDetail mBookMessageDetail;

    @Override
    public void setChildTag() {

    }

    @Override
    protected View getXmlView() {
        return UIUtils.inflate(R.layout.fragment_xq);
    }

    @Override
    protected void initXmlData() {
        Bundle arguments = getArguments();
        mBookMessageDetail = (BookMessageDetail) arguments.getSerializable(Contacts.BOOKDETAIL);

    }

    @Override
    protected void loadData() {
        //添加数据
        if (mBookMessageDetail != null) {
            //书名
            textBooktitle.setText((TextUtils.isEmpty(mBookMessageDetail.getBookTitle()) ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getBookTitle()));
            //学科
            textSubject.setText(TextUtils.isEmpty(mBookMessageDetail.getBookSubjectName()) ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getBookSubjectName());
            //图书类型
            textBookType.setText(TextUtils.isEmpty(mBookMessageDetail.getBookTypeName()) ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getBookTypeName());
            //年级
            textGrade.setText(TextUtils.isEmpty(mBookMessageDetail.getFitGradeL())?mBookMessageDetail.getFitGradeU():mBookMessageDetail.getFitGradeL()+mBookMessageDetail.getBookExtraInfo().getBookVolName());
            //教材版本
            textProject.setText(TextUtils.isEmpty(mBookMessageDetail.getPublishVerName()) ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getPublishVerName());
            //出版社
            textCyan.setText(TextUtils.isEmpty(mBookMessageDetail.getBookExtraInfo().getBookPublisherName()) ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getBookExtraInfo().getBookPublisherName());
            //图书编号
            textNumber.setText(mBookMessageDetail.getBookId()+"");
            //ISBN
            textIsbn.setText(TextUtils.isEmpty(mBookMessageDetail.getBookExtraInfo().getBookIsbn()) ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getBookExtraInfo().getBookIsbn());
            //关键字
            textKeyword.setText(TextUtils.isEmpty(mBookMessageDetail.getBookExtraInfo().getBookKeyword()) ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getBookExtraInfo().getBookKeyword());
            //次数
            textYins.setText((mBookMessageDetail.getBookExtraInfo().getBookReprintNum() == 0 ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getBookExtraInfo().getBookReprintNum() + ""));
            //版次
            textBanc.setText((mBookMessageDetail.getBookExtraInfo().getBookEditionNum() == 0 ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getBookExtraInfo().getBookEditionNum() + ""));
            //原价
            textYuanj.setText((mBookMessageDetail.getBookExtraInfo().getBookOriginPrice() == 0.0 ? getResources().getString(R.string.string_no_message) : mBookMessageDetail.getBookExtraInfo().getBookOriginPrice() + ""));
        }
    }

}
