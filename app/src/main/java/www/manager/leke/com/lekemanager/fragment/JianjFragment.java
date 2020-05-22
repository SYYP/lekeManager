package www.manager.leke.com.lekemanager.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.bean.BookMessageDetail;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.UIUtils;
import www.manager.leke.com.lekemanager.view.AlignTextView;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class JianjFragment extends BaseFragment {
    @BindView(R.id.text_introduction)
    AlignTextView textIntroduction;
    BookMessageDetail mBookMessageDetail;

    @Override
    public void setChildTag() {
    }
    @Override
    protected View getXmlView() {
        return UIUtils.inflate(R.layout.fragment_jianj);
    }

    @Override
    protected void initXmlData() {
        Bundle arguments = getArguments();
        mBookMessageDetail = (BookMessageDetail) arguments.getSerializable(Contacts.BOOKDETAIL);
    }

    @Override
    protected void loadData() {
        if(textIntroduction!=null) {
            //加载数据
            textIntroduction.setText(TextUtils.isEmpty(mBookMessageDetail.getBookExtraInfo().getBookSummary()) ? "" : Html.fromHtml(mBookMessageDetail.getBookExtraInfo().getBookSummary()));
        }
    }

}
